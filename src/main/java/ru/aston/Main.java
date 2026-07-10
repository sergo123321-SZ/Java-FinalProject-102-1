package ru.aston;

import ru.aston.command.CommandProcessor;

public class Main {
	public static void main(String[] args) {

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
