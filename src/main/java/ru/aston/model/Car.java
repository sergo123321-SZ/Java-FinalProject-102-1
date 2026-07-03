package ru.aston.model;

import org.jetbrains.annotations.NotNull;
import java.time.Year;
import java.util.Objects;

public class Car implements Comparable<Car> {
	private final int power;
	private final String model;
	private final int productionYear;

	private Car(@NotNull final Builder builder) {
		this(builder.power, builder.model, builder.productionYear);
	}

	public Car(final int power, @NotNull final String model, final int productionYear) {
		this.power = power;
		if (power <= 0) {
			throw new IllegalArgumentException("Мощность автомобиля должна быть строго больше 0 л.с.");
		}
		this.model = Objects.requireNonNull(model, "Модель автомобиля не может быть пустой или равной null");
		if (model.isBlank()) {
			throw new IllegalArgumentException("Модель автомобиля не может быть пустой");
		}
		this.productionYear = productionYear;
		int currentYear = Year.now().getValue();

		if (productionYear < 1886) {
			throw new IllegalArgumentException("Год производства не может быть раньше 1886");
		}
		if (productionYear > currentYear) {
			throw new IllegalArgumentException("Год производства не может быть в будущем");
		}
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Car car))
			return false;

		return this.power == car.power && this.model.equals(car.model) && this.productionYear == car.productionYear;
	}

	@Override
	public int hashCode() {
		return Objects.hash(power, model, productionYear);
	}

	@Override
	public int compareTo(@NotNull Car other) {
		int result = Integer.compare(this.power, other.power);
		if (result != 0)
			return result;

		result = this.model.compareTo(other.model);
		if (result != 0)
			return result;

		return Integer.compare(this.productionYear, other.productionYear);
	}

	public static class Builder {
		private int power;
		private String model;
		private int productionYear;

		public Builder() {
		}

		public Builder setPower(int power) {
			this.power = power;
			return this;
		}

		public Builder setModel(String model) {
			this.model = model;
			return this;
		}

		public Builder setProductionYear(int productionYear) {
			this.productionYear = productionYear;
			return this;
		}

		public Car build() {
			return new Car(this);
		}
	}
}