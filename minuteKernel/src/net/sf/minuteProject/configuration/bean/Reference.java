package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.model.data.Column;


public class Reference {
	public String tableName;
	public String columnName;
	public net.sf.minuteProject.configuration.bean.model.data.Table table;
	public Column column;
	
	//Constructor
	public Reference(net.sf.minuteProject.configuration.bean.model.data.Table table, Column column, String tableName, String columnName) {
		setTableName(tableName);
		setColumnName(columnName);
		setTable(table);
		setColumn(column);
	}

	public Reference(){}
	
	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public net.sf.minuteProject.configuration.bean.model.data.Table getTable() {
		return table;
	}
	public void setTable(net.sf.minuteProject.configuration.bean.model.data.Table table) {
		this.table = table;
	}


}
