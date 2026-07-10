package ru.aston.sort.students;

import ru.aston.model.Student;
import ru.aston.sort.OrderType;

public interface StudentSorter {

	void sortStudentsCollection(Student[] students, int leftIndex, int rightIndex, OrderType orderType);
}
