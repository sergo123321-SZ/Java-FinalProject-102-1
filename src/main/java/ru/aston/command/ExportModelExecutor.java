package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import ru.aston.core.AppConstants.WriteMode;
import ru.aston.core.TranslationManager;
import ru.aston.jsonrw.writers.BarrelJsonWriter;
import ru.aston.jsonrw.writers.CarJsonWriter;
import ru.aston.jsonrw.writers.StudentJsonWriter;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
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
				throw new IllegalArgumentException(TranslationManager.getUnknownModelTypeError());
		}
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		String optionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.EXPORT.shortOpt);
		if (optionValue == null || optionValue.isBlank()) {
			lastError = TranslationManager.getExportOptionValueRequiredError();
			return false;
		}

		Path path;
		try {
			path = Path.of(optionValue).toAbsolutePath().normalize();
		}
		catch (InvalidPathException e) {
			lastError = TranslationManager.getExportOptionInvalidPathError();
			return false;
		}

		if (Files.exists(path)) {
			if (!Files.isRegularFile(path) || !Files.isWritable(path)) {
				lastError = TranslationManager.getExportOptionInvalidPathError();
				return false;
			}
			return true;
		}

		Path parent = path.getParent();
		if (parent == null || !Files.exists(parent) || !Files.isDirectory(parent) || !Files.isWritable(parent)) {
			lastError = TranslationManager.getExportOptionInvalidPathError();
			return false;
		}

		return true;
	}
}
