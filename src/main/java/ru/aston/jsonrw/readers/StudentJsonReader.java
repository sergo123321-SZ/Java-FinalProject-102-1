package ru.aston.jsonrw.readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class StudentJsonReader {
	private final ObjectMapper objectMapper;

	public StudentJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Student.class, MixinUtils.StudentMixIn.class);
		this.objectMapper.addMixIn(Student.Builder.class, MixinUtils.StudentBuilderMixIn.class);
	}

	public Collection<Student> readStudentsFromFile(final String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return List.of();
		}

		final Path path = Path.of(filePath);
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			System.err.printf("Не удалось найти файл со студентами '%s'%n", filePath);
			return List.of();
		}

		try {
			Collection<Student> students = objectMapper.readValue(path.toFile(), new TypeReference<>() {
			});
			return (students != null) ? students : List.of();
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла со студентами: '%s'%n", e.getMessage());
			return List.of();
		}
	}
}