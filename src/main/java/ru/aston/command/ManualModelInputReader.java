package ru.aston.command;

import org.jetbrains.annotations.NotNull;
import ru.aston.core.TranslationManager;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public final class ManualModelInputReader {
	private ManualModelInputReader() {
	}

	public static Collection<Car> readCars(@NotNull Scanner scanner, int count) {
		Collection<Car> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			result.add(readCar(scanner, i + 1));
		}
		return result;
	}

	public static Collection<Student> readStudents(@NotNull Scanner scanner, int count) {
		Collection<Student> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			result.add(readStudent(scanner, i + 1));
		}
		return result;
	}

	public static Collection<Barrel> readBarrels(@NotNull Scanner scanner, int count) {
		Collection<Barrel> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			result.add(readBarrel(scanner, i + 1));
		}
		return result;
	}

	public static Car readCar(@NotNull Scanner scanner, int index) {
		while (true) {
			try {
				System.out.println(TranslationManager.getManualCarInputPrompt(index));
				String model = readNonBlank(scanner, TranslationManager.getManualPromptCarModel());
				int power = readInt(scanner, TranslationManager.getManualPromptCarPower());
				int productionYear = readInt(scanner, TranslationManager.getManualPromptCarProductionYear());
				return Car.builder().setModel(model).setPower(power).setProductionYear(productionYear).build();
			}
			catch (IllegalArgumentException e) {
				System.out.println(TranslationManager.getValidationError(e.getMessage()));
			}
		}
	}

	public static Student readStudent(@NotNull Scanner scanner, int index) {
		while (true) {
			try {
				System.out.println(TranslationManager.getManualStudentInputPrompt(index));
				String groupNumber = readNonBlank(scanner, TranslationManager.getManualPromptStudentGroupNumber());
				double averageGrade = readDouble(scanner, TranslationManager.getManualPromptStudentAverageGrade());
				String recordBook = readNonBlank(scanner, TranslationManager.getManualPromptStudentRecordBook());
				return Student.builder()
						.setGroupNumber(groupNumber)
						.setAverageGrade(averageGrade)
						.setRecordBookNumber(recordBook)
						.build();
			}
			catch (IllegalArgumentException e) {
				System.out.println(TranslationManager.getValidationError(e.getMessage()));
			}
		}
	}

	public static Barrel readBarrel(@NotNull Scanner scanner, int index) {
		while (true) {
			try {
				System.out.println(TranslationManager.getManualBarrelInputPrompt(index));
				double volume = readDouble(scanner, TranslationManager.getManualPromptBarrelVolume());
				String storedMaterial = readNonBlank(scanner, TranslationManager.getManualPromptBarrelStoredMaterial());
				String barrelMaterial = readNonBlank(scanner, TranslationManager.getManualPromptBarrelMaterial());
				return Barrel.builder()
						.setVolume(volume)
						.setStoredMaterial(storedMaterial)
						.setBarrelMaterial(barrelMaterial)
						.build();
			}
			catch (IllegalArgumentException e) {
				System.out.println(TranslationManager.getValidationError(e.getMessage()));
			}
		}
	}

	private static String readNonBlank(@NotNull Scanner scanner, @NotNull String prompt) {
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

	private static int readInt(@NotNull Scanner scanner, @NotNull String prompt) {
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

	private static double readDouble(@NotNull Scanner scanner, @NotNull String prompt) {
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
