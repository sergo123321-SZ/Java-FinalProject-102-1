package ru.aston.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Year;

class CarTest {

	@Test
	@DisplayName("equals: должен возвращать true при сравнении машины с самой собой")
	void equals_shouldReturnTrueWhenTheCarIsTheSame() {
		Car car = Car.builder()
				.setPower(245)
				.setModel("BMW 3")
				.setProductionYear(2023)
				.build();

		Assertions.assertEquals(car, car, "Машина должна быть равной самой себе");
	}

	@Test
	@DisplayName("equals: должен возвращать true для разных машин с одинаковыми свойствами")
	void equals_shouldReturnTrueWhenCarsAreIdentical() {
		Car car1 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2024)
				.build();
		Car car2 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2024)
				.build();
		Assertions.assertEquals(car1, car2, "Машины с одинаковыми свойствами должны быть равны");
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении с null")
	void equals_shouldReturnFalseWhenComparedWithNull() {
		Car car = Car.builder()
				.setPower(150)
				.setModel("Honda Civic")
				.setProductionYear(2022)
				.build();
		Assertions.assertNotEquals(null, car, "Машина не должна быть равной null");
	}

	@Test
	@DisplayName("equals: должен возвращать false для машин с разными свойствами")
	void equals_shouldReturnFalseWhenComparingTwoDifferentCarsWithDifferentAttributes() {
		Car car1 = Car.builder()
				.setPower(245)
				.setModel("BMW 3")
				.setProductionYear(2023)
				.build();
		Car car2 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2024)
				.build();
		Assertions.assertNotEquals(car1, car2, "Машины с разными свойствами не должны быть равны");
	}

	@Test
	@DisplayName("equals: должен возвращать false, если отличается только мощность")
	void equals_shouldReturnFalseWhenComparingCarsWithOneDifferentAttribute() {
		Car car1 = Car.builder()
				.setPower(306)
				.setModel("Nissan Z")
				.setProductionYear(2023)
				.build();
		Car car2 = Car.builder()
				.setPower(400)
				.setModel("Nissan Z")
				.setProductionYear(2023)
				.build();
		Assertions.assertNotEquals(car1, car2, "Машины с разной мощностью не должны быть равны");
	}

	@Test
	@DisplayName("hashCode: должен быть одинаковым у машин с идентичными свойствами")
	void hashCode_shouldReturnSameHashWhenCarsAreIdentical() {
		Car car1 = Car.builder()
				.setPower(340)
				.setModel("Porsche Cayman")
				.setProductionYear(2022)
				.build();
		Car car2 = Car.builder()
				.setPower(340)
				.setModel("Porsche Cayman")
				.setProductionYear(2022)
				.build();
		Assertions.assertEquals(car1.hashCode(), car2.hashCode(), "Хэш-коды одинаковых машин должны совпадать");
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды при сравнении машин с разными свойствами")
	void hashCode_shouldReturnDifferentHashWhenComparingTwoDifferentCarsWithDifferentAttributes() {
		Car car1 = Car.builder()
				.setPower(150)
				.setModel("Honda Civic")
				.setProductionYear(2022)
				.build();
		Car car2 = Car.builder()
				.setPower(612)
				.setModel("Mercedes E63")
				.setProductionYear(2021)
				.build();
		Assertions.assertNotEquals(car1.hashCode(), car2.hashCode(), "У разных машин хэш-коды должны отличаться");
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды, если изменена только модель")
	void hashCode_shouldReturnDifferentHashWhenComparingTwoDifferentCarsWithOneDifferentAttribute() {
		Car car1 = Car.builder()
				.setPower(245)
				.setModel("BMW 3")
				.setProductionYear(2023)
				.build();
		Car car2 = Car.builder()
				.setPower(245)
				.setModel("Audi A4")
				.setProductionYear(2023)
				.build();
		Assertions.assertNotEquals(car1.hashCode(), car2.hashCode(), "Машины с разной моделью должны иметь разный хэш-код");
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 для машин с одинаковыми свойствами")
	void compareTo_shouldReturnZeroWhenCarsAreIdentical() {
		Car car1 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2024)
				.build();
		Car car2 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2024)
				.build();
		Assertions.assertEquals(0, car1.compareTo(car2), "Идентичные машины должны возвращать 0");
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если у первой машины мощность меньше")
	void compareTo_shouldReturnNegativeValueWhenThePowerOfTheFirstCarIsLessThanPowerOfTheSecondCar() {
		Car car1 = Car.builder()
				.setPower(150)
				.setModel("Honda Civic")
				.setProductionYear(2022)
				.build();
		Car car2 = Car.builder()
				.setPower(200)
				.setModel("Honda Civic")
				.setProductionYear(2022)
				.build();
		Assertions.assertTrue(car1.compareTo(car2) < 0, "Результат должен быть меньше 0, так как мощность меньше");
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если у первой машины мощность больше")
	void compareTo_shouldReturnPositiveValueWhenThePowerOfTheFirstCarIsGreaterThanPowerOfTheSecondCar() {
		Car car1 = Car.builder()
				.setPower(340)
				.setModel("BMW 3")
				.setProductionYear(2023)
				.build();
		Car car2 = Car.builder()
				.setPower(245)
				.setModel("BMW 3")
				.setProductionYear(2023)
				.build();

		Assertions.assertTrue(car1.compareTo(car2) > 0, "Результат должен быть больше 0, так как мощность больше");
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если название модели первой машины идет раньше по алфавиту")
	void compareTo_shouldReturnNegativeValueWhenTheLetterOfTheModelOfTheFirstCarGoesEarlierThanTheLetterOfTheSecondCar() {
		Car car1 = Car.builder()
				.setPower(245)
				.setModel("Audi A4")
				.setProductionYear(2023)
				.build();
		Car car2 = Car.builder()
				.setPower(245)
				.setModel("BMW 3")
				.setProductionYear(2023)
				.build();

		Assertions.assertTrue(car1.compareTo(car2) < 0, "Результат должен быть меньше 0, так как модель Audi раньше BMW");
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если название модели первой машины идет позже по алфавиту")
	void compareTo_shouldReturnPositiveValueWhenTheLetterOfTheModelOfTheFirstCarGoesLaterThanTheLetterOfTheSecondCar() {
		Car car1 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2024)
				.build();
		Car car2 = Car.builder()
				.setPower(200)
				.setModel("Honda Civic")
				.setProductionYear(2024)
				.build();

		Assertions.assertTrue(car1.compareTo(car2) > 0, "Результат должен быть больше 0, так как модель Camry позже Civic");
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если год производства первой машины меньше")
	void compareTo_shouldReturnNegativeValueWhenTheProductionYearOfTheFirstCarIsLessThanProductionYearOfTheSecondCar() {
		Car car1 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2018)
				.build();
		Car car2 = Car.builder()
				.setPower(200)
				.setModel("Toyota Camry")
				.setProductionYear(2024)
				.build();

		Assertions.assertTrue(car1.compareTo(car2) < 0, "Результат должен быть меньше 0, так как год меньше");
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если год производства первой машины больше")
	void compareTo_shouldReturnPositiveValueWhenTheProductionYearOfTheFirstCarIsGreaterThanProductionYearOfTheSecondCar() {
		Car car1 = Car.builder()
				.setPower(150)
				.setModel("Honda Civic")
				.setProductionYear(2025)
				.build();
		Car car2 = Car.builder()
				.setPower(150)
				.setModel("Honda Civic")
				.setProductionYear(2022)
				.build();

		Assertions.assertTrue(car1.compareTo(car2) > 0, "Результат должен быть больше 0, так как год больше");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при мощности равной 0")
	void validation_ShouldThrowIllegalArgumentExceptionWhenThePowerEqualsZero() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Car.builder()
						.setPower(0)
						.setModel("BMW 3")
						.setProductionYear(2023)
						.build(),
				"Ожидалось исключение при мощности 0"
		);
		Assertions.assertEquals("Мощность автомобиля должна быть строго больше 0 л.с.", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при мощности меньше 0")
	void validation_ShouldThrowIllegalArgumentExceptionWhenThePowerIsLessThanZero() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Car.builder()
						.setPower(-50)
						.setModel("Toyota Camry")
						.setProductionYear(2024)
						.build(),
				"Ожидалось исключение при мощности меньше 0"
		);
		Assertions.assertEquals("Мощность автомобиля должна быть строго больше 0 л.с.", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом названии модели")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheModelIsBlank() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Car.builder()
						.setPower(150)
						.setModel("")
						.setProductionYear(2022)
						.build(),
				"Ожидалось исключение при пустом названии модели"
		);
		Assertions.assertEquals("Модель автомобиля должна быть указана", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при годе производства меньше 1886")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheProductionYearIsTooOld() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Car.builder()
						.setPower(245)
						.setModel("Audi A4")
						.setProductionYear(1885)
						.build(),
				"Ожидалось исключение при годе производства меньше 1886"
		);
		Assertions.assertEquals("Год производства должен быть в диапазоне от 1886 до текущего года", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при годе производства больше текущего года")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheProductionYearIsInFuture() {
		int nextYear = Year.now().getValue() + 1;
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Car.builder()
						.setPower(200)
						.setModel("Toyota Camry")
						.setProductionYear(nextYear)
						.build(),
				"Ожидалось исключение при годе производства в будущем"
		);
		Assertions.assertEquals("Год производства должен быть в диапазоне от 1886 до текущего года", exception.getMessage());
	}
}