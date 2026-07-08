package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.model.Car;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.time.Year;

public class CarRandomGenerator {
	private static final Faker faker = new Faker(Locale.forLanguageTag("ru"));

	public static Collection<Car> generate(int size) {
		Collection<Car> result = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			Car item = Car.builder()
					.setModel(faker.vehicle().model())
					.setPower(faker.number().numberBetween(60, 600))
					.setProductionYear(faker.number().numberBetween(1950, Year.now().getValue()))
					.build();
			result.add(item);
		}
		return result;
	}
}
