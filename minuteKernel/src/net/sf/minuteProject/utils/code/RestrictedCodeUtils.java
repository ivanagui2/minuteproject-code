package net.sf.minuteProject.utils.code;

import net.sf.minuteProject.utils.FormatUtils;

public class RestrictedCodeUtils {

	public static final String REPLCACEMENT_CHAR = "_";

	public static final String convertToValidJava(String input) {
		return FormatUtils.convertToValidJava(input, "[^A-Za-z0-9_]",
				REPLCACEMENT_CHAR);
	}

	private static final String convertToValidJava(String input,
			boolean upperCase) {
		return FormatUtils.convertToValidJava(input, "[^A-Za-z0-9_]",
				REPLCACEMENT_CHAR, false);
	}

	public static final String convertToValidJavaWithUpperCase(String input) {
		return FormatUtils.convertToValidJava(input, "[^A-Za-z0-9_]",
				REPLCACEMENT_CHAR, true);
	}

}
