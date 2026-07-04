package ru.aston.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarTest {

	@Test
	@DisplayName("equals: должен возвращать true при сравнении машины с самой собой")
	void equals_sameCar_returnsTrue() {
		Car car = Car.builder().setPower(245).setModel("BMW 3").setProductionYear(2023).build();

		assertThat(car).as("Машина должна быть равной самой себе").isEqualTo(car);
	}

	@Test
	@DisplayName("equals: должен возвращать true для разных машин с одинаковыми свойствами")
	void equals_identicalCars_returnsTrue() {
		Car car1 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2024).build();
		Car car2 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2024).build();

		assertThat(car1).as("Машины с одинаковыми свойствами должны быть равны").isEqualTo(car2);
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении с null")
	void equals_comparedWithNull_returnsFalse() {
		Car car = Car.builder().setPower(150).setModel("Honda Civic").setProductionYear(2022).build();

		assertThat(car).as("Машина не должна быть равной null").isNotNull();
	}

	@Test
	@DisplayName("equals: должен возвращать false для машин с разными свойствами")
	void equals_differentCars_returnsFalse() {
		Car car1 = Car.builder().setPower(245).setModel("BMW 3").setProductionYear(2023).build();
		Car car2 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2024).build();

		assertThat(car1).as("Машины с разными свойствами не должны быть равны").isNotEqualTo(car2);
	}

	@Test
	@DisplayName("equals: должен возвращать false, если отличается только одно свойство")
	void equals_onlyPowerIsDifferent_returnsFalse() {
		Car car1 = Car.builder().setPower(306).setModel("Nissan Z").setProductionYear(2023).build();
		Car car2 = Car.builder().setPower(400).setModel("Nissan Z").setProductionYear(2023).build();

		assertThat(car1).as("Машины с одним разным свойством не должны быть равны").isNotEqualTo(car2);
	}

	@Test
	@DisplayName("hashCode: должен быть одинаковым у машин с идентичными свойствами")
	void hashCode_identicalCars_returnsSameHash() {
		Car car1 = Car.builder().setPower(340).setModel("Porsche Cayman").setProductionYear(2022).build();
		Car car2 = Car.builder().setPower(340).setModel("Porsche Cayman").setProductionYear(2022).build();

		assertThat(car1.hashCode()).as("Хэш-коды одинаковых машин должны совпадать").isEqualTo(car2.hashCode());
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды при сравнении машин с разными свойствами")
	void hashCode_differentCars_returnsDifferentHash() {
		Car car1 = Car.builder().setPower(150).setModel("Honda Civic").setProductionYear(2022).build();
		Car car2 = Car.builder().setPower(612).setModel("Mercedes E63").setProductionYear(2021).build();

		assertThat(car1.hashCode()).as("У разных машин хэш-коды должны отличаться").isNotEqualTo(car2.hashCode());
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды, если разное только одно свойство")
	void hashCode_onlyModelIsDifferent_returnsDifferentHash() {
		Car car1 = Car.builder().setPower(245).setModel("BMW 3").setProductionYear(2023).build();
		Car car2 = Car.builder().setPower(245).setModel("Audi A4").setProductionYear(2023).build();

		assertThat(car1.hashCode()).as("Машины с разной моделью должны иметь разный хэш-код")
				.isNotEqualTo(car2.hashCode());
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 при сравнении машины с самой собой")
	void compareTo_sameCar_returnZero() {
		Car car = Car.builder().setPower(245).setModel("BMW 3").setProductionYear(2023).build();
		assertThat(car.compareTo(car)).as("Машина должна быть равна сама себе").isZero();
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 для машин с одинаковыми свойствами")
	void compareTo_identicalCars_returnsZero() {
		Car car1 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2024).build();
		Car car2 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2024).build();

		assertThat(car1.compareTo(car2)).as("Идентичные машины должны возвращать 0").isZero();
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если у первой машины мощность меньше")
	void compareTo_powerIsLower_returnsNegative() {
		Car car1 = Car.builder().setPower(150).setModel("Honda Civic").setProductionYear(2022).build();
		Car car2 = Car.builder().setPower(200).setModel("Honda Civic").setProductionYear(2022).build();

		assertThat(car1.compareTo(car2)).as("Результат должен быть меньше 0, так как мощность меньше").isNegative();
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если у первой машины мощность больше")
	void compareTo_powerIsHigher_returnsPositive() {
		Car car1 = Car.builder().setPower(340).setModel("BMW 3").setProductionYear(2023).build();
		Car car2 = Car.builder().setPower(245).setModel("BMW 3").setProductionYear(2023).build();

		assertThat(car1.compareTo(car2)).as("Результат должен быть больше 0, так как мощность больше").isPositive();
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если название модели первой машины идет раньше по алфавиту")
	void compareTo_modelIsEarlier_returnsNegative() {
		Car car1 = Car.builder().setPower(245).setModel("Audi A4").setProductionYear(2023).build();
		Car car2 = Car.builder().setPower(245).setModel("BMW 3").setProductionYear(2023).build();

		assertThat(car1.compareTo(car2)).as("Результат должен быть меньше 0, так как модель Audi раньше BMW")
				.isNegative();
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если название модели первой машины идет позже по алфавиту")
	void compareTo_modelIsLater_returnsPositive() {
		Car car1 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2024).build();
		Car car2 = Car.builder().setPower(200).setModel("Honda Civic").setProductionYear(2024).build();

		assertThat(car1.compareTo(car2)).as("Результат должен быть больше 0, так как модель Camry позже Civic")
				.isPositive();
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если год производства первой машины меньше")
	void compareTo_productionYearIsLower_returnsNegative() {
		Car car1 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2018).build();
		Car car2 = Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(2024).build();

		assertThat(car1.compareTo(car2)).as("Результат должен быть меньше 0, так как год меньше").isNegative();
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если год производства первой машины больше")
	void compareTo_productionYearIsHigher_returnsPositive() {
		Car car1 = Car.builder().setPower(150).setModel("Honda Civic").setProductionYear(2025).build();
		Car car2 = Car.builder().setPower(150).setModel("Honda Civic").setProductionYear(2022).build();

		assertThat(car1.compareTo(car2)).as("Результат должен быть больше 0, так как год больше").isPositive();
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при мощности равной 0")
	void validation_powerEqualsZero_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Car.builder().setPower(0).setModel("BMW 3").setProductionYear(2023).build())
				.as("Ожидалось исключение при мощности 0").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Мощность автомобиля должна быть строго больше 0 л.с.");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при мощности меньше 0")
	void validation_powerIsLessThanZero_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Car.builder().setPower(-50).setModel("Toyota Camry").setProductionYear(2024).build())
				.as("Ожидалось исключение при мощности меньше 0").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Мощность автомобиля должна быть строго больше 0 л.с.");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом названии модели")
	void validation_modelIsBlank_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Car.builder().setPower(150).setModel("").setProductionYear(2022).build())
				.as("Ожидалось исключение при пустом названии модели").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Модель автомобиля должна быть указана");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при годе производства меньше 1886")
	void validation_productionYearIsTooOld_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Car.builder().setPower(245).setModel("Audi A4").setProductionYear(1885).build())
				.as("Ожидалось исключение при годе производства меньше 1886")
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Год производства должен быть в диапазоне от 1886 до текущего года");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при годе производства больше текущего года")
	void validation_productionYearIsInFuture_throwsIllegalArgumentException() {
		int nextYear = Year.now().getValue() + 1;

		assertThatThrownBy(
				() -> Car.builder().setPower(200).setModel("Toyota Camry").setProductionYear(nextYear).build()
		).as("Ожидалось исключение при годе производства в будущем").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Год производства должен быть в диапазоне от 1886 до текущего года");
	}
}