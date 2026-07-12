package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class ImportModelExecutor extends BaseExecutor {

	public ImportModelExecutor() {
		super(List.of(CommandProcessor.CommandStep.IMPORT));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {

	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		String optionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.IMPORT.shortOpt);
		if (optionValue == null) {
			throw new IllegalArgumentException("Import option value is required");
		}

		if (!Files.exists(Path.of(optionValue)) || !Files.isRegularFile(Path.of(optionValue))) {
			throw new IllegalArgumentException("Import option must be a valid file path");
		}

		return true;
	}

}
