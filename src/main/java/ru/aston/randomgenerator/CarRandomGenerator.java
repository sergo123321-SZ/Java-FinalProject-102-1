package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.core.TranslationManager;
import ru.aston.model.Car;

import java.time.Year;
import java.util.List;
import java.util.Locale;

public class CarRandomGenerator {
	private static final Faker faker = new Faker(TranslationManager.getLocale());

	public static List<Car> generate(int size) {
		return faker.collection(() -> Car.builder()
				.setModel(faker.vehicle().model())
				.setPower(faker.number().numberBetween(60, 600))
				.setProductionYear(faker.number().numberBetween(1950, Year.now().getValue()))
				.build()).len(size).generate();
	}
}
