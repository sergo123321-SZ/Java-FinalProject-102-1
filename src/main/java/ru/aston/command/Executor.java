package ru.aston.command;


import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;


public interface Executor {
	void execute(@NotNull Options options);

	boolean checkOptions(@NotNull Options options);

	String getLastError();
}
