package ru.aston.jsonrw;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.aston.counter.MultiThreadCounter;
import ru.aston.jsonrw.readers.StudentJsonReader;
import ru.aston.jsonrw.writers.StudentJsonWriter;
import ru.aston.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTest {
	static List<Student> students;
	static Student targetStudent;
	static final String TARGET_GROUP_NUMBER = "АЯ-51";
	static final String TARGET_RECORD_BOOK_NUMBER = "104857";
	static final Double TARGET_AVERAGE_GRADE = 5.0;
	static String TEST_FILE_NAME = "student.txt";
	static int TEST_COUNT = 100_000;

	static Collection<Student> readStudents;

	static void deleteTestFile() {
		if (Files.exists(Path.of(TEST_FILE_NAME))) {
			try {
				Files.delete(Path.of(TEST_FILE_NAME));
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

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
		deleteTestFile();
		dataPrepare();
	}

	@Test
	@Order(0)
	void writeTest() throws IOException {
		StudentJsonWriter studentJsonWriter = new StudentJsonWriter();
		studentJsonWriter.writeStudentsToFile(students, TEST_FILE_NAME);

		assertTrue(Files.exists(Path.of(TEST_FILE_NAME)));
		assertTrue(Files.size(Path.of(TEST_FILE_NAME)) > 0);
	}

	@Test
	@Order(1)
	void readTest() {
		StudentJsonReader studentJsonReader = new StudentJsonReader();
		assertFalse(studentJsonReader.readStudentsFromFile(TEST_FILE_NAME).isEmpty());
	}

	@Test
	@Order(2)
	void appendStudentsToFileTestPlusRead() {
		StudentJsonWriter studentJsonWriter = new StudentJsonWriter();
		studentJsonWriter.appendStudentsToFile(List.of(Student.builder()
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
								.build()), TEST_FILE_NAME);

		StudentJsonReader studentJsonReader = new StudentJsonReader();
		readStudents = studentJsonReader.readStudentsFromFile(TEST_FILE_NAME);

		assertFalse(readStudents.isEmpty());
		assertEquals(readStudents.size(), TEST_COUNT + 3);
	}

	@Test
	@Order(3)
	void parallelStreamSearchTest() {
		long start1 = System.nanoTime();
		assertEquals(3, MultiThreadCounter.countParallelStream(readStudents, targetStudent));
		long end1 = System.nanoTime();
		long parallelStreamTime = (end1 - start1) / 1_000_000;
		System.out.println("parallelStreamTest: " + parallelStreamTime + " ms");
	}

	@Test
	@Order(4)
	void addStudentsToCollectionTest() {
		Random random = new Random();
		Collection<Student> extendedStudents = Stream.generate(() -> Student.builder()
				.setGroupNumber(RandomStringUtils.insecure().nextNumeric(10))
				.setAverageGrade(random.nextDouble())
				.setRecordBookNumber(RandomStringUtils.insecure().nextAlphabetic(10))
				.build())
				.limit(TEST_COUNT)
				.collect(Collectors.toList());

		StudentJsonReader studentJsonReader = new StudentJsonReader();
		studentJsonReader.addStudentsToCollection(extendedStudents, TEST_FILE_NAME);

		assertEquals(TEST_COUNT * 2 + 3, extendedStudents.size());
	}
}
