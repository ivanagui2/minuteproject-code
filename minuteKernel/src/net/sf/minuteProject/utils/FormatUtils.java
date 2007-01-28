package net.sf.minuteProject.utils;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {
	
	public static String getDirFromPackage (String packageSt) {
		return StringUtils.replace(packageSt,".","/");
	}
	
	
	public static String getJavaName(String name) {
		if (name==null)
			return "JAVA_NAME_RETURNS_NULL";
		StringTokenizer st = new StringTokenizer (name,"_");
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()){
			String token = firstUpperCaseOnly(st.nextToken());
			sb.append(token);
		}
		return sb.toString();
		//return name.substring(0,1).toUpperCase() +name.substring(1,name.length()).toLowerCase();
	}	
	public static String getJavaNameVariable(String name) {
		if (name==null)
			return "ERROR_GET_JAVANAMEVARIABLE_WITH_NULL";
		if (name.equals(""))
			return name;
		String javaname = getJavaName(name);
		return firstLowerCase(javaname);
	}	
	
	public static String getJavaNameVariableFirstLetter(String name) {
		return firstLowerCase(name);
	}	
	
	
	private static String firstUpperCaseOnly (String st) {
		return st.substring(0,1).toUpperCase() +st.substring(1,st.length()).toLowerCase();
	}
	private static String firstLowerCaseOnly (String st) {
		return st.substring(0,1).toLowerCase() +st.substring(1,st.length()).toLowerCase();
	}	
	private static String firstLowerCase (String st) {
		return st.substring(0,1).toLowerCase() +st.substring(1,st.length());
	}	
}
