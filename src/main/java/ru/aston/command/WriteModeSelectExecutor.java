package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants;
import ru.aston.core.TranslationManager;

import java.util.List;

public class WriteModeSelectExecutor extends BaseExecutor {
	public WriteModeSelectExecutor() {
		super(List.of(CommandProcessor.CommandStep.WRITE_MODE));
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		String selectedWriteMode = commandLine.getOptionValue(CommandProcessor.CommandStep.WRITE_MODE.shortOpt);
		if (selectedWriteMode == null || selectedWriteMode.isBlank()) {
			lastError = TranslationManager.getWriteModeUnsupportedError();
			return false;
		}

		try {
			parseWriteMode(selectedWriteMode);
		}
		catch (IllegalArgumentException e) {
			lastError = TranslationManager.getWriteModeUnsupportedError();
			return false;
		}

		return true;
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		String selectedWriteMode = options.getOptionValue(CommandProcessor.CommandStep.WRITE_MODE.shortOpt);
		executionData.writeMode = parseWriteMode(selectedWriteMode);
	}

	private @NotNull AppConstants.WriteMode parseWriteMode(@NotNull String writeModeId) {
		String normalized = writeModeId.trim().toUpperCase();
		normalized = switch (normalized) {
			case "A" -> AppConstants.WriteMode.APPEND.name();
			case "W" -> AppConstants.WriteMode.OVERWRITE.name();
			default -> normalized;
		};

		return AppConstants.WriteMode.valueOf(normalized);
	}

}
