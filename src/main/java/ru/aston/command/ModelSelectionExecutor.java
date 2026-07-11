package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants;

import java.util.Locale;
import java.util.List;

public class ModelSelectionExecutor extends BaseExecutor {
	public ModelSelectionExecutor() {
		super(List.of(CommandProcessor.CommandStep.MODEL));
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		String selectedModelId = commandLine.getOptionValue(CommandProcessor.CommandStep.MODEL.shortOpt);
		if (selectedModelId == null) {
			throw new IllegalStateException("model id is required");
		}

		try {
			parseModelType(selectedModelId);
		}
		catch (IllegalArgumentException e) {
			/// \todo use the correct string
			lastError = e.getMessage();
			return false;
		}

		return true;
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		String selectedModelId = options.getOptionValue(CommandProcessor.CommandStep.MODEL.shortOpt);
		executionData.modelType = parseModelType(selectedModelId);
	}

	private @NotNull AppConstants.ModelType parseModelType(@NotNull String selectedModelId) {
		String normalized = selectedModelId.trim().toUpperCase();
		normalized = switch (normalized) {
			case "CAR" -> AppConstants.ModelType.CARS.name();
			case "STUDENT" -> AppConstants.ModelType.STUDENTS.name();
			case "BARREL" -> AppConstants.ModelType.BARRELS.name();
			default -> normalized;
		};

		return AppConstants.ModelType.valueOf(selectedModelId);
	}

}
