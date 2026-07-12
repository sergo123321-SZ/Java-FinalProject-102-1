package ru.aston.counter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.aston.core.TranslationManager;
import ru.aston.model.Student;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentCounterTest {
	static List<Student> students;
	static Student targetStudent;
	static final String TARGET_GROUP_NUMBER = "АЯ-51";
	static final String TARGET_RECORD_BOOK_NUMBER = "104857";
	static final Double TARGET_AVERAGE_GRADE = 5.0;

	@BeforeAll
	static void setUp() {
		TranslationManager.setLocale(Locale.of("ru"));
		Random random = new Random();
		students = Stream.generate(() -> Student.builder()
				.setGroupNumber(RandomStringUtils.insecure().nextNumeric(10))
				.setAverageGrade(random.nextDouble())
				.setRecordBookNumber(RandomStringUtils.insecure().nextAlphabetic(10))
				.build())
				.limit(100_000)
				.collect(Collectors.toList());

		students.addAll(List.of(Student.builder()
				.setGroupNumber(TARGET_GROUP_NUMBER)
				.setAverageGrade(TARGET_AVERAGE_GRADE)
				.setRecordBookNumber(TARGET_RECORD_BOOK_NUMBER)
				.build(), Student.builder()
						.setGroupNumber(TARGET_GROUP_NUMBER)
						.setAverageGrade(TARGET_AVERAGE_GRADE)
						.setRecordBookNumber(TARGET_RECORD_BOOK_NUMBER)
						.build(), Student.builder()
								.setGroupNumber(TARGET_GROUP_NUMBER)
								.setAverageGrade(TARGET_AVERAGE_GRADE)
								.setRecordBookNumber(TARGET_RECORD_BOOK_NUMBER)
								.build()));

		targetStudent = Student.builder()
				.setGroupNumber(TARGET_GROUP_NUMBER)
				.setAverageGrade(TARGET_AVERAGE_GRADE)
				.setRecordBookNumber(TARGET_RECORD_BOOK_NUMBER)
				.build();
	}

	@Test
	void countParallelArrayTest() throws ExecutionException, InterruptedException {
		long start1 = System.nanoTime();
		Long result = MultiThreadCounter.countParallelArray(students.toArray(), targetStudent, 1);
		long end1 = System.nanoTime();
		long oneThreadTime = (end1 - start1) / 1_000_000;
		System.out.println("Thread One: " + oneThreadTime + " ms");

		assertEquals(3, result);

		long start2 = System.nanoTime();
		result = MultiThreadCounter.countParallelArray(students.toArray(), targetStudent, 4);
		long end2 = System.nanoTime();
		long fourThreadsTime = (end2 - start2) / 1_000_000;
		System.out.println("Four threads: " + fourThreadsTime + " ms");

		assertEquals(3, result);
	}

	@Test
	void parallelSubListTest() throws ExecutionException, InterruptedException {
		long start1 = System.nanoTime();
		Long result = MultiThreadCounter.countParallelSubList(students, targetStudent, 1);
		long end1 = System.nanoTime();
		long oneThreadTime = (end1 - start1) / 1_000_000;
		System.out.println("Thread One: " + oneThreadTime + " ms");

		assertEquals(3, result);

		long start2 = System.nanoTime();
		result = MultiThreadCounter.countParallelSubList(students, targetStudent, 2);
		long end2 = System.nanoTime();
		long twoThreadsTime = (end2 - start2) / 1_000_000;
		System.out.println("Two threads: " + twoThreadsTime + " ms");

		assertEquals(3, result);
	}

	@Test
	void parallelStreamTest() {
		long start1 = System.nanoTime();
		assertEquals(3, MultiThreadCounter.countParallelStream(students, targetStudent));
		long end1 = System.nanoTime();
		long parallelStreamTime = (end1 - start1) / 1_000_000;
		System.out.println("parallelStreamTest: " + parallelStreamTime + " ms");
	}
}
