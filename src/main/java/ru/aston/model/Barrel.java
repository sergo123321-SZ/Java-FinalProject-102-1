package ru.aston.model;

public class Barrel {
	private final double volume;
	private final String storedMaterial;
	private final String barrelMaterial;

	public Barrel(double volume, String storedMaterial, String barrelMaterial) {
		if (storedMaterial == null || storedMaterial.trim().isEmpty()) {
			throw new IllegalArgumentException("Хранимый материал не может быть пустым или равным null");
		}
		if (barrelMaterial == null || barrelMaterial.trim().isEmpty()) {
			throw new IllegalArgumentException("Материал бочки не может быть пустым или равным null");
		}

		if (volume <= 0.0) {
			throw new IllegalArgumentException("Объем бочки должен быть больше 0. Передано: " + volume);
		}

		this.volume = volume;
		this.storedMaterial = storedMaterial.trim();
		this.barrelMaterial = barrelMaterial.trim();
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
