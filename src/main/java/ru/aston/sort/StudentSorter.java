package ru.aston.sort;

import ru.aston.model.Student;

import java.util.Arrays;

public class StudentSorter {

	public static void quickSortStudents(Student[] students, int leftIndex, int rightIndex, OrderType orderType) {
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
			quickSortStudents(students, leftIndex, rightMarker, orderType);
		}
		if (rightIndex > leftMarker) {
			quickSortStudents(students, leftMarker, rightIndex, orderType);
		}
	}

	public static void mergeSortStudents(Student[] students, OrderType orderType) {
		Student[] temp = new Student[students.length];
		mergeSortAsc(students, 0, students.length - 1, temp, orderType);
	}

	private static void mergeSortAsc(Student[] students, int left, int right, Student[] temp, OrderType orderType) {
		if (left >= right) {
			return;
		}
		int mid = left + (right - left) / 2;
		mergeSortAsc(students, left, mid, temp, orderType);
		mergeSortAsc(students, mid + 1, right, temp, orderType);
		mergeAsc(students, left, mid, right, temp, orderType);
	}

	private static void mergeAsc(Student[] students, int left, int mid, int right, Student[] temp, OrderType orderType) {
		int i = left; // индекс левой части
		int j = mid + 1; // индекс правой части
		int k = left; // индекс в temp

		while (i <= mid && j <= right) {
			// для возрастания берём меньший элемент
			if (OrderType.NATURAL_ORDER == orderType) {
				if (students[i].compareTo(students[j]) <= 0) {
					temp[k++] = students[i++];
				}
				else {
					temp[k++] = students[j++];
				}
			}
			else {
				if (students[i].compareTo(students[j]) >= 0) {
					temp[k++] = students[i++];
				}
				else {
					temp[k++] = students[j++];
				}
			}
		}

		// копируем остатки
		while (i <= mid) {
			temp[k++] = students[i++];
		}
		while (j <= right) {
			temp[k++] = students[j++];
		}

		// возвращаем в исходный массив
		for (int p = left; p <= right; p++) {
			students[p] = temp[p];
		}
	}

	public static void customSortStudent(Student[] students, OrderType orderType) {
		if (students == null) {
			return;
		}
		Student[] evenValue = new Student[students.length];

		int k = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getAverageGrade() % 2 == 0.0) {
				evenValue[k] = students[i];
				k++;
			}
		}

		Student[] evenValueTrim = Arrays.copyOf(evenValue, k);

		averageGradeSort(evenValueTrim, 0, evenValueTrim.length - 1, orderType);


		k = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getAverageGrade() % 2 == 0.0) {
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

