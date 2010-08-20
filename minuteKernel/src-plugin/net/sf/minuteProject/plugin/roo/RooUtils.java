package net.sf.minuteProject.plugin.roo;

import static net.sf.minuteProject.utils.ConvertUtils.isDateType;
import static net.sf.minuteProject.utils.ConvertUtils.isNumberType;
import static net.sf.minuteProject.utils.ConvertUtils.isStringType;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import static net.sf.minuteProject.utils.ColumnUtils.isLengthPrecisionColumn;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.java.JavaUtils;
import net.sf.minuteProject.utils.sql.SqlUtils;
import net.sf.minuteproject.model.db.type.FieldType;

import org.apache.commons.lang.StringUtils;

public class RooUtils {

	public static String getRooConsoleType (Column column){
	   return StringUtils.lowerCase(ConvertUtils.getJavaTypeFromDBType(column));
	}
	
	public static String getRooTypeChunk (Column column){
		return StringUtils.lowerCase(ConvertUtils.getJavaTypeFromDBType(column));
	}
	
	public static RooColumn getRooColumn (Column column) {
		RooColumn rooColumn = new RooColumn();
		String type = column.getType();
//		System.out.println(">>> type = "+type+" for "+column.getName()+" isString? "+isStringType (type));
		if (isStringType (type)) {
		   rooColumn.setRooConsoleType("string");
		   rooColumn.setTypeChunk("");
		} else if (isDateType (type)) {
		   rooColumn.setRooConsoleType("date");
		   rooColumn.setTypeChunk(getRooColumnTypeChunck("java.lang.Date"));
	    } else if (isNumberType (type)) {
			rooColumn.setRooConsoleType("number");
			rooColumn.setTypeChunk(getRooColumnTypeChunck(getNumberType(type)));
	    } else 
	    	System.out.println(">> untreated type "+type);
		// mandatory value
		rooColumn.setNotNullChunk ((column.isRequired())?"--notNull":"");
		rooColumn.setMinSizeChunk("");//TODO with enrichment
		rooColumn.setSizeMaxChunk (isLengthPrecisionColumn(column)?"--sizeMax "+column.getSizeAsInt():"");
		rooColumn.setColumnVariableName (getRooEntityFieldVariable(column));
		return rooColumn;
	}

	private static String getRooColumnTypeChunck (String value) {
		return "--type "+value;
	}
	private static String getNumberType(String dBType) {
		if (FieldType.BIGINT.toString().equals(dBType)) return "java.lang.Long";
		if (FieldType.DECIMAL.toString().equals(dBType)) return "java.lang.Double";
		if (FieldType.INTEGER.toString().equals(dBType)) return "java.lang.Integer";
		return "";
	}
	
	public static String getRooEntityFieldVariable (Column column) {
		return getRooEntityFieldVariable(column.getName());
	}

	private static String getRooEntityFieldVariable(String name) {
		String nameLC = StringUtils.lowerCase(name);
		String s = FormatUtils.getJavaNameVariable(name);
		if (SqlUtils.isReservedWord(nameLC) || JavaUtils.isReservedWord(nameLC))
			return s+"_";
		return s;
	}
	
}
