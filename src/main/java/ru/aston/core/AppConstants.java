package ru.aston.core;


public class AppConstants {
	private AppConstants() {
	}

	public enum SortType {
		ASC("ASC"),
		DESC("DESC"),
		SPECIAL("SPECIAL");

		private final String value;

		SortType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum WriteMode {
		APPEND("A"),
		OVERWRITE("W");

		private final String value;

		WriteMode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum ModelType {
		CARS,
		STUDENTS,
		BARRELS
	}
}
