package ru.aston.jsonreader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import ru.aston.model.Student;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StudentJsonReader {
	private final ObjectMapper objectMapper;

	public StudentJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Student.class, StudentMixIn.class);
		this.objectMapper.addMixIn(Student.Builder.class, StudentBuilderMixIn.class);
	}

	public void writeStudentsToFile(final List<Student> students, final String filePath) {
		if (students == null || filePath == null || filePath.isBlank()) {
			return;
		}
		try {
			File file = new File(filePath);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, students);
			System.out.println("Студенты успешно записаны в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при записи студентов в файл: '%s'%n", e.getMessage());
		}
	}

	public List<Student> readStudentsFromFile(final String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return List.of();
		}

		final Path file = Path.of(filePath);
		if (!Files.exists(file) || !Files.isRegularFile(file)) {
			System.err.printf("Не удалось найти файл со студентами '%s'%n", filePath);
			return List.of();
		}

		try {
			List<Student> students = objectMapper.readValue(file.toFile(), new TypeReference<>() {
			});
			return (students != null) ? students : List.of();
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла со студентами: '%s'%n", e.getMessage());
			return List.of();
		}
	}

	@JsonDeserialize(builder = Student.Builder.class)
	abstract static class StudentMixIn {
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	abstract static class StudentBuilderMixIn {
	}
}