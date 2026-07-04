package ru.aston.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentTest {

	@Test
	@DisplayName("equals: должен возвращать true при сравнении студента с самим собой")
	void equals_shouldReturnTrueWhenTheStudentIsTheSame() {
		Student student = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertEquals(student, student, "Студент должен быть равным самому себе");
	}

	@Test
	@DisplayName("equals: должен возвращать true для разных студентов с одинаковыми свойствами")
	void equals_shouldReturnTrueWhenStudentsAreIdentical() {
		Student student1 = Student.builder()
				.setGroupNumber("ПМ-22")
				.setAverageGrade(3.90)
				.setRecordBookNumber("205948")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("ПМ-22")
				.setAverageGrade(3.90)
				.setRecordBookNumber("205948")
				.build();

		Assertions.assertEquals(student1, student2, "Студенты с одинаковыми свойствами должны быть равны");
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении с null")
	void equals_shouldReturnFalseWhenComparedWithNull() {
		Student student = Student.builder()
				.setGroupNumber("ИВТ-23")
				.setAverageGrade(4.20)
				.setRecordBookNumber("301245")
				.build();

		Assertions.assertNotEquals(null, student, "Студент не должен быть равным null");
	}

	@Test
	@DisplayName("equals: должен возвращать false для студентов с разными свойствами")
	void equals_shouldReturnFalseWhenComparingTwoDifferentStudentsWithDifferentAttributes() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("ПМ-22")
				.setAverageGrade(3.90)
				.setRecordBookNumber("205948")
				.build();

		Assertions.assertNotEquals(student1, student2, "Студенты с разными свойствами не должны быть равны");
	}

	@Test
	@DisplayName("equals: должен возвращать false, если отличается только номер группы")
	void equals_shouldReturnFalseWhenComparingStudentsWithOneDifferentAttribute() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("ПМ-24")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertNotEquals(student1, student2, "Студенты с разной группой не должны быть равны");
	}

	@Test
	@DisplayName("hashCode: должен быть одинаковым у студентов с идентичными свойствами")
	void hashCode_shouldReturnSameHashWhenStudentsAreIdentical() {
		Student student1 = Student.builder()
				.setGroupNumber("ИВТ-23")
				.setAverageGrade(4.20)
				.setRecordBookNumber("301245")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("ИВТ-23")
				.setAverageGrade(4.20)
				.setRecordBookNumber("301245")
				.build();

		Assertions.assertEquals(student1.hashCode(), student2.hashCode(), "Хэш-коды одинаковых студентов должны совпадать");
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды при сравнении студентов с разными свойствами")
	void hashCode_shouldReturnDifferentHashWhenComparingTwoDifferentStudentsWithDifferentAttributes() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("ПМ-22")
				.setAverageGrade(3.90)
				.setRecordBookNumber("205948")
				.build();

		Assertions.assertNotEquals(student1.hashCode(), student2.hashCode(), "У разных студентов хэш-коды должны отличаться");
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды, если изменен только средний балл")
	void hashCode_shouldReturnDifferentHashWhenComparingTwoDifferentStudentsWithOneDifferentAttribute() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.20)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertNotEquals(student1.hashCode(), student2.hashCode(), "Студенты с разным баллом должны иметь разный хэш-код");
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 для студентов с одинаковыми свойствами")
	void compareTo_shouldReturnZeroWhenStudentsAreIdentical() {
		Student student1 = Student.builder()
				.setGroupNumber("ПМ-22")
				.setAverageGrade(3.90)
				.setRecordBookNumber("205948")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("ПМ-22")
				.setAverageGrade(3.90)
				.setRecordBookNumber("205948")
				.build();

		Assertions.assertEquals(0, student1.compareTo(student2), "Идентичные студенты должны возвращать 0");
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если название группы первого студента идет раньше по алфавиту")
	void compareTo_shouldReturnNegativeValueWhenTheLetterOfTheGroupNumberOfTheFirstStudentGoesEarlierThanTheLetterOfTheSecondStudent() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("ПМ-24")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertTrue(student1.compareTo(student2) < 0, "Результат должен быть меньше 0, так как группа АМ раньше ПМ");
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если название группы первого студента идет позже по алфавиту")
	void compareTo_shouldReturnPositiveValueWhenTheLetterOfTheGroupNumberOfTheFirstStudentGoesLaterThanTheLetterOfTheSecondStudent() {
		Student student1 = Student.builder()
				.setGroupNumber("ПМ-24")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertTrue(student1.compareTo(student2) > 0, "Результат должен быть больше 0, так как группа ПМ позже АМ");
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если у первого студента средний балл меньше")
	void compareTo_shouldReturnNegativeValueWhenTheAverageGradeOfTheFirstStudentIsLessThanAverageGradeOfTheSecondStudent() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(3.90)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertTrue(student1.compareTo(student2) < 0, "Результат должен быть меньше 0, так как балл меньше");
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если у первого студента средний балл больше")
	void compareTo_shouldReturnPositiveValueWhenTheAverageGradeOfTheFirstStudentIsGreaterThanAverageGradeOfTheSecondStudent() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(3.90)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertTrue(student1.compareTo(student2) > 0, "Результат должен быть больше 0, так как балл больше");
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если номер зачетной книжки первого студента идет раньше по алфавиту")
	void compareTo_shouldReturnNegativeValueWhenTheLetterOfTheRecordBookNumberOfTheFirstStudentGoesEarlierThanTheLetterOfTheSecondStudent() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("205948")
				.build();

		Assertions.assertTrue(student1.compareTo(student2) < 0, "Результат должен быть меньше 0, так как зачетка 104... раньше 205...");
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если номер зачетной книжки первого студента идет позже по алфавиту")
	void compareTo_shouldReturnPositiveValueWhenTheLetterOfTheRecordBookNumberOfTheFirstStudentGoesLaterThanTheLetterOfTheSecondStudent() {
		Student student1 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("205948")
				.build();
		Student student2 = Student.builder()
				.setGroupNumber("АЯ-51")
				.setAverageGrade(4.75)
				.setRecordBookNumber("104857")
				.build();

		Assertions.assertTrue(student1.compareTo(student2) > 0, "Результат должен быть больше 0, так как зачетка 205... позже 104...");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом номере группы")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheGroupNumberIsBlank() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Student.builder()
						.setGroupNumber("")
						.setAverageGrade(4.5)
						.setRecordBookNumber("104857")
						.build(),
				"Ожидалось исключение при пустом номере группы"
		);
		Assertions.assertEquals("Номер группы должен быть указан", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при среднем балле меньше 0.0")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheAverageGradeIsLessThanZero() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Student.builder()
						.setGroupNumber("АЯ-51")
						.setAverageGrade(-0.5)
						.setRecordBookNumber("104857")
						.build(),
				"Ожидалось исключение при среднем балле меньше 0.0"
		);
		Assertions.assertEquals("Средний балл должен быть числом в диапазоне от 0.0 до 5.0", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при среднем балле больше 5.0")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheAverageGradeIsGreaterThanFive() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Student.builder()
						.setGroupNumber("АЯ-51")
						.setAverageGrade(5.1)
						.setRecordBookNumber("104857")
						.build(),
				"Ожидалось исключение при среднем балле больше 5.0"
		);
		Assertions.assertEquals("Средний балл должен быть числом в диапазоне от 0.0 до 5.0", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при среднем балле равном NaN")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheAverageGradeIsNaN() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Student.builder()
						.setGroupNumber("АЯ-51")
						.setAverageGrade(Double.NaN)
						.setRecordBookNumber("104857")
						.build(),
				"Ожидалось исключение при среднем балле равном NaN"
		);
		Assertions.assertEquals("Средний балл должен быть числом в диапазоне от 0.0 до 5.0", exception.getMessage());
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом номере зачетной книжки")
	void validation_ShouldThrowIllegalArgumentExceptionWhenTheRecordBookNumberIsBlank() {
		IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Student.builder()
						.setGroupNumber("АЯ-51")
						.setAverageGrade(4.5)
						.setRecordBookNumber("   ")
						.build(),
				"Ожидалось исключение при пустом номере зачетной книжки"
		);
		Assertions.assertEquals("Номер зачетной книжки должен быть указан", exception.getMessage());
	}
}
