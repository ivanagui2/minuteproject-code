package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteproject.model.db.type.FieldType;

import org.apache.commons.lang.StringUtils;

public class ConvertUtils {
	
	public static final String JAVA_BOOLEAN_TYPE 				=   "java.lang.Boolean";					
	public static final String JAVA_LONG_TYPE 					=   "java.lang.Long";	
	public static final String JAVA_DOUBLE_TYPE 				=   "java.lang.Double";			
	public static final String JAVA_INTEGER_TYPE 				=   "java.lang.Integer";		
	public static final String JAVA_TIMESTAMP_TYPE 				=   "java.lang.Timestamp";			
	public static final String JAVA_BIGDECIMAL_TYPE 			=   "java.math.BigDecimal";
	public static final String JAVA_BIGINTEGER_TYPE 			=   "java.math.BigInteger";
	public static final String JAVA_STRING_TYPE 				=   "java.lang.String";						
	public static final String JAVA_DATE_TYPE 					=   "java.lang.Date";
	public static final String JAVA_BLOB_TYPE 					=   "java.sql.Blob";	
	public static final String JAVA_CLOB_TYPE 					=   "java.sql.Clob";	

	public static final String DB_STRING_CHAR_TYPE 				=   "CHAR";	
	public static final String DB_STRING2_CHAR_TYPE 			=   "CHAR2";	
	public static final String DB_STRING_TYPE 					=   "VARCHAR";	
	public static final String DB_STRING2_TYPE 					=   "VARCHAR2";	
	public static final String DB_INTEGER_TYPE 					=   "INTEGER";	
	public static final String DB_DECIMAL_TYPE 					=   "DECIMAL";
	public static final String DB_TIMESTAMP_TYPE 				=   "TIMESTAMP";
	public static final String DB_DATE_TYPE 					=   "DATE";
	public static final String DB_NUMERIC_TYPE 					=   "NUMERIC";	
	
	public static final String UML_STRING_TYPE 					=   "string";	
	public static final String UML_INTEGER_TYPE 				=   "integer";	
	public static final String UML_LONG_TYPE 					=   "decimal";	
	
	public static String getDBFullTypeFromUMLType (String type) {
		if (type==null) return DB_STRING_TYPE;
		type = StringUtils.lowerCase(type);
		if (UML_STRING_TYPE.equals(type)) return DB_STRING_TYPE;
		if (UML_INTEGER_TYPE.equals(type)) return DB_INTEGER_TYPE;
		if (UML_LONG_TYPE.equals(type)) return DB_DECIMAL_TYPE;
		return DB_STRING_TYPE;
	}
	
	public static String getUMLTypeFromDBFullType (String type) {
		if (type==null) return DB_STRING_TYPE;
		type = StringUtils.lowerCase(type);
		if (DB_STRING_TYPE.equals(type)) return UML_STRING_TYPE;
		if (DB_STRING2_TYPE.equals(type)) return UML_STRING_TYPE;
		if (DB_STRING_CHAR_TYPE.equals(type)) return UML_STRING_TYPE;
		if (DB_STRING2_CHAR_TYPE.equals(type)) return UML_STRING_TYPE;
		if (DB_INTEGER_TYPE.equals(type)) return UML_INTEGER_TYPE;
		if (DB_DECIMAL_TYPE.equals(type)) return UML_LONG_TYPE;
		return UML_STRING_TYPE;
	}
	
	public static String getDDLUtilsTypeFromDBType (String type) {
		return getDBFullTypeFromUMLType(getUMLTypeFromDBFullType(type));
	}	
	
	public static String getJavaTypeFromDBFullType (String dBType) {
		String retStr=null;
		if (dBType.equals("BOOLEAN"))
			return  JAVA_BOOLEAN_TYPE;					
		if (dBType.equals("BIGINT"))
			return  JAVA_LONG_TYPE;	
		if (dBType.equals("DOUBLE"))
			return  JAVA_DOUBLE_TYPE;			
		if (dBType.equals("INT"))
			return  JAVA_INTEGER_TYPE;		
		if (dBType.equals("TIME"))
			return  JAVA_TIMESTAMP_TYPE;			
		if (dBType.equals("DECIMAL"))
			return  JAVA_BIGDECIMAL_TYPE;
		if (dBType.equals("SMALLINT"))
			return  JAVA_STRING_TYPE;	
		if (dBType.equals("VARCHAR"))
			return  JAVA_STRING_TYPE;	
		if (dBType.equals("LONGVARCHAR"))
			return  JAVA_STRING_TYPE;	
		if (dBType.equals("VARCHAR2"))
			return  JAVA_STRING_TYPE;		
		if (dBType.equals("VARGRAPHIC"))
			return  JAVA_STRING_TYPE;			
		if (dBType.equals("CHAR"))
			return  JAVA_STRING_TYPE;		
		if (dBType.equals("INTEGER"))
			return  JAVA_INTEGER_TYPE;	
		if (dBType.equals("NUMERIC"))
			return  JAVA_INTEGER_TYPE;		
		if (dBType.equals("NUMBER"))
			return  JAVA_LONG_TYPE;		
		if (dBType.equals("DATE"))
			return  JAVA_DATE_TYPE;
		if (dBType.equals("TIMESTAMP"))
			return  JAVA_TIMESTAMP_TYPE;	
		if (dBType.equals("BLOB"))
			return  JAVA_BLOB_TYPE;	
		if (dBType.equals("BINARY"))
			return  JAVA_BLOB_TYPE;	
		if (dBType.equals("CLOB"))
			return  JAVA_CLOB_TYPE;	
		if (dBType.equals("NVARCHAR2"))
			return  JAVA_STRING_TYPE;	
		if (dBType.equals("NVARCHAR"))
			return  JAVA_STRING_TYPE;	
		
		// to re implement when externalizing the mapping
		if (dBType.equals("OTHER"))
			return  JAVA_STRING_TYPE;		
		return retStr;		
	}	

