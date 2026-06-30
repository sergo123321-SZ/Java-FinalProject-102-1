package ru.aston.model;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

public class Barrel implements Comparable<Barrel> {
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

	@Override
	public int compareTo(@NotNull Barrel other) {
		return Double.compare(this.volume, other.volume);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Barrel barrel = (Barrel) o;
		return Double.compare(barrel.volume, volume) == 0 && storedMaterial.equals(barrel.storedMaterial)
				&& barrelMaterial.equals(barrel.barrelMaterial);
	}

	@Override
	public int hashCode() {
		return Objects.hash(volume, storedMaterial, barrelMaterial);
	}

	public static final Comparator<Barrel> BY_STORED_MATERIAL = Comparator.comparing(Barrel::getStoredMaterial);

	public static final Comparator<Barrel> BY_BARREL_MATERIAL = Comparator.comparing(Barrel::getBarrelMaterial);

	public static final Comparator<Barrel> BY_MATERIAL_THEN_VOLUME_DESC = Comparator
			.comparing(Barrel::getStoredMaterial)
			.thenComparing(Comparator.comparingDouble(Barrel::getVolume).reversed());

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