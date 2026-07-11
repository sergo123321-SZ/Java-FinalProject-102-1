package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import ru.aston.core.AppConstants;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;
import ru.aston.sort.OrderType;
import ru.aston.sort.SortType;
import ru.aston.sort.Sorter;
import ru.aston.sort.TypeCollectionChanger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ModelSortExecutor extends BaseExecutor {
	ModelSortExecutor() {
		super(List.of(CommandProcessor.CommandStep.SORT));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		if (executionData.modelType == null) {
			throw new IllegalArgumentException("'modelType' is null. MUST be assigned before execution!");
		}

		String sortOptionValue = options.getOptionValue(CommandProcessor.CommandStep.SORT.shortOpt);
		AppConstants.SortType selectedSortType = getSortType(sortOptionValue);
		executionData.sortType = selectedSortType;

		Collection<?> collection = switch (executionData.modelType) {
			case BARRELS -> executionData.barrelCollection;
			case CARS -> executionData.carCollection;
			case STUDENTS -> executionData.studentCollection;
			default -> null;
		};
		if (collection != null && !collection.isEmpty()) {
			Sorter sorter = new Sorter(new TypeCollectionChanger());
			Collection<Object> sortedCollection = sorter
					.sort(new ArrayList<>(collection), toInternalSortType(selectedSortType), toOrderType(selectedSortType));

			switch (executionData.modelType) {
				case BARRELS -> executionData.barrelCollection = castCollection(sortedCollection, Barrel.class);
				case CARS -> executionData.carCollection = castCollection(sortedCollection, Car.class);
				case STUDENTS -> executionData.studentCollection = castCollection(sortedCollection, Student.class);
				default -> throw new IllegalArgumentException("'modelType' is unknown. MUST be assigned before execution!");
			}
		}
		else {
			System.out.println("Nothing to sort. The collection is empty.");
		}
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		try {
			String sortOptionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.SORT.shortOpt);
			if (sortOptionValue == null || sortOptionValue.isBlank()) {
				lastError = "'sort' option is required and must be non-empty";
				return false;
			}
			getSortType(sortOptionValue);
		}
		catch (IllegalArgumentException e) {
			lastError = "Unsupported sort type. Allowed values: ASC, DESC, SPECIAL";
			return false;
		}

		return true;
	}

	private @NotNull AppConstants.SortType getSortType(@NotNull String optionId) {
		String normalized = optionId.trim().toUpperCase();
		normalized = switch (normalized) {
			case "ASCENDING", "ASC" -> AppConstants.SortType.ASC.name();
			case "DESCENDING", "DESC" -> AppConstants.SortType.DESC.name();
			case "SPECIAL" -> AppConstants.SortType.SPECIAL.name();
			default -> normalized;
		};

		return AppConstants.SortType.valueOf(normalized);
	}

	private @NotNull SortType toInternalSortType(@NotNull AppConstants.SortType sortType) {
		return switch (sortType) {
			case ASC, DESC -> SortType.QUICK_SORT;
			case SPECIAL -> SortType.CUSTOM_SORT;
		};
	}

	private @NotNull OrderType toOrderType(@NotNull AppConstants.SortType sortType) {
		return switch (sortType) {
			case ASC, SPECIAL -> OrderType.NATURAL_ORDER;
			case DESC -> OrderType.REVERSE_ORDER;
		};
	}

	private <T> @NotNull Collection<T> castCollection(@NotNull Collection<Object> source, @NotNull Class<T> targetType) {
		return source.stream().map(targetType::cast).collect(Collectors.toList());
	}
}
