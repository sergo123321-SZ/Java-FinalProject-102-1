package ru.aston.model;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Barrel {
	private final double volume;
	private final String storedMaterial;
	private final String barrelMaterial;

	private Barrel(@NotNull final Builder builder) {
		this(builder.volume, builder.storedMaterial, builder.barrelMaterial);
	}

	public Barrel(final double volume, @NotNull final String storedMaterial, @NotNull final String barrelMaterial) {
		if (volume <= 0.0) {
			throw new IllegalArgumentException("Объем бочки должен быть больше 0. Передано: " + volume);
		}

		this.storedMaterial = Objects.requireNonNull(storedMaterial, "Хранимый материал должен быть указан").trim();
		this.barrelMaterial = Objects.requireNonNull(barrelMaterial, "Материал бочки должен быть указан").trim();

		if (this.storedMaterial.isEmpty()) {
			throw new IllegalArgumentException("Хранимый материал не может быть пустым");
		}
		if (this.barrelMaterial.isEmpty()) {
			throw new IllegalArgumentException("Материал бочки не может быть пустым");
		}

		this.volume = volume;
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

		public Builder volume(double volume) {
			this.volume = volume;
			return this;
		}

		public Builder storedMaterial(String storedMaterial) {
			this.storedMaterial = storedMaterial;
			return this;
		}

		public Builder barrelMaterial(String barrelMaterial) {
			this.barrelMaterial = barrelMaterial;
			return this;
		}

		public Barrel build() {
			return new Barrel(this);
		}
	}
}