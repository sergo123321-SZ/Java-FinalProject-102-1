package ru.aston.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface CollectionSorter {
	Collection<Object> sort(@NotNull Collection<Object> objects, SortType sortType, OrderType orderType);
}
