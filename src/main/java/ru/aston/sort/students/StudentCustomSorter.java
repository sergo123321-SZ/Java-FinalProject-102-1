package ru.aston.sort.students;

import ru.aston.model.Student;
import ru.aston.sort.OrderType;

import java.util.Arrays;

public class StudentCustomSorter implements StudentSorter {
	@Override
	public void sortStudentsCollection(Student[] students, int leftIndex, int rightIndex, OrderType orderType) {
		if (students == null) {
			return;
		}
		Student[] evenValue = new Student[students.length];

		int k = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getAverageGrade() % 2 == 0) {
				evenValue[k] = students[i];
				k++;
			}
		}

		Student[] evenValueTrim = Arrays.copyOf(evenValue, k);

		averageGradeSort(evenValueTrim, 0, evenValueTrim.length - 1, orderType);


		k = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getAverageGrade() % 2 == 0) {
				students[i] = evenValueTrim[k];
				k++;
			}
		}
	}

	private static void averageGradeSort(Student[] evenValueTrim, int from, int to, OrderType orderType) {
		if (from >= to) {
			return;
		}

		Student pivot = evenValueTrim[(from + to) / 2];
		int leftMarker = from;
		int rightMarker = to;

		while (leftMarker <= rightMarker) {
			if (orderType == OrderType.NATURAL_ORDER) {
				while (evenValueTrim[leftMarker].getAverageGrade() > pivot.getAverageGrade()) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].getAverageGrade() > pivot.getAverageGrade()) {
					rightMarker--;
				}
			} else {
				while (evenValueTrim[leftMarker].getAverageGrade() > pivot.getAverageGrade()) {
					leftMarker++;
				}
				while (evenValueTrim[rightMarker].getAverageGrade() < pivot.getAverageGrade()) {
					rightMarker--;
				}
			}

			if (leftMarker <= rightMarker) {
				Student swap = evenValueTrim[leftMarker];
				evenValueTrim[leftMarker] = evenValueTrim[rightMarker];
				evenValueTrim[rightMarker] = swap;
				leftMarker++;
				rightMarker--;
			}
		}

		if (from < rightMarker) {
			averageGradeSort(evenValueTrim, from, rightMarker, orderType);
		}
		if (leftMarker < to) {
			averageGradeSort(evenValueTrim, leftMarker, to, orderType);
		}
	}
}
