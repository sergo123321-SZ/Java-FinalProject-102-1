package ru.aston.randomgenerator;

import net.datafaker.Faker;
import ru.aston.model.Barrel;

import java.util.List;
import java.util.Locale;

public class BarrelRandomGenerator {
	/// \todo use locale from \c TranslationManager
	private static final Faker faker = new Faker(Locale.forLanguageTag("ru"));

	public static List<Barrel> generate(int size) {
		return faker.collection(
				() -> Barrel.builder()
						.setVolume(Math.round(faker.number().randomDouble(1, 10, 500) * 10.0) / 10.0)
						.setStoredMaterial(
								faker.options().option("Нефть", "Бензин", "Мазут", "Вода", "Вино", "Пиво", "Мед", "Сок")
						)
						.setBarrelMaterial(faker.options().option("Дуб", "Сосна", "Сталь", "Пластик", "Алюминий"))
						.build()
		).len(size).generate();
	}
}
