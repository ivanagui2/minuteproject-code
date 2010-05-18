package net.sf.minuteProject.utils.code;

import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.lang.XmlCharEntityReferencesUtils;

public class RestrictedCodeUtils {

	public static final String REPLACEMENT_CHAR = "_";

	public static final String convertToValidJava(String input) {
		return convertToValidJava(input, false);
	}

	private static final String convertToValidJava(String input, boolean upperCase) {
		input = FormatUtils.convertToValidJava(input, "[^A-Za-z0-9_]", REPLACEMENT_CHAR, upperCase);
		return FormatUtils.trimExpression(FormatUtils.eliminateMultipleSequenceOfChar(input, REPLACEMENT_CHAR.toCharArray()), REPLACEMENT_CHAR);
	}

	public static final String convertToValidJavaWithUpperCase(String input) {
		return convertToValidJava(input, true);
	}

	public static String convertToValidPlainTextFromXml(String input) {
		return XmlCharEntityReferencesUtils.convertToValidPlainTextFromXml(input);
	}

}
