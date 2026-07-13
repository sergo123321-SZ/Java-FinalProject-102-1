package ru.aston.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomCollectionTest {
	private LinkedList<String> standardCollection;
	private CustomCollection<String> customCollection;
	ListIterator<String> iterator;
	ListIterator<String> standardIterator;
	private final String[] testData = {
			"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"
	};

	void compareCollectionsExpectEq() {
		compareCollectionsExpectEq(true, true);
	}

	void compareCollectionsExpectEq(boolean checkNextIter, boolean checkPrevIter) {
		assertThat(customCollection.size()).isEqualTo(standardCollection.size());
		assertThat(customCollection).containsExactlyElementsOf(standardCollection);
		assertThat(iterator.nextIndex()).isEqualTo(standardIterator.nextIndex());
		assertThat(iterator.previousIndex()).isEqualTo(standardIterator.previousIndex());
		if (checkNextIter) {
			assertThat(iterator.next()).isEqualTo(standardIterator.next());
		}
		if (checkPrevIter) {
			assertThat(iterator.previous()).isEqualTo(standardIterator.previous());
		}
	}

	@BeforeEach
	void setUp() {
		standardCollection = new LinkedList<>();
		customCollection = new CustomCollection<>();

		for (String testData : testData) {
			customCollection.add(testData);
			standardCollection.add(testData);
		}

		iterator = customCollection.iterator();
		standardIterator = standardCollection.listIterator();
	}

	@Test
	@DisplayName("Test size, order and add")
	void baseTest() {
		assertThat(customCollection.size()).isEqualTo(testData.length);
		assertThat(customCollection).containsExactly(testData);
	}

	@Test
	@DisplayName("Test iterators (forward move)")
	void iteratorsTest() {
		while (standardIterator.hasNext()) {
			assertThat(iterator.hasNext()).isTrue();
			assertThat(iterator.nextIndex()).isEqualTo(standardIterator.nextIndex());
			assertThat(iterator.previousIndex()).isEqualTo(standardIterator.previousIndex());

			assertThat(iterator.next()).isEqualTo(standardIterator.next());
		}

		assertThat(iterator.hasNext()).isFalse();
		assertThat(iterator.nextIndex()).isEqualTo(customCollection.size());
	}

	@Test
	@DisplayName("Test iterators (backward move)")
	void iteratorsBackwardTest() {
		while (standardIterator.hasNext()) {
			iterator.next();
			standardIterator.next();
		}

		while (standardIterator.hasPrevious()) {
			assertThat(iterator.hasPrevious()).isTrue();
			assertThat(iterator.nextIndex()).isEqualTo(standardIterator.nextIndex());
			assertThat(iterator.previousIndex()).isEqualTo(standardIterator.previousIndex());

			assertThat(iterator.previous()).isEqualTo(standardIterator.previous());
		}
		assertThat(iterator.hasPrevious()).isFalse();
	}

	@Test
	@DisplayName("Remove test (forward move")
	void removeTest() {
		iterator.next();
		standardIterator.next();

		iterator.remove();
		standardIterator.remove();

		compareCollectionsExpectEq();

		for (int i = 0; i < 3; ++i) {
			iterator.next();
			standardIterator.next();
		}

		iterator.remove();
		standardIterator.remove();
		compareCollectionsExpectEq();

		while (iterator.hasNext()) {
			iterator.next();
			standardIterator.next();
		}

		iterator.remove();
		standardIterator.remove();
		compareCollectionsExpectEq(false, true);
	}

	@Test
	@DisplayName("Remove test (backward move")
	void removeBackwardTest() {
		while (standardIterator.hasNext()) {
			iterator.next();
			standardIterator.next();
		}

		iterator.previous();
		standardIterator.previous();
		iterator.remove();
		standardIterator.remove();

		compareCollectionsExpectEq(false, true);

		for (int i = 0; i < 3; ++i) {
			iterator.previous();
			standardIterator.previous();
		}

		iterator.remove();
		standardIterator.remove();
		compareCollectionsExpectEq();

		while (iterator.hasPrevious()) {
			iterator.previous();
			standardIterator.previous();
		}
		iterator.remove();
		standardIterator.remove();
		compareCollectionsExpectEq();
	}

	@Test
	@DisplayName("Set test")
	void setTest() {
		iterator.next();
		standardIterator.next();

		iterator.set("HI!");
		standardIterator.set("HI!");
		compareCollectionsExpectEq();

		for (int i = 0; i < 5; ++i) {
			iterator.next();
			standardIterator.next();
		}

		iterator.previous();
		standardIterator.previous();
		iterator.set("HI!");
		standardIterator.set("HI!");
		compareCollectionsExpectEq();
	}

	@Test
	@DisplayName("Test illegal operations")
	void illegalOperationsTest() {
		assertThatThrownBy(() -> {
			iterator.remove();
		}).isInstanceOf(IllegalStateException.class);

		iterator.next();
		iterator.remove();

		assertThatThrownBy(() -> {
			iterator.remove();
		}).isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("Out of bounds test")
	void outOfBoundsTest() {
		CustomCollection<Integer> customCollection = new CustomCollection<>();
		ListIterator<Integer> iterator = customCollection.iterator();
		assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
		assertThatThrownBy(iterator::previous).isInstanceOf(NoSuchElementException.class);

	}


}