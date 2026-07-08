package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.model.Barrel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class BarrelRandomGenerator {
	private static final Faker faker = new Faker(Locale.forLanguageTag("ru"));

	public static Collection<Barrel> generate(int size) {
		Collection<Barrel> result = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			Barrel item = Barrel.builder()
					.setVolume(Math.round(faker.number().randomDouble(1, 10, 500) * 10.0) / 10.0)
					.setStoredMaterial(
							faker.options().option("Нефть", "Бензин", "Мазут", "Вода", "Вино", "Пиво", "Мед", "Сок")
					)
					.setBarrelMaterial(faker.options().option("Дуб", "Сосна", "Сталь", "Пластик", "Алюминий"))
					.build();
			result.add(item);
		}
		return result;
	}
}
