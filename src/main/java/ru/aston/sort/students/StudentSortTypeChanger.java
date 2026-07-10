package ru.aston.sort.students;

import ru.aston.sort.SortType;

public class StudentSortTypeChanger {

	public StudentSorter changeTypeSort(SortType sortType) {
		if (SortType.QUICK_SORT == sortType) {
			return new StudentQuickSorter();
		}
		else if (SortType.MERGE_SORT == sortType) {
			return new StudentMergeSorter();
		}
		else {
			return new StudentCustomSorter();
		}
	}
}
