package ru.aston.jsonrw.writers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import ru.aston.jsonrw.MixinUtils;
import ru.aston.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentJsonWriterTest {

	private static final Student TEST_STUDENT_1 = Student.builder()
			.setGroupNumber("CS-101")
			.setAverageGrade(4.5)
			.setRecordBookNumber("123456")
			.build();

	private static final Student TEST_STUDENT_2 = Student.builder()
			.setGroupNumber("MA-202")
			.setAverageGrade(3.8)
			.setRecordBookNumber("789012")
			.build();

	private StudentJsonWriter writer;
	private ObjectMapper objectMapper;

	@TempDir Path tempDir;

	@BeforeEach
	void setUp() {
		writer = new StudentJsonWriter();
		objectMapper = new ObjectMapper();
		objectMapper.addMixIn(Student.class, MixinUtils.StudentMixIn.class);
		objectMapper.addMixIn(Student.Builder.class, MixinUtils.StudentBuilderMixIn.class);
	}

	// ======================= writeStudentsToFile(Collection, String)
	// =======================

	@Test
	void writeStudentsToFile_WithValidCollection_WritesValidJson() throws IOException {
		Path testFile = tempDir.resolve("students.json");
		Collection<Student> students = List.of(TEST_STUDENT_1, TEST_STUDENT_2);

		writer.writeStudentsToFile(students, testFile.toString());

		assertTrue(Files.exists(testFile));
		String content = Files.readString(testFile);
		assertTrue(content.contains("CS-101"));
		assertTrue(content.contains("MA-202"));
		assertTrue(content.startsWith("["));
	}

	@Test
	void writeStudentsToFile_WithNullCollection_DoesNothing() throws IOException {
		Path testFile = tempDir.resolve("students.json");
		writer.writeStudentsToFile(null, testFile.toString());
		assertFalse(Files.exists(testFile));
	}

	@Test
	void writeStudentsToFile_WithEmptyCollection_WritesEmptyArray() throws IOException {
		Path testFile = tempDir.resolve("students.json");
		writer.writeStudentsToFile(List.of(), testFile.toString());

		assertTrue(Files.exists(testFile));
		String content = Files.readString(testFile);
		assertEquals("[ ]", content.strip());
	}

	@Test
	void writeStudentsToFile_WithNullPath_DoesNothing() {
		assertDoesNotThrow(() -> writer.writeStudentsToFile(List.of(TEST_STUDENT_1), null));
	}

	@Test
	void writeStudentsToFile_WithBlankPath_DoesNothing() {
		assertDoesNotThrow(() -> writer.writeStudentsToFile(List.of(TEST_STUDENT_1), "   "));
	}

	// ======================= appendStudentsToFile =======================

	@Test
	void appendStudentsToFile_WithNullCollection_DoesNothing() throws IOException {
		Path testFile = tempDir.resolve("students.json");
		writer.appendStudentsToFile(null, testFile.toString());
		assertFalse(Files.exists(testFile));
	}

	@Test
	void appendStudentsToFile_WithEmptyCollection_DoesNothing() throws IOException {
		Path testFile = tempDir.resolve("students.json");
		writer.appendStudentsToFile(List.of(), testFile.toString());
		assertFalse(Files.exists(testFile));
	}

	@Test
	void appendStudentsToFile_WhenFileDoesNotExist_WritesFreshFile() throws IOException {
		Path testFile = tempDir.resolve("new-file.json");

		writer.appendStudentsToFile(List.of(TEST_STUDENT_1), testFile.toString());

		assertTrue(Files.exists(testFile));
		List<Student> readBack = objectMapper.readValue(testFile.toFile(), new TypeReference<>() {
		});
		assertEquals(1, readBack.size());
		assertEquals(TEST_STUDENT_1, readBack.get(0));
	}

	@Test
	void appendStudentsToFile_WhenFileExists_MergesData() throws IOException {
		Path testFile = tempDir.resolve("existing.json");
		writer.writeStudentsToFile(List.of(TEST_STUDENT_1), testFile.toString());

		writer.appendStudentsToFile(List.of(TEST_STUDENT_2), testFile.toString());

		List<Student> merged = objectMapper.readValue(testFile.toFile(), new TypeReference<>() {
		});
		assertEquals(2, merged.size());
		assertEquals(TEST_STUDENT_1, merged.get(0));
		assertEquals(TEST_STUDENT_2, merged.get(1));
	}

	@Test
	void appendStudentsToFile_WhenFileIsEmpty_WritesFreshArray() throws IOException {
		Path testFile = tempDir.resolve("empty.json");
		Files.createFile(testFile);

		writer.appendStudentsToFile(List.of(TEST_STUDENT_1), testFile.toString());

		List<Student> result = objectMapper.readValue(testFile.toFile(), new TypeReference<>() {
		});
		assertEquals(1, result.size());
	}

	@Test
	void appendStudentsToFile_WithMalformedExistingFile_PrintsErrorAndDoesNotCrash() throws IOException {
		Path testFile = tempDir.resolve("corrupt.json");
		Files.writeString(testFile, "not json at all");

		assertDoesNotThrow(() -> writer.appendStudentsToFile(List.of(TEST_STUDENT_1), testFile.toString()));
	}

	@Test
	void appendStudentsToFile_WithNullExistingAfterRead_TreatsAsEmpty() throws IOException {
		// Simulate Jackson returning null (edge case)
		Path testFile = tempDir.resolve("null-file.json");
		Files.writeString(testFile, "[]"); // valid JSON, but we'll simulate null read

		// Since Jackson doesn't return null for arrays, we test robustness:
		assertDoesNotThrow(() -> writer.appendStudentsToFile(List.of(TEST_STUDENT_1), testFile.toString()));
	}

	// ======================= writeStudentsToFile(..., append=false)
	// =======================

	@Test
	void writeStudentsToFile_WithAppendFalse_Overwrites() throws IOException {
		Path testFile = tempDir.resolve("overwrite.json");
		writer.writeStudentsToFile(List.of(TEST_STUDENT_1), testFile.toString());

		writer.writeStudentsToFile(List.of(TEST_STUDENT_2), testFile.toString(), false);

		List<Student> result = objectMapper.readValue(testFile.toFile(), new TypeReference<>() {
		});
		assertEquals(1, result.size());
		assertEquals(TEST_STUDENT_2, result.get(0));
	}

	@Test
	void writeStudentsToFile_WithAppendTrue_Merges() throws IOException {
		Path testFile = tempDir.resolve("merge.json");
		writer.writeStudentsToFile(List.of(TEST_STUDENT_1), testFile.toString());

		writer.writeStudentsToFile(List.of(TEST_STUDENT_2), testFile.toString(), true);

		List<Student> result = objectMapper.readValue(testFile.toFile(), new TypeReference<>() {
		});
		assertEquals(2, result.size());
	}
}