package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import ru.aston.core.AppConstants.WriteMode;
import ru.aston.jsonrw.writers.BarrelJsonWriter;
import ru.aston.jsonrw.writers.CarJsonWriter;
import ru.aston.jsonrw.writers.StudentJsonWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class ExportModelExecutor extends BaseExecutor {
	public ExportModelExecutor() {
		super(List.of(CommandProcessor.CommandStep.EXPORT));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		String filePath = Path.of(options.getOptionValue(CommandProcessor.CommandStep.EXPORT.shortOpt)).toAbsolutePath().toString();
		boolean doAppend = executionData.writeMode == null || executionData.writeMode == WriteMode.APPEND;
		switch (executionData.modelType) {
			case BARRELS:
				assert executionData.barrelCollection != null;
				new BarrelJsonWriter().writeBarrelsToFile(executionData.barrelCollection, filePath, doAppend);
				break;
			case CARS:
				assert executionData.carCollection != null;
				new CarJsonWriter().writeCarsToFile(executionData.carCollection, filePath, doAppend);
				break;
			case STUDENTS:
				assert executionData.studentCollection != null;
				new StudentJsonWriter().writeStudentsToFile(executionData.studentCollection, filePath, doAppend);
				break;
			default:
				throw new IllegalArgumentException("'modelType' is unknown. MUST be assigned before execution!");
		}
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		String optionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.EXPORT.shortOpt);
		if (optionValue == null) {
			lastError = "Export option value is required";
			return false;
		}

		if (!Files.exists(Path.of(optionValue)) || !Files.isRegularFile(Path.of(optionValue))) {
			lastError = "Export option must be a valid file path";
			return false;
		}

		return true;
	}
}
