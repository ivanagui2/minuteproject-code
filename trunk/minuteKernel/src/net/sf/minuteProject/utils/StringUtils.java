package net.sf.minuteProject.utils;

public class StringUtils {

	public static boolean startsWithIgnoreCase(String valueToTest, String startsWith) {
		valueToTest = org.apache.commons.lang.StringUtils.upperCase(valueToTest);
		startsWith = org.apache.commons.lang.StringUtils.upperCase(startsWith);
		return valueToTest.startsWith(startsWith);
	}
}
