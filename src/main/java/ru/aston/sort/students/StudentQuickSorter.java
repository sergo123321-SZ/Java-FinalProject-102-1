package ru.aston.sort.students;

import ru.aston.model.Student;
import ru.aston.sort.OrderType;

public class StudentQuickSorter implements StudentSorter {
	@Override
	public void sortStudentsCollection(Student[] students, int leftIndex, int rightIndex, OrderType orderType) {
		if (leftIndex >= rightIndex) {
			return;
		}
		Student pivot = students[(leftIndex + rightIndex) / 2];
		int leftMarker = leftIndex;
		int rightMarker = rightIndex;

		while (leftMarker <= rightMarker) {
			if (orderType == OrderType.NATURAL_ORDER) {
				while (students[leftMarker].compareTo(pivot) < 0) {
					leftMarker++;
				}

				while (students[rightMarker].compareTo(pivot) > 0) {
					rightMarker--;
				}
			}
			else {
				while (students[leftMarker].compareTo(pivot) > 0) {
					leftMarker++;
				}

				while (students[rightMarker].compareTo(pivot) < 0) {
					rightMarker--;
				}
			}
			if (leftMarker <= rightMarker) {
				Student swap = students[leftMarker];
				students[leftMarker] = students[rightMarker];
				students[rightMarker] = swap;
				leftMarker++;
				rightMarker--;
			}
		}
		if (leftIndex < rightMarker) {
			sortStudentsCollection(students, leftIndex, rightMarker, orderType);
		}
		if (rightIndex > leftMarker) {
			sortStudentsCollection(students, leftMarker, rightIndex, orderType);
		}
	}
}
