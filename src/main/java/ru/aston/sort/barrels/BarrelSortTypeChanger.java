package ru.aston.sort.barrels;

import ru.aston.sort.SortType;

public class BarrelSortTypeChanger {

	public BarrelSorter changeTypeSort(SortType sortType) {
		if (SortType.QUICK_SORT == sortType) {
			return new BarrelQuickSorter();
		}
		else if (SortType.MERGE_SORT == sortType) {
			return new BarrelMergeSorter();
		}
		else {
			return new BarrelCustomSorter();
		}
	}
}
