package ru.aston.jsonrw.readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import ru.aston.core.TranslationManager;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Car;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CarJsonReader {
	private final ObjectMapper objectMapper;

	public CarJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Car.class, MixinUtils.CarMixIn.class);
		this.objectMapper.addMixIn(Car.Builder.class, MixinUtils.CarBuilderMixIn.class);
	}

	public void readCarsFromFile(Collection<Car> collection, final String filePath, boolean append) {
		if (append) {
			if (collection == null) {
				collection = new LinkedList<>();
			}
			addCarsToCollection(collection, filePath);
		}
		else {
			collection = readCarsFromFile(filePath);
		}
	}

	public void addCarsToCollection(final Collection<Car> collection, final String filePath) {
		if (collection == null || filePath == null || filePath.isBlank()) {
			return;
		}

		final Path path = Path.of(filePath);
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			System.err.println(TranslationManager.getCarsFileNotFoundError(filePath));
			return;
		}

		try (JsonParser parser = objectMapper.getFactory().createParser(path.toFile())) {
			JsonToken currentToken = parser.nextToken();

			if (currentToken != JsonToken.START_ARRAY) {
				System.err.println(TranslationManager.getJsonArrayExpectedError());
				return;
			}

			Collection<Car> carsFromFile = objectMapper.readValue(parser, new TypeReference<>() {
			});
			if (carsFromFile != null && !carsFromFile.isEmpty()) {
				collection.addAll(carsFromFile);
			}
		}
		catch (Exception e) {
			System.err.println(TranslationManager.getReadCarsJsonError(e.getMessage()));
		}
	}

	public Collection<Car> readCarsFromFile(final String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return List.of();
		}

		final Path path = Path.of(filePath);
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			System.err.println(TranslationManager.getCarsFileNotFoundError(filePath));
			return List.of();
		}

		try (JsonParser parser = objectMapper.getFactory().createParser(path.toFile())) {
			JsonToken currentToken = parser.nextToken();

			if (currentToken != JsonToken.START_ARRAY) {
				System.err.println(TranslationManager.getJsonArrayExpectedError());
				return List.of();
			}

			Collection<Car> cars = objectMapper.readValue(parser, new TypeReference<>() {
			});
			return (cars != null) ? cars : List.of();
		}
		catch (Exception e) {
			System.err.println(TranslationManager.getReadCarsJsonError(e.getMessage()));
			return List.of();
		}
	}

}
