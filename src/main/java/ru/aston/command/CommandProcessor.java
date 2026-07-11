package ru.aston.command;


import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants;
import ru.aston.core.TranslationManager;

import java.util.List;

/// \todo translate all strings
public class CommandProcessor {
	private final CommandLineParser parser = new DefaultParser();
	private final HelpFormatter formatter = new HelpFormatter();
	private final Options options = new Options();
	private final CascadedExecutor executor = new CascadedExecutor();
	private static final String HELP_SYNTAX = "--help";

	public CommandProcessor() {
		for (CommandStep step : CommandStep.values()) {
			options.addOption(Option.builder(step.shortOpt)
					.longOpt(step.longOpt)
					.hasArg(step.hasArg)
					.desc(getDescription(step))
					.build());
		}
		executor.addExecutor(new ModelSelectionExecutor()).addExecutor(new ModelGenerationExecutor())
				.addExecutor(new ModelDisplayExecutor());
	}

	public void executeCommands(String[] commands, ExecutionData executionData) {
		try {
			CommandLine cmd = parser.parse(options, commands);
			if (cmd.hasOption(CommandStep.EXIT.shortOpt)) {
				executionData.isExitRequested = true;
				return;
			}
			if (cmd.hasOption(CommandStep.HELP.shortOpt)) {
				formatter.printHelp(HELP_SYNTAX, options);
				return;
			}
			if (!executor.checkOptions(cmd)) {
				System.out.println("Ошибка валидации: " + executor.getLastError());
				formatter.printHelp(HELP_SYNTAX, options);
				return;
			}
			executor.execute(cmd, executionData);
		}
		catch (ParseException e) {
			System.out.println("Ошибка парсинга: " + e.getMessage());
			formatter.printHelp(HELP_SYNTAX, options);
		}
	}

	// @formatter:off
	private String getDescription(@NotNull CommandStep step) {
		return switch (step) {
			case EXIT -> TranslationManager.getExitOptionDescription();
			case HELP -> TranslationManager.getHelpText("1.0", "TeamName");
			case MODEL -> TranslationManager.getModelOptionDescription(getAcceptableOptionsString(step));
			case RESET -> TranslationManager.getResetOptionDescription(
					getRequiredStepsString(step)
			);
			case LENGTH -> TranslationManager.getLengthOptionDescription(
					getRequiredStepsString(step)
			);
			case DISPLAY -> TranslationManager.getDisplayOptionDescription(
					getRequiredStepsString(step)
			);
			case SORT -> TranslationManager.getSortOptionDescription(
					AppConstants.SortType.ASC.getValue(),
					AppConstants.SortType.DESC.getValue(),
					AppConstants.SortType.SPECIAL.getValue(),
					getRequiredStepsString(step)
			);
			case CREATE -> TranslationManager.getCreateOptionDescription(
					getRequiredStepsString(step)
			);
			case EXPORT -> TranslationManager.getExportOptionDescription(
					getRequiredStepsString(step)
			);
			case IMPORT -> TranslationManager.getImportOptionDescription(
					getRequiredStepsString(step)
			);
			case WRITE_MODE -> TranslationManager.getWriteModeOptionDescription(
					AppConstants.WriteMode.APPEND.getValue(),
					AppConstants.WriteMode.OVERWRITE.getValue()
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
			return "-";
		}

		return String.join(" | ", step.requiredSteps.stream().map(s -> s.longOpt).toList());
	}

	enum CommandStep {
		EXIT("Q", "exit", false),
		HELP("H", "help", false),
		MODEL("M", "model", true),
		RESET("R", "reset", false),
		LENGTH("L", "length", false, List.of(MODEL)),
		DISPLAY("D", "display", false, List.of(MODEL)),
		SORT("S", "sort", true, List.of(MODEL)),
		CREATE("C", "create", true, List.of(MODEL)),
		EXPORT("E", "export", true, List.of(MODEL)),
		IMPORT("I", "import", true, List.of(MODEL)),
		WRITE_MODE("W", "write-mode", true);

		static {
			MODEL.acceptableVariants = List
					.of(AppConstants.ModelType.CARS.name(), AppConstants.ModelType.STUDENTS.name(), AppConstants.ModelType.BARRELS.name());
		}

		public final String shortOpt;
		public final String longOpt;
		public final boolean hasArg;
		public List<String> acceptableVariants;
		public List<CommandStep> requiredSteps;

		CommandStep(String shortOpt, String longOpt, boolean hasArg, List<CommandStep> requiredSteps) {
			this(shortOpt, longOpt, hasArg);
			this.requiredSteps = requiredSteps;
		}

		CommandStep(String shortOpt, String longOpt, boolean hasArg) {
			this.shortOpt = shortOpt;
			this.longOpt = longOpt;
			this.hasArg = hasArg;
			this.acceptableVariants = List.of();
			this.requiredSteps = List.of();
		}
	}
}
