package ru.aston.command;


import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants;
import ru.aston.core.TranslationManager;

import java.util.List;


public class CommandProcessor {
	private final CommandLineParser parser = new DefaultParser();
	private final HelpFormatter formatter = new HelpFormatter();
	private final Options options = new Options();
	private final CascadedExecutor executor = new CascadedExecutor();

	public CommandProcessor() {
		for (CommandStep step : CommandStep.values()) {
			options.addOption(Option.builder(step.shortOpt)
					.longOpt(step.longOpt)
					.hasArg(step.hasArg)
					.desc(getDescription(step))
					.build());
		}
		/// \todo add executor.addExecutor();
	}

	public void executeCommands(String[] commands) {
		try {
			CommandLine cmd = parser.parse(options, commands);
			executor.execute(cmd);
		}
		catch (ParseException e) {
			System.out.println("Ошибка парсинга: " + e.getMessage());
			formatter.printHelp("", options);
		}
	}

	// @formatter:off
	private String getDescription(@NotNull CommandStep step) {
		return switch (step) {
			case EXIT -> TranslationManager.getExitOptionDescription();
			case HELP -> TranslationManager.getHelpText("1.0", "TeamName");
			case MODEL -> TranslationManager.getModelOptionDescription(getAcceptableOptionsString(step));
			case LENGTH -> TranslationManager.getLengthOptionDescription(
					getRequiredStepsString(step),
					getConflictingStepsString(step)
			);
			case DISPLAY -> TranslationManager.getDisplayOptionDescription(
					getRequiredStepsString(step),
					getConflictingStepsString(step)
			);
			case SORT -> TranslationManager.getSortOptionDescription(
					AppConstants.SortType.ASC.getValue(),
					AppConstants.SortType.DESC.getValue(),
					AppConstants.SortType.SPECIAL.getValue(),
					getRequiredStepsString(step),
					getConflictingStepsString(step)
			);
			case CREATE -> TranslationManager.getCreateOptionDescription(
					getRequiredStepsString(step),
					getConflictingStepsString(step)
			);
			case FILE -> TranslationManager.getFileOptionDescription(
					getRequiredStepsString(step),
					getConflictingStepsString(step)
			);
			case EXPORT -> TranslationManager.getExportOptionDescription(
					AppConstants.WriteType.APPEND.getValue(),
					AppConstants.WriteType.OVERWRITE.getValue(),
					getRequiredStepsString(step),
					getConflictingStepsString(step)
			);
			case IMPORT -> TranslationManager.getImportOptionDescription(
					AppConstants.WriteType.APPEND.getValue(),
					AppConstants.WriteType.OVERWRITE.getValue(),
					getRequiredStepsString(step),
					getConflictingStepsString(step)
			);
			default -> throw new IllegalArgumentException("Unknown command step: " + step);
		};
	}
	// @formatter:on

	private @NotNull String getAcceptableOptionsString(@NotNull CommandStep step) {
		if (step.acceptableVariants.isEmpty()) {
			throw new IllegalArgumentException("No acceptable options for step: " + step);
		}

		return String.join(" | ", step.acceptableVariants);
	}

	private @NotNull String getRequiredStepsString(@NotNull CommandStep step) {
		if (step.requiredSteps.isEmpty()) {
			throw new IllegalArgumentException("No required steps for step: " + step);
		}

		return String.join(" | ", step.requiredSteps.stream().map(s -> s.longOpt).toList());
	}

	private @NotNull String getConflictingStepsString(@NotNull CommandStep step) {
		if (step.conflictingSteps.isEmpty()) {
			throw new IllegalArgumentException("No conflicting steps for step: " + step);
		}

		return String.join(" | ", step.conflictingSteps.stream().map(s -> s.longOpt).toList());
	}

	enum CommandStep {
		EXIT("E", "exit", false),
		HELP("H", "help", false),
		MODEL("M", "model", true),
		LENGTH("L", "length", false, List.of(MODEL)),
		DISPLAY("D", "display", false, List.of(MODEL)),
		SORT("S", "sort", true, List.of(MODEL)),
		CREATE("C", "create", true, List.of(MODEL)),
		FILE("F", "file", true, List.of(MODEL)),
		EXPORT("E", "export", true, List.of(FILE, MODEL)),
		IMPORT("I", "import", true, List.of(FILE, MODEL));

		static {
			LENGTH.conflictingSteps = List.of(DISPLAY);
			DISPLAY.conflictingSteps = List.of(LENGTH);
			EXPORT.conflictingSteps = List.of(IMPORT, CREATE);
			IMPORT.conflictingSteps = List.of(EXPORT, CREATE);
			CREATE.conflictingSteps = List.of(IMPORT, EXPORT);
		}

		public final String shortOpt;
		public final String longOpt;
		public final boolean hasArg;
		public final List<String> acceptableVariants;
		public List<CommandStep> requiredSteps;
		public List<CommandStep> conflictingSteps;

		CommandStep(String shortOpt, String longOpt, boolean hasArg, List<CommandStep> requiredSteps) {
			this(shortOpt, longOpt, hasArg);
			this.requiredSteps = requiredSteps;
			this.conflictingSteps = List.of();
		}

		CommandStep(String shortOpt, String longOpt, boolean hasArg) {
			this.shortOpt = shortOpt;
			this.longOpt = longOpt;
			this.hasArg = hasArg;
			this.acceptableVariants = List.of();
			this.requiredSteps = List.of();
			this.conflictingSteps = List.of();
		}
	}
}
