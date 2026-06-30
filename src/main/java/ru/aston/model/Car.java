package ru.aston.model;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

public class Car implements Comparable<Car> {
	private final int power;
	private final String model;
	private final int productionYear;

	private Car(@NotNull final Builder builder) {
		this(builder.power, builder.model, builder.productionYear);
	}

	public Car(final int power, @NotNull final String model, final int productionYear) {
		this.model = Objects.requireNonNull(model, "Модель автомобиля не может быть пустой или равной null");
		this.power = power;
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

	@Override
	public int compareTo(@NotNull Car other) {
		return this.model.compareTo(other.model);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Car car = (Car) o;
		return power == car.power && productionYear == car.productionYear && model.equals(car.model);
	}

	@Override
	public int hashCode() {
		return Objects.hash(power, model, productionYear);
	}

	public static final Comparator<Car> BY_POWER = Comparator.comparingInt(Car::getPower);

	public static final Comparator<Car> BY_YEAR = Comparator.comparingInt(Car::getProductionYear);

	public static final Comparator<Car> BY_MODEL_THEN_POWER_DESC = Comparator.comparing(Car::getModel)
			.thenComparing(Comparator.comparingInt(Car::getPower).reversed());

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