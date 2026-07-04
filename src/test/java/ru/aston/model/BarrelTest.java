package ru.aston.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BarrelTest {

	@Test
	@DisplayName("equals: должен возвращать true при сравнении бочки с самой собой")
	void equals_shouldReturnTrueWhenTheBarrelIsTheSame() {
		Barrel barrel = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Assertions.assertEquals(barrel, barrel, "Бочка должна быть равной самой себе");
	}

	@Test
	@DisplayName("equals: должен возвращать true при сравнении двух разных бочек с одинаковыми свойствами")
	void equals_shouldReturnTrueWhenTwoBarrelsHaveTheSameAttributes() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Assertions.assertEquals(barrel1, barrel2, "Две разные бочки с одинаковыми свойствами должны быть равны");
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении бочки с null")
	void equals_shouldReturnFalseWhenComparingBarrelToNull() {
		Barrel barrel = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Assertions.assertNotEquals(null, barrel, "Бочка не должна быть равна null");
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении двух разных бочек с разными свойствами")
	void equals_shouldReturnFalseWhenComparingTwoDifferentBarrelsWithDifferentAttributes() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(500.0)
				.setStoredMaterial("Water")
				.setBarrelMaterial("Plastic")
				.build();
		Assertions.assertNotEquals(barrel1, barrel2, "Две разные бочки с разными свойствами не должны быть равны");
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении двух разных бочек с одним разным свойством")
	void equals_shouldReturnFalseWhenComparingTwoDifferentBarrelsWithOneDifferentAttribute() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Water")
				.setBarrelMaterial("Steel")
				.build();
		Assertions.assertNotEquals(barrel1, barrel2, "Две разные бочки с одним разными свойством не должны быть равны");
	}

	@Test
	@DisplayName("hashCode: должен быть одинаковым при сравнении двух разных бочек с одинаковыми свойствами")
	void hashCode_shouldReturnTrueWhenComparingHashCodesOfTwoBarrelsWithTheSameProperties() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Assertions.assertEquals(barrel1.hashCode(), barrel2.hashCode(), "Две разные бочки с одинаковыми свойствами должны быть равны по hashCode");
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные hashCode при сравнении двух разных бочек с разными свойствами")
	void hashCode_shouldReturnFalseWhenComparingTwoDifferentBarrelsWithDifferentAttributes() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(500.0)
				.setStoredMaterial("Water")
				.setBarrelMaterial("Plastic")
				.build();
		Assertions.assertNotEquals(barrel1.hashCode(), barrel2.hashCode(), "Две разные бочки с разными свойствами не должны быть равны по hashCode");
	}

	@Test
	@DisplayName("hashCode: должен возвращать false при сравнении двух разных бочек с одним разным свойством")
	void hashCode_shouldReturnFalseWhenComparingTwoDifferentBarrelsWithOneDifferentAttribute() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Water")
				.setBarrelMaterial("Steel")
				.build();
		Assertions.assertNotEquals(barrel1.hashCode(), barrel2.hashCode(), "Две разные бочки с одним разными свойством не должны быть равны по hashCode");
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 для разных бочек с одинаковыми свойствами")
	void compareTo_ShouldReturnZeroWhenBarrelsAreIdentical() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		int result = barrel1.compareTo(barrel2);
		Assertions.assertEquals(0, result, "Две разные бочки с одинаковыми свойствами должны возвращать 0");
	}

	@Test
	@DisplayName("compareTo: должна возвращать 0 при сравнении бочки с самой собой")
	void compareTo_shouldReturnZeroWhenComparingToItself() {
		Barrel barrel = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		int result = barrel.compareTo(barrel);
		Assertions.assertEquals(0, result, "Бочка должна быть равна себе и возвращать 0");
	}

	@Test
	@DisplayName("compareTo: должна возвращать отрицательное значение, если у первой бочки объем меньше, чем у второй")
	void compareTo_shouldReturnNegativeValueWhenTheVolumeOfTheFirstBarrelIsLessThanVolumeOfTheSecondBarrel() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(500.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		int result = barrel1.compareTo(barrel2);
		Assertions.assertTrue(result < 0, "Бочка 1 должна быть меньше, чем бочка 2.");
	}

	@Test
	@DisplayName("compareTo: должна возвращать отрицательное значение, если название хранимого материала первой бочки начинается с буквы, которая идет раньше по алфавиту, чем у второй бочки")
	void compareTo_shouldReturnNegativeValueWhenTheLetterOfTheStoredMaterialOfTheFirstBarrelGoesEarlierThanTheLetterOfTheSecondBarrel() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Water")
				.setBarrelMaterial("Steel")
				.build();
		int result = barrel1.compareTo(barrel2);
		Assertions.assertTrue(result < 0, "Буква, с которой  название хранимого материала в первой бочке идет раньше по алфавиту, чем у второй бочки");
	}

	@Test
	@DisplayName("compareTo: должна возвращать отрицательное значение, если название материала бочки начинается с буквы, которая идет раньше по алфавиту, чем у второй бочки")
	void compareTo_shouldReturnNegativeValueWhenTheLetterOfTheBarrelMaterialOfTheFirstBarrelGoesEarlierThanTheLetterOfTheSecondBarrel() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Plastic")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		int result = barrel1.compareTo(barrel2);
		Assertions.assertTrue(result < 0, "Буква, с которой  название хранимого материала в первой бочке идет раньше по алфавиту, чем у второй бочки");
	}


	@Test
	@DisplayName("compareTo: должна возвращать положительное значение, если у первой бочки объем меньше, чем у второй")
	void compareTo_shouldReturnPositiveValueWhenTheVolumeOfTheFirstBarrelIsMoreThanVolumeOfTheSecondBarrel() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(500.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(300.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		int result = barrel1.compareTo(barrel2);
		Assertions.assertTrue(result > 0, "Бочка 1 должна быть больше, чем бочка 2.");
	}

	@Test
	@DisplayName("compareTo: должна возвращать положительное значение, если название хранимого материала первой бочки начинается с буквы, которая идет позже по алфавиту, чем у второй бочки")
	void compareTo_shouldReturnPositiveValueWhenTheLetterOfTheStoredMaterialOfTheFirstBarrelGoesLaterThanTheLetterOfTheSecondBarrel() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Water")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		int result = barrel1.compareTo(barrel2);
		Assertions.assertTrue(result > 0, "Буква, с которой  название хранимого материала в первой бочке идет позже по алфавиту, чем у второй бочки");
	}

	@Test
	@DisplayName("compareTo: должна возвращать положительное значение, если название материала бочки начинается с буквы, которая идет позже по алфавиту, чем у второй бочки")
	void compareTo_shouldReturnPositiveValueWhenTheLetterOfTheBarrelMaterialOfTheFirstBarrelGoesLaterThanTheLetterOfTheSecondBarrel() {
		Barrel barrel1 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Steel")
				.build();
		Barrel barrel2 = Barrel.builder()
				.setVolume(200.0)
				.setStoredMaterial("Oil")
				.setBarrelMaterial("Plastic")
				.build();
		int result = barrel1.compareTo(barrel2);
		Assertions.assertTrue(result > 0, "Буква, с которой  название хранимого материала в первой бочке идет позже по алфавиту, чем у второй бочки");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при объеме равном 0")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheVolumeEqualsZero() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Barrel.builder()
						.setVolume(0.0)
						.setStoredMaterial("Oil")
						.setBarrelMaterial("Steel")
						.build(),
				"Ожидалось исключение при объеме 0.0"
		);
		Assertions.assertEquals("Объем бочки должен быть числом строго больше 0.0 л", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при объеме меньше 0")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheVolumeIsLessThanZero() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Barrel.builder()
						.setVolume(-154.0)
						.setStoredMaterial("Oil")
						.setBarrelMaterial("Steel")
						.build(),
				"Ожидалось исключение при объеме меньше 0"
		);
		Assertions.assertEquals("Объем бочки должен быть числом строго больше 0.0 л", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом хранимом материале")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheStoredMaterialIsBlank() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Barrel.builder()
						.setVolume(154.2)
						.setStoredMaterial("")
						.setBarrelMaterial("Steel")
						.build(),
				"Ожидалось исключение при пустом хранимом материале"
		);
		Assertions.assertEquals("Хранимый материал должен быть указан", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом материале бочки")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheBarrelMaterialIsBlank() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Barrel.builder()
						.setVolume(154.2)
						.setStoredMaterial("Oil")
						.setBarrelMaterial("")
						.build(),
				"Ожидалось исключение при пустом материале бочки"
		);
		Assertions.assertEquals("Материал бочки должен быть указан", exception.getMessage());
	}
}