package ru.aston.core;


import org.apache.commons.cli.*;


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
		SELECT("M", "model", true, TranslationManager.getModelOptionDescriptionString()),
		SORT("S", "sort", true, TranslationManager.getSortOptionDescriptionString()),
		EXPORT("e", "export", true, TranslationManager.getExportOptionDescriptionString());

		final String shortOpt;
		final String longOpt;
		final boolean hasArg;
		final String description;

		CommandStep(String shortOpt, String longOpt, boolean hasArg, String description) {
			this.shortOpt = shortOpt;
			this.longOpt = longOpt;
			this.hasArg = hasArg;
			this.description = description;
		}
	}
}
