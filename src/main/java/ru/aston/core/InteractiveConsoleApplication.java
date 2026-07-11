package ru.aston.core;


import java.util.Scanner;

import ru.aston.command.CommandProcessor;
import ru.aston.command.ExecutionData;


public class InteractiveConsoleApplication {
	public void exec() {

		CommandProcessor p = new CommandProcessor();
		Scanner scanner = new Scanner(System.in);
		System.out.println(TranslationManager.getHelpText("1.0", "TeamName"));
		ExecutionData executionData = new ExecutionData();
		while (!executionData.isExitRequested) {
			System.out.print("> ");
			StringBuilder buff = new StringBuilder();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					break;
				}
				buff.append(line).append(" ");
			}
			String input = buff.toString().trim();
			String[] commands = input.split(" ");
			p.executeCommands(commands, executionData);
		}
		scanner.close();
	}
}
