package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class StudentRandomGenerator {
	private static final Faker faker = new Faker(Locale.forLanguageTag("ru"));

	public static Collection<Student> generate(int size) {
		Collection<Student> result = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			Student item = Student.builder()
					.setGroupNumber(faker.regexify("[А-Я]{2}-\\d{2}"))
					.setAverageGrade(Math.round(faker.number().randomDouble(2, 2, 5) * 100.0) / 100.0)
					.setRecordBookNumber(faker.regexify("\\d{6}"))
					.build();
			result.add(item);
		}
		return result;
	}
}
