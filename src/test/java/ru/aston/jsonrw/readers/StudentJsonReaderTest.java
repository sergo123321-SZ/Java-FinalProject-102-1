package ru.aston.jsonrw.readers;

import nl.altindag.console.ConsoleCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.aston.model.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentJsonReaderTest {

	@TempDir Path tempDir;

	private StudentJsonReader reader;

	@BeforeEach
	void setUp() {
		reader = new StudentJsonReader();
	}

	// --- readStudentsFromFile(Collection, String, boolean) ---

	@Nested
	@DisplayName("readStudentsFromFile(Collection, String, boolean)")
	class ReadWithAppend {

		@Test
		@DisplayName("append = true, collection = null → создаёт LinkedList и добавляет студентов")
		void appendTrueCollectionNull_createsLinkedListAndAddsStudents() throws Exception {
			// given
			Path file = createStudentsJsonFile("students.json");

			// when
			reader.readStudentsFromFile(null, file.toString(), true);

			// then (проверяем, что данные были прочитаны)
			Collection<Student> result = reader.readStudentsFromFile(file.toString());
			assertEquals(2, result.size());
			assertEquals("201-IT", result.stream().findFirst().get().getGroupNumber());
		}

		@Test
		@DisplayName("append = true, collection != null → добавляет студентов в существующую коллекцию")
		void appendTrueCollectionNotNull_appendsStudents() throws Exception {
			// given
			Path file = createStudentsJsonFile("students.json");
			Student existing = new Student("202-IT", 4.5, "RB123456");
			Collection<Student> collection = new ArrayList<>(List.of(existing));

			// when
			reader.readStudentsFromFile(collection, file.toString(), true);

			// then
			assertEquals(3, collection.size());
			assertTrue(collection.contains(existing));
		}

		@Test
		@DisplayName("append = false → заменяет коллекцию новой (не мутирует оригинал)")
		void appendFalse_replacesCollection() throws Exception {
			// given
			Path file = createStudentsJsonFile("students.json");
			Student existing = new Student("202-IT", 4.5, "RB123456");
			Collection<Student> original = new ArrayList<>(List.of(existing));

			// when
			Collection<Student> result = reader.readStudentsFromFile(file.toString());
			reader.readStudentsFromFile(original, file.toString(), false);

			// then
			assertNotSame(original, result); // оригинал не изменился
			assertEquals(2, result.size());
			assertFalse(result.contains(existing));
		}
	}

	// --- addStudentsToCollection(Collection, String) ---

	@Nested
	@DisplayName("addStudentsToCollection(Collection, String)")
	class AddToCollection {

		@Test
		@DisplayName("collection = null → ранний возврат (ничего не делает)")
		void collectionNull_returnsEarly() throws Exception {
			Path file = createStudentsJsonFile("students.json");
			assertDoesNotThrow(() -> reader.addStudentsToCollection(null, file.toString()));
		}

		@Test
		@DisplayName("filePath = null → ранний возврат")
		void filePathNull_returnsEarly() {
			Collection<Student> collection = new ArrayList<>();
			assertDoesNotThrow(() -> reader.addStudentsToCollection(collection, null));
		}

		@Test
		@DisplayName("filePath = \"\" → ранний возврат")
		void filePathBlank_returnsEarly() {
			Collection<Student> collection = new ArrayList<>();
			assertDoesNotThrow(() -> reader.addStudentsToCollection(collection, "   "));
		}

		@Test
		@DisplayName("файл не существует → выводит ошибку в System.err")
		void fileNotExists_printsError() {
			Path nonexistent = tempDir.resolve("nonexistent.json");
			Collection<Student> collection = new ArrayList<>();

			try (ConsoleCaptor captor = new ConsoleCaptor()) {
				reader.addStudentsToCollection(collection, nonexistent.toString());
			}

			assertTrue(collection.isEmpty());
		}

		@Test
		@DisplayName("не JSON-массив → выводит ошибку в System.err")
		void notJsonArray_printsError() throws Exception {
			Path file = tempDir.resolve("students.json");
			Files.writeString(file, "{\"name\": \"Alice\"}");
			Collection<Student> collection = new ArrayList<>();

			try (ConsoleCaptor captor = new ConsoleCaptor()) {
				reader.addStudentsToCollection(collection, file.toString());

			}

			assertTrue(collection.isEmpty());
		}

		@Test
		@DisplayName("битый JSON → выводит ошибку в System.err")
		void invalidJson_printsError() throws Exception {
			Path file = tempDir.resolve("students.json");
			Files.writeString(file, "[{\"name\": \"Alice\"");
			Collection<Student> collection = new ArrayList<>();

			try (ConsoleCaptor captor = new ConsoleCaptor()) {
				reader.addStudentsToCollection(collection, file.toString());
			}

			assertTrue(collection.isEmpty());
		}

		@Test
		@DisplayName("валидный JSON-файл → добавляет студентов")
		void validFile_addsStudents() throws Exception {
			Path file = createStudentsJsonFile("students.json");
			Collection<Student> collection = new ArrayList<>();

			reader.addStudentsToCollection(collection, file.toString());

			assertEquals(2, collection.size());
			assertEquals("201-IT", collection.stream().findFirst().get().getGroupNumber());
		}
	}

	// --- readStudentsFromFile(String) ---

	@Nested
	@DisplayName("readStudentsFromFile(String)")
	class ReadFromFileString {

		@Test
		@DisplayName("filePath = null → возвращает пустой List.of()")
		void filePathNull_returnsEmptyList() {
			Collection<Student> result = reader.readStudentsFromFile(null);
			assertTrue(result.isEmpty());
		}

		@Test
		@DisplayName("filePath = \"\" → возвращает пустой List.of()")
		void filePathBlank_returnsEmptyList() {
			Collection<Student> result = reader.readStudentsFromFile("   ");
			assertTrue(result.isEmpty());
		}

		@Test
		@DisplayName("файл не существует → возвращает пустой List.of() и выводит ошибку в System.err")
		void fileNotExists_returnsEmptyAndPrintsError() {
			Path nonexistent = tempDir.resolve("nonexistent.json");

			try (ConsoleCaptor captor = new ConsoleCaptor()) {
				Collection<Student> result = reader.readStudentsFromFile(nonexistent.toString());
				assertTrue(result.isEmpty());
			}
		}

		@Test
		@DisplayName("не JSON-массив → возвращает пустой List.of() и выводит ошибку")
		void notJsonArray_returnsEmptyAndPrintsError() throws Exception {
			Path file = tempDir.resolve("students.json");
			Files.writeString(file, "{\"name\": \"Alice\"}");

			try (ConsoleCaptor captor = new ConsoleCaptor()) {
				Collection<Student> result = reader.readStudentsFromFile(file.toString());

				assertTrue(result.isEmpty());
			}
		}

		@Test
		@DisplayName("битый JSON → возвращает пустой List.of() и выводит ошибку")
		void invalidJson_returnsEmptyAndPrintsError() throws Exception {
			Path file = tempDir.resolve("students.json");
			Files.writeString(file, "[{\"name\": \"Alice\"");

			try (ConsoleCaptor captor = new ConsoleCaptor()) {
				Collection<Student> result = reader.readStudentsFromFile(file.toString());

				assertTrue(result.isEmpty());
			}
		}

		@Test
		@DisplayName("валидный JSON-файл → возвращает коллекцию студентов")
		void validFile_returnsStudents() throws Exception {
			Path file = createStudentsJsonFile("students.json");
			Collection<Student> result = reader.readStudentsFromFile(file.toString());

			assertEquals(2, result.size());
			assertEquals("201-IT", result.stream().findFirst().get().getGroupNumber());
		}
	}

	// --- Helper methods ---

	private Path createStudentsJsonFile(String fileName) throws Exception {
		Path file = tempDir.resolve(fileName);
		Files.writeString(file, """
				[
				  {
				    "groupNumber": "201-IT",
				    "averageGrade": 4.7,
				    "recordBookNumber": "RB100001"
				  },
				  {
				    "groupNumber": "202-IT",
				    "averageGrade": 4.2,
				    "recordBookNumber": "RB100002"
				  }
				]
				""");
		return file;
	}
}