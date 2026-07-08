package ru.aston.sort;

import ru.aston.model.Car;
import ru.aston.model.Student;

import java.util.Arrays;


public class CarSorter {

	public static void quickSortCars(Car[] cars, int leftIndex, int rightIndex, OrderType orderType) {
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
			}
			else {
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
			quickSortCars(cars, leftIndex, rightMarker, orderType);
		}
		if (rightIndex > leftMarker) {
			quickSortCars(cars, leftMarker, rightIndex, orderType);
		}
	}

	public static void mergeSorCars(Car[] cars, OrderType orderType) {
		Car[] temp = new Car[cars.length];
		mergeSortAsc(cars, 0, cars.length - 1, temp, orderType);
	}

	private static void mergeSortAsc(Car[] cars, int left, int right, Car[] temp, OrderType orderType) {
		if (left >= right) {
			return;
		}
		int mid = left + (right - left) / 2;
		mergeSortAsc(cars, left, mid, temp, orderType);
		mergeSortAsc(cars, mid + 1, right, temp, orderType);
		mergeAsc(cars, left, mid, right, temp, orderType);
	}

	private static void mergeAsc(Car[] cars, int left, int mid, int right, Car[] temp, OrderType orderType) {
		int i = left; // индекс левой части
		int j = mid + 1; // индекс правой части
		int k = left; // индекс в temp

		while (i <= mid && j <= right) {
			// для возрастания берём меньший элемент
			if (OrderType.NATURAL_ORDER == orderType) {
				if (cars[i].compareTo(cars[j]) <= 0) {
					temp[k++] = cars[i++];
				}
				else {
					temp[k++] = cars[j++];
				}
			}
			else {
				if (cars[i].compareTo(cars[j]) >= 0) {
					temp[k++] = cars[i++];
				}
				else {
					temp[k++] = cars[j++];
				}
			}
		}

		// копируем остатки
		while (i <= mid) {
			temp[k++] = cars[i++];
		}
		while (j <= right) {
			temp[k++] = cars[j++];
		}

		// возвращаем в исходный массив
		for (int p = left; p <= right; p++) {
			cars[p] = temp[p];
		}
	}

	public static void customSortCar(Car[] cars, OrderType orderType) {
		if (cars == null) {
			return;
		}
		Car[] evenValue = new Car[cars.length];

		int k = 0;
		for (int i = 0; i < cars.length; i++) {
			if (cars[i].getPower() % 2 == 0.0) {
				evenValue[k] = cars[i];
				k++;
			}
		}

		Car[] evenValueTrim = Arrays.copyOf(evenValue, k);

		powerSort(evenValueTrim, 0, evenValueTrim.length - 1, orderType);


		k = 0;
		for (int i = 0; i < cars.length; i++) {
			if (cars[i].getPower() % 2 == 0.0) {
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
				while (evenValueTrim[leftMarker].compareTo(pivot) < 0) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].compareTo(pivot) > 0) {
					rightMarker--;
				}
			}
			else {
				while (evenValueTrim[leftMarker].compareTo(pivot) > 0) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].compareTo(pivot) < 0) {
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

