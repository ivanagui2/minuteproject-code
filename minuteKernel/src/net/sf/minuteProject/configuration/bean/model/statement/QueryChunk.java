package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class QueryChunk extends AbstractConfiguration{
	
	private List<QueryChunkValue> queryChunkValues;
	
	public List<QueryChunkValue> getQueryChunkValues() {
		if (queryChunkValues == null)
			queryChunkValues = new ArrayList<QueryChunkValue>();
		return queryChunkValues;
	}

	public void addAction(QueryChunkValue queryChunkValue) {
		getQueryChunkValues().add(queryChunkValue);
	}


}
