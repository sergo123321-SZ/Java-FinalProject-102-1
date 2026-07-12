package ru.aston.command;

import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.TranslationManager;
import ru.aston.counter.MultiThreadCounter;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ModelCountExecutor extends BaseExecutor {
	private int threadsCount = 0;

	ModelCountExecutor() {
		super(List.of(CommandProcessor.CommandStep.COUNT));
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		try {
			String countOptionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.COUNT.shortOpt);
			threadsCount = Integer.parseInt(countOptionValue);
			if (threadsCount <= 0) {
				lastError = TranslationManager.getCountOptionInvalidValueError();
				return false;
			}
		}
		catch (Exception e) {
			lastError = TranslationManager.getCountOptionInvalidValueError();
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
		CounterMethod method = askMethod(scanner);

		try {
			switch (executionData.modelType) {
				case CARS:
					countAndPrintForCars(scanner, executionData.carCollection, method);
					break;
				case STUDENTS:
					countAndPrintForStudents(scanner, executionData.studentCollection, method);
					break;
				case BARRELS:
					countAndPrintForBarrels(scanner, executionData.barrelCollection, method);
					break;
				default:
					throw new IllegalArgumentException(TranslationManager.getUnknownModelTypeError());
			}
		}
		catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private void countAndPrintForCars(@NotNull Scanner scanner, Collection<Car> collection, CounterMethod method) throws Exception {
		if (collection == null || collection.isEmpty()) {
			System.out.println(TranslationManager.getCollectionNotInitializedMessage());
			return;
		}
		Car target = ManualModelInputReader.readCar(scanner, 1);
		long result = runCount(method, collection, target);
		System.out.println(TranslationManager.getCounterResultMessage(result));
	}

	private void countAndPrintForStudents(@NotNull Scanner scanner, Collection<Student> collection, CounterMethod method)
			throws Exception
	{
		if (collection == null || collection.isEmpty()) {
			System.out.println(TranslationManager.getCollectionNotInitializedMessage());
			return;
		}
		Student target = ManualModelInputReader.readStudent(scanner, 1);
		long result = runCount(method, collection, target);
		System.out.println(TranslationManager.getCounterResultMessage(result));
	}

	private void countAndPrintForBarrels(@NotNull Scanner scanner, Collection<Barrel> collection, CounterMethod method)
			throws Exception
	{
		if (collection == null || collection.isEmpty()) {
			System.out.println(TranslationManager.getCollectionNotInitializedMessage());
			return;
		}
		Barrel target = ManualModelInputReader.readBarrel(scanner, 1);
		long result = runCount(method, collection, target);
		System.out.println(TranslationManager.getCounterResultMessage(result));
	}

	private long runCount(@NotNull CounterMethod method, @NotNull Collection<?> collection, @NotNull Object target)
			throws Exception
	{
		return switch (method) {
			case ARRAY -> MultiThreadCounter.countParallelArray(collection.toArray(), target, threadsCount);
			case SUBLIST -> MultiThreadCounter.countParallelSubList(collection, target, threadsCount);
			case STREAM -> MultiThreadCounter.countParallelStream(collection, target);
		};
	}

	private CounterMethod askMethod(@NotNull Scanner scanner) {
		while (true) {
			System.out.print(TranslationManager.getCounterMethodPrompt());
			if (!scanner.hasNextLine()) {
				throw new IllegalStateException(TranslationManager.getScannerInputEndedError());
			}
			String methodName = scanner.nextLine().trim().toUpperCase();
			try {
				return CounterMethod.valueOf(methodName);
			}
			catch (IllegalArgumentException e) {
				System.out.println(TranslationManager.getCounterMethodInvalidError());
			}
		}
	}

	enum CounterMethod {
		ARRAY,
		SUBLIST,
		STREAM
	}
}
