package ru.aston.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Barrel implements Comparable<Barrel> {
	private final double volume;
	private final String storedMaterial;
	private final String barrelMaterial;

	private Barrel(@NotNull final Builder builder) {
		this(builder.volume, builder.storedMaterial, builder.barrelMaterial);
	}

	public Barrel(final double volume, @NotNull final String storedMaterial, @NotNull final String barrelMaterial) {
		if (Double.isNaN(volume) || Double.isInfinite(volume) || volume <= 0.0) {
			throw new IllegalArgumentException("Объем бочки должен быть числом строго больше 0.0 л");
		}
		if (storedMaterial.isBlank()) {
			throw new IllegalArgumentException("Хранимый материал должен быть указан");
		}
		if (barrelMaterial.isBlank()) {
			throw new IllegalArgumentException("Материал бочки должен быть указан");
		}

		this.volume = volume;
		this.storedMaterial = storedMaterial;
		this.barrelMaterial = barrelMaterial;
	}

	public static Builder builder() {
		return new Builder();
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Barrel barrel))
			return false;

		return Double.compare(this.volume, barrel.volume) == 0 && this.storedMaterial.equals(barrel.storedMaterial)
				&& this.barrelMaterial.equals(barrel.barrelMaterial);
	}

	@Override
	public int hashCode() {
		return Objects.hash(volume, storedMaterial, barrelMaterial);
	}

	@Override
	public int compareTo(@NotNull Barrel other) {
		int result = Double.compare(this.volume, other.volume);
		if (result != 0)
			return result;

		result = this.storedMaterial.compareTo(other.storedMaterial);
		if (result != 0)
			return result;

		return this.barrelMaterial.compareTo(other.barrelMaterial);
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