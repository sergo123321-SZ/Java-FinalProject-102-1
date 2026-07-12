package ru.aston.jsonrw.writers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.core.TranslationManager;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Barrel;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class BarrelJsonWriter {
	private final ObjectMapper objectMapper;

	public BarrelJsonWriter() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Barrel.class, MixinUtils.BarrelMixIn.class);
		this.objectMapper.addMixIn(Barrel.Builder.class, MixinUtils.BarrelBuilderMixIn.class);
	}

	public void writeBarrelsToFile(final Collection<Barrel> barrels, final String filePath, boolean append) {
		if (append) {
			appendBarrelsToFile(barrels, filePath);
		}
		else {
			writeBarrelsToFile(barrels, filePath);
		}
	}

	public void writeBarrelsToFile(final Collection<Barrel> barrels, final String filePath) {
		if (barrels == null || filePath == null || filePath.isBlank()) {
			return;
		}
		try {
			final Path path = Path.of(filePath);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), barrels);
			System.out.println(TranslationManager.getWriteBarrelsSuccessMessage(filePath));
		}
		catch (Exception e) {
			System.err.println(TranslationManager.getWriteBarrelsError(e.getMessage()));
		}
	}

	public void appendBarrelsToFile(final Collection<Barrel> barrels, final String filePath) {
		if (barrels == null || barrels.isEmpty() || filePath == null || filePath.isBlank()) {
			return;
		}

		final File file = new File(filePath);

		if (!file.exists() || file.length() == 0) {
			writeBarrelsToFile(barrels, filePath);
			return;
		}

		try {
			List<Barrel> existing = objectMapper.readValue(file, new TypeReference<>() {
			});

			List<Barrel> merged = Stream.concat(Optional.ofNullable(existing).stream().flatMap(Collection::stream), barrels.stream())
					.toList();

			writeBarrelsToFile(merged, filePath);
			System.out.println(TranslationManager.getAppendBarrelsSuccessMessage(filePath));
		}
		catch (Exception e) {
			System.err.println(TranslationManager.getAppendBarrelsError(e.getMessage()));
		}
	}
}
