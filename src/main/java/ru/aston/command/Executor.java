package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;


public interface Executor {
	void execute(@NotNull CommandLine options, ExecutionData executionData);

	boolean checkOptions(@NotNull CommandLine commandLine);

	String getLastError();
}
