package ru.aston.jsonrw.readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class StudentJsonReader {
	private final ObjectMapper objectMapper;

	public StudentJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Student.class, MixinUtils.StudentMixIn.class);
		this.objectMapper.addMixIn(Student.Builder.class, MixinUtils.StudentBuilderMixIn.class);
	}

	public void readStudentsFromFile(Collection<Student> collection, final String filePath, boolean append) {
		if (append) {
			if (collection == null) {
				collection = new LinkedList<>();
			}
			addStudentsToCollection(collection, filePath);
		}
		else {
			collection = readStudentsFromFile(filePath);
		}
	}

	public void addStudentsToCollection(final Collection<Student> collection, final String filePath) {
		if (collection == null || filePath == null || filePath.isBlank()) {
			return;
		}

		final Path path = Path.of(filePath);
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			System.err.printf("Не удалось найти файл со студентами '%s'%n", filePath);
			return;
		}

		try (JsonParser parser = objectMapper.getFactory().createParser(path.toFile())) {
			JsonToken currentToken = parser.nextToken();

			if (currentToken != JsonToken.START_ARRAY) {
				System.err.println("Ошибка: JSON должен начинаться с массива объектов");
				return;
			}

			Collection<Student> studentsFromFile = objectMapper.readValue(parser, new TypeReference<>() {
			});
			if (studentsFromFile != null && !studentsFromFile.isEmpty()) {
				collection.addAll(studentsFromFile);
			}
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла со студентами: '%s'%n", e.getMessage());
		}
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

		try (JsonParser parser = objectMapper.getFactory().createParser(path.toFile())) {
			JsonToken currentToken = parser.nextToken();

			if (currentToken != JsonToken.START_ARRAY) {
				System.err.println("Ошибка: JSON должен начинаться с массива объектов");
				return List.of();
			}

			Collection<Student> students = objectMapper.readValue(parser, new TypeReference<>() {
			});
			return (students != null) ? students : List.of();
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла со студентами: '%s'%n", e.getMessage());
			return List.of();
		}
	}
}