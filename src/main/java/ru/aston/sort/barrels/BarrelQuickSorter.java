package ru.aston.sort.barrels;

import ru.aston.model.Barrel;
import ru.aston.sort.OrderType;

public class BarrelQuickSorter implements BarrelSorter {
	@Override
	public void sortBarrelCollection(Barrel[] barrels, int leftIndex, int rightIndex, OrderType orderType) {
		if (leftIndex >= rightIndex) {
			return;
		}
		Barrel pivot = barrels[(leftIndex + rightIndex) / 2];
		int leftMarker = leftIndex;
		int rightMarker = rightIndex;

		while (leftMarker <= rightMarker) {
			if (orderType == OrderType.NATURAL_ORDER) {
				while (barrels[leftMarker].compareTo(pivot) < 0) {
					leftMarker++;
				}

				while (barrels[rightMarker].compareTo(pivot) > 0) {
					rightMarker--;
				}
			} else {
				while (barrels[leftMarker].compareTo(pivot) > 0) {
					leftMarker++;
				}

				while (barrels[rightMarker].compareTo(pivot) < 0) {
					rightMarker--;
				}
			}
			if (leftMarker <= rightMarker) {
				Barrel swap = barrels[leftMarker];
				barrels[leftMarker] = barrels[rightMarker];
				barrels[rightMarker] = swap;
				leftMarker++;
				rightMarker--;
			}
		}
		if (leftIndex < rightMarker) {
			sortBarrelCollection(barrels, leftIndex, rightMarker, orderType);
		}
		if (rightIndex > leftMarker) {
			sortBarrelCollection(barrels, leftMarker, rightIndex, orderType);
		}
	}

}
