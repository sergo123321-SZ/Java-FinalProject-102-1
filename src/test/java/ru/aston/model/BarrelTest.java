package ru.aston.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aston.core.TranslationManager;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BarrelTest {

	@BeforeAll
	static void setUp() {
		TranslationManager.setLocale(Locale.of("ru"));
	}

	@Test
	@DisplayName("equals: должен возвращать true при сравнении бочки с самой собой")
	void equals_sameBarrel_returnsTrue() {
		Barrel barrel = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel).as("Бочка должна быть равной самой себе").isEqualTo(barrel);
	}

	@Test
	@DisplayName("equals: должен возвращать true при сравнении двух разных бочек с одинаковыми свойствами")
	void equals_identicalBarrels_returnsTrue() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel1).as("Две разные бочки с одинаковыми свойствами должны быть равны").isEqualTo(barrel2);
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении бочки с null")
	void equalsWithNull() {
		Barrel barrel = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		assertFalse(barrel.equals(null));
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении двух разных бочек с разными свойствами")
	void equals_differentBarrels_returnsFalse() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(500.0).setStoredMaterial("Water").setBarrelMaterial("Plastic")
				.build();

		assertThat(barrel1).as("Две разные бочки с разными свойствами не должны быть равны").isNotEqualTo(barrel2);
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении двух разных бочек с одним разным свойством")
	void equals_onlyStoredMaterialIsDifferent_returnsFalse() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Water").setBarrelMaterial("Steel")
				.build();

		assertThat(barrel1).as("Две разные бочки с одним разными свойством не должны быть равны").isNotEqualTo(barrel2);
	}

	@Test
	@DisplayName("hashCode: должен быть одинаковым при сравнении двух разных бочек с одинаковыми свойствами")
	void hashCode_identicalBarrels_returnsSameHash() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel1.hashCode()).as("Две разные бочки с одинаковыми свойствами должны быть равны по hashCode")
				.isEqualTo(barrel2.hashCode());
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные hashCode при сравнении двух разных бочек с разными свойствами")
	void hashCode_differentBarrels_returnsDifferentHash() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(500.0).setStoredMaterial("Water").setBarrelMaterial("Plastic")
				.build();

		assertThat(barrel1.hashCode()).as("Две разные бочки с разными свойствами не должны быть равны по hashCode")
				.isNotEqualTo(barrel2.hashCode());
	}

	@Test
	@DisplayName("hashCode: должен возвращать false при сравнении двух разных бочек с одним разным свойством")
	void hashCode_onlyStoredMaterialIsDifferent_returnsDifferentHash() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Water").setBarrelMaterial("Steel")
				.build();

		assertThat(barrel1.hashCode()).as("Две разные бочки с одним разными свойством не должны быть равны по hashCode")
				.isNotEqualTo(barrel2.hashCode());
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 для разных бочек с одинаковыми свойствами")
	void compareTo_identicalBarrels_returnsZero() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel1.compareTo(barrel2)).as("Две разные бочки с одинаковыми свойствами должны возвращать 0")
				.isZero();
	}

	@Test
	@DisplayName("compareTo: должна возвращать 0 при сравнении бочки с самой собой")
	void compareTo_sameBarrel_returnsZero() {
		Barrel barrel = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel.compareTo(barrel)).as("Бочка должна быть равна себе и возвращать 0").isZero();
	}

	@Test
	@DisplayName("compareTo: должна возвращать отрицательное значение, если у первой бочки объем меньше, чем у второй")
	void compareTo_volumeIsLower_returnsNegative() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(500.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel1.compareTo(barrel2)).as("Бочка 1 должна быть меньше, чем бочка 2.").isNegative();
	}

	@Test
	@DisplayName("compareTo: должна возвращать отрицательное значение, если название хранимого материала первой бочки начинается с буквы, которая идет раньше по алфавиту, чем у второй бочки")
	void compareTo_storedMaterialIsEarlier_returnsNegative() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Water").setBarrelMaterial("Steel")
				.build();

		assertThat(barrel1.compareTo(barrel2))
				.as("Буква, с которой название хранимого материала в первой бочке идет раньше по алфавиту, чем у второй бочки")
				.isNegative();
	}

	@Test
	@DisplayName("compareTo: должна возвращать отрицательное значение, если название материала бочки начинается с буквы, которая идет раньше по алфавиту, чем у второй бочки")
	void compareTo_barrelMaterialIsEarlier_returnsNegative() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Plastic")
				.build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel1.compareTo(barrel2))
				.as("Буква, с которой название материала в первой бочке идет раньше по алфавиту, чем у второй бочки")
				.isNegative();
	}

	@Test
	@DisplayName("compareTo: должна возвращать положительное значение, если у первой бочки объем больше, чем у второй")
	void compareTo_volumeIsHigher_returnsPositive() {
		Barrel barrel1 = Barrel.builder().setVolume(500.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(300.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel1.compareTo(barrel2)).as("Бочка 1 должна быть больше, чем бочка 2.").isPositive();
	}

	@Test
	@DisplayName("compareTo: должна возвращать положительное значение, если название хранимого материала первой бочки начинается с буквы, которая идет позже по алфавиту, чем у второй бочки")
	void compareTo_storedMaterialIsLater_returnsPositive() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Water").setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();

		assertThat(barrel1.compareTo(barrel2))
				.as("Буква, с которой название хранимого материала в первой бочке идет позже по алфавиту, чем у второй бочки")
				.isPositive();
	}

	@Test
	@DisplayName("compareTo: должна возвращать положительное значение, если название материала бочки начинается с буквы, которая идет позже по алфавиту, чем у второй бочки")
	void compareTo_barrelMaterialIsLater_returnsPositive() {
		Barrel barrel1 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build();
		Barrel barrel2 = Barrel.builder().setVolume(200.0).setStoredMaterial("Oil").setBarrelMaterial("Plastic")
				.build();

		assertThat(barrel1.compareTo(barrel2))
				.as("Буква, с которой название материала в первой бочке идет позже по алфавиту, чем у второй бочки")
				.isPositive();
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при объеме равном 0")
	void validation_volumeEqualsZero_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Barrel.builder().setVolume(0.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build())
				.as("Ожидалось исключение при объеме 0.0").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Объем бочки должен быть числом строго больше 0.0 л");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при объеме меньше 0")
	void validation_volumeIsLessThanZero_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Barrel.builder().setVolume(-154.0).setStoredMaterial("Oil").setBarrelMaterial("Steel").build())
				.as("Ожидалось исключение при объеме меньше 0").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Объем бочки должен быть числом строго больше 0.0 л");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при объеме равном Double.NaN(не число)")
	void validation_Volume_IllegalArgumentException() {
		assertThatThrownBy(() -> Barrel.builder().setVolume(Double.NaN).setStoredMaterial("Oil").setBarrelMaterial("Steel").build())
				.as("Ожидалось исключение при объеме равном Double.NaN(не число)").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Объем бочки должен быть числом строго больше 0.0 л");

		assertThatThrownBy(() -> Barrel.builder().setVolume(Double.POSITIVE_INFINITY).setStoredMaterial("Oil").setBarrelMaterial("Steel")
				.build())
				.as("Ожидалось исключение при объеме равном Double.NaN(не число)").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Объем бочки должен быть числом строго больше 0.0 л");

		assertThatThrownBy(() -> Barrel.builder().setVolume(Double.NEGATIVE_INFINITY).setStoredMaterial("Oil").setBarrelMaterial("Steel")
				.build())
				.as("Ожидалось исключение при объеме равном Double.NaN(не число)").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Объем бочки должен быть числом строго больше 0.0 л");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом хранимом материале")
	void validation_storedMaterialIsBlank_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Barrel.builder().setVolume(154.3).setStoredMaterial("").setBarrelMaterial("Steel").build())
				.as("Ожидалось исключение при пустом хранимом материале").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Хранимый материал должен быть указан");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом материале бочки")
	void validation_emptyFields_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Barrel.builder().setVolume(154.3).setStoredMaterial("Oil").setBarrelMaterial("").build())
				.as("Ожидалось исключение при пустом материале бочки").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Материал бочки должен быть указан");

		assertThatThrownBy(() -> Barrel.builder().setVolume(154.3).setStoredMaterial("Oil").setBarrelMaterial("   ").build())
				.as("Ожидалось исключение при пустом материале бочки").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Материал бочки должен быть указан");
	}
}