package grafika;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static String BUNDLE_NAME = "grafika.englis"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	Locale spanishLocale = new Locale("grafika.messages", "ES");
	//ResourceBundle bundle3 = ResourceBundle.getBundle("grafika.messages", spanishLocale);


	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static void setString(String str) {
		BUNDLE_NAME=str;
	}
	
	public static void setBundle(Locale locale) {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		} catch (Exception e) {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ENGLISH);
		}
	}
}
