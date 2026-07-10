package ru.aston.sort.barrels;


import org.jetbrains.annotations.NotNull;
import ru.aston.model.Barrel;
import ru.aston.sort.OrderType;
import ru.aston.sort.SortType;
import ru.aston.sort.CollectionSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class BarrelCollectionSorter implements CollectionSorter {

	private BarrelSortTypeChanger barrelSortTypeChanger;

	public BarrelCollectionSorter(BarrelSortTypeChanger barrelSortTypeChanger) {
		this.barrelSortTypeChanger = barrelSortTypeChanger;
	}

	@Override
	public Collection<Object> sort(@NotNull Collection<Object> objects, SortType sortType, OrderType orderType) {
		Object[] arrayObjects = objects.toArray();
		Barrel[] barrels = new Barrel[objects.size()];

		for (int i = 0; i < arrayObjects.length; i++) {
			barrels[i] = (Barrel) arrayObjects[i];
		}

		barrelSortTypeChanger.changeTypeSort(sortType).sortBarrelCollection(barrels, 0, barrels.length - 1, orderType);

		return Arrays.stream(barrels).collect(Collectors.toCollection(ArrayList::new));
	}
}

