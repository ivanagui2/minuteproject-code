package net.sf.minuteProject.plugin.liferay;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.TableUtils;

public class LiferayUtils {

	public static String getSbPrimaryKeyType(Column column) {
		Table table = column.getTable();
		if (ColumnUtils.isNaturalPk(column))
			return "";
		//strip auto in autoincrement
		return DatabaseUtils.getPrimaryKeyPolicyPatternEnum(table).toString().toLowerCase();
	}
	public static String getSbColumnType(Column column) {
		if (ColumnUtils.isString(column))
			return "String";
		if (ColumnUtils.isTime(column))
			return "Date";
		if (ColumnUtils.isInteger(column))
			return "Integer";
		if (ColumnUtils.isLong(column))
			return "long";
		//do clob and glob
		return "String";
	}
}
