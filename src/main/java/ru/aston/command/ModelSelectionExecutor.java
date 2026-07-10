package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants;
import java.util.List;

public class ModelSelectionExecutor extends BaseExecutor {
	public ModelSelectionExecutor() {
		super(List.of(CommandProcessor.CommandStep.MODEL));
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		if (!super.checkOptions(commandLine)) {
			return false;
		}
		String selectedModelId = commandLine.getOptionValue(CommandProcessor.CommandStep.MODEL.shortOpt);
		if (selectedModelId == null) {
			throw new IllegalStateException("model id is required");
		}

		try {
			AppConstants.ModelType.valueOf(selectedModelId);
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
		executionData.modelType = AppConstants.ModelType.valueOf(selectedModelId);
	}

}
