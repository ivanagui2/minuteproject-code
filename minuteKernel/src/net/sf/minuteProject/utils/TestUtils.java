package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Table;

import org.apache.commons.lang.StringUtils;

public class TestUtils {
	
	public String getTestPopulateFieldMethod (String dBType, String length) {
		String retStr=null;
		dBType = StringUtils.upperCase(dBType);
		if (dBType.equals("BOOLEAN"))
			return  "getBoolean ()";					
		if (dBType.equals("BIGINT"))
			return  "getLong ()";	
		if (dBType.equals("LONG"))
			return  "getLong ()";	
		if (dBType.equals("DOUBLE"))
			return  "getDouble ()";			
		if (dBType.equals("INT"))
			return  "getInteger()";		
		if (dBType.equals("TIME"))
			return  "getTimestamp ()";			
		if (dBType.equals("DECIMAL"))
			return  "getDecimal()";
		if (dBType.equals("SMALLINT"))
			return  "getString ("+length+")";	
		if (dBType.equals("VARCHAR"))
			return  "getString ("+length+")";	
		if (dBType.equals("LONGVARCHAR"))
			return  "getString ("+length+")";	
		if (dBType.equals("VARCHAR2"))
			return  "getString ("+length+")";		
		if (dBType.equals("VARGRAPHIC"))
			return  "getString ("+length+")";			
		if (dBType.equals("CHAR"))
			return  "getString ("+length+")";		
		if (dBType.equals("INTEGER"))
			return  "getLong()";	
		if (dBType.equals("NUMERIC"))
			return  "getInteger()";		
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
		return retStr;			
	}
	
	public String getTestLookUpMethod (String dBType, String length) {
		return getTestPopulateFieldMethod(dBType, length);
	}
	
	public String getTestLookUpMethod (String dBType) {
		return getTestPopulateFieldMethod(dBType, "10");
	}
	
	public static String importIbatisTestClass(Model model, Table table, Template template, String targetTemplateName) {
		return CommonUtils.getEntityLevelTemplateFullPath(model, table, template, targetTemplateName);
	}

}
