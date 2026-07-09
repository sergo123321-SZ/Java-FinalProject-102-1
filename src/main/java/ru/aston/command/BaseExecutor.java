package ru.aston.command;

import org.jetbrains.annotations.NotNull;

abstract class BaseExecutor implements Executor {
	abstract boolean isStepSupported(@NotNull CommandProcessor.CommandStep step);
}
