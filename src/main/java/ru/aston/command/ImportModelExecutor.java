package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import ru.aston.core.AppConstants.WriteMode;
import ru.aston.core.TranslationManager;
import ru.aston.jsonrw.readers.BarrelJsonReader;
import ru.aston.jsonrw.readers.CarJsonReader;
import ru.aston.jsonrw.readers.StudentJsonReader;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
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
				if (doAppend) {
					executionData.barrelCollection = ensureCollection(executionData.barrelCollection);
					new BarrelJsonReader().readBarrelsFromFile(executionData.barrelCollection, filePath, true);
				}
				else {
					executionData.barrelCollection = new BarrelJsonReader().readBarrelsFromFile(filePath);
				}
				break;
			case CARS:
				if (doAppend) {
					executionData.carCollection = ensureCollection(executionData.carCollection);
					new CarJsonReader().readCarsFromFile(executionData.carCollection, filePath, true);
				}
				else {
					executionData.carCollection = new CarJsonReader().readCarsFromFile(filePath);
				}
				break;
			case STUDENTS:
				if (doAppend) {
					executionData.studentCollection = ensureCollection(executionData.studentCollection);
					new StudentJsonReader().readStudentsFromFile(executionData.studentCollection, filePath, true);
				}
				else {
					executionData.studentCollection = new StudentJsonReader().readStudentsFromFile(filePath);
				}
				break;
			default:
				throw new IllegalArgumentException(TranslationManager.getUnknownModelTypeError());
		}

	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		String optionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.IMPORT.shortOpt);
		if (optionValue == null || optionValue.isBlank()) {
			lastError = TranslationManager.getImportOptionValueRequiredError();
			return false;
		}

		if (!Files.exists(Path.of(optionValue)) || !Files.isRegularFile(Path.of(optionValue))) {
			lastError = TranslationManager.getImportOptionInvalidPathError();
			return false;
		}

		return true;
	}

	private <T> Collection<T> ensureCollection(Collection<T> collection) {
		if (collection != null) {
			return collection;
		}

		return new ArrayList<>();
	}

}
