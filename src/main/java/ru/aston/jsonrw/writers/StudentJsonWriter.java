package ru.aston.jsonrw.writers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Student;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StudentJsonWriter {
	private final ObjectMapper objectMapper;

	public StudentJsonWriter() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Student.class, MixinUtils.StudentMixIn.class);
		this.objectMapper.addMixIn(Student.Builder.class, MixinUtils.StudentBuilderMixIn.class);
	}

	public void writeStudentsToFile(final Collection<Student> students, final String filePath) {
		if (students == null || filePath == null || filePath.isBlank()) {
			return;
		}
		try {
			final Path path = Path.of(filePath);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), students);
			System.out.println("Студенты успешно записаны в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при записи студентов в файл: '%s'%n", e.getMessage());
		}
	}

	public void appendStudentsToFile(final Collection<Student> students, final String filePath) {
		if (students == null || students.isEmpty() || filePath == null || filePath.isBlank()) {
			return;
		}

		final File file = new File(filePath);

		if (!file.exists() || file.length() == 0) {
			writeStudentsToFile(students, filePath);
			return;
		}

		try {
			List<Student> existing = objectMapper.readValue(file, new TypeReference<>() {
			});

			List<Student> merged = Stream.concat(Optional.ofNullable(existing).stream().flatMap(Collection::stream), students.stream())
					.toList();

			writeStudentsToFile(merged, filePath);
			System.out.println("Студенты успешно добавлены в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при добавлении студентов в файл: '%s'%n", e.getMessage());
		}
	}
}