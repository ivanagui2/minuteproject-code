package net.sf.minuteProject.configuration.bean;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;

public class Reference {
	public String tableName;
	public String columnName;
	public Table table;
	

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
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}


}
