package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.model.Car;

import java.util.List;
import java.util.Locale;
import java.time.Year;

public class CarRandomGenerator {
	/// \todo use locale from \c TranslationManager
	private static final Faker faker = new Faker(Locale.forLanguageTag("ru"));

	public static List<Car> generate(int size) {
		return faker.collection(() -> Car.builder()
				.setModel(faker.vehicle().model())
				.setPower(faker.number().numberBetween(60, 600))
				.setProductionYear(faker.number().numberBetween(1950, Year.now().getValue()))
				.build()).len(size).generate();
	}
}
