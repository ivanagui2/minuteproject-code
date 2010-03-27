package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Table;

import org.apache.commons.lang.StringUtils;

public class TestUtils {
	
	public String getTestPopulateFieldMethod (String dBType, String length, int number) {
		String retStr=null;
		dBType = StringUtils.upperCase(dBType);
		if (dBType.equals("BOOLEAN"))
			return  "getBoolean"+number+"()";					
		if (dBType.equals("BIGINT"))
			return  "getLong"+number+"()";	
		if (dBType.equals("LONG"))
			return  "getLong"+number+"()";	
		if (dBType.equals("DOUBLE"))
			return  "getDouble"+number+"()";			
		if (dBType.equals("INT"))
			return  "getInteger"+number+"()";		
		if (dBType.equals("TIME"))
			return  "getTimestamp ()";			
		if (dBType.equals("DECIMAL"))
			return  "getBigDecimal"+number+"()";
		if (dBType.equals("SMALLINT"))
			return  "getString"+number+"("+length+")";	
		if (dBType.equals("VARCHAR"))
			return  "getString"+number+"("+length+")";	
		if (dBType.equals("LONGVARCHAR"))
			return  "getString"+number+"("+length+")";	
		if (dBType.equals("VARCHAR2"))
			return  "getString"+number+"("+length+")";		
		if (dBType.equals("VARGRAPHIC"))
			return  "getString"+number+"("+length+")";			
		if (dBType.equals("CHAR"))
			return  "getString"+number+"("+length+")";		
		if (dBType.equals("INTEGER"))
			return  "getInteger"+number+"()";	
		if (dBType.equals("NUMERIC"))
			return  "getInteger"+number+"()";		
		if (dBType.equals("DATE"))
			return  "getDate()";
		if (dBType.equals("TIMESTAMP"))
			return  "getTimestamp()";	
		if (dBType.equals("BLOB"))
			return  "getBlob("+length+")";	
		if (dBType.equals("BINARY"))
			return  "getBlob"+length+")";	
		if (dBType.equals("CLOB"))
			return  "getClob"+length+")";	
		if (dBType.equals("NVARCHAR2"))
			return  "getString"+number+"("+length+")";	
		if (dBType.equals("OTHER"))
			return  "getString"+number+"("+length+")";
		return retStr;			
	}
	
	public String getTestLookUpMethod (String dBType, String length, int number) {
		return getTestPopulateFieldMethod(dBType, length, number);
	}
	
	public String getTestLookUpMethod (String dBType, int number) {
		return getTestPopulateFieldMethod(dBType, "10", number);
	}

}
