package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.TranslationManager;
import ru.aston.randomgenerator.BarrelRandomGenerator;
import ru.aston.randomgenerator.CarRandomGenerator;
import ru.aston.randomgenerator.StudentRandomGenerator;

import java.util.List;

public class ModelGenerationExecutor extends BaseExecutor {
	private int size = 0;

	ModelGenerationExecutor() {
		super(List.of(CommandProcessor.CommandStep.CREATE));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		if (executionData.modelType == null) {
			throw new IllegalArgumentException(TranslationManager.getNullModelTypeError());
		}

		switch (executionData.modelType) {
			case CARS:
				executionData.carCollection = CarRandomGenerator.generate(size);
				break;
			case STUDENTS:
				executionData.studentCollection = StudentRandomGenerator.generate(size);
				break;
			case BARRELS:
				executionData.barrelCollection = BarrelRandomGenerator.generate(size);
				break;
			default:
				throw new IllegalArgumentException(TranslationManager.getUnknownModelTypeError());
		}
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		try {
			String createOptionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.CREATE.shortOpt);
			size = Integer.parseInt(createOptionValue);
			if (size <= 0) {
				lastError = TranslationManager.getCreateOptionInvalidValueError();
				return false;
			}
		}
		catch (Exception e) {
			lastError = TranslationManager.getCreateOptionInvalidValueError();
			return false;
		}

		return true;
	}
}
