package ru.aston.command;

import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants.WriteMode;
import ru.aston.core.TranslationManager;

import java.util.List;
import java.util.Scanner;

public class ManualModelInputExecutor extends BaseExecutor {
	private int size = 0;

	ManualModelInputExecutor() {
		super(List.of(CommandProcessor.CommandStep.MANUAL));
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		try {
			String manualOptionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.MANUAL.shortOpt);
			size = Integer.parseInt(manualOptionValue);
			if (size <= 0) {
				lastError = TranslationManager.getManualOptionInvalidValueError();
				return false;
			}
		}
		catch (Exception e) {
			lastError = TranslationManager.getManualOptionInvalidValueError();
			return false;
		}

		return true;
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		if (executionData.modelType == null) {
			throw new IllegalArgumentException(TranslationManager.getNullModelTypeError());
		}
		if (executionData.scanner == null) {
			throw new IllegalStateException(TranslationManager.getScannerNotAvailableError());
		}

		Scanner scanner = executionData.scanner;
		boolean doAppend = executionData.writeMode == null || executionData.writeMode == WriteMode.APPEND;

		switch (executionData.modelType) {
			case CARS:
				if (doAppend && executionData.carCollection != null) {
					executionData.carCollection.addAll(ManualModelInputReader.readCars(scanner, size));
				}
				else {
					executionData.carCollection = ManualModelInputReader.readCars(scanner, size);
				}
				break;
			case STUDENTS:
				if (doAppend && executionData.studentCollection != null) {
					executionData.studentCollection.addAll(ManualModelInputReader.readStudents(scanner, size));
				}
				else {
					executionData.studentCollection = ManualModelInputReader.readStudents(scanner, size);
				}
				break;
			case BARRELS:
				if (doAppend && executionData.barrelCollection != null) {
					executionData.barrelCollection.addAll(ManualModelInputReader.readBarrels(scanner, size));
				}
				else {
					executionData.barrelCollection = ManualModelInputReader.readBarrels(scanner, size);
				}
				break;
			default:
				throw new IllegalArgumentException(TranslationManager.getUnknownModelTypeError());
		}
	}
}
