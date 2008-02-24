package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
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
	
	public static boolean isNaturalPk(Column column) {
		if (column.isPrimaryKey() && 
		   !(column.getType().equals("INT") || 
			column.getType().equals("BIGINT") || 
			column.getType().equals("INTEGER")))
			return true;
		return false;
	}
	
	public static boolean isPkUserProvided (Column column) {
		return isNaturalPk(column);
	}
	
	public static boolean isForeignKey (Column column) {
		//TODO for not to operate it each time put it in the Column implementation abstract class
		Table table = column.getTable();
		Reference[] reference = table.getParents();
		for (int i = 0; i < reference.length; i++) {
			if (reference[i].getLocalColumnName().equals(column.getName()))
				return true;
		}
		return false;
	}
	
}
