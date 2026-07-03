package ru.aston.jsonreader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import ru.aston.model.Barrel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BarrelJsonReader {
	private final ObjectMapper objectMapper;

	public BarrelJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Barrel.class, BarrelMixIn.class);
		this.objectMapper.addMixIn(Barrel.Builder.class, BuilderMixIn.class);
	}

	public void writeBarrelsToFile(final List<Barrel> barrels, final String filePath) {
		if (barrels == null || filePath == null || filePath.isBlank()) {
			return;
		}
		try {
			File file = new File(filePath);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, barrels);
			System.out.println("Бочки успешно записаны в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при записи бочек в файл: '%s'%n", e.getMessage());
		}
	}

	public List<Barrel> readBarrelsFromFile(final String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return List.of();
		}

		final Path file = Path.of(filePath);
		if (!Files.exists(file) || !Files.isRegularFile(file)) {
			System.err.printf("Не удалось найти файл с бочками '%s'%n", filePath);
			return List.of();
		}

		try {
			List<Barrel> barrels = objectMapper.readValue(file.toFile(), new TypeReference<>() {
			});
			return (barrels != null) ? barrels : List.of();
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла с бочками: '%s'%n", e.getMessage());
			return List.of();
		}
	}

	@JsonDeserialize(builder = Barrel.Builder.class)
	abstract static class BarrelMixIn {
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	abstract static class BuilderMixIn {
	}
}
