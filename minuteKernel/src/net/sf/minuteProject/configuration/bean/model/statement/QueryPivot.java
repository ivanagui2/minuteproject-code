package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;

public class QueryPivot extends AbstractConfiguration{
	
	private Query query;
	private String key;
	private String columns;
	private List<Column> columnList;
	private List<QueryPivot> siblings;
	
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
	public void setQuery(Query query) {
		this.query = query;
	}
	public List<Column> getColumnList() {
		if (columnList==null) {
			columnList = ColumnUtils.getColumns(query.getOutputBean(), columns);
		}
		return columnList;
	}
	public List<QueryPivot> getSiblings() {
		
		if (siblings==null) {
			siblings = new ArrayList<QueryPivot>(query.getPivots());
			siblings.remove(this);
		}
		return siblings;
	}

}
