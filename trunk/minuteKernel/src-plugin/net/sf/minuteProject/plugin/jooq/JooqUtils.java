package net.sf.minuteProject.plugin.jooq;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.TableUtils;

public class JooqUtils {

	public static final String JOOQ_DECIMAL = "DECIMAL";
	public static final String JOOQ_FLOAT = "FLOAT";
	public static final String JOOQ_BIGINT = "BIGINT";
	public static final String JOOQ_SMALLINT = "SMALLINT";
	public static final String JOOQ_INTEGER = "INTEGER";

	public static String getDatabaseJavaPackage(String database) {
		if ("MYSQL".equals(database))
			return "org.jooq.util.mysql.MySQLDatabase";
		return "to implement";
	}

	public static String getEntitySuperClass(Table table) {
		if (TableUtils.isView(table))
			return "to implement";
		return "org.jooq.impl.UpdatableTableImpl";
	}

	public static String getJooqFullType(Column column) {
		String type = column.getType();
		String javaType = ConvertUtils.getJavaTypeFromDBFullType(column);
		choice: {
			if (ConvertUtils.JAVA_BIGDECIMAL_TYPE.equals(javaType)) {
				type = JOOQ_DECIMAL;
				break choice;
			}
			if (ConvertUtils.JAVA_LONG_TYPE.equals(javaType)) {
				type = JOOQ_BIGINT;
				break choice;
			}
			if (ConvertUtils.JAVA_SHORT_TYPE.equals(javaType)) {
				type = JOOQ_SMALLINT;
				break choice;
			}
			if (ConvertUtils.JAVA_INTEGER_TYPE.equals(javaType)) {
				type = JOOQ_INTEGER;
				break choice;
			}
			if (ConvertUtils.DB_BLOB.equals(type)) {
				type = "BLOB";
				break choice;
			}
			if (ConvertUtils.DB_LONGVARBINARY.equals(type) || ConvertUtils.DB_LONGBLOB.equals(type)) {
				type = ConvertUtils.DB_LONGVARBINARY;
				break choice;
			}
				
		}
		return "org.jooq.impl.SQLDataType." + type;
	}

	public static String getJooqColumnFullType(Column column) {
		String type = column.getType();
		if (ConvertUtils.DB_BLOB.equals(type) ||
			ConvertUtils.DB_LONGVARBINARY.equals(type) || 
			ConvertUtils.DB_LONGBLOB.equals(type))
			return "byte[]";
		return CommonUtils.getFullType2(column);
	}

	public static String getRandomSerialNumber() {
		return "123456789";// tochange
	}

	public static boolean isTableNameAndAnyColumnNameAmbiguous(Table table) {
		String proposedName = table.getAlias();
		for (Column column : table.getColumns()) {
			if (proposedName.toLowerCase().equals(column.getAlias().toLowerCase()))
				return true;
		}
		return false;
	}
	public static boolean isTableNameAndAnyColumnNameAmbiguous(Table table, String name) {
		String proposedName = table.getAlias();
		return (proposedName.toLowerCase().equals(name.toLowerCase()))?true:false;
	}

	public static String getTableConstant(Table table) {
//		return isTableNameAndAnyColumnNameAmbiguous(table) ? "ENTITY_"
//				+ table.getAlias() : table.getAlias();
		return "__"+table.getAlias();
	}

	public static String getTableColumnConstant(Table table, String name) {
//		return isTableNameAndAnyColumnNameAmbiguous(table, name) ? "FIELD_" + name
//				: name;
		return name;
	}
}
