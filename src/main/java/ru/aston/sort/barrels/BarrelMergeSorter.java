package ru.aston.sort.barrels;

import ru.aston.model.Barrel;
import ru.aston.sort.OrderType;

public class BarrelMergeSorter implements BarrelSorter {
	@Override
	public void sortBarrelCollection(Barrel[] barrels, int leftIndex, int rightIndex, OrderType orderType) {
		Barrel[] temp = new Barrel[barrels.length];
		mergeSortAsc(barrels, 0, barrels.length - 1, temp, orderType);
	}

	private static void mergeSortAsc(Barrel[] barrels, int left, int right, Barrel[] temp, OrderType orderType) {
		if (left >= right) {
			return;
		}
		int mid = left + (right - left) / 2;
		mergeSortAsc(barrels, left, mid, temp, orderType);
		mergeSortAsc(barrels, mid + 1, right, temp, orderType);
		mergeAsc(barrels, left, mid, right, temp, orderType);
	}

	private static void mergeAsc(Barrel[] barrels, int left, int mid, int right, Barrel[] temp, OrderType orderType) {
		int i = left; // индекс левой части
		int j = mid + 1; // индекс правой части
		int k = left; // индекс в temp

		while (i <= mid && j <= right) {
			// для возрастания берём меньший элемент
			if (OrderType.NATURAL_ORDER == orderType) {
				if (barrels[i].compareTo(barrels[j]) <= 0) {
					temp[k++] = barrels[i++];
				} else {
					temp[k++] = barrels[j++];
				}
			} else {
				if (barrels[i].compareTo(barrels[j]) >= 0) {
					temp[k++] = barrels[i++];
				} else {
					temp[k++] = barrels[j++];
				}
			}
		}

		// копируем остатки
		while (i <= mid) {
			temp[k++] = barrels[i++];
		}
		while (j <= right) {
			temp[k++] = barrels[j++];
		}

		// возвращаем в исходный массив
		for (int p = left; p <= right; p++) {
			barrels[p] = temp[p];
		}
	}
}
