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
		   !(
			column.getType().equals("INT") || 
			column.getType().equals("BIGINT") || 
			column.getType().equals("INTEGER") ||
			column.getType().equals("NUMBER") ||
			column.getType().equals("DECIMAL")
			)
			)
			return true;
		return false;
	}
	
	public static boolean isPkUserProvided (Column column) {
		return isNaturalPk(column);
	}
	
	public static boolean isForeignKey (Column column) {
		//TODO for not to operate it each time put it in the Column implementation abstract class
		Table table = column.getTable();
		return isForeignKey(column, table);
//		Reference[] reference = table.getParents();
//		for (int i = 0; i < reference.length; i++) {
//			if (reference[i].getLocalColumnName().equals(column.getName()))
//				return true;
//		}
//		return false;
	}

	public static boolean isForeignKey (Column column, Table table) {
		//TODO for not to operate it each time put it in the Column implementation abstract class
		Reference[] reference = table.getParents();
		for (int i = 0; i < reference.length; i++) {
			if (reference[i].getLocalColumnName().equals(column.getName()))
				return true;
		}
		return false;
	}
	
	public static boolean isLengthPrecisionColumn(Column column) {
		if (
			column.getType().equals("CHAR") ||
			column.getType().equals("CHAR2") ||
			column.getType().equals("VARCHAR") ||
			column.getType().equals("VARCHAR2") ||
			column.getType().equals("VARGRAPHIC") ||
			column.getType().equals("VARGRAPHIC2") ||
			column.getType().equals("CLOB")
			)
			return true;
		return false;
	}
	
	public static String getMethodInputParameters (Column columns[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (i!=0)
				sb.append(", ");
			sb.append(CommonUtils.getFullType2(columns[i]));
			sb.append(" ");
			sb.append(CommonUtils.getJavaVariableName(columns[i].getName()));
		}
		return sb.toString();
	}
}
