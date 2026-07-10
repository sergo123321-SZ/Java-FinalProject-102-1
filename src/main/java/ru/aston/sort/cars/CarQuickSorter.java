package ru.aston.sort.cars;

import ru.aston.model.Car;
import ru.aston.sort.OrderType;

public class CarQuickSorter implements CarSorter {
	@Override
	public void sortCarCollection(Car[] cars, int leftIndex, int rightIndex, OrderType orderType) {
		if (leftIndex >= rightIndex) {
			return;
		}
		Car pivot = cars[(leftIndex + rightIndex) / 2];
		int leftMarker = leftIndex;
		int rightMarker = rightIndex;

		while (leftMarker <= rightMarker) {
			if (orderType == OrderType.NATURAL_ORDER) {
				while (cars[leftMarker].compareTo(pivot) < 0) {
					leftMarker++;
				}

				while (cars[rightMarker].compareTo(pivot) > 0) {
					rightMarker--;
				}
			} else {
				while (cars[leftMarker].compareTo(pivot) > 0) {
					leftMarker++;
				}

				while (cars[rightMarker].compareTo(pivot) < 0) {
					rightMarker--;
				}
			}
			if (leftMarker <= rightMarker) {
				Car swap = cars[leftMarker];
				cars[leftMarker] = cars[rightMarker];
				cars[rightMarker] = swap;
				leftMarker++;
				rightMarker--;
			}
		}
		if (leftIndex < rightMarker) {
			sortCarCollection(cars, leftIndex, rightMarker, orderType);
		}
		if (rightIndex > leftMarker) {
			sortCarCollection(cars, leftMarker, rightIndex, orderType);
		}
	}
}
