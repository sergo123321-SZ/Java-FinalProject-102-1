package ru.aston.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aston.core.TranslationManager;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

	@BeforeAll
	static void setUp() {
		TranslationManager.setLocale(Locale.of("ru"));
	}

	@Test
	void equalTest() {
		Student student1 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertAll("Verify student equal properties", () -> assertThat(student1).as("Студент должен быть равным самому себе")
				.isEqualTo(student1), () -> assertThat(student1).as("Студенты с одинаковыми свойствами должны быть равны")
						.isEqualTo(student2), () -> assertNotEquals(null, student1));
	}

	@Test
	void notEqualTest() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertAll("Verify student not equal properties", () -> assertThat(student1)
				.as("Студенты с разными свойствами не должны быть равны")
				.isNotEqualTo(student2));
	}

	@Test
	void hashCodeTest() {
		Student student1 = Student.builder().setGroupNumber("ИТ-23").setAverageGrade(4.20).setRecordBookNumber("301245")
				.build();
		Student student2 = Student.builder().setGroupNumber("ИТ-23").setAverageGrade(4.20).setRecordBookNumber("301245")
				.build();
		Student student3 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertAll(() -> assertThat(student1.hashCode()).as("Хэш-коды одинаковых студентов должны совпадать")
				.isEqualTo(student2.hashCode()), () -> assertThat(student1.hashCode())
						.as("У разных студентов хэш-коды должны отличаться")
						.isNotEqualTo(student3.hashCode()));

		Student student4 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student5 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.20).setRecordBookNumber("104857")
				.build();

		assertThat(student4.hashCode()).as("Студенты с разным баллом должны иметь разный хэш-код")
				.isNotEqualTo(student5.hashCode());
	}

	@Test
	@DisplayName("compareTo: должен возвращать 0 при сравнении студента с самим собой")
	void compareTest() {
		Student student1 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student2 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student3 = Student.builder().setGroupNumber("ПМ-22").setAverageGrade(3.90).setRecordBookNumber("205948")
				.build();

		assertAll(() -> assertThat(student1.compareTo(student1))
				.as("Студент должен быть равен себе и возвращать 0")
				.isZero(), () -> assertThat(student1.compareTo(student2))
						.as("Идентичные студенты должны возвращать 0 при сравнении")
						.isZero(), () -> assertThat(student1.compareTo(student3))
								.as("Результат должен быть отрицательным, так как группа АЯ идет раньше ПМ").isNegative());

		Student student4 = Student.builder().setGroupNumber("ПМ-24").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student5 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student4.compareTo(student5))
				.as("Результат должен быть положительным, так как группа ПМ идет позже АЯ").isPositive();

		Student student6 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student7 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(3.90).setRecordBookNumber("104857")
				.build();

		assertThat(student6.compareTo(student7))
				.as("Результат должен быть положительным, так как балл первого студента больше").isPositive();

		Student student8 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(3.90).setRecordBookNumber("104857")
				.build();
		Student student9 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student8.compareTo(student9))
				.as("Результат должен быть отрицательным, так как балл первого студента меньше").isNegative();

		Student student10 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();
		Student student11 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("205948")
				.build();

		assertThat(student10.compareTo(student11))
				.as("Результат должен быть отрицательным, так как зачетка 104... идет раньше 205...")
				.isNegative();

		Student student12 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("205948")
				.build();
		Student student13 = Student.builder().setGroupNumber("АЯ-51").setAverageGrade(4.75).setRecordBookNumber("104857")
				.build();

		assertThat(student12.compareTo(student13))
				.as("Результат должен быть положительным, так как зачетка 205... идет позже 104...").isPositive();
	}

	@Test
	void validationTest() {
		assertAll(() -> assertThatThrownBy(() -> Student.builder().setGroupNumber("").setAverageGrade(4.5).setRecordBookNumber("104857")
				.build()).as("Ожидалось исключение при пустом номере группы")
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Номер группы должен быть указан"), () -> assertThatThrownBy(() -> Student.builder().setGroupNumber("АЯ-51")
						.setAverageGrade(-0.5).setRecordBookNumber("104857")
						.build()).as("Ожидалось исключение при среднем балле меньше 0.0").isInstanceOf(IllegalArgumentException.class)
						.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0"), () -> assertThatThrownBy(() -> Student
								.builder().setGroupNumber("АЯ-51").setAverageGrade(5.1).setRecordBookNumber("104857")
								.build()).as("Ожидалось исключение при среднем балле больше 5.0")
								.isInstanceOf(IllegalArgumentException.class)
								.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0"), () -> assertThatThrownBy(() -> Student
										.builder().setGroupNumber("АЯ-51").setAverageGrade(Double.NaN)
										.setRecordBookNumber("104857").build())
										.as("Ожидалось исключение при среднем балле равном Double.NaN(не число)")
										.isInstanceOf(IllegalArgumentException.class)
										.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0"), () -> assertThatThrownBy(() -> Student
												.builder().setGroupNumber("АЯ-51").setAverageGrade(Double.POSITIVE_INFINITY)
												.setRecordBookNumber("104857").build())
												.as("Ожидалось исключение при среднем балле равном Double.NaN(не число)")
												.isInstanceOf(IllegalArgumentException.class)
												.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0"), () -> assertThatThrownBy(() -> Student
														.builder().setGroupNumber("АЯ-51").setAverageGrade(Double.NEGATIVE_INFINITY)
														.setRecordBookNumber("104857").build())
														.as("Ожидалось исключение при среднем балле равном Double.NaN(не число)")
														.isInstanceOf(IllegalArgumentException.class)
														.hasMessage("Средний балл должен быть числом в диапазоне от 0.0 до 5.0"), () -> assertThatThrownBy(() -> Student
																.builder().setGroupNumber("АЯ-51").setAverageGrade(4.5)
																.setRecordBookNumber("   ").build())
																.as("Ожидалось исключение при пустом номере зачетной книжки")
																.isInstanceOf(IllegalArgumentException.class)
																.hasMessage("Номер зачетной книжки должен быть указан"), () -> assertThatThrownBy(() -> Student
																		.builder().setGroupNumber(" ").setAverageGrade(4.5)
																		.setRecordBookNumber("1").build())
																		.as("Ожидалось исключение при пустом номере группы")
																		.isInstanceOf(IllegalArgumentException.class)
																		.hasMessage("Номер группы должен быть указан"));
	}
}