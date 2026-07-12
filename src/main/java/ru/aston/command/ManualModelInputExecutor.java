package ru.aston.command;

import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import ru.aston.core.AppConstants.WriteMode;
import ru.aston.core.TranslationManager;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

import java.util.ArrayList;
import java.util.Collection;
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
					executionData.carCollection.addAll(readCars(scanner, size));
				}
				else {
					executionData.carCollection = readCars(scanner, size);
				}
				break;
			case STUDENTS:
				if (doAppend && executionData.studentCollection != null) {
					executionData.studentCollection.addAll(readStudents(scanner, size));
				}
				else {
					executionData.studentCollection = readStudents(scanner, size);
				}
				break;
			case BARRELS:
				if (doAppend && executionData.barrelCollection != null) {
					executionData.barrelCollection.addAll(readBarrels(scanner, size));
				}
				else {
					executionData.barrelCollection = readBarrels(scanner, size);
				}
				break;
			default:
				throw new IllegalArgumentException(TranslationManager.getUnknownModelTypeError());
		}
	}

	private Collection<Car> readCars(@NotNull Scanner scanner, int count) {
		Collection<Car> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			while (true) {
				try {
					System.out.println(TranslationManager.getManualCarInputPrompt(i + 1));
					String model = readNonBlank(scanner, TranslationManager.getManualPromptCarModel());
					int power = readInt(scanner, TranslationManager.getManualPromptCarPower());
					int productionYear = readInt(scanner, TranslationManager.getManualPromptCarProductionYear());
					result.add(Car.builder().setModel(model).setPower(power).setProductionYear(productionYear).build());
					break;
				}
				catch (IllegalArgumentException e) {
					System.out.println(TranslationManager.getValidationError(e.getMessage()));
				}
			}
		}
		return result;
	}

	private Collection<Student> readStudents(@NotNull Scanner scanner, int count) {
		Collection<Student> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			while (true) {
				try {
					System.out.println(TranslationManager.getManualStudentInputPrompt(i + 1));
					String groupNumber = readNonBlank(scanner, TranslationManager.getManualPromptStudentGroupNumber());
					double averageGrade = readDouble(scanner, TranslationManager.getManualPromptStudentAverageGrade());
					String recordBook = readNonBlank(scanner, TranslationManager.getManualPromptStudentRecordBook());
					result.add(Student.builder()
							.setGroupNumber(groupNumber)
							.setAverageGrade(averageGrade)
							.setRecordBookNumber(recordBook)
							.build());
					break;
				}
				catch (IllegalArgumentException e) {
					System.out.println(TranslationManager.getValidationError(e.getMessage()));
				}
			}
		}
		return result;
	}

	private Collection<Barrel> readBarrels(@NotNull Scanner scanner, int count) {
		Collection<Barrel> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			while (true) {
				try {
					System.out.println(TranslationManager.getManualBarrelInputPrompt(i + 1));
					double volume = readDouble(scanner, TranslationManager.getManualPromptBarrelVolume());
					String storedMaterial = readNonBlank(scanner, TranslationManager.getManualPromptBarrelStoredMaterial());
					String barrelMaterial = readNonBlank(scanner, TranslationManager.getManualPromptBarrelMaterial());
					result.add(Barrel.builder()
							.setVolume(volume)
							.setStoredMaterial(storedMaterial)
							.setBarrelMaterial(barrelMaterial)
							.build());
					break;
				}
				catch (IllegalArgumentException e) {
					System.out.println(TranslationManager.getValidationError(e.getMessage()));
				}
			}
		}
		return result;
	}

	private String readNonBlank(@NotNull Scanner scanner, @NotNull String prompt) {
		while (true) {
			System.out.print(prompt);
			if (!scanner.hasNextLine()) {
				throw new IllegalStateException(TranslationManager.getScannerInputEndedError());
			}
			String value = scanner.nextLine().trim();
			if (!value.isBlank()) {
				return value;
			}
			System.out.println(TranslationManager.getManualInputMustNotBeBlankError());
		}
	}

	private int readInt(@NotNull Scanner scanner, @NotNull String prompt) {
		while (true) {
			System.out.print(prompt);
			if (!scanner.hasNextLine()) {
				throw new IllegalStateException(TranslationManager.getScannerInputEndedError());
			}
			String value = scanner.nextLine().trim();
			try {
				return Integer.parseInt(value);
			}
			catch (NumberFormatException e) {
				System.out.println(TranslationManager.getManualInputMustBeIntegerError());
			}
		}
	}

	private double readDouble(@NotNull Scanner scanner, @NotNull String prompt) {
		while (true) {
			System.out.print(prompt);
			if (!scanner.hasNextLine()) {
				throw new IllegalStateException(TranslationManager.getScannerInputEndedError());
			}
			String value = scanner.nextLine().trim();
			try {
				return Double.parseDouble(value);
			}
			catch (NumberFormatException e) {
				System.out.println(TranslationManager.getManualInputMustBeNumberError());
			}
		}
	}
}
