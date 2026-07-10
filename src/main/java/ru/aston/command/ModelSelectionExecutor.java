package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.AppConstants;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

import java.util.Collection;
import java.util.List;

public class ModelSelectionExecutor extends BaseExecutor {
	private Collection<Car> carCollection = null;
	private Collection<Student> studentCollection = null;
	private Collection<Barrel> barrelCollection = null;

	public ModelSelectionExecutor(
			Collection<Car> carCollection,
			Collection<Student> studentCollection,
			Collection<Barrel> barrelCollection)
	{
		super(List.of(CommandProcessor.CommandStep.MODEL));
		this.carCollection = carCollection;
		this.studentCollection = studentCollection;
		this.barrelCollection = barrelCollection;
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		if (!super.checkOptions(commandLine)) {
			return false;
		}
		String selectedModelId = commandLine.getOptionValue(CommandProcessor.CommandStep.MODEL.shortOpt);
		if (selectedModelId == null) {
			throw new IllegalStateException("model id is required");
		}

		try {
			AppConstants.ModelType.valueOf(selectedModelId);
		}
		catch (IllegalArgumentException e) {
			/// \todo use the correct string
			lastError = e.getMessage();
			return false;
		}

		return true;
	}

	@Override
	void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData) {
		String selectedModelId = options.getOptionValue(CommandProcessor.CommandStep.MODEL.shortOpt);
		executionData.modelType = AppConstants.ModelType.valueOf(selectedModelId);
	}

}
