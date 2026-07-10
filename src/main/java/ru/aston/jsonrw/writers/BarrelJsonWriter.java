package ru.aston.jsonrw.writers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Barrel;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
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

	public void appendBarrelsToFile(final Collection<Barrel> barrels, final String filePath) {
		if (barrels == null || barrels.isEmpty() || filePath == null || filePath.isBlank()) {
			return;
		}

		final File file = new File(filePath);

		if (!file.exists() || file.length() == 0) {
			writeBarrelsToFile(barrels, filePath);
			return;
		}

		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
			long length = raf.length();
			long pointer = length - 1;

			while (pointer >= 0) {
				raf.seek(pointer);
				int ch = raf.read();
				if (ch == ']') {
					break;
				}
				pointer--;
			}

			if (pointer < 0) {
				throw new IllegalStateException("Файл не содержит валидного JSON-массива");
			}

			boolean isArrayEmpty = false;
			if (pointer > 0) {
				raf.seek(pointer - 1);
				if (raf.read() == '[') {
					isArrayEmpty = true;
				}
			}

			raf.seek(pointer);
			if (!isArrayEmpty) {
				raf.writeBytes(",\n  ");
			}
			else {
				raf.writeBytes("\n  ");
			}

			DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
			DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("  ", DefaultIndenter.SYS_LF);
			prettyPrinter.indentObjectsWith(indenter);
			prettyPrinter.indentArraysWith(indenter);

			try (JsonGenerator generator = objectMapper.getFactory()
					.createGenerator(Channels.newOutputStream(raf.getChannel()))) {

				generator.setPrettyPrinter(prettyPrinter);

				barrels.stream().forEach(barrel -> {
					try {
						objectMapper.writeValue(generator, barrel);
					}
					catch (Exception e) {
						throw new RuntimeException("Ошибка записи элемента", e);
					}
				});
			}

			try (RandomAccessFile rafClose = new RandomAccessFile(file, "rw")) {
				rafClose.seek(rafClose.length());
				rafClose.writeBytes("\n]");
			}

			System.out.println("Бочки успешно добавлены в файл: " + filePath);

		}
		catch (Exception e) {
			System.err.printf("Ошибка при добавлении бочек в файл: '%s'%n", e.getMessage());
		}
	}
}
