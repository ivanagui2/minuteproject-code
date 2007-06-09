package net.sf.minuteProject.utils;

import org.apache.commons.lang.StringUtils;

public class ConvertUtils {
	
	public static String getJavaTypeFromDBFullType (String dBType) {
		String retStr=null;
		if (dBType.equals("BIGINT"))
			return  "java.lang.Long";	
		if (dBType.equals("DOUBLE"))
			return  "java.lang.Double";			
		if (dBType.equals("INT"))
			return  "java.lang.Integer";		
		if (dBType.equals("TIME"))
			return  "java.lang.Timestamp";			
		if (dBType.equals("DECIMAL"))
			return  "java.math.BigDecimal";
		if (dBType.equals("SMALLINT"))
			return  "java.lang.String";	
		if (dBType.equals("VARCHAR"))
			return  "java.lang.String";	
		if (dBType.equals("CHAR"))
			return  "java.lang.String";		
		if (dBType.equals("INTEGER"))
			return  "java.lang.Integer";	
		if (dBType.equals("DATE"))
			return  "java.lang.Date";
		if (dBType.equals("TIMESTAMP"))
			return  "java.sql.Timestamp";	
		if (dBType.equals("BLOB"))
			return  "java.sql.Blob";	
		if (dBType.equals("CLOB"))
			return  "java.sql.Clob";	
		return retStr;		
	}	
	
	public static String getJavaTypeFromDBType (String dBType) {
		//return StringUtils.
		// TODO from getJavaTypeFromDBFullType
		String retStr=null;
		if (dBType.equals("BIGINT"))
			return  "Long";	
		if (dBType.equals("DOUBLE"))
			return  "Double";			
		if (dBType.equals("INT"))
			return  "Integer";		
		if (dBType.equals("TIME"))
			return  "Timestamp";			
		if (dBType.equals("DECIMAL"))
			return  "java.math.BigDecimal";
		if (dBType.equals("SMALLINT"))
			return  "String";	
		if (dBType.equals("VARCHAR"))
			return  "String";	
		if (dBType.equals("CHAR"))
			return  "String";		
		if (dBType.equals("INTEGER"))
			return  "Integer";	
		if (dBType.equals("DATE"))
			return  "Date";
		if (dBType.equals("TIMESTAMP"))
			return  "Timestamp";	
		if (dBType.equals("BLOB"))
			return  "Blob";	
		if (dBType.equals("CLOB"))
			return  "Clob";	
		return retStr;		
	}	

	public static String getJavaTypeFromDBType (String dBType, int scale) {
		String retStr=getJavaTypeFromDBType (dBType);		
		if (dBType.equals("DECIMAL")) {
			if (scale==0)
				return "Long";
			else
				return  "java.math.BigDecimal";
		}	
		return retStr;		
	}
	
	public static String getJavaType (String input) {
		input = StringUtils.upperCase(input);
		if (input.endsWith("STRING")){
			return "String";
		}
		return "no type found";
	}

}
