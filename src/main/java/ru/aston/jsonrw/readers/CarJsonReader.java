package ru.aston.jsonrw.readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingIterator;

import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Car;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class CarJsonReader {
	private final ObjectMapper objectMapper;

	public CarJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Car.class, MixinUtils.CarMixIn.class);
		this.objectMapper.addMixIn(Car.Builder.class, MixinUtils.CarBuilderMixIn.class);
	}

	public void addCarsToCollection(final Collection<Car> collection, final String filePath) {
		if (collection == null || filePath == null || filePath.isBlank()) {
			return;
		}

		final Path path = Path.of(filePath);
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			System.err.printf("Не удалось найти файл с машинами '%s'%n", filePath);
			return;
		}

		try (JsonParser parser = objectMapper.getFactory().createParser(path.toFile())) {
			JsonToken currentToken = parser.nextToken();

			if (currentToken != JsonToken.START_ARRAY) {
				System.err.println("Ошибка: JSON должен начинаться с массива объектов");
				return;
			}

			MappingIterator<Car> it = objectMapper.readValues(parser, Car.class);
			while (it.hasNext()) {
				Car car = it.next();
				if (car != null) {
					collection.add(car);
				}
			}
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла с машинами: '%s'%n", e.getMessage());
		}
	}

	public Collection<Car> readCarsFromFile(final String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return List.of();
		}

		final Path path = Path.of(filePath);
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			System.err.printf("Не удалось найти файл с машинами '%s'%n", filePath);
			return List.of();
		}

		try (JsonParser parser = objectMapper.getFactory().createParser(path.toFile())) {
			JsonToken currentToken = parser.nextToken();

			if (currentToken != JsonToken.START_ARRAY) {
				System.err.println("Ошибка: JSON должен начинаться с массива объектов");
				return List.of();
			}

			Collection<Car> cars = objectMapper.readValue(parser, new TypeReference<>() {
			});
			return (cars != null) ? cars : List.of();
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла с машинами: '%s'%n", e.getMessage());
			return List.of();
		}
	}

}
