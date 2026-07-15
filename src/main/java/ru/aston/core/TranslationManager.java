package ru.aston.core;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


public class TranslationManager {
	private static final String TRANSLATIONS_BASE_NAME = "messages";

	private static Locale currentLocale = Locale.getDefault();
	private static ResourceBundle resourceBundle = loadResources();
	private static String version;
	private static String authors;
	static {
		Properties properties = new Properties();
		try (InputStream input = TranslationManager.class.getClassLoader().getResourceAsStream("app.properties")) {
			if (input != null) {
				properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
				version = properties.getProperty("app.version");
				authors = properties.getProperty("app.authors");
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private TranslationManager() {
	}

	public static void setLocale(Locale locale) {
		currentLocale = locale;
		resourceBundle = loadResources();
	}

	public static Locale getLocale() {
		return currentLocale;
	}

	private static ResourceBundle loadResources() {
		return ResourceBundle.getBundle(TRANSLATIONS_BASE_NAME, currentLocale);
	}

	private static String getString(String key) {
		return resourceBundle.getString(key);
	}

	private static String getString(String key, Object... args) {
		return MessageFormat.format(resourceBundle.getString(key), args);
	}

	public static String getHelloText() {
		return getString("helloText", version, authors);
	}

	public static String getHelpText() {
		return getString("option.help.description");
	}

	public static String getExitOptionDescription() {
		return getString("option.exit.description");
	}

	public static String getLanguageOptionDescription() {
		return getString("option.language.description");
	}

	public static String getModelOptionDescription(String acceptableOptions) {
		return getString("option.model.description", acceptableOptions);
	}

	public static String getResetOptionDescription(String requiredSteps) {
		return getString("option.reset.description", requiredSteps);
	}

	public static String getLengthOptionDescription(String requiredSteps) {
		return getString("option.length.description", requiredSteps);
	}

	public static String getDisplayOptionDescription(String requiredSteps) {
		return getString("option.display.description", requiredSteps);
	}

	public static String getCreateOptionDescription(String requiredSteps) {
		return getString("option.create.description", requiredSteps);
	}

	public static String getManualOptionDescription(String requiredSteps) {
		return getString("option.manual.description", requiredSteps);
	}

	public static String getCountOptionDescription(String requiredSteps) {
		return getString("option.count.description", requiredSteps);
	}

	public static String getSortOptionDescription(
			String ascVariantName,
			String descVariantName,
			String specialVariantName,
			String requiredSteps)
	{
		return getString("option.sort.description", ascVariantName, descVariantName, specialVariantName, requiredSteps);
	}

	public static String getImportOptionDescription(
			String requiredSteps)
	{
		return getString("option.import.description", requiredSteps);
	}

	public static String getExportOptionDescription(
			String requiredSteps)
	{
		return getString("option.export.description", requiredSteps);
	}

	public static String getWriteModeOptionDescription(String addVariant, String overwriteVariant) {
		return getString("option.write-mode.description", addVariant, overwriteVariant);
	}

	public static String getValidationError(String details) {
		return getString("error.validation", details);
	}

	public static String getParsingError(String details) {
		return getString("error.parsing", details);
	}

	public static String getUnknownCommandStepError(String step) {
		return getString("error.command.step.unknown", step);
	}

	public static String getNoAcceptableOptionsError(String step) {
		return getString("error.command.acceptable-options.empty", step);
	}

	public static String getMissingRequiredOptionsError(String optionsDisplay) {
		return getString("error.options.required.missing", optionsDisplay);
	}

	public static String getUnknownOptionError(String optionName) {
		return getString("error.option.unknown", optionName);
	}

	public static String getUnsupportedExecutorError() {
		return getString("error.executor.unsupported");
	}

	public static String getNoExecutorsDefinedError() {
		return getString("error.executor.empty");
	}

	public static String getUnknownModelTypeError() {
		return getString("error.model-type.unknown");
	}

	public static String getNullModelTypeError() {
		return getString("error.model-type.null");
	}

	public static String getExportOptionValueRequiredError() {
		return getString("error.option.export.value-required");
	}

	public static String getExportOptionInvalidPathError() {
		return getString("error.option.export.invalid-path");
	}

	public static String getImportOptionValueRequiredError() {
		return getString("error.option.import.value-required");
	}

	public static String getImportOptionInvalidPathError() {
		return getString("error.option.import.invalid-path");
	}

	public static String getCreateOptionInvalidValueError() {
		return getString("error.option.create.invalid-value");
	}

	public static String getManualOptionInvalidValueError() {
		return getString("error.option.manual.invalid-value");
	}

	public static String getCountOptionInvalidValueError() {
		return getString("error.option.count.invalid-value");
	}

	public static String getSortOptionRequiredError() {
		return getString("error.option.sort.value-required");
	}

	public static String getSortOptionUnsupportedError() {
		return getString("error.option.sort.unsupported");
	}

	public static String getModelIdRequiredError() {
		return getString("error.option.model-id.required");
	}

	public static String getModelUnsupportedError() {
		return getString("error.option.model.unsupported");
	}

	public static String getWriteModeUnsupportedError() {
		return getString("error.option.write-mode.unsupported");
	}

	public static String getEmptyDisplayCollectionMessage() {
		return getString("output.collection.display.empty");
	}

	public static String getEmptySortCollectionMessage() {
		return getString("output.collection.sort.empty");
	}

	public static String getCollectionLengthMessage(int size) {
		return getString("output.collection.length", size);
	}

	public static String getCollectionNotInitializedMessage() {
		return getString("output.collection.uninitialized");
	}

	public static String getInputPrompt() {
		return getString("output.console.prompt");
	}

	public static String getBarrelsFileNotFoundError(String filePath) {
		return getString("error.json.file-not-found.barrels", filePath);
	}

	public static String getCarsFileNotFoundError(String filePath) {
		return getString("error.json.file-not-found.cars", filePath);
	}

	public static String getStudentsFileNotFoundError(String filePath) {
		return getString("error.json.file-not-found.students", filePath);
	}

	public static String getJsonArrayExpectedError() {
		return getString("error.json.array-required");
	}

	public static String getReadBarrelsJsonError(String details) {
		return getString("error.json.read.barrels", details);
	}

	public static String getReadCarsJsonError(String details) {
		return getString("error.json.read.cars", details);
	}

	public static String getReadStudentsJsonError(String details) {
		return getString("error.json.read.students", details);
	}

	public static String getWriteBarrelsSuccessMessage(String filePath) {
		return getString("output.json.write.barrels", filePath);
	}

	public static String getWriteCarsSuccessMessage(String filePath) {
		return getString("output.json.write.cars", filePath);
	}

	public static String getWriteStudentsSuccessMessage(String filePath) {
		return getString("output.json.write.students", filePath);
	}

	public static String getWriteBarrelsError(String details) {
		return getString("error.json.write.barrels", details);
	}

	public static String getWriteCarsError(String details) {
		return getString("error.json.write.cars", details);
	}

	public static String getWriteStudentsError(String details) {
		return getString("error.json.write.students", details);
	}

	public static String getAppendBarrelsSuccessMessage(String filePath) {
		return getString("output.json.append.barrels", filePath);
	}

	public static String getAppendCarsSuccessMessage(String filePath) {
		return getString("output.json.append.cars", filePath);
	}

	public static String getAppendStudentsSuccessMessage(String filePath) {
		return getString("output.json.append.students", filePath);
	}

	public static String getAppendBarrelsError(String details) {
		return getString("error.json.append.barrels", details);
	}

	public static String getAppendCarsError(String details) {
		return getString("error.json.append.cars", details);
	}

	public static String getAppendStudentsError(String details) {
		return getString("error.json.append.students", details);
	}

	public static String getCarPowerValidationError() {
		return getString("error.model.car.power");
	}

	public static String getCarModelValidationError() {
		return getString("error.model.car.model");
	}

	public static String getCarProductionYearValidationError() {
		return getString("error.model.car.production-year");
	}

	public static String getCarDisplayText(String model, int power, int productionYear) {
		return getString("output.model.car", model, power, productionYear);
	}

	public static String getStudentGroupNumberValidationError() {
		return getString("error.model.student.group-number");
	}

	public static String getStudentAverageGradeValidationError() {
		return getString("error.model.student.average-grade");
	}

	public static String getStudentRecordBookValidationError() {
		return getString("error.model.student.record-book");
	}

	public static String getStudentDisplayText(String groupNumber, double averageGrade, String recordBookNumber) {
		return getString("output.model.student", groupNumber, averageGrade, recordBookNumber);
	}

	public static String getBarrelVolumeValidationError() {
		return getString("error.model.barrel.volume");
	}

	public static String getBarrelStoredMaterialValidationError() {
		return getString("error.model.barrel.stored-material");
	}

	public static String getBarrelMaterialValidationError() {
		return getString("error.model.barrel.material");
	}

	public static String getBarrelDisplayText(double volume, String storedMaterial, String barrelMaterial) {
		return getString("output.model.barrel", volume, storedMaterial, barrelMaterial);
	}

	public static String getThreadsCountMustBePositiveError() {
		return getString("error.counter.threads-count.invalid");
	}

	public static String getCollectionMustNotBeEmptyError() {
		return getString("error.collection.empty");
	}

	public static String getUnknownDataTypeError() {
		return getString("error.data-type.unknown");
	}

	public static String getScannerNotAvailableError() {
		return getString("error.console.scanner.unavailable");
	}

	public static String getScannerInputEndedError() {
		return getString("error.console.input.ended");
	}

	public static String getManualInputMustNotBeBlankError() {
		return getString("error.manual.input.blank");
	}

	public static String getManualInputMustBeIntegerError() {
		return getString("error.manual.input.integer");
	}

	public static String getManualInputMustBeNumberError() {
		return getString("error.manual.input.number");
	}

	public static String getManualCarInputPrompt(int index) {
		return getString("output.manual.car.header", index);
	}

	public static String getManualStudentInputPrompt(int index) {
		return getString("output.manual.student.header", index);
	}

	public static String getManualBarrelInputPrompt(int index) {
		return getString("output.manual.barrel.header", index);
	}

	public static String getManualPromptCarModel() {
		return getString("output.manual.prompt.car.model");
	}

	public static String getManualPromptCarPower() {
		return getString("output.manual.prompt.car.power");
	}

	public static String getManualPromptCarProductionYear() {
		return getString("output.manual.prompt.car.production-year");
	}

	public static String getManualPromptStudentGroupNumber() {
		return getString("output.manual.prompt.student.group-number");
	}

	public static String getManualPromptStudentAverageGrade() {
		return getString("output.manual.prompt.student.average-grade");
	}

	public static String getManualPromptStudentRecordBook() {
		return getString("output.manual.prompt.student.record-book");
	}

	public static String getManualPromptBarrelVolume() {
		return getString("output.manual.prompt.barrel.volume");
	}

	public static String getManualPromptBarrelStoredMaterial() {
		return getString("output.manual.prompt.barrel.stored-material");
	}

	public static String getManualPromptBarrelMaterial() {
		return getString("output.manual.prompt.barrel.material");
	}

	public static String getCounterMethodPrompt() {
		return getString("output.counter.method.prompt");
	}

	public static String getCounterMethodInvalidError() {
		return getString("error.counter.method.invalid");
	}

	public static String getCounterResultMessage(long count) {
		return getString("output.counter.result", count);
	}
}
