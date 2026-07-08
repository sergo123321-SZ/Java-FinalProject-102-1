package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.model.Student;

import java.util.List;
import java.util.Locale;

public class StudentRandomGenerator {
	/// \todo use locale from \c TranslationManager
	private static final Faker faker = new Faker(Locale.forLanguageTag("ru"));

	public static List<Student> generate(int size) {
		return faker.collection(
				() -> Student.builder()
						.setGroupNumber(faker.regexify("[А-Я]{2}-\\d{2}"))
						.setAverageGrade(Math.round(faker.number().randomDouble(2, 2, 5) * 100.0) / 100.0)
						.setRecordBookNumber(faker.regexify("\\d{6}"))
						.build()
		).len(size).generate();
	}
}
