package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class QueryPivot extends AbstractConfiguration{
	
	private String key;
	private String columns;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}

}
