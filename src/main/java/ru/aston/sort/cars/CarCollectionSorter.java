package ru.aston.sort.cars;

import org.jetbrains.annotations.NotNull;
import ru.aston.model.Car;
import ru.aston.sort.OrderType;
import ru.aston.sort.SortType;
import ru.aston.sort.CollectionSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


public class CarCollectionSorter implements CollectionSorter {

	private CarSortTypeChanger carSortTypeChanger;

	public CarCollectionSorter(CarSortTypeChanger carSortTypeChanger) {
		this.carSortTypeChanger = carSortTypeChanger;
	}

	@Override
	public Collection<Object> sort(@NotNull Collection<Object> objects, SortType sortType, OrderType orderType) {
		Object[] arrayObjects = objects.toArray();
		Car[] cars = new Car[objects.size()];

		for (int i = 0; i < arrayObjects.length; i++) {
			cars[i] = (Car) arrayObjects[i];
		}
		carSortTypeChanger.changeTypeSort(sortType).sortCarCollection(cars, 0, cars.length - 1, orderType);

		return Arrays.stream(cars).collect(Collectors.toCollection(ArrayList::new));

	}
}

