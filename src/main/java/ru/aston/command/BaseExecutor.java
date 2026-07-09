package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import java.util.List;


abstract class BaseExecutor implements Executor {
	protected List<CommandProcessor.CommandStep> requiredSteps;

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		boolean hasAllRequiredOptions = requiredSteps.stream().map(s -> s.shortOpt).allMatch(commandLine::hasOption);
		if (!hasAllRequiredOptions) {
			return false;
		}

		return true;
	}

	boolean checkOption(@NotNull CommandProcessor.CommandStep step, @NotNull CommandLine commandLine) {

		boolean hasConflictingOptions = step.conflictingSteps.stream()
				.map(s -> s.shortOpt)
				.anyMatch(commandLine::hasOption);

		boolean hasAllRequiredOptions = step.requiredSteps.stream()
				.map(s -> s.shortOpt)
				.allMatch(commandLine::hasOption);

		return !hasConflictingOptions && hasAllRequiredOptions;
	}

	abstract boolean isStepSupported(@NotNull CommandProcessor.CommandStep step);

	BaseExecutor(List<CommandProcessor.CommandStep> requiredSteps) {
		this.requiredSteps = requiredSteps;
	}
}