	public static String getJavaTypeFromDBFullType (Column column) {
		if (column==null)
			return null;
		return getJavaTypeFromDBFullType(column.getType(), column.getScale());
	}
	
	public static String getJavaDefaultMask (Column column) {
		String type = getJavaTypeFromDBFullType(column);
		if (JAVA_BOOLEAN_TYPE.equals(type)) return "new Boolean(\"false\")";					
		if (JAVA_LONG_TYPE.equals(type)) return "Long.valueOf(-1)";
		if (JAVA_DOUBLE_TYPE.equals(type)) return "Double.valueOf(-1)";	
		if (JAVA_INTEGER_TYPE.equals(type)) return "Integer.valueOf(-1)";	
		if (JAVA_TIMESTAMP_TYPE.equals(type)) return "null"; //not supported yet	
		if (JAVA_BIGDECIMAL_TYPE.equals(type)) return "java.math.BigDecimal.valueOf(-1)";
		if (JAVA_STRING_TYPE.equals(type)) return "new String()";				
		if (JAVA_DATE_TYPE.equals(type)) return "new Date()";
		if (JAVA_BLOB_TYPE.equals(type)) return "null";	
		if (JAVA_CLOB_TYPE.equals(type)) return "null";
		return "null";
	}
	
	public static String getJavaTypeFromDBFullType (String dBType, int scale) {
		String retStr=getJavaTypeFromDBFullType (dBType);		
		if (dBType.equals("DECIMAL")) {
			if (scale==0)
				return JAVA_LONG_TYPE;
			else
				return  JAVA_BIGDECIMAL_TYPE;
		}	
		return retStr;		
	}

	public static String getJavaTypeClassFromDBType (Column column) {
		return getJavaTypeClassFromDBType(column.getType(), column.getScale());
	}
	
	public static String getJavaTypeClassFromDBType (String dBType, int scale) {
		if (dBType.equals("BOOLEAN"))
			return  "Boolean";	
		if (dBType.equals("NUMERIC"))
			return  "Integer";		
		if (dBType.equals("BLOB"))
			return  "String";	
		if (dBType.equals("BINARY"))
			return  "String";	
		if (dBType.equals("CLOB"))
			return  "String";			
		return getJavaTypeFromDBTypeOnly (dBType, scale);
	}
	
	public static String getJavaTypeFromDBType (String dBType) {
		//return StringUtils.
		// TODO from getJavaTypeFromDBFullType
		String retStr=null;
		if (dBType==null) return "String";
		if (dBType.equals("BOOLEAN"))
			return  "java.lang.Boolean";					
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
		if (dBType.equals("LONGVARCHAR"))
			return  "String";	
		if (dBType.equals("VARCHAR2"))
			return  "String";		
		if (dBType.equals("VARGRAPHIC"))
			return  "String";			
		if (dBType.equals("CHAR"))
			return  "String";		
		if (dBType.equals("INTEGER"))
			return  "Integer";	
		if (dBType.equals("NUMBER"))
			return  "java.lang.Long";
		if (dBType.equals("NUMERIC"))
			return  "java.lang.Integer";		
		if (dBType.equals("DATE"))
			return  "Date";
		if (dBType.equals("TIMESTAMP"))
			return  "Timestamp";	
		if (dBType.equals("BLOB"))
			return  "Blob";	
		if (dBType.equals("BINARY"))
			return  "java.sql.Blob";	
		if (dBType.equals("CLOB"))
			return  "Clob";	
		if (dBType.equals("BIT"))
			return  "Long";	
		if (dBType.equals("NVARCHAR2"))
			return  "String";	
		if (dBType.equals("NVARCHAR"))
			return  "String";	
		
		// to re implement when externalizing the mapping
		if (dBType.equals("OTHER"))
			return  "String";
		if (dBType.equals("BIT")) //mysql
			return  "Boolean";		
		return "String";		
	}	

	public static String getJavaTypeFromDBType (Column column) {
		return getJavaTypeFromDBType(column.getType(), column.getScale());
	}

	public static String getJavaTypeFromDBTypeOnly (String dBType, int scale) {
		String retStr=getJavaTypeFromDBType (dBType);	
		if (dBType==null) return retStr;
		if (dBType.equals("DECIMAL") || dBType.equals("NUMERIC") ) {
			if (scale==0)
				return "Long";
			else
				return  "BigDecimal";
		}	
		return retStr;		
	}
	
	public static String getJavaTypeFromDBType (String dBType, int scale) {
		String retStr=getJavaTypeFromDBType (dBType);	
		if (dBType==null) return retStr;
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

	public static boolean isStringType (String dBType) {
		return (getJavaTypeFromDBType(dBType).equals("String"))?true:false;
	}
	
	public static boolean isDateType (String dBType) {
		return (FieldType.DATE.toString().equals(dBType) ||
				FieldType.TIMESTAMP.toString().equals(dBType))?true:false;
	}	

	public static boolean isNumberType (String dBType) {
		return (FieldType.BIGINT.toString().equals(dBType) ||
				FieldType.DECIMAL.toString().equals(dBType) ||
				FieldType.INTEGER.toString().equals(dBType))?true:false;
	}
	
	public static boolean isBooleanType (String dBType) {
		return (FieldType.BOOLEAN.toString().equals(dBType)||
				  FieldType.BIT.toString().equals(dBType))?true:false;
	}
}
