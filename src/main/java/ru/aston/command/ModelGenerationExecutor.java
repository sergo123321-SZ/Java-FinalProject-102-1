package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants.WriteMode;
import ru.aston.randomgenerator.BarrelRandomGenerator;
import ru.aston.randomgenerator.CarRandomGenerator;
import ru.aston.randomgenerator.StudentRandomGenerator;

import java.util.List;

public class ModelGenerationExecutor extends BaseExecutor {
	private int size = 0;
	private WriteMode generationRule;

	ModelGenerationExecutor() {
		super(List.of(CommandProcessor.CommandStep.CREATE));
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		if (executionData.modelType == null) {
			throw new IllegalArgumentException("'modelType' is null. MUST be assigned before execution!");
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
				throw new IllegalArgumentException("'modelType' is unknown. MUST be assigned before execution!");
		}
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		try {
			String createOptionValue = commandLine.getOptionValue(CommandProcessor.CommandStep.CREATE.shortOpt);
			size = Integer.parseInt(createOptionValue);
		}
		catch (Exception e) {
			lastError = "'create' option must be a positive integer";
			return false;
		}

		try {
			/// \todo implement it
			// String generationRuleValue =
			// commandLine.getOptionValue(CommandProcessor.CommandStep.GENERATION_RULE.shortOpt);
			// generationRule = AppConstants.WriteType.valueOf(generationRuleValue);
			generationRule = WriteMode.OVERWRITE;
		}
		catch (Exception e) {
			lastError = "'generationRule' option is invalid";
			return false;
		}


		return true;
	}
}
