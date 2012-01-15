package net.sf.minuteProject.plugin.jooq;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.TableUtils;

public class JooqUtils {

	public static String getDatabaseJavaPackage (String database) {
		if ("MYSQL".equals(database)) return "org.jooq.util.mysql.MySQLDatabase";
		return "to implement";
	}
	
	public static String getEntitySuperClass (Table table) {
		if (TableUtils.isView(table)) return "to implement";
		return "org.jooq.impl.UpdatableTableImpl";
	}
	
	public static String getJooqFullType(Column column) {
		return "org.jooq.impl.SQLDataType."+column.getType();
	}
	
	public static String getRandomSerialNumber () {
		return "123456789";//tochange
	}
	
	public static boolean isTableNameAndAnyColumnNameAmbiguous(Table table) {
		String proposedName = table.getAlias();
		for (Column column : table.getColumns()) {
			if (proposedName.equals(column.getAlias()))
					return true;
		}
		return false;
	}
	
	public static String getTableConstant(Table table) {
		return isTableNameAndAnyColumnNameAmbiguous(table)?"ENTITY_"+table.getAlias():table.getAlias();
	}
	public static String getTableColumnConstant(Table table, String name) {
		return isTableNameAndAnyColumnNameAmbiguous(table)?"FIELD_"+name:name;
	}
}
