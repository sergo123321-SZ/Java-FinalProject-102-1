package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import ru.aston.core.AppConstants;

import java.util.List;

public class WriteModeSelectExecutor extends BaseExecutor {
	public WriteModeSelectExecutor() {
		super(List.of(CommandProcessor.CommandStep.WRITE_MODE));
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		String selectedModelId = commandLine.getOptionValue(CommandProcessor.CommandStep.WRITE_MODE.shortOpt);
		if (selectedModelId == null) {
			throw new IllegalStateException("model id is required");
		}

		try {
			AppConstants.WriteMode.valueOf(selectedModelId.trim().toUpperCase());
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
		String selectedModelId = options.getOptionValue(CommandProcessor.CommandStep.WRITE_MODE.shortOpt);
		executionData.writeMode = AppConstants.WriteMode.valueOf(selectedModelId.trim().toUpperCase());
	}

}
