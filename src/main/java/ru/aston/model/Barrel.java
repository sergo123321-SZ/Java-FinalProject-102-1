package ru.aston.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Barrel {
	private final double volume;
	private final String storedMaterial;
	private final String barrelMaterial;

	private Barrel(@NotNull final Builder builder) {
		this(builder.volume, builder.storedMaterial, builder.barrelMaterial);
	}

	public Barrel(final double volume, @NotNull final String storedMaterial, @NotNull final String barrelMaterial) {

		this.volume = volume;
		this.storedMaterial = Objects.requireNonNull(storedMaterial, "Хранимый материал должен быть указан");
		this.barrelMaterial = Objects.requireNonNull(barrelMaterial, "Материал бочки должен быть указан");
	}

	public double getVolume() {
		return volume;
	}

	public String getStoredMaterial() {
		return storedMaterial;
	}

	public String getBarrelMaterial() {
		return barrelMaterial;
	}

	@Override
	public String toString() {
		return String.format(
				"Бочка [Объем: %.1f л, Содержимое: %s, Материал бочки: %s]", volume, storedMaterial, barrelMaterial
		);
	}

	public static class Builder {
		private double volume;
		private String storedMaterial;
		private String barrelMaterial;

		public Builder() {
		}

		public Builder setVolume(double volume) {
			this.volume = volume;
			return this;
		}

		public Builder setStoredMaterial(String storedMaterial) {
			this.storedMaterial = storedMaterial;
			return this;
		}

		public Builder setBarrelMaterial(String barrelMaterial) {
			this.barrelMaterial = barrelMaterial;
			return this;
		}

		public Barrel build() {
			return new Barrel(this);
		}
	}
}