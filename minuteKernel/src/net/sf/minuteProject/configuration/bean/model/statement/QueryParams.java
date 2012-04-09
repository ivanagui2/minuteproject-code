package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

public class QueryParams {

	private List<QueryParam> queryParams;

	public List<QueryParam> getQueryParams() {
		if (queryParams==null) 
			queryParams=new ArrayList<QueryParam>();
		return queryParams;
	}

	public void setQueryParams(List<QueryParam> queryParams) {
		this.queryParams = queryParams;
	}
	
	public void addQueryParam (QueryParam queryParam) {
		getQueryParams().add(queryParam);
	}
}
