package ru.aston.sort.barrels;

import ru.aston.model.Barrel;
import ru.aston.sort.OrderType;

import java.util.Arrays;

public class BarrelCustomSorter implements BarrelSorter {
	@Override
	public void sortBarrelCollection(Barrel[] barrels, int leftIndex, int rightIndex, OrderType orderType) {
		if (barrels == null) {
			return;
		}
		Barrel[] evenValue = new Barrel[barrels.length];

		int k = 0;
		for (int i = 0; i < barrels.length; i++) {
			if (barrels[i].getVolume() % 2 == 0) {
				evenValue[k] = barrels[i];
				k++;
			}
		}

		Barrel[] evenValueTrim = Arrays.copyOf(evenValue, k);

		volumeSort(evenValueTrim, 0, evenValueTrim.length - 1, orderType);


		k = 0;
		for (int i = 0; i < barrels.length; i++) {
			if (barrels[i].getVolume() % 2 == 0) {
				barrels[i] = evenValueTrim[k];
				k++;
			}
		}
	}

	private static void volumeSort(Barrel[] evenValueTrim, int from, int to, OrderType orderType) {
		if (from >= to) {
			return;
		}

		Barrel pivot = evenValueTrim[(from + to) / 2];
		int leftMarker = from;
		int rightMarker = to;

		while (leftMarker <= rightMarker) {
			if (orderType == OrderType.NATURAL_ORDER) {
				while (evenValueTrim[leftMarker].getVolume() < pivot.getVolume()) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].getVolume() > pivot.getVolume()) {
					rightMarker--;
				}
			}
			else {
				while (evenValueTrim[leftMarker].getVolume() > pivot.getVolume()) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].getVolume() < pivot.getVolume()) {
					rightMarker--;
				}
			}

			if (leftMarker <= rightMarker) {
				Barrel swap = evenValueTrim[leftMarker];
				evenValueTrim[leftMarker] = evenValueTrim[rightMarker];
				evenValueTrim[rightMarker] = swap;
				leftMarker++;
				rightMarker--;
			}
		}

		if (from < rightMarker) {
			volumeSort(evenValueTrim, from, rightMarker, orderType);
		}
		if (leftMarker < to) {
			volumeSort(evenValueTrim, leftMarker, to, orderType);
		}
	}
}
