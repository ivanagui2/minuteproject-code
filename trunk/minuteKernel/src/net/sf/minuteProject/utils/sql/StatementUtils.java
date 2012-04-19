package net.sf.minuteProject.utils.sql;

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
		String type = queryParam.getType();
		if (type.equals(FieldType.INTEGER))
			return "Int";
		if (type.equals(FieldType.CHAR))
			return "String";
		return "String";
	}
	
	public static final String getJdbcGetter(QueryParam queryParam) {
		return "get"+getJdbcType(queryParam);
	}
	
	public static final String getJdbcSetter(QueryParam queryParam) {
		return "get"+getJdbcType(queryParam);
	}
}
