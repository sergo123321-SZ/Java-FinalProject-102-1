package ru.aston.core;


import ru.aston.command.CommandProcessor;


public class InteractiveConsoleApplication {
	public void exec() {
		/// \todo add loop and display text for user
		CommandProcessor p = new CommandProcessor();
		p.executeCommands(new String[]{
				"-M",
				"BARRELS",
				"-C",
				"10",
				"-D"
		});
	}
}
