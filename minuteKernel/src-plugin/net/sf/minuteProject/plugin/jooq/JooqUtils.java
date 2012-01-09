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
		return "rg.jooq.impl.SQLDataType."+column.getType();
	}
	
	public static String getRandomSerialNumber () {
		return "123456789";//tochange
	}
}
