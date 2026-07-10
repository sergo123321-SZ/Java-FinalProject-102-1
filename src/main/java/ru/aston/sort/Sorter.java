package ru.aston.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Sorter {

	private TypeCollectionChanger typeCollectionChanger;

	public Sorter(TypeCollectionChanger typeCollectionChanger) {
		this.typeCollectionChanger = typeCollectionChanger;
	}

	public Collection<Object> sort(@NotNull Collection<Object> objects, SortType sortType, OrderType orderType) {
		return typeCollectionChanger.sortCollection(objects).sort(objects, sortType, orderType);
	}
}
