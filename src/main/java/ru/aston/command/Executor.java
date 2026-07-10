package ru.aston.command;


import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NotNull;


public interface Executor {
	ExecutionData execute(@NotNull CommandLine options);

	boolean checkOptions(@NotNull CommandLine commandLine);

	String getLastError();
}
