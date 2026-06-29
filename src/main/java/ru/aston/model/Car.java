package ru.aston.model;

import java.time.Year;

public class Car {
	private final int power;
	private final String model;
	private final int productionYear;

	public Car(final int power, final String model, final int productionYear) {
		if (model == null || model.isBlank()) {
			throw new IllegalArgumentException("Модель автомобиля не может быть пустой или равной null");
		}

		if (power <= 0) {
			throw new IllegalArgumentException("Мощность автомобиля должна быть больше 0. Передано: " + power);
		}

		int currentYear = Year.now().getValue();
		if (productionYear < 1886 || productionYear > currentYear) {
			throw new IllegalArgumentException(
			        "Некорректный год производства. Должен быть от 1886 до текущего года." + currentYear
			);
		}

		this.power = power;
		this.model = model;
		this.productionYear = productionYear;
	}

	public int getPower() {
		return power;
	}

	public String getModel() {
		return model;
	}

	public int getProductionYear() {
		return productionYear;
	}

	@Override
	public String toString() {
		return String.format("Автомобиль [Модель: %s, Мощность: %d л.с., Год: %d]", model, power, productionYear);
	}
}