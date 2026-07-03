package ru.aston.jsonrw.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Barrel;

import java.nio.file.Path;
import java.util.Collection;

public class BarrelJsonWriter {
	private final ObjectMapper objectMapper;

	public BarrelJsonWriter() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Barrel.class, MixinUtils.BarrelMixIn.class);
		this.objectMapper.addMixIn(Barrel.Builder.class, MixinUtils.BarrelBuilderMixIn.class);
	}

	public void writeBarrelsToFile(final Collection<Barrel> barrels, final String filePath) {
		if (barrels == null || filePath == null || filePath.isBlank()) {
			return;
		}
		try {
			final Path path = Path.of(filePath);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), barrels);
			System.out.println("Бочки успешно записаны в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при записи бочек в файл: '%s'%n", e.getMessage());
		}
	}
}
