package ru.aston.sort.cars;

import ru.aston.sort.SortType;


public class CarSortTypeChanger {

	public CarSorter changeTypeSort(SortType sortType) {
		if (SortType.QUICK_SORT == sortType) {
			return new CarQuickSorter();
		} else if (SortType.MERGE_SORT == sortType) {
			return new CarMergeSorter();
		} else {
			return new CarCustomSorter();
		}
	}
}
