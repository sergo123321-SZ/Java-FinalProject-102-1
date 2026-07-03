package ru.aston.jsonreader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import ru.aston.model.Car;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CarJsonReader {
	private final ObjectMapper objectMapper;

	public CarJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Car.class, CarMixIn.class);
		this.objectMapper.addMixIn(Car.Builder.class, CarBuilderMixIn.class);
	}

	public void writeCarsToFile(final List<Car> cars, final String filePath) {
		if (cars == null || filePath == null || filePath.isBlank()) {
			return;
		}
		try {
			File file = new File(filePath);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, cars);
			System.out.println("Машины успешно записаны в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при записи машин в файл: '%s'%n", e.getMessage());
		}
	}

	public List<Car> readCarsFromFile(final String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return List.of();
		}

		final Path file = Path.of(filePath);
		if (!Files.exists(file) || !Files.isRegularFile(file)) {
			System.err.printf("Не удалось найти файл с машинами '%s'%n", filePath);
			return List.of();
		}

		try {
			List<Car> cars = objectMapper.readValue(file.toFile(), new TypeReference<>() {
			});
			return (cars != null) ? cars : List.of();
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла с машинами: '%s'%n", e.getMessage());
			return List.of();
		}
	}

	@JsonDeserialize(builder = Car.Builder.class)
	abstract static class CarMixIn {
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	abstract static class CarBuilderMixIn {
	}
}
