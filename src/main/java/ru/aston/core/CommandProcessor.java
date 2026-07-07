package ru.aston.core;


import org.apache.commons.cli.*;


public class CommandProcessor {
	private final CommandLineParser parser = new DefaultParser();
	private final HelpFormatter formatter = new HelpFormatter();
	private final Options options = new Options();

	public CommandProcessor() {
		for (CommandStep step : CommandStep.values()) {
			options.addOption(Option.builder(step.shortOpt)
					.longOpt(step.longOpt)
					.hasArg(step.hasArg)
					.desc(step.description)
					.build());
		}
	}

	public void executeCommands(String[] commands) {
		try {
			CommandLine cmd = parser.parse(options, commands);

			System.out.println("--- Запуск конвейера обработки ---");

			if (cmd.hasOption(CommandStep.SELECT.longOpt)) {
				String path = cmd.getOptionValue(CommandStep.SELECT.longOpt);
				System.out.println("[Шаг 1 - SELECT] Загрузка данных из: " + path);
			}
			else {
				System.out.println("[Ошибка] Выполнение невозможно: не выбран объект (--select)");
				return;
			}

			if (cmd.hasOption(CommandStep.FILTER.longOpt)) {
				String filterRule = cmd.getOptionValue(CommandStep.FILTER.longOpt);
				System.out.println("[Шаг 2 - FILTER] Применение фильтра: " + filterRule);
			}

			if (cmd.hasOption(CommandStep.SORT.longOpt)) {
				String sortRule = cmd.getOptionValue(CommandStep.SORT.longOpt);
				System.out.println("[Шаг 3 - SORT] Сортировка по полю: " + sortRule);
			}

			if (cmd.hasOption(CommandStep.EXPORT.longOpt)) {
				String dest = cmd.getOptionValue(CommandStep.EXPORT.longOpt);
				System.out.println("[Шаг 4 - EXPORT] Сохранение результата в: " + dest);
			}

			System.out.println("--- Конвейер успешно завершен ---");

		}
		catch (ParseException e) {
			System.out.println("Ошибка парсинга: " + e.getMessage());
			formatter.printHelp("", options);
		}
	}

	private enum CommandStep {
		SELECT("s", "select", true, "Выбрать объект"),
		FILTER("f", "filter", true, "Отфильтровать данные"),
		SORT("sort", "sort", true, "Отсортировать данные"),
		EXPORT("e", "export", true, "Экспортировать результат");

		final String shortOpt;
		final String longOpt;
		final boolean hasArg;
		final String description;

		CommandStep(String shortOpt, String longOpt, boolean hasArg, String description) {
			this.shortOpt = shortOpt;
			this.longOpt = longOpt;
			this.hasArg = hasArg;
			this.description = description;
		}
	}
}
