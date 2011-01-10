package net.sf.minuteProject.plugin.ruby;

import net.sf.minuteProject.configuration.bean.model.data.Column;

public class RubyUtils {

	public static final String RUBY_BOOLEAN_TYPE 				=   "boolean";					
	public static final String RUBY_LONG_TYPE 					=   "long";	
	public static final String RUBY_DOUBLE_TYPE 				=   "double";			
	public static final String RUBY_INTEGER_TYPE 				=   "integer";		
	public static final String RUBY_TIMESTAMP_TYPE 				=   "timestamp";			
	public static final String RUBY_BIGDECIMAL_TYPE 			=   "long";
	public static final String RUBY_STRING_TYPE 				=   "string";						
	public static final String RUBY_DATE_TYPE 					=   "date";
	public static final String RUBY_BLOB_TYPE 					=   "blob";	
	public static final String RUBY_CLOB_TYPE 					=   "clob";
	
	public static String getRubyType (Column column) {
		return RubyUtils.getRubyType(column.getType());
	}

	private static String getRubyType(String dBType) {
		String retStr=null;
		if (dBType.equals("BOOLEAN"))
			return  RUBY_BOOLEAN_TYPE;					
		if (dBType.equals("BIGINT"))
			return  RUBY_LONG_TYPE;	
		if (dBType.equals("DOUBLE"))
			return  RUBY_DOUBLE_TYPE;			
		if (dBType.equals("INT"))
			return  RUBY_INTEGER_TYPE;		
		if (dBType.equals("TIME"))
			return  RUBY_TIMESTAMP_TYPE;			
		if (dBType.equals("DECIMAL"))
			return  RUBY_BIGDECIMAL_TYPE;
		if (dBType.equals("SMALLINT"))
			return  RUBY_STRING_TYPE;	
		if (dBType.equals("VARCHAR"))
			return  RUBY_STRING_TYPE;	
		if (dBType.equals("LONGVARCHAR"))
			return  RUBY_STRING_TYPE;	
		if (dBType.equals("VARCHAR2"))
			return  RUBY_STRING_TYPE;		
		if (dBType.equals("VARGRAPHIC"))
			return  RUBY_STRING_TYPE;			
		if (dBType.equals("CHAR"))
			return  RUBY_STRING_TYPE;		
		if (dBType.equals("INTEGER"))
			return  RUBY_INTEGER_TYPE;	
		if (dBType.equals("NUMERIC"))
			return  RUBY_INTEGER_TYPE;		
		if (dBType.equals("NUMBER"))
			return  RUBY_LONG_TYPE;		
		if (dBType.equals("DATE"))
			return  RUBY_DATE_TYPE;
		if (dBType.equals("TIMESTAMP"))
			return  RUBY_TIMESTAMP_TYPE;	
		if (dBType.equals("BLOB"))
			return  RUBY_BLOB_TYPE;	
		if (dBType.equals("BINARY"))
			return  RUBY_BLOB_TYPE;	
		if (dBType.equals("CLOB"))
			return  RUBY_CLOB_TYPE;	
		if (dBType.equals("NVARCHAR2"))
			return  RUBY_STRING_TYPE;	
		if (dBType.equals("NVARCHAR"))
			return  RUBY_STRING_TYPE;	
		if (dBType.equals("OTHER"))
			return  RUBY_STRING_TYPE;		
		return retStr;		
	}
}
