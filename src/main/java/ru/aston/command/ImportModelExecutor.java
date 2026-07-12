package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import ru.aston.core.AppConstants.WriteMode;
import ru.aston.jsonrw.readers.BarrelJsonReader;
import ru.aston.jsonrw.readers.CarJsonReader;
import ru.aston.jsonrw.readers.StudentJsonReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class ImportModelExecutor extends BaseExecutor {

	public ImportModelExecutor() {
		super(List.of(CommandProcessor.CommandStep.IMPORT));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		String filePath = Path.of(options.getOptionValue(CommandProcessor.CommandStep.IMPORT.shortOpt)).toAbsolutePath().toString();
		boolean doAppend = executionData.writeMode == null || executionData.writeMode == WriteMode.APPEND;

		switch (executionData.modelType) {
			case BARRELS:
				assert executionData.barrelCollection != null;
				new BarrelJsonReader().readBarrelsFromFile(executionData.barrelCollection, filePath, doAppend);
				break;
			case CARS:
				assert executionData.carCollection != null;
				new CarJsonReader().readCarsFromFile(executionData.carCollection, filePath, doAppend);
				break;
			case STUDENTS:
				assert executionData.studentCollection != null;
				new StudentJsonReader().readStudentsFromFile(executionData.studentCollection, filePath, doAppend);
				break;
			default:
				throw new IllegalArgumentException("'modelType' is unknown. MUST be assigned before execution!");
		}

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
