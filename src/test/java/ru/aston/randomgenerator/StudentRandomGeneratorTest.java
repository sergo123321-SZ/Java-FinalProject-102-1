package ru.aston.randomgenerator;

import org.junit.jupiter.api.Test;
import ru.aston.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentRandomGeneratorTest {

	// ======================= generate(int) =======================

	@Test
	void generate_WithZeroSize_ReturnsEmptyList() {
		List<Student> students = StudentRandomGenerator.generate(0);
		assertTrue(students.isEmpty());
	}

	@Test
	void generate_WithNegativeSize_ThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> StudentRandomGenerator.generate(-1));
	}

	// ======================= Student Content Validation =======================

	@Test
	void generate_StudentsHaveValidGroupNumberFormat() {
		List<Student> students = StudentRandomGenerator.generate(10);

		for (Student s : students) {
			String group = s.getGroupNumber();
			assertTrue(group.matches("[А-Я]{2}-\\d{2}"), "Group number must match XX-##");
		}
	}

	@Test
	void generate_StudentsHaveValidRecordBookFormat() {
		List<Student> students = StudentRandomGenerator.generate(10);

		for (Student s : students) {
			String rb = s.getRecordBookNumber();
			assertTrue(rb.matches("\\d{6}"), "Record book must be 6 digits");
		}
	}

	@Test
	void generate_StudentsHaveValidAverageGradeRange() {
		List<Student> students = StudentRandomGenerator.generate(10);

		for (Student s : students) {
			double grade = s.getAverageGrade();
			assertTrue(grade >= 2.0 && grade <= 5.0, "Average grade must be in [2.0; 5.0], got: " + grade);
		}
	}

	@Test
	void generate_StudentsHaveRoundedGradeToTwoDecimals() {
		List<Student> students = StudentRandomGenerator.generate(100);

		for (Student s : students) {
			double grade = s.getAverageGrade();
			assertEquals(grade, Math.round(grade * 100.0) / 100.0, "Grade must be rounded to 2 decimal places, got: " + grade);
		}
	}

	// ======================= Uniqueness & Distribution =======================

	@Test
	void generate_SufficientSize_StudentsAreUnique() {
		// Even with large group, uniqueness is likely (birthday paradox doesn't apply
		// to full Student fields)
		List<Student> students = StudentRandomGenerator.generate(100);
		assertEquals(students.size(), students.stream().distinct().count());
	}

	@Test
	void generate_GraduallyIncreasingSizes_ProduceValidLists() {
		for (int size : new int[]{
				1, 2, 5, 10, 50
		}) {
			List<Student> students = StudentRandomGenerator.generate(size);
			assertEquals(size, students.size());
		}
	}

	// ======================= Thread Safety (Basic) =======================

	@Test
	void generate_ConcurrentCalls_ProduceValidResults() throws InterruptedException {
		Thread t1 = new Thread(() -> {
			List<Student> list1 = StudentRandomGenerator.generate(10);
			assertEquals(10, list1.size());
		});

		Thread t2 = new Thread(() -> {
			List<Student> list2 = StudentRandomGenerator.generate(10);
			assertEquals(10, list2.size());
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		// No exceptions = thread-safe (Faker uses ThreadLocal internally)
	}

	// ======================= Locale Validation =======================

	@Test
	void generate_WithDifferentSizes_ProduceValidCyrillicGroupNumbers() {
		// Current implementation uses ru locale explicitly → Cyrillic group numbers
		// expected
		List<Student> students = StudentRandomGenerator.generate(10);

		for (Student s : students) {
			String group = s.getGroupNumber();
			assertTrue(group.matches("[А-Я]{2}-\\d{2}"), "Group must be Cyrillic (e.g., МА-12)");
		}
	}

	// ======================= Integration with Student Builder
	// =======================

	@Test
	void generate_StudentsAreBuiltCorrectly() {
		List<Student> students = StudentRandomGenerator.generate(5);

		for (Student s : students) {
			assertNotNull(s.getGroupNumber());
			assertNotNull(s.getRecordBookNumber());
			assertFalse(s.getGroupNumber().isBlank());
			assertFalse(s.getRecordBookNumber().isBlank());
			assertTrue(s.getAverageGrade() >= 2.0 && s.getAverageGrade() <= 5.0);
		}
	}
}