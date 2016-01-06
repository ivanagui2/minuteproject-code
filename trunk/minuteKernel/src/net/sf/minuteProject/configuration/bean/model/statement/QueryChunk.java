package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class QueryChunk extends AbstractConfiguration{
	
	private String sampleValue;
	private List<QueryChunkValue> queryChunkValues;
	private QueryChunkValue defaultValue;
	
	public String getSampleValue() {
		return sampleValue;
	}

	public void setSampleValue(String sampleValue) {
		this.sampleValue = sampleValue;
	}

	public List<QueryChunkValue> getQueryChunkValues() {
		if (queryChunkValues == null)
			queryChunkValues = new ArrayList<QueryChunkValue>();
		return queryChunkValues;
	}

	public void addQueryChunkValue(QueryChunkValue queryChunkValue) {
		queryChunkValue.setQueryChunk(this);
		if (queryChunkValue.isDefault())
			defaultValue = queryChunkValue;
		getQueryChunkValues().add(queryChunkValue);
	}

	public QueryChunkValue getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(QueryChunkValue defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean hasDefault() {
		return defaultValue != null;
	}
}
