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

	public enum WriteType {
		APPEND("A"),
		OVERWRITE("W");

		private final String value;

		WriteType(String value) {
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
