package ru.aston.sort.barrels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ru.aston.model.Barrel;
import ru.aston.sort.OrderType;
import ru.aston.sort.SortType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BarrelCollectionSorterTest {

	private BarrelCollectionSorter sorter;

	@BeforeEach
	void setUp() {
		sorter = new BarrelCollectionSorter(new BarrelSortTypeChanger());
	}

	@Nested
	@DisplayName("Реальная сортировка")
	class ActualSorting {

		@Test
		@DisplayName("sort: QUICK_SORT с NATURAL_ORDER — сортирует поcompareTo()")
		void quickSortNaturalOrder_sortsCorrectly() {
			Barrel b1 = new Barrel(200.0, "water", "steel");
			Barrel b2 = new Barrel(100.0, "oil", "plastic");
			Barrel b3 = new Barrel(100.0, "oil", "steel");
			Barrel b4 = new Barrel(100.0, "oil", "plastic");
			Collection<Object> input = Arrays.asList(b1, b2, b3, b4);

			Collection<Object> result = sorter.sort(input, SortType.QUICK_SORT, OrderType.NATURAL_ORDER);

			Barrel[] sorted = result.toArray(new Barrel[0]);
			assertEquals(b2, sorted[0]);
			assertEquals(b4, sorted[1]);
			assertEquals(b3, sorted[2]);
			assertEquals(b1, sorted[3]);
		}

		@Test
		@DisplayName("sort: MERGE_SORT с REVERSE_ORDER — сортирует в обратном порядке")
		void mergeSortReverseOrder_sortsReverse() {
			Barrel b1 = new Barrel(100.0, "water", "steel");
			Barrel b2 = new Barrel(200.0, "oil", "plastic");
			Barrel b3 = new Barrel(300.0, "acid", "glass");
			Collection<Object> input = Arrays.asList(b1, b2, b3);

			Collection<Object> result = sorter.sort(input, SortType.MERGE_SORT, OrderType.REVERSE_ORDER);

			Barrel[] sorted = result.toArray(new Barrel[0]);
			assertEquals(b3, sorted[0], "300.0");
			assertEquals(b2, sorted[1], "200.0");
			assertEquals(b1, sorted[2], "100.0");
		}

		// @Test
		// @DisplayName("sort: CUSTOM_SORT с NATURAL_ORDER — использует compareTo()")
		// void customSortNaturalOrder_usesNaturalOrder() {
		// Barrel b1 = new Barrel(99, "acid", "glass");
		// Barrel b2 = new Barrel(100, "water", "steel");
		// Barrel b3 = new Barrel(101, "oil", "plastic");
		// Collection<Object> input = Arrays.asList(b1, b2, b3);
		//
		// Collection<Object> result = sorter.sort(input, SortType.CUSTOM_SORT,
		// OrderType.NATURAL_ORDER);
		//
		// Barrel[] sorted = result.toArray(new Barrel[0]);
		// assertEquals(b1, sorted[0], "acid < oil < water");
		// assertEquals(b3, sorted[1]);
		// assertEquals(b2, sorted[2]);
		// }

		@Test
		@DisplayName("sort: не мутирует исходную коллекцию")
		void originalCollectionNotMutated() {
			Barrel b1 = new Barrel(300.0, "acid", "glass");
			Barrel b2 = new Barrel(100.0, "water", "steel");
			Barrel b3 = new Barrel(200.0, "oil", "plastic");
			Collection<Object> original = Arrays.asList(b1, b2, b3);

			sorter.sort(original, SortType.QUICK_SORT, OrderType.NATURAL_ORDER);

			Barrel[] originalArray = original.toArray(new Barrel[0]);
			assertEquals(300.0, originalArray[0].getVolume());
			assertEquals(100.0, originalArray[1].getVolume());
			assertEquals(200.0, originalArray[2].getVolume());
		}

		@Test
		@DisplayName("sort: пустая коллекция → пустой ArrayList")
		void emptyCollection_returnsEmptyArrayList() {
			Collection<Object> result = sorter.sort(Collections.emptyList(), SortType.QUICK_SORT, OrderType.NATURAL_ORDER);

			assertTrue(result.isEmpty());
			assertTrue(result instanceof ArrayList);
		}

		@Test
		@DisplayName("sort: singleton → возвращает список из одного элемента")
		void singletonCollection_returnsSingletonList() {
			Barrel b1 = new Barrel(100.0, "water", "steel");
			Collection<Object> input = List.of(b1);

			Collection<Object> result = sorter.sort(input, SortType.MERGE_SORT, OrderType.NATURAL_ORDER);

			assertEquals(1, result.size());
			assertEquals(b1, result.toArray()[0]);
		}

		@Test
		@DisplayName("sort: null внутри коллекции → ClassCastException")
		void nullElement_throwsNullPointerException() {
			Collection<Object> input = Arrays.asList(new Barrel(100.0, "water", "steel"), null);

			NullPointerException e = assertThrows(NullPointerException.class, () -> sorter
					.sort(input, SortType.QUICK_SORT, OrderType.NATURAL_ORDER));

			assertNotNull(e);
		}

		@Test
		@DisplayName("sort: элемент не Barrel → ClassCastException")
		void nonBarrelElement_throwsClassCastException() {
			Collection<Object> input = Arrays.asList("not a barrel");

			ClassCastException e = assertThrows(ClassCastException.class, () -> sorter
					.sort(input, SortType.QUICK_SORT, OrderType.NATURAL_ORDER));

			assertNotNull(e);
		}

		@Test
		@DisplayName("sort: дубликаты сохраняются")
		void duplicates_preserved() {
			Barrel b1 = new Barrel(100.0, "water", "steel");
			Barrel b2 = new Barrel(100.0, "water", "steel");
			Barrel b3 = new Barrel(200.0, "oil", "plastic");
			Collection<Object> input = Arrays.asList(b1, b2, b3);

			Collection<Object> result = sorter.sort(input, SortType.QUICK_SORT, OrderType.NATURAL_ORDER);

			assertEquals(3, result.size());
			assertEquals(2, result.stream().filter(b -> b.equals(b1)).count());
		}

		@Test
		@DisplayName("sort: сортировка по разным полям compareTo()")
		void sortWithMultipleFields() {
			Barrel b1 = new Barrel(100.0, "water", "glass");
			Barrel b2 = new Barrel(100.0, "water", "steel");
			Barrel b3 = new Barrel(100.0, "oil", "plastic");
			Collection<Object> input = Arrays.asList(b1, b2, b3);

			Collection<Object> result = sorter.sort(input, SortType.QUICK_SORT, OrderType.NATURAL_ORDER);

			Barrel[] sorted = result.toArray(new Barrel[0]);
			assertEquals(b3, sorted[0], "oil < water");
			assertEquals(b1, sorted[1], "water < water, glass < steel");
			assertEquals(b2, sorted[2]);
		}
	}
}