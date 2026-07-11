package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;


public class CascadedExecutor implements Executor {
	private final List<BaseExecutor> executors = new LinkedList<>();
	private String lastError = null;

	public CascadedExecutor addExecutor(@NotNull final Executor executor) {
		if (!(executor instanceof BaseExecutor e)) {
			throw new IllegalArgumentException("Only package BaseExecutors supported");
		}

		executors.add(e);
		return this;
	}

	@Override
	public void execute(@NotNull CommandLine options, ExecutionData executionData) {
		checkState();

		for (BaseExecutor executor : executors) {
			if (executor.canExecute(options)) {
				executor.doExec(options, executionData);
			}
		}
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine commandLine) {
		checkState();

		for (Executor executor : executors) {
			if (!executor.checkOptions(commandLine)) {
				lastError = executor.getLastError();

				return false;
			}
		}

		return true;
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

	private void checkState() {
		if (executors.isEmpty()) {
			throw new IllegalStateException("No executors defined");
		}
	}
}
