package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.core.TranslationManager;
import ru.aston.model.Student;

import java.util.List;
import java.util.Locale;

public class StudentRandomGenerator {
	private static final Faker faker = new Faker(TranslationManager.getLocale());

	public static List<Student> generate(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size must be positive");
		}

		return faker.collection(() -> Student.builder()
				.setGroupNumber(faker.regexify("[А-Я]{2}-\\d{2}"))
				.setAverageGrade(Math.round(faker.number().randomDouble(2, 2, 5) * 100.0) / 100.0)
				.setRecordBookNumber(faker.regexify("\\d{6}"))
				.build()).len(size).generate();
	}
}
