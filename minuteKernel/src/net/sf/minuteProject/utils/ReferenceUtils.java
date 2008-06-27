package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

public class ReferenceUtils {

	public Reference getReference(Table table, Column column) {
		Reference reference = new Reference();
		reference.setTableName(table.getName());
		reference.setColumnName(column.getName());
		reference.setTable(table);
		reference.setColumn(column);
		return reference;
	}
	
	public Reference getReference(Table table, Column column, String tableName, String columnName) {
		Reference reference = new Reference();
		reference.setTableName(tableName);
		reference.setColumnName(columnName);
		reference.setTable(table);
		reference.setColumn(column);
		return reference;
	}	
	
	public static String getParentLink (String foreignTableName, String localColumnName) {
		return FormatUtils.getJavaName(foreignTableName)+"_"+FormatUtils.getJavaName(localColumnName);
	}
	
	public static String getParentLinkUML (String foreignTableName, String localColumnName) {
		return FormatUtils.getJavaName(foreignTableName)+"_"+FormatUtils.getJavaName(localColumnName);
	}	
}
