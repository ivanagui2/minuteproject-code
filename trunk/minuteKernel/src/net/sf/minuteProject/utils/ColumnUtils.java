package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

public class ColumnUtils {
	
	public static Column getColumn(Table table, String ColumnName) {
		int maxColumn = table.getColumns().length;
		for (int i = 0; i < maxColumn; i++) {
			Column column = table.getColumns()[i];
			if (column.getName().equals(ColumnName))
				return column;
		}
		return null;		
	}
}
