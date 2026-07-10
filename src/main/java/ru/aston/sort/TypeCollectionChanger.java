package ru.aston.sort;

import org.jetbrains.annotations.NotNull;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;
import ru.aston.sort.barrels.BarrelCollectionSorter;
import ru.aston.sort.barrels.BarrelSortTypeChanger;
import ru.aston.sort.cars.CarCollectionSorter;
import ru.aston.sort.cars.CarSortTypeChanger;
import ru.aston.sort.students.StudentCollectionSorter;
import ru.aston.sort.students.StudentSortTypeChanger;

import java.util.Collection;

public class TypeCollectionChanger {

	public CollectionSorter sortCollection(@NotNull Collection<Object> objects) {
		Object[] arrayObjects = objects.toArray();
		if (arrayObjects[0] instanceof Student) {
			return new StudentCollectionSorter(new StudentSortTypeChanger());
		} else if (arrayObjects[0] instanceof Car) {
			return new CarCollectionSorter(new CarSortTypeChanger());
		} else if (arrayObjects[0] instanceof Barrel) {
			return new BarrelCollectionSorter(new BarrelSortTypeChanger());
		} else {
			throw new IllegalArgumentException("Неизвестный тип данных");
		}
	}
}
