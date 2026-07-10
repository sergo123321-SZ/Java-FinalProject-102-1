package ru.aston.sort.cars;

import ru.aston.model.Car;
import ru.aston.sort.OrderType;

import java.util.Arrays;

public class CarCustomSorter implements CarSorter {


	@Override
	public void sortCarCollection(Car[] cars, int leftIndex, int rightIndex, OrderType orderType) {
		if (cars == null) {
			return;
		}
		Car[] evenValue = new Car[cars.length];

		int k = 0;
		for (int i = 0; i < cars.length; i++) {
			if (cars[i].getPower() % 2 == 0) {
				evenValue[k] = cars[i];
				k++;
			}
		}

		Car[] evenValueTrim = Arrays.copyOf(evenValue, k);

		powerSort(evenValueTrim, 0, evenValueTrim.length - 1, orderType);


		k = 0;
		for (int i = 0; i < cars.length; i++) {
			if (cars[i].getPower() % 2 == 0) {
				cars[i] = evenValueTrim[k];
				k++;
			}
		}
	}

	private static void powerSort(Car[] evenValueTrim, int from, int to, OrderType orderType) {
		if (from >= to) {
			return;
		}

		Car pivot = evenValueTrim[(from + to) / 2];
		int leftMarker = from;
		int rightMarker = to;

		while (leftMarker <= rightMarker) {
			if (orderType == OrderType.NATURAL_ORDER) {
				while (evenValueTrim[leftMarker].getPower() < pivot.getPower()) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].getPower() > pivot.getPower()) {
					rightMarker--;
				}
			} else {
				while (evenValueTrim[leftMarker].getPower() > pivot.getPower()) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].getPower() < pivot.getPower()) {
					rightMarker--;
				}
			}

			if (leftMarker <= rightMarker) {
				Car swap = evenValueTrim[leftMarker];
				evenValueTrim[leftMarker] = evenValueTrim[rightMarker];
				evenValueTrim[rightMarker] = swap;
				leftMarker++;
				rightMarker--;
			}
		}

		if (from < rightMarker) {
			powerSort(evenValueTrim, from, rightMarker, orderType);
		}
		if (leftMarker < to) {
			powerSort(evenValueTrim, leftMarker, to, orderType);
		}
	}
}
