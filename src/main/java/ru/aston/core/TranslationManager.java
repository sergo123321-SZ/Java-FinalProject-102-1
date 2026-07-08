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

	public static String getExitOptionDescription() {
		return getString("option.exit.description");
	}

	public static String getModelOptionDescription() {
		return getString("option.model.description");
	}

	public static String getDisplayOptionDescription() {
		return getString("option.display.description");
	}

	public static String getSortOptionDescription() {
		return getString("option.sort.description");
	}

	public static String getExportOptionDescription() {
		return getString("option.export.description");
	}

	public static String getFileOptionDescription() {
		return getString("option.file.description");
	}

	public static String getImportOptionDescription() {
		return getString("option.import.description");
	}

	public static String getCreateOptionDescription() {
		return getString("option.create.description");
	}

}
