package ru.aston.core;


import org.apache.commons.cli.*;
import java.util.List;


public class CommandProcessor {
	private final CommandLineParser parser = new DefaultParser();
	private final HelpFormatter formatter = new HelpFormatter();
	private final Options options = new Options();

	public CommandProcessor() {
		for (CommandStep step : CommandStep.values()) {
			options.addOption(Option.builder(step.shortOpt)
					.longOpt(step.longOpt)
					.hasArg(step.hasArg)
					.desc(step.description)
					.build());
		}
	}

	public void executeCommands(String[] commands) {
		try {
			CommandLine cmd = parser.parse(options, commands);

		}
		catch (ParseException e) {
			System.out.println("Ошибка парсинга: " + e.getMessage());
			formatter.printHelp("", options);
		}
	}

	private enum CommandStep {
		EXIT("E", "exit", false, TranslationManager.getExitOptionDescription()),
		MODEL("M", "model", true, TranslationManager.getModelOptionDescription()),
		DISPLAY("D", "display", false, TranslationManager.getDisplayOptionDescription()),
		SORT("S", "sort", true, TranslationManager.getSortOptionDescription(), List.of(MODEL)),
		CREATE("C", "create", true, TranslationManager.getCreateOptionDescription(), List.of(MODEL)),
		FILE("F", "file", true, TranslationManager.getFileOptionDescription(), List.of(MODEL)),
		EXPORT("E", "export", true, TranslationManager.getExportOptionDescription(), List.of(FILE, MODEL)),
		IMPORT("I", "import", true, TranslationManager.getImportOptionDescription(), List.of(FILE, MODEL));

		static {
			EXPORT.conflictingSteps = List.of(IMPORT, CREATE);
			IMPORT.conflictingSteps = List.of(EXPORT, CREATE);
			CREATE.conflictingSteps = List.of(IMPORT, EXPORT);
		}

		final String shortOpt;
		final String longOpt;
		final boolean hasArg;
		final String description;
		List<CommandStep> requiredSteps;
		List<CommandStep> conflictingSteps;

		CommandStep(String shortOpt, String longOpt, boolean hasArg, String description) {
			this.shortOpt = shortOpt;
			this.longOpt = longOpt;
			this.hasArg = hasArg;
			this.description = description;
			this.requiredSteps = List.of();
			this.conflictingSteps = List.of();
		}

		CommandStep(String shortOpt, String longOpt, boolean hasArg, String description, List<CommandStep> requiredSteps) {
			this(shortOpt, longOpt, hasArg, description);
			this.requiredSteps = requiredSteps;
			this.conflictingSteps = List.of();
		}
	}
}
