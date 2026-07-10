package ru.aston.sort.barrels;

import ru.aston.model.Barrel;
import ru.aston.sort.OrderType;


public interface BarrelSorter {

	void sortBarrelCollection(Barrel[] barrels, int leftIndex, int rightIndex, OrderType orderType);
}
