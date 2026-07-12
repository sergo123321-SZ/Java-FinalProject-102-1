package ru.aston.core;


import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


public class TranslationManager {
	private static final String TRANSLATIONS_BASE_NAME = "messages";

	private static Locale currentLocale = Locale.getDefault();
	private static ResourceBundle resourceBundle = loadResources();

	private TranslationManager() {
	}

	public static void setLocale(Locale locale) {
		currentLocale = locale;
		resourceBundle = loadResources();
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

	public static String getHelpText(String version, String team) {
		return getString("helpText", version, team);
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
}
