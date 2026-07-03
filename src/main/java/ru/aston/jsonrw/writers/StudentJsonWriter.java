package ru.aston.jsonrw.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Student;

import java.nio.file.Path;
import java.util.Collection;

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
}