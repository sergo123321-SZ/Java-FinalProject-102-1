package ru.aston.model;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class Student implements Comparable<Student> {
	private final String groupNumber;
	private final double averageGrade;
	private final String recordBookNumber;

	private Student(@NotNull final Builder builder) {
		this(builder.groupNumber, builder.averageGrade, builder.recordBookNumber);
	}

	public Student(
			@NotNull final String groupNumber, final double averageGrade, @NotNull final String recordBookNumber
	) {
		if (groupNumber.isBlank()) {
			throw new IllegalArgumentException("Номер группы должен быть указан");
		}
		if (Double.isNaN(averageGrade) || Double.isInfinite(averageGrade) || averageGrade < 0.0 || averageGrade > 5.0) {
			throw new IllegalArgumentException("Средний балл должен быть числом в диапазоне от 0.0 до 5.0");
		}
		if (recordBookNumber.isBlank()) {
			throw new IllegalArgumentException("Номер зачетной книжки должен быть указан");
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Student student))
			return false;

		return this.groupNumber.equals(student.groupNumber)
				&& Double.compare(this.averageGrade, student.averageGrade) == 0
				&& this.recordBookNumber.equals(student.recordBookNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupNumber, averageGrade, recordBookNumber);
	}

	@Override
	public int compareTo(@NotNull Student other) {
		int result = this.groupNumber.compareTo(other.groupNumber);
		if (result != 0)
			return result;

		result = Double.compare(this.averageGrade, other.averageGrade);
		if (result != 0)
			return result;

		return this.recordBookNumber.compareTo(other.recordBookNumber);
	}

	public static class Builder {
		private String groupNumber;
		private double averageGrade = 0.0;
		private String recordBookNumber;

		public Builder() {
		}

		public Builder setGroupNumber(String groupNumber) {
			this.groupNumber = groupNumber;
			return this;
		}

		public Builder setAverageGrade(double averageGrade) {
			this.averageGrade = averageGrade;
			return this;
		}

		public Builder setRecordBookNumber(String recordBookNumber) {
			this.recordBookNumber = recordBookNumber;
			return this;
		}

		public Student build() {
			return new Student(this);
		}
	}
}