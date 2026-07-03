package ru.aston.jsonrw.readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Barrel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class BarrelJsonReader {
	private final ObjectMapper objectMapper;

	public BarrelJsonReader() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Barrel.class, MixinUtils.BarrelMixIn.class);
		this.objectMapper.addMixIn(Barrel.Builder.class, MixinUtils.BarrelBuilderMixIn.class);
	}

	public Collection<Barrel> readBarrelsFromFile(final String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return List.of();
		}

		final Path path = Path.of(filePath);
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			System.err.printf("Не удалось найти файл с бочками '%s'%n", filePath);
			return List.of();
		}

		try {
			Collection<Barrel> barrels = objectMapper.readValue(path.toFile(), new TypeReference<>() {
			});
			return (barrels != null) ? barrels : List.of();
		}
		catch (Exception e) {
			System.err.printf("Ошибка при чтении JSON файла с бочками: '%s'%n", e.getMessage());
			return List.of();
		}
	}
}
