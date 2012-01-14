package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Condition;

public class StringUtils {

	public static boolean startsWithIgnoreCase(String valueToTest, String startsWith) {
		if (valueToTest==null) return false;
		if (startsWith==null) return false;
		valueToTest = org.apache.commons.lang.StringUtils.upperCase(valueToTest);
		startsWith = org.apache.commons.lang.StringUtils.upperCase(startsWith);
		return valueToTest.startsWith(startsWith);
	}
	
	public static boolean endsWithIgnoreCase(String valueToTest, String endsWith) {
		if (valueToTest==null) return false;
		if (endsWith==null) return false;
		valueToTest = org.apache.commons.lang.StringUtils.upperCase(valueToTest);
		endsWith = org.apache.commons.lang.StringUtils.upperCase(endsWith);
		return valueToTest.endsWith(endsWith);
	}

	public static boolean isEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isEmpty(str);
	}

	public static boolean checkExpression(String valueToTest, String expression, String pattern) {
		if (Condition.STARTS_WITH.equals(expression))
			return startsWithIgnoreCase(valueToTest, pattern);
		if (Condition.ENDS_WITH.equals(expression))
			return endsWithIgnoreCase(valueToTest, pattern);
		return false;
	}

	public static boolean regex(String valueToTest, String regex) {
		// TODO Auto-generated method stub
		return false;
	}

}
