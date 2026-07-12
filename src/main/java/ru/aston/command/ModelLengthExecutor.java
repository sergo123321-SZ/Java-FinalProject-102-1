package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.TranslationManager;

import java.util.Collection;
import java.util.List;

public class ModelLengthExecutor extends BaseExecutor {
	public ModelLengthExecutor() {
		super(List.of(CommandProcessor.CommandStep.LENGTH));
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
			System.out.println(TranslationManager.getCollectionLengthMessage(collection.size()));
		}
		else {
			System.out.println(TranslationManager.getCollectionNotInitializedMessage());
		}
	}

}
