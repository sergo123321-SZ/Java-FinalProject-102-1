package ru.aston.model;

public class Barrel {
	private final double volume;
	private final String storedMaterial;
	private final String barrelMaterial;

	public Barrel(final double volume, final String storedMaterial, final String barrelMaterial) {
		if (storedMaterial == null || storedMaterial.isBlank()) {
			throw new IllegalArgumentException("Хранимый материал не может быть пустым или равным null");
		}
		if (barrelMaterial == null || barrelMaterial.isBlank()) {
			throw new IllegalArgumentException("Материал бочки не может быть пустым или равным null");
		}

		if (volume <= 0.0) {
			throw new IllegalArgumentException("Объем бочки должен быть больше 0. Передано: " + volume);
		}

		this.volume = volume;
		this.storedMaterial = storedMaterial;
		this.barrelMaterial = barrelMaterial;
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
}
