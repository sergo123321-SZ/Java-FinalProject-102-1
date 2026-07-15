package ru.aston.sort.students;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ru.aston.model.Student;
import ru.aston.sort.OrderType;
import ru.aston.sort.SortType;
import ru.aston.sort.students.StudentCollectionSorter;
import ru.aston.sort.students.StudentSortTypeChanger;
import ru.aston.sort.students.StudentSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentCollectionSorterTest {

	private StudentCollectionSorter sorter;
	private StudentSortTypeChanger mockChanger;
	private StudentSorter mockSorter;

	static List<Student> students;
	static Student targetStudent;
	static final String TARGET_GROUP_NUMBER = "АЯ-51";
	static final String TARGET_RECORD_BOOK_NUMBER = "104857";
	static final Double TARGET_AVERAGE_GRADE = (Double) 5.0;
	static int TEST_COUNT = 100_000;

	static void dataPrepare() {
		Random random = new Random();
		students = Stream.generate(() -> Student.builder()
				.setGroupNumber(RandomStringUtils.insecure().nextNumeric(10))
				.setAverageGrade(random.nextDouble())
				.setRecordBookNumber(RandomStringUtils.insecure().nextAlphabetic(10))
				.build())
				.limit(TEST_COUNT)
				.collect(Collectors.toList());

		targetStudent = Student.builder()
				.setGroupNumber(TARGET_GROUP_NUMBER)
				.setAverageGrade(TARGET_AVERAGE_GRADE)
				.setRecordBookNumber(TARGET_RECORD_BOOK_NUMBER)
				.build();
	}

	@BeforeAll
	static void setUp() {
		dataPrepare();
	}

	@BeforeEach
	void setUpEach() {
		mockChanger = mock(StudentSortTypeChanger.class);
		mockSorter = mock(StudentSorter.class);
		when(mockChanger.changeTypeSort(any(SortType.class))).thenReturn(mockSorter);
		sorter = new StudentCollectionSorter(mockChanger);
	}

	@Test
	@DisplayName("sort: возвращает ArrayList (не HashSet/LinkedList)")
	void sort_returnsArrayList() {
		Student s1 = new Student(TARGET_GROUP_NUMBER, 5.0, TARGET_RECORD_BOOK_NUMBER);
		Collection<Object> input = List.of(s1);

		Collection<Object> result = sorter.sort(input, SortType.MERGE_SORT, OrderType.NATURAL_ORDER);

		assertInstanceOf(ArrayList.class, result, "Результат должен быть ArrayList");
	}

	@Test
	@DisplayName("sort: не мутирует исходную коллекцию")
	void sort_doesNotMutateOriginalCollection() {
		Student s1 = new Student(TARGET_GROUP_NUMBER, 3.0, TARGET_RECORD_BOOK_NUMBER);
		Student s2 = new Student(TARGET_GROUP_NUMBER, 4.0, TARGET_RECORD_BOOK_NUMBER);
		Collection<Object> original = new ArrayList<>(List.of(s1, s2));

		sorter.sort(original, SortType.MERGE_SORT, OrderType.NATURAL_ORDER);

		System.out.println(original);
		// проверяем, что original не изменился
		assertEquals(2, original.size());
		assertEquals(TARGET_GROUP_NUMBER, ((Student) original.toArray()[0]).getGroupNumber());
		assertEquals(TARGET_RECORD_BOOK_NUMBER, ((Student) original.toArray()[1]).getRecordBookNumber());
	}

	@Test
	@DisplayName("sort: элемент не Student → ClassCastException")
	void sort_nonStudentElement_throwsClassCastException() {
		Collection<Object> input = Arrays.asList("not a student");

		ClassCastException e = assertThrows(ClassCastException.class, () -> sorter
				.sort(input, SortType.QUICK_SORT, OrderType.NATURAL_ORDER));

		assertTrue(e.getMessage() != null);
	}

	@Test
	@DisplayName("sort: пустая коллекция → возвращает пустой ArrayList")
	void sort_emptyCollection_returnsEmptyList() {
		Collection<Object> result = sorter.sort(Collections.emptyList(), SortType.QUICK_SORT, OrderType.NATURAL_ORDER);

		assertTrue(result.isEmpty());
		assertEquals(0, result.size());
	}

	@Test
	@DisplayName("sort: singleton → вызывает sortStudentsCollection с left=0, right=0")
	void sort_singletonCallsSorterWithLeftRightZero() {
		Student s1 = new Student(TARGET_GROUP_NUMBER, 5.0, TARGET_RECORD_BOOK_NUMBER);
		Collection<Object> input = List.of(s1);

		sorter.sort(input, SortType.QUICK_SORT, OrderType.NATURAL_ORDER);

		verify(mockSorter).sortStudentsCollection(argThat(students -> students.length == 1
				&& students[0] == s1), eq(0), eq(0), eq(OrderType.NATURAL_ORDER));
	}

	@Nested
	@DisplayName("Все значения перечислений")
	class EnumValues {

		@ParameterizedTest
		@EnumSource(SortType.class)
		@DisplayName("sort: вызывает changeTypeSort для каждого SortType")
		void sort_callsChangeTypeSortForEachSortType(SortType sortType) {
			Collection<Object> input = List.of(new Student(TARGET_GROUP_NUMBER, 4.0, TARGET_RECORD_BOOK_NUMBER));

			sorter.sort(input, sortType, OrderType.NATURAL_ORDER);

			verify(mockChanger).changeTypeSort(eq(sortType));
		}

		@ParameterizedTest
		@EnumSource(OrderType.class)
		@DisplayName("sort: вызывает sortStudentsCollection для каждого OrderType")
		void sort_callsSorterForEachOrderType(OrderType orderType) {
			Collection<Object> input = List.of(new Student(TARGET_GROUP_NUMBER, 3.0, TARGET_RECORD_BOOK_NUMBER));

			sorter.sort(input, SortType.QUICK_SORT, orderType);

			verify(mockSorter).sortStudentsCollection(any(), anyInt(), anyInt(), eq(orderType));
		}
	}
}