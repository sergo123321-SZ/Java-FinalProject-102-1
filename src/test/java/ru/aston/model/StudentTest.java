package ru.aston.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudentTest {

	@Test
	@DisplayName("equals: должен возвращать true при сравнении студента с самим собой")
	void equals_sameStudent_returnsTrue() {
		Student student = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student).as("Студент должен быть равным самому себе").isEqualTo(student);
	}

	@Test
	@DisplayName("equals: должен возвращать true для разных студентов с одинаковыми свойствами")
	void equals_identicalStudents_returnsTrue() {
		Student student1 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertThat(student1).as("Студенты с одинаковыми свойствами должны быть равны").isEqualTo(student2);
	}

	@Test
	@DisplayName("equals: должен возвращать false при сравнении с null")
	void equals_comparedWithNull_returnsFalse() {
		Student student = Student.builder().setGroupNumber("ИВТ-23").setAverageGrade(4.20).setRecordBookNumber("301245")
				.build();

		assertThat(student).as("Студент не должен быть равным null").isNotNull();
	}

	@Test
	@DisplayName("equals: должен возвращать false для студентов с разными свойствами")
	void equals_differentStudents_returnsFalse() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertThat(student1).as("Студенты с разными свойствами не должны быть равны").isNotEqualTo(student2);
	}

	@Test
	@DisplayName("equals: должен возвращать false, если отличается только одно свойство")
	void equals_onlyGroupNumberIsDifferent_returnsFalse() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-24").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student1).as("Студенты с разной группой не должны быть равны").isNotEqualTo(student2);
	}

	@Test
	@DisplayName("hashCode: должен быть одинаковым у студентов с идентичными свойствами")
	void hashCode_identicalStudents_returnsSameHash() {
		Student student1 = Student.builder().setGroupNumber("ИТ-23").setAverageGrade(4.20).setRecordBookNumber("301245")
				.build();
		Student student2 = Student.builder().setGroupNumber("ИТ-23").setAverageGrade(4.20).setRecordBookNumber("301245")
				.build();

		assertThat(student1.hashCode()).as("Хэш-коды одинаковых студентов должны совпадать")
				.isEqualTo(student2.hashCode());
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды при сравнении студентов с разными свойствами")
	void hashCode_differentStudents_returnsDifferentHash() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertThat(student1.hashCode()).as("У разных студентов хэш-коды должны отличаться")
				.isNotEqualTo(student2.hashCode());
	}

	@Test
	@DisplayName("hashCode: должен возвращать разные хэш-коды, если разное только одно свойство")
	void hashCode_onlyAverageGradeIsDifferent_returnsDifferentHash() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.20).setRecordBookNumber("104857")
				.build();

		assertThat(student1.hashCode()).as("Студенты с разным баллом должны иметь разный хэш-код")
				.isNotEqualTo(student2.hashCode());
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 при сравнении студента с самим собой")
	void compareTo_itself_returnsZero() {
		Student student = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(5.00).setRecordBookNumber("205300")
				.build();
		assertThat(student.compareTo(student)).as("Студент должен быть равен себе и возвращать 0").isZero();
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 для студентов с одинаковыми свойствами")
	void compareTo_identicalStudents_returnsZero() {
		Student student1 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertThat(student1.compareTo(student2)).as("Идентичные студенты должны возвращать 0 при сравнении").isZero();
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если название группы первого студента идет раньше по алфавиту")
	void compareTo_groupNumberIsEarlier_returnsNegative() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-24").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student1.compareTo(student2))
				.as("Результат должен быть отрицательным, так как группа АЯ идет раньше ПМ").isNegative();
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если название группы первого студента идет позже по алфавиту")
	void compareTo_groupNumberIsLater_returnsPositive() {
		Student student1 = Student.builder().setGroupNumber("ПМ-24").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student1.compareTo(student2))
				.as("Результат должен быть положительным, так как группа ПМ идет позже АЯ").isPositive();
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если у первого студента средний балл меньше")
	void compareTo_averageGradeIsLower_returnsNegative() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(3.90).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student1.compareTo(student2))
				.as("Результат должен быть отрицательным, так как балл первого студента меньше").isNegative();
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если у первого студента средний балл больше")
	void compareTo_averageGradeIsHigher_returnsPositive() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(3.90).setRecordBookNumber("104857")
				.build();

		assertThat(student1.compareTo(student2))
				.as("Результат должен быть положительным, так как балл первого студента больше").isPositive();
	}

	@Test
	@DisplayName("compareTo: должен возвращать отрицательное значение, если номер зачетной книжки первого студента идет раньше по алфавиту")
	void compareTo_recordBookNumberIsEarlier_returnsNegative() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("205948")
				.build();

		assertThat(student1.compareTo(student2))
				.as("Результат должен быть отрицательным, так как зачетка 104... идет раньше 205...").isNegative();
	}

	@Test
	@DisplayName("compareTo: должен возвращать положительное значение, если номер зачетной книжки первого студента идет позже по алфавиту")
	void compareTo_recordBookNumberIsLater_returnsPositive() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("205948")
				.build();
		Student student2 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student1.compareTo(student2))
				.as("Результат должен быть положительным, так как зачетка 205... идет позже 104...").isPositive();
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом номере группы")
	void validation_groupNumberIsBlank_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Student.builder().setGroupNumber("").setAverageGrade(4.5).setRecordBookNumber("104857").build())
				.as("Ожидалось исключение при пустом номере группы").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Номер группы должен быть указан");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при среднем балле меньше 0.0")
	void validation_averageGradeIsLessThanZero_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Student.builder().setGroupNumber("АЯ-51").setAverageGrade(-0.5).setRecordBookNumber("104857")
				.build()).as("Ожидалось исключение при среднем балле меньше 0.0").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при среднем балле больше 5.0")
	void validation_averageGradeIsGreaterThanFive_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Student.builder().setGroupNumber("АЯ-51").setAverageGrade(5.1).setRecordBookNumber("104857")
				.build()).as("Ожидалось исключение при среднем балле больше 5.0").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при среднем балле равном Double.NaN(не число)")
	void validation_averageGradeIsNaN_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Student.builder().setGroupNumber("АЯ-51").setAverageGrade(Double.NaN)
				.setRecordBookNumber("104857").build()).as("Ожидалось исключение при среднем балле равном Double.NaN(не число)")
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0");
	}

	@Test
	@DisplayName("validation: должен выбрасывать IllegalArgumentException при пустом номере зачетной книжки")
	void validation_recordBookNumberIsBlank_throwsIllegalArgumentException() {
		assertThatThrownBy(() -> Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.5).setRecordBookNumber("   ").build())
				.as("Ожидалось исключение при пустом номере зачетной книжки").isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Номер зачетной книжки должен быть указан");
	}
}