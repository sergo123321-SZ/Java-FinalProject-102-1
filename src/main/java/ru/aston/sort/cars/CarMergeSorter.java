package ru.aston.sort.cars;

import ru.aston.model.Car;
import ru.aston.sort.OrderType;

public class CarMergeSorter implements CarSorter {
	@Override
	public void sortCarCollection(Car[] cars, int leftIndex, int rightIndex, OrderType orderType) {
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
				} else {
					temp[k++] = cars[j++];
				}
			} else {
				if (cars[i].compareTo(cars[j]) >= 0) {
					temp[k++] = cars[i++];
				} else {
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
}
