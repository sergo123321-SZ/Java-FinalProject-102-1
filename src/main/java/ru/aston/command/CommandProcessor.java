package ru.aston.command;


import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants;
import ru.aston.core.TranslationManager;

import java.util.List;
import java.util.Locale;

public class CommandProcessor {
	private final CommandLineParser parser = new DefaultParser();
	private final HelpFormatter formatter = new HelpFormatter();
	private final CascadedExecutor executor = new CascadedExecutor();
	private static final String HELP_SYNTAX = "--help";
	private Options options;

	public CommandProcessor() {
		initializeOptions();
		executor.addExecutor(new ModelSelectionExecutor())
				.addExecutor(new ModelGenerationExecutor())
				.addExecutor(new ModelDisplayExecutor())
				.addExecutor(new ModelLengthExecutor())
				.addExecutor(new ModelResetExecutor())
				.addExecutor(new WriteModeSelectExecutor())
				.addExecutor(new ModelSortExecutor())
				.addExecutor(new ExportModelExecutor())
				.addExecutor(new ImportModelExecutor());
	}

	public void executeCommands(String[] commands, ExecutionData executionData) {
		try {
			executionData.modelType = null;
			CommandLine cmd = parser.parse(options, commands);
			if (cmd.hasOption(CommandStep.EXIT.shortOpt)) {
				executionData.isExitRequested = true;
				return;
			}
			if (cmd.hasOption(CommandStep.HELP.shortOpt)) {
				formatter.printHelp(HELP_SYNTAX, options);
				return;
			}
			if (cmd.hasOption(CommandStep.LANGUAGE.longOpt)) {
				String languageId = cmd.getOptionValue(CommandStep.LANGUAGE.longOpt).toLowerCase();
				TranslationManager.setLocale(switch (languageId) {
					case "en" -> Locale.ENGLISH;
					case "ru" -> Locale.of("ru");
					default -> Locale.getDefault();
				});
				initializeOptions();
			}
			if (!executor.checkOptions(cmd)) {
				System.out.println(TranslationManager.getValidationError(executor.getLastError()));
				formatter.printHelp(HELP_SYNTAX, options);
				return;
			}
			executor.execute(cmd, executionData);
		}
		catch (ParseException e) {
			System.out.println(TranslationManager.getParsingError(e.getMessage()));
			formatter.printHelp(HELP_SYNTAX, options);
		}
	}

	// @formatter:off
	private String getDescription(@NotNull CommandStep step) {
		return switch (step) {
			case EXIT -> TranslationManager.getExitOptionDescription();
			case HELP -> TranslationManager.getHelpText();
			case LANGUAGE -> TranslationManager.getLanguageOptionDescription();
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
			default -> throw new IllegalArgumentException(TranslationManager.getUnknownCommandStepError(step.name()));
		};
	}
	// @formatter:on

	private void initializeOptions() {
		options = new Options();
		for (CommandStep step : CommandStep.values()) {
			options.addOption(Option.builder(step.shortOpt)
					.longOpt(step.longOpt)
					.hasArg(step.hasArg)
					.desc(getDescription(step))
					.build());
		}
	}

	private @NotNull String getAcceptableOptionsString(@NotNull CommandStep step) {
		if (step.acceptableVariants.isEmpty()) {
			throw new IllegalArgumentException(TranslationManager.getNoAcceptableOptionsError(step.name()));
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
		LANGUAGE("lang", "lang", true),
		MODEL("M", "model", true),
		RESET("R", "reset", false, List.of(MODEL)),
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
