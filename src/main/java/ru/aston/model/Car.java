package ru.aston.model;

import org.jetbrains.annotations.NotNull;

import java.time.Year;
import java.util.Objects;

public class Car {
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
