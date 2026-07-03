package ru.aston.jsonrw;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

public final class MixinUtils {

	private MixinUtils() {
	}

	@JsonDeserialize(builder = Barrel.Builder.class)
	public abstract static class BarrelMixIn {
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	public abstract static class BarrelBuilderMixIn {
	}

	@JsonDeserialize(builder = Car.Builder.class)
	public abstract static class CarMixIn {
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	public abstract static class CarBuilderMixIn {
	}

	@JsonDeserialize(builder = Student.Builder.class)
	public abstract static class StudentMixIn {
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	public abstract static class StudentBuilderMixIn {
	}
}
