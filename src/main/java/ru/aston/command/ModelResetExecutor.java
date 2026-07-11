package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ModelResetExecutor extends BaseExecutor {
	public ModelResetExecutor() {
		super(List.of(CommandProcessor.CommandStep.RESET));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		Collection<?> collection = switch (executionData.modelType) {
			case BARRELS -> executionData.barrelCollection;
			case CARS -> executionData.carCollection;
			case STUDENTS -> executionData.studentCollection;
			default -> null;
		};
		if (collection != null) {
			collection.clear();
		}
	}

}
