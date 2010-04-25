package net.sf.minuteProject.utils.code;

import net.sf.minuteProject.utils.FormatUtils;

public class RestrictedCodeUtils {

	public static final String REPLACEMENT_CHAR = "_";

	public static final String convertToValidJava(String input) {
		return FormatUtils.convertToValidJava(input, "[^A-Za-z0-9_]",
				REPLACEMENT_CHAR);
	}

	private static final String convertToValidJava(String input,
			boolean upperCase) {
		return FormatUtils.convertToValidJava(input, "[^A-Za-z0-9_]",
				REPLACEMENT_CHAR, false);
	}

	public static final String convertToValidJavaWithUpperCase(String input) {
		return FormatUtils.convertToValidJava(input, "[^A-Za-z0-9_]",
				REPLACEMENT_CHAR, true);
	}

}
