package ru.aston.sort;

import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MainSorter {

	public static List<Object> sort(Collection<Object> objects, SortType sortType, OrderType orderType) {
		if (objects == null || objects.size() < 2) {
			return null;
		}
		Object[] arrayObjects = objects.toArray();

		Student[] students = new Student[objects.size()];
		Car[] cars = new Car[objects.size()];
		Barrel[] barrels = new Barrel[objects.size()];

		if (arrayObjects[0] instanceof Student) {
			for (int i = 0; i < arrayObjects.length; i++) {
				students[i] = (Student) arrayObjects[i];
			}
			if (SortType.QUICK_SORT == sortType) {
				StudentSorter.quickSortStudents(students, 0, students.length - 1, orderType);
			}
			else if (SortType.MERGE_SORT == sortType) {
				StudentSorter.mergeSortStudents(students, orderType);
			}
			else if (SortType.CUSTOM_SORT == sortType) {
				StudentSorter.customSortStudent(students, orderType);
			}

			return Arrays.stream(students).collect(Collectors.toCollection(ArrayList::new));

		}
		else if (arrayObjects[0] instanceof Car) {
			for (int i = 0; i < arrayObjects.length; i++) {
				cars[i] = (Car) arrayObjects[i];
			}
			if (SortType.QUICK_SORT == sortType) {
				CarSorter.quickSortCars(cars, 0, students.length - 1, orderType);
			}
			else if (SortType.MERGE_SORT == sortType) {
				CarSorter.mergeSorCars(cars, orderType);
			}
			else if (SortType.CUSTOM_SORT == sortType) {
				CarSorter.customSortCar(cars, orderType);
			}

			return Arrays.stream(cars).collect(Collectors.toCollection(ArrayList::new));

		}
		else if (arrayObjects[0] instanceof Barrel) {

			for (int i = 0; i < arrayObjects.length; i++) {
				barrels[i] = (Barrel) arrayObjects[i];
			}
			if (SortType.QUICK_SORT == sortType) {
				BarrelSorter.quickSortBarrels(barrels, 0, students.length - 1, orderType);
			}
			else if (SortType.MERGE_SORT == sortType) {
				BarrelSorter.mergeSortBarrels(barrels, orderType);
			}
			else if (SortType.CUSTOM_SORT == sortType) {
				BarrelSorter.customSortBarrel(barrels, orderType);
			}

			return Arrays.stream(barrels).collect(Collectors.toCollection(ArrayList::new));
		}
		return Arrays.stream(students).collect(Collectors.toCollection(ArrayList::new));
	}

}
