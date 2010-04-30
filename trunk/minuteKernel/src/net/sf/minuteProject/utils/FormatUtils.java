package net.sf.minuteProject.utils;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import net.sf.minuteProject.utils.format.FormatCache;
import net.sf.minuteProject.utils.format.FormatCacheEntry;
import net.sf.minuteProject.utils.java.JavaUtils;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {

	public static final String CONVERT_TO_JAVA_NAME = "CONVERT_TO_JAVA_NAME";
	public static final String CONVERT_TO_JAVA_VARIABLE_NAME = "CONVERT_TO_JAVA_VARIABLE_NAME";
	public static final String CONVERT_TO_JAVA_VARIABLE_NAME_CONVERTING_RESERVED_WORD = "CONVERT_TO_JAVA_VARIABLE_NAME_CONVERTING_RESERVED_WORD";
	
//	private static String getResultOfInputConversion (String input, String converter) {
//		
//	}
//	
	/**/
	public static String getJavaName(String name) {
		FormatCacheEntry fce = new FormatCacheEntry(name, CONVERT_TO_JAVA_NAME);
		String value = FormatCache.getInstance().getCacheEntry(fce);
		if (value==null) {
			value = performGetJavaName (name);
			FormatCache.getInstance().putCacheEntryValue(fce, value);
		}
		return value;
	}
	
	public static String getJavaNameVariable(String name) {
		FormatCacheEntry fce = new FormatCacheEntry(name, CONVERT_TO_JAVA_VARIABLE_NAME);
		String value = FormatCache.getInstance().getCacheEntry(fce);
		if (value==null) {
			value = performGetJavaNameVariable(name);
			FormatCache.getInstance().putCacheEntryValue(fce, value);
		}
		return value;
	}	
	
	public static String getJavaNameVariableConvertReservedWord(String name) {
		FormatCacheEntry fce = new FormatCacheEntry(name, CONVERT_TO_JAVA_VARIABLE_NAME_CONVERTING_RESERVED_WORD);
		String value = FormatCache.getInstance().getCacheEntry(fce);
		if (value==null) {
			value = performGetJavaNameVariableConvertReservedWord(name);
			FormatCache.getInstance().putCacheEntryValue(fce, value);
		}
		return value;
	}	
	/*
	public static String getJavaNameVariable(String name) {
		return performGetJavaNameVariable(name);
	}
	
	public static String getJavaName(String name) {
		return performGetJavaName(name);
	}
	*/
	public static String getDirFromPackage(String packageSt) {
		return (packageSt != null) ? StringUtils.replace(packageSt, ".", "/"): "";
	}

	public static String getDirToPackage(String packageSt) {
		return StringUtils.replace(packageSt, "/", ".");
	}

	private static String performGetJavaName(String name) {
		if (name == null || name.equals(""))
			return "JAVA_NAME_RETURNS_NULL";
		String underscoreName = StringUtils.replace(name, "-", "_");
		return getJavaNameViaCharStrip(underscoreName, "_");
	}

	private static String getJavaNameViaCharStrip(String name,
			String charToStrip) {
		StringTokenizer st = new StringTokenizer(name, charToStrip);
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			sb.append(firstUpperCaseOnly(st.nextToken()));
		}
		return sb.toString();
	}

	private static String performGetJavaNameVariableConvertReservedWord(String name) {
		String value = performGetJavaNameVariable(name);
		if (JavaUtils.isReservedWord(name))
			value = convertReservedWord (value);
		return value;
	}
	
	private static String performGetJavaNameVariable(String name) {
		if (name == null)
			return "ERROR_GET_JAVANAMEVARIABLE_WITH_NULL";
		if (name.equals(""))
			return name;
		String javaname = getJavaName(name);
		if (isStandardBean(name))
			return firstLowerCase(javaname);
		// trick java part
		// this is due to have correct getter
		// to create a correct getter apply the following rule
		// if second letter is a Capital then the first one should also be a
		// capital
		//
		return firstUpperCase(javaname);
	}

	public static String getJavaNameVariableFirstLetter(String name) {
		return firstLowerCase(name);
	}

	public static String firstUpperCaseOnly(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toUpperCase()
				+ st.substring(1, st.length()).toLowerCase();
	}

	private static String firstLowerCaseOnly(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toLowerCase()
				+ st.substring(1, st.length()).toLowerCase();
	}

	public static String firstLowerCase(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toLowerCase() + st.substring(1, st.length());
	}

	public static String firstUpperCase(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toUpperCase() + st.substring(1, st.length());
	}

	public static String remove_ID_patternFromColumnName(String input) {
		input = StringUtils.removeStart(input, "ID_");
		input = StringUtils.removeEnd(input, "_ID");
		return input;
	}

	public static String formatToSQLSingleString(String input) {
		input = StringUtils.replace(input, " ", "_");
		return upperCase(input);
	}

	public static String upperCase(String st) {
		return StringUtils.upperCase(st);
	}

	private static boolean isStandardBean(String name) {
		if (name.indexOf("_") == 1)
			return false;
		return true;
	}

	public static String getShortNameFromVerbose(String name) {
		name = getEachWordFirstLetterUpper(name, " ");
		name = getEachWordFirstLetterUpper(name, "-");
		return name;
	}

	private static String getEachWordFirstLetterUpper(String name, String token) {
		StringTokenizer st = new StringTokenizer(name, token);
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			sb.append(firstUpperCase(s));
		}
		return sb.toString();
	}

	public static final String convertToValidJava(String input, String regEx,
			String replacement, boolean upperCase) {
		input = input.trim();
		String res = convertToValidJava(input, regEx, replacement);
		if (upperCase)
			res = res.toUpperCase();
		return res;
	}

	public static final String convertToValidJava(String input, String regEx,
			String replacement) {
		String res = input.trim();
		Pattern pattern = Pattern.compile(regEx);
		java.util.regex.Matcher matcher = pattern.matcher(res);
		while (matcher.find()) {
			String val = matcher.group();
			res = res.replace(val, replacement);
		}
		return res;
	}

	private static String convertReservedWord (String word) {
		return "_"+word;
	}
}
