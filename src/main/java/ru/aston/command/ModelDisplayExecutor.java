package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.TranslationManager;

import java.util.Collection;
import java.util.List;


public class ModelDisplayExecutor extends BaseExecutor {

	public ModelDisplayExecutor() {
		super(List.of(CommandProcessor.CommandStep.DISPLAY));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		Collection<?> collection = switch (executionData.modelType) {
			case BARRELS -> executionData.barrelCollection;
			case CARS -> executionData.carCollection;
			case STUDENTS -> executionData.studentCollection;
			default -> throw new IllegalArgumentException(TranslationManager.getUnknownModelTypeError());
		};
		if (collection != null && !collection.isEmpty()) {
			collection.forEach(System.out::println);
		}
		else {
			System.out.println(TranslationManager.getEmptyDisplayCollectionMessage());
		}
	}
}
