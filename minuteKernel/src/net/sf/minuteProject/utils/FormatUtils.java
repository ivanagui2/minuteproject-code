package net.sf.minuteProject.utils;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {
	
	public static String getDirFromPackage (String packageSt) {
		return (packageSt!=null)? StringUtils.replace(packageSt,".","/"):"";
	}
	
	public static String getDirToPackage (String packageSt) {
		return StringUtils.replace(packageSt,"/",".");
	}
	
	public static String getJavaName(String name) {
		if (name==null || name.equals(""))
			return "JAVA_NAME_RETURNS_NULL";
		//String javaName = getJavaNameViaCharStrip(name, "-");
		String underscoreName = StringUtils.replace(name, "-", "_");
		return getJavaNameViaCharStrip(underscoreName, "_");
//		StringTokenizer st = new StringTokenizer (name,"_");
//		StringBuffer sb = new StringBuffer();
//		String firstToken = st.nextToken();
//		if (firstToken.length()==1) {
//			sb.append(firstToken.toUpperCase());
//			while (st.hasMoreTokens()){
//				//sb.append(st.nextToken().toUpperCase());
//				sb.append(firstUpperCaseOnly(st.nextToken()));
//			}			
//		} else {
//			sb.append(firstUpperCaseOnly(firstToken));
//			while (st.hasMoreTokens()){
//				String token = firstUpperCaseOnly(st.nextToken());
//				sb.append(token);
//			}
//		}
//		return sb.toString();
		//return name.substring(0,1).toUpperCase() +name.substring(1,name.length()).toLowerCase();
	}	
	
	private static String getJavaNameViaCharStrip (String name, String charToStrip) {
		StringTokenizer st = new StringTokenizer (name,charToStrip);
		StringBuffer sb = new StringBuffer();
		//String firstToken = st.nextToken();
//		if (firstToken.length()==1) {
//			sb.append(firstToken.toUpperCase());
//			while (st.hasMoreTokens()){
//				//sb.append(st.nextToken().toUpperCase());
//				sb.append(firstUpperCaseOnly(st.nextToken()));
//			}			
//		} else {
//			sb.append(firstUpperCaseOnly(firstToken));
//			while (st.hasMoreTokens()){
//				String token = firstUpperCaseOnly(st.nextToken());
//				sb.append(token);
//			}
//		}
		
		//sb.append(firstToken.toUpperCase());
		while (st.hasMoreTokens()){
			//sb.append(st.nextToken().toUpperCase());
			sb.append(firstUpperCaseOnly(st.nextToken()));
		}		
		return sb.toString();		
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
	
	
	public static String firstUpperCaseOnly (String st) {
		if (st==null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0,1).toUpperCase() +st.substring(1,st.length()).toLowerCase();
	}
	
	private static String firstLowerCaseOnly (String st) {
		if (st==null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0,1).toLowerCase() +st.substring(1,st.length()).toLowerCase();
	}	
	
	public static String firstLowerCase (String st) {
		if (st==null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0,1).toLowerCase() +st.substring(1,st.length());
	}	
	
	public static String firstUpperCase (String st) {
		if (st==null)
			return "INPUT_STRING_IS_NULL";		
		return st.substring(0,1).toUpperCase() +st.substring(1,st.length());
	}	
	
	public static String remove_ID_patternFromColumnName(String input) {
		input = StringUtils.removeStart(input, "ID_");
		input = StringUtils.removeEnd(input, "_ID");
		return input;
	}
	
	public static String upperCase (String st) {
		return StringUtils.upperCase(st);
	}
	
	private static boolean isStandardBean(String name){
		if (name.indexOf("_")==1)
			return false;
		return true;
	}
	
	public static String getShortNameFromVerbose(String name) {
		name = getEachWordFirstLetterUpper(name, " ");
		name = getEachWordFirstLetterUpper(name, "-");
		return name;
	}
	
	private static String getEachWordFirstLetterUpper (String name, String token) {
		StringTokenizer st = new StringTokenizer(name, token);
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			sb.append(firstUpperCase(s));
		}
		return sb.toString();
	}
	
	
}
