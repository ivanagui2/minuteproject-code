package net.sf.minuteProject.configuration.bean;

import org.apache.commons.lang.builder.EqualsBuilder;

import net.sf.minuteProject.configuration.bean.model.data.Column;


public class Reference {
	private String tableName;
	private String columnName;
	private net.sf.minuteProject.configuration.bean.model.data.Table table;
	private Column column;
	private Column localColumn;
	
	//Constructor
	public Reference(net.sf.minuteProject.configuration.bean.model.data.Table table, Column column, String tableName, String columnName) {
		setTableName(tableName);
		setColumnName(columnName);
		setTable(table);
		setColumn(column);
		//setLocalColumn(localColumn);
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

    public boolean equals(Object obj)
    {
        if (obj instanceof Reference)
        {
            Reference other = (Reference)obj;

            return new EqualsBuilder().append(tableName,   other.getTableName())
                                      .append(columnName, other.getColumnName())
                                      .isEquals();
        }
        else
        {
            return false;
        }
    }

	public Column getLocalColumn() {
		return localColumn;
	}

	public void setLocalColumn(Column localColumn) {
		this.localColumn = localColumn;
	}


}
