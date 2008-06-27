package net.sf.minuteProject.utils;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {
	
	public static String getDirFromPackage (String packageSt) {
		return StringUtils.replace(packageSt,".","/");
	}
	
	public static String getDirToPackage (String packageSt) {
		return StringUtils.replace(packageSt,"/",".");
	}
	
	public static String getJavaName(String name) {
		if (name==null || name.equals(""))
			return "JAVA_NAME_RETURNS_NULL";
		StringTokenizer st = new StringTokenizer (name,"_");
		StringBuffer sb = new StringBuffer();
		String firstToken = st.nextToken();
		if (firstToken.length()==1) {
			sb.append(firstToken.toUpperCase());
			while (st.hasMoreTokens()){
				//sb.append(st.nextToken().toUpperCase());
				sb.append(firstUpperCaseOnly(st.nextToken()));
			}			
		} else {
			sb.append(firstUpperCaseOnly(firstToken));
			while (st.hasMoreTokens()){
				String token = firstUpperCaseOnly(st.nextToken());
				sb.append(token);
			}
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
		if (isStandardBean(name))
			return firstLowerCase(javaname);
		// trick java part
		// this is due to have correct getter
		// to create a correct getter apply the following rule
		// if second letter is a Capital then the first one should also be a capital
		//
		return firstUpperCase(javaname);
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
	private static String firstUpperCase (String st) {
		return st.substring(0,1).toUpperCase() +st.substring(1,st.length());
	}	
	
	public static String remove_ID_patternFromColumnName(String input) {
		input = StringUtils.removeStart(input, "ID_");
		input = StringUtils.removeEnd(input, "_ID");
		return input;
	}
	
	private static boolean isStandardBean(String name){
		if (name.indexOf("_")==1)
			return false;
		return true;
	}
}
