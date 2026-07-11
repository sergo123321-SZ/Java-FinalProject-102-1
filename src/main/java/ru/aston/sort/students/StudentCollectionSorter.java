package ru.aston.sort.students;

import org.jetbrains.annotations.NotNull;
import ru.aston.model.Student;
import ru.aston.sort.CollectionSorter;
import ru.aston.sort.OrderType;
import ru.aston.sort.SortType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class StudentCollectionSorter implements CollectionSorter {

	private final StudentSortTypeChanger studentSortTypeChanger;

	public StudentCollectionSorter(StudentSortTypeChanger studentSortTypeChanger) {
		this.studentSortTypeChanger = studentSortTypeChanger;
	}

	@Override
	public Collection<Object> sort(@NotNull Collection<Object> objects, SortType sortType, OrderType orderType) {

		Object[] arrayObjects = objects.toArray();
		Student[] students = new Student[objects.size()];

		for (int i = 0; i < arrayObjects.length; i++) {
			students[i] = (Student) arrayObjects[i];
		}

		studentSortTypeChanger.changeTypeSort(sortType).sortStudentsCollection(students, 0, students.length - 1, orderType);

		return Arrays.stream(students).collect(Collectors.toCollection(ArrayList::new));

	}
}

