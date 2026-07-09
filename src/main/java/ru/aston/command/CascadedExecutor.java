package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;


public class CascadedExecutor implements Executor {
	private final List<Executor> executors = new LinkedList<>();
	private String lastError = null;

	public void addExecutor(@NotNull final Executor executor) {
		executors.add(executor);
	}

	@Override
	public void execute(@NotNull CommandLine options) {
		checkState();

		for (Executor executor : executors) {
			executor.execute(options);
		}
	}

	@Override
	public boolean checkOptions(@NotNull CommandLine options) {
		checkState();

		for (Executor executor : executors) {
			if (!executor.checkOptions(options)) {
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
