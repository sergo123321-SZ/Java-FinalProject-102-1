package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.jetbrains.annotations.NotNull;
import ru.aston.core.TranslationManager;

import java.util.*;

abstract class BaseExecutor implements Executor {
	protected List<CommandProcessor.CommandStep> requiredSteps;
	protected String lastError = null;

	public boolean canExecute(@NotNull CommandLine commandLine) {
		lastError = null;

		boolean hasAllRequiredOptions = requiredSteps.stream().map(s -> s.shortOpt).allMatch(commandLine::hasOption);
		if (!hasAllRequiredOptions) {
			lastError = TranslationManager.getMissingRequiredOptionsError(getOptionsDisplay(requiredSteps));
			return false;
		}

		return Arrays.stream(commandLine.getOptions()).allMatch(option -> checkOption(option, commandLine));
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		return true;
	}

	@Override
	public void execute(@NotNull CommandLine options, ExecutionData executionData) {
		if (!canExecute(options)) {
			return;
		}

		ExecutionData result = new ExecutionData();
		doExec(options, result);
	}

	@Override
	public String getLastError() {
		try {
			return lastError;
		}
		finally {
			lastError = null;
		}
	}

	@NotNull
	String getOptionsDisplay(@NotNull Collection<CommandProcessor.CommandStep> steps) {
		List<String> optionsNames = new ArrayList<>(steps.size());
		steps.forEach(s -> optionsNames.add(s.longOpt));

		return String.join(", ", optionsNames);
	}

	boolean checkOption(@NotNull Option option, @NotNull CommandLine commandLine) {
		lastError = null;

		CommandProcessor.CommandStep step = null;
		for (CommandProcessor.CommandStep s : CommandProcessor.CommandStep.values()) {
			if (Objects.equals(s.shortOpt, option.getOpt()) || Objects.equals(s.longOpt, option.getLongOpt())) {
				step = s;
				break;
			}
		}
		if (step == null) {
			lastError = TranslationManager.getUnknownOptionError(option.getLongOpt());
			return false;
		}

		boolean hasAllRequiredOptions = step.requiredSteps.stream().map(s -> s.shortOpt).allMatch(commandLine::hasOption);

		if (!hasAllRequiredOptions) {
			lastError = "Missing required options. Please ensure the following options are provided: "
					+ getOptionsDisplay(step.requiredSteps);
			return false;
		}

		return true;
	}

	abstract void doExec(@NotNull CommandLine options, @NotNull ExecutionData executionData);

	BaseExecutor(List<CommandProcessor.CommandStep> requiredSteps) {
		this.requiredSteps = requiredSteps;
	}
}
