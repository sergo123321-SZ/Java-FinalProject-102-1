package ru.aston.model;

public class Student {
	private final String groupNumber;
	private final double averageGrade;
	private final String recordBookNumber;

	public Student(final String groupNumber, final double averageGrade, final String recordBookNumber) {
		if (groupNumber == null || groupNumber.isBlank()) {
			throw new IllegalArgumentException("Номер группы не может быть пустым или равным null.");
		}
		if (recordBookNumber == null || recordBookNumber.isBlank()) {
			throw new IllegalArgumentException("Номер зачетной книжки не может быть пустым или равным null.");
		}
		if (averageGrade < 0.0 || averageGrade > 5.0) {
			throw new IllegalArgumentException(
			        "Средний балл должен быть в диапазоне от 0,0 до 5,0. Дано:" + averageGrade
			);
		}

		this.groupNumber = groupNumber;
		this.averageGrade = averageGrade;
		this.recordBookNumber = recordBookNumber;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public double getAverageGrade() {
		return averageGrade;
	}

	public String getRecordBookNumber() {
		return recordBookNumber;
	}

	@Override
	public String toString() {
		return String.format(
		        "Студент [Группа: %s, Средний балл: %.2f, Номер зачетной книжки: %s]", groupNumber, averageGrade,
		        recordBookNumber
		);
	}
}