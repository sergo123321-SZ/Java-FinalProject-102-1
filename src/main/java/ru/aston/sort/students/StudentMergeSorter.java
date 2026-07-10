package ru.aston.sort.students;

import ru.aston.model.Student;
import ru.aston.sort.OrderType;

public class StudentMergeSorter implements StudentSorter {
	@Override
	public void sortStudentsCollection(Student[] students, int leftIndex, int rightIndex, OrderType orderType) {
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

	private static void mergeAsc(Student[] students, int left, int mid, int right, Student[] temp,
			OrderType orderType)
	{
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
}
