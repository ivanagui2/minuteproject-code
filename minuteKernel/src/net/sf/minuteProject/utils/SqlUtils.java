package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

public class SqlUtils {
	
	public static boolean isQuotedColumn (Column column) {
		if (column == null)
			return false;
		if (column.getType().equals("INT") 
			|| column.getType().equals("NUMBER")
			|| column.getType().equals("INTEGER")
			|| column.getType().equals("LONG")
			|| column.getType().equals("DECIMAL")
			|| column.getType().equals("BIGINT"))
			return false;
		return true;
			
	}
	
	/**
	 * isPKQuotedColumn return true is the First pk column is not a sql type of integer.
	 * @param table
	 * @return
	 */
	public static boolean isPKQuotedColumn (Table table) {
		return isQuotedColumn (TableUtils.getPrimaryFirstColumn(table));
	}

}
