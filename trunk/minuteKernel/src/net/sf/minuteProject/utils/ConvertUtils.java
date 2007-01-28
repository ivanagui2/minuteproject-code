package net.sf.minuteProject.utils;

public class ConvertUtils {
	public static String getJavaTypeFromDBType (String dBType) {
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

}
