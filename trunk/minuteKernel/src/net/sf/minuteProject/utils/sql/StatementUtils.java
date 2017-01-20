package net.sf.minuteProject.utils.sql;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteproject.model.db.type.FieldType;

public class StatementUtils {

	public static final String getJavaType(QueryParam queryParam) {
		String type = queryParam.getType();
		return ConvertUtils.getJavaTypeFromDBType(type);
	}
	
	public static final String getJavaVariableName(QueryParam queryParam) {
		String name = queryParam.getName();
		return FormatUtils.getJavaNameVariable(name);
	}
	
	private static final String getJdbcType(QueryParam queryParam) {
		return getJdbcType(queryParam.getType());
	}
	
	public static final String getJdbcType(Column column) {
		return getJdbcType(column.getType());
	}
	
	private static final String getJdbcType(String type) {
		if (type!=null) {
			type = type.toUpperCase();
			if (type.equals(FieldType.INTEGER.toString()))
				return "Int";
			if (type.equals(FieldType.DECIMAL.toString()))
				return "Long";
			if (type.equals(FieldType.BIGINT.toString()))
				return "Long";
			if (type.equals(FieldType.DOUBLE.toString()))
				return "BigDecimal";
			if (type.equals(FieldType.CHAR.toString()))
				return "String";
			if (type.equals(FieldType.DATE.toString()))
				return "Date";
			if (type.equals(FieldType.DATETIME.toString()))
				return "Time";
			if (type.equals(FieldType.TIME.toString()))
				return "Time";
			if (type.equals(FieldType.TIMESTAMP.toString()))
				return "Timestamp";
			if (type.equals(FieldType.BOOLEAN.toString()))
				return "Boolean";
		}
		return "String";
	}
	
/*	
	public static final String getCmisType(Column column) {
		return getCmisType(column.getType());
	}
	
	private static final String getCmisType(String type) {
		if (type!=null) {
			if (type.equals(FieldType.BIGINT.toString()))
				return "java.math.BigInteger";
			if (type.equals(FieldType.DATE.toString()))
				return "java.util.GregorianCalendar";
			if (type.equals(FieldType.BOOLEAN.toString()))
				return "java.lang.Boolean";
			else return getJdbcType(type);
		}
		return "String";
	}
*/	
	public static final String getJdbcGetter(QueryParam queryParam) {
		return "get"+getJdbcType(queryParam);
	}
	
	public static final String getJdbcSetter(QueryParam queryParam) {
		return "get"+getJdbcType(queryParam);
	}
	
	public static final boolean hasResult (Table table) {
		return table!=null && table.getColumnCount()>0;
	}
}
