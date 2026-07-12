package ru.aston.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ru.aston.command.CommandProcessor;
import ru.aston.command.ExecutionData;


public class InteractiveConsoleApplication {
	public void exec() {

		CommandProcessor p = new CommandProcessor();
		Scanner scanner = new Scanner(System.in);
		System.out.println(TranslationManager.getHelpText());
		ExecutionData executionData = new ExecutionData();
		while (!executionData.isExitRequested) {
			System.out.print("> ");
			if (!scanner.hasNextLine()) {
				break;
			}
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				continue;
			}
			String[] commands = tokenize(input);
			p.executeCommands(commands, executionData);
		}
		scanner.close();
	}

	private String[] tokenize(String input) {
		List<String> tokens = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;
		boolean escaping = false;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			if (escaping) {
				current.append(c);
				escaping = false;
				continue;
			}

			if (c == '\\') {
				escaping = true;
				continue;
			}

			if (c == '\'' && !inDoubleQuote) {
				inSingleQuote = !inSingleQuote;
				continue;
			}

			if (c == '"' && !inSingleQuote) {
				inDoubleQuote = !inDoubleQuote;
				continue;
			}

			if (Character.isWhitespace(c) && !inSingleQuote && !inDoubleQuote) {
				if (current.length() > 0) {
					tokens.add(current.toString());
					current.setLength(0);
				}
				continue;
			}

			current.append(c);
		}

		if (escaping) {
			current.append('\\');
		}

		if (current.length() > 0) {
			tokens.add(current.toString());
		}

		return tokens.toArray(String[]::new);
	}
}
