package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

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
			default -> throw new IllegalArgumentException("'modelType' is unknown. MUST be assigned before execution!");
		};
		if (collection != null && !collection.isEmpty()) {
			collection.forEach(System.out::println);
		}
		else {
			System.out.println("Nothing to display. The collection is empty.");
		}
	}
}
