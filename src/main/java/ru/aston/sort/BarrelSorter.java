package ru.aston.sort;


import ru.aston.model.Barrel;


import java.util.Arrays;

public class BarrelSorter {

	public static void quickSortBarrels(Barrel[] barrels, int leftIndex, int rightIndex, OrderType orderType) {
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
			quickSortBarrels(barrels, leftIndex, rightMarker, orderType);
		}
		if (rightIndex > leftMarker) {
			quickSortBarrels(barrels, leftMarker, rightIndex, orderType);
		}
	}

	public static void mergeSortBarrels(Barrel[] barrels, OrderType orderType) {
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

	public static void customSortBarrel(Barrel[] barrels, OrderType orderType) {
		if (barrels == null) {
			return;
		}
		Barrel[] evenValue = new Barrel[barrels.length];

		int k = 0;
		for (int i = 0; i < barrels.length; i++) {
			if (barrels[i].getVolume() % 2 == 0.0) {
				evenValue[k] = barrels[i];
				k++;
			}
		}

		Barrel[] evenValueTrim = Arrays.copyOf(evenValue, k);

		volumeSort(evenValueTrim, 0, evenValueTrim.length - 1, orderType);


		k = 0;
		for (int i = 0; i < barrels.length; i++) {
			if (barrels[i].getVolume() % 2 == 0.0) {
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
				while (evenValueTrim[leftMarker].compareTo(pivot) < 0) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].compareTo(pivot) > 0) {
					rightMarker--;
				}
			} else {
				while (evenValueTrim[leftMarker].compareTo(pivot) > 0) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].compareTo(pivot) < 0) {
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

