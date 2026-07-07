package ru.aston.core;


import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationManager {
	private static final String TRANSLATIONS_BASE_NAME = "messages";

	private static Locale currentLocale = Locale.getDefault();
	private static ResourceBundle resourceBundle = reInitializeResources();

	private TranslationManager() {
	}

	public static void setLocale(Locale locale) {
		currentLocale = locale;
		reInitializeResources();
	}

	private static ResourceBundle reInitializeResources() {
		resourceBundle = ResourceBundle.getBundle(TRANSLATIONS_BASE_NAME, currentLocale);
		return resourceBundle;
	}

	public static String getString(String key) {
		return resourceBundle.getString(key);
	}

	public static String getString(String key, Object... args) {
		String resourceBundleString = resourceBundle.getString(key);
		return MessageFormat.format(resourceBundleString, args);
	}

	public static String sayHello(String name) {
		return getString("greeting", name);
	}

	public static String sayBye() {
		return getString("farewell");
	}

	public static String displayCount(int count) {
		return getString("items_count", count);
	}
}
