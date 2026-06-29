package ru.aston.model;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class Student {
	private final String groupNumber;
	private final double averageGrade;
	private final String recordBookNumber;

	private Student(@NotNull final Builder builder) {
		this(builder.groupNumber, builder.averageGrade, builder.recordBookNumber);
	}

	public Student(
	        @NotNull final String groupNumber, final double averageGrade, @NotNull final String recordBookNumber
	) {
		if (averageGrade < 0.0 || averageGrade > 5.0) {
			throw new IllegalArgumentException(
			        "Средний балл должен быть в диапазоне от 0.0 до 5.0. Передано: " + averageGrade
			);
		}

		this.groupNumber = Objects.requireNonNull(groupNumber, "Номер группы должен быть указан").trim();
		this.recordBookNumber = Objects.requireNonNull(recordBookNumber, "Номер зачетной книжки должен быть указан")
		        .trim();

		if (this.groupNumber.isEmpty()) {
			throw new IllegalArgumentException("Номер группы не может быть пустым");
		}
		if (this.recordBookNumber.isEmpty()) {
			throw new IllegalArgumentException("Номер зачетной книжки не может быть пустым");
		}

		this.averageGrade = averageGrade;
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

	public static class Builder {
		private String groupNumber;
		private double averageGrade = 0.0;
		private String recordBookNumber;

		public Builder() {
		}

		public Builder groupNumber(String groupNumber) {
			this.groupNumber = groupNumber;
			return this;
		}

		public Builder averageGrade(double averageGrade) {
			this.averageGrade = averageGrade;
			return this;
		}

		public Builder recordBookNumber(String recordBookNumber) {
			this.recordBookNumber = recordBookNumber;
			return this;
		}

		public Student build() {
			return new Student(this);
		}
	}
}