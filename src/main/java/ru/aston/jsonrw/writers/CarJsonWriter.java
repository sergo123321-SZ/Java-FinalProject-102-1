package ru.aston.jsonrw.writers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Car;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CarJsonWriter {
	private final ObjectMapper objectMapper;

	public CarJsonWriter() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.addMixIn(Car.class, MixinUtils.CarMixIn.class);
		this.objectMapper.addMixIn(Car.Builder.class, MixinUtils.CarBuilderMixIn.class);
	}

	public void writeCarsToFile(final Collection<Car> cars, final String filePath, boolean append) {
		if (append) {
			appendCarsToFile(cars, filePath);
		}
		else {
			writeCarsToFile(cars, filePath);
		}
	}

	public void writeCarsToFile(final Collection<Car> cars, final String filePath) {
		if (cars == null || filePath == null || filePath.isBlank()) {
			return;
		}
		try {
			final Path path = Path.of(filePath);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), cars);
			System.out.println("Машины успешно записаны в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при записи машин в файл: '%s'%n", e.getMessage());
		}
	}

	public void appendCarsToFile(final Collection<Car> cars, final String filePath) {
		if (cars == null || cars.isEmpty() || filePath == null || filePath.isBlank()) {
			return;
		}

		final File file = new File(filePath);

		if (!file.exists() || file.length() == 0) {
			writeCarsToFile(cars, filePath);
			return;
		}

		try {
			List<Car> existing = objectMapper.readValue(file, new TypeReference<>() {
			});

			List<Car> merged = Stream.concat(Optional.ofNullable(existing).stream().flatMap(Collection::stream), cars.stream()).toList();

			writeCarsToFile(merged, filePath);
			System.out.println("Машины успешно добавлены в файл: " + filePath);
		}
		catch (Exception e) {
			System.err.printf("Ошибка при добавлении машин в файл: '%s'%n", e.getMessage());
		}
	}
}