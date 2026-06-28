package ru.aston.model;

public class Student {
    private final String groupNumber;
    private final double averageGrade;
    private final String recordBookNumber;

    public Student(String groupNumber, double averageGrade, String recordBookNumber) {
        // Защита от NPE и пустых строк (проверяем обе переменные сразу)
        if (groupNumber == null || groupNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Номер группы не может быть пустым или равным null.");
        }
        if (recordBookNumber == null || recordBookNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Номер зачетной книжки не может быть пустым или равным null.");
        }
        if (averageGrade < 0.0 || averageGrade > 5.0) {
            throw new IllegalArgumentException("Средний балл должен быть в диапазоне от 0,0 до 5,0. Дано:" + averageGrade);
        }

        this.groupNumber = groupNumber.trim();
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber.trim();
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
        return String.format("Студент [Группа: %s, Средний балл: %.2f, Номер записной книжки: %s]",
                groupNumber, averageGrade, recordBookNumber);
    }
}