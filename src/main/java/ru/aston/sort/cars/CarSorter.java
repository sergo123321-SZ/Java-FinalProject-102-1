package ru.aston.sort.cars;

import ru.aston.model.Car;
import ru.aston.sort.OrderType;


public interface CarSorter {

	void sortCarCollection(Car[] cars, int leftIndex, int rightIndex, OrderType orderType);
}
