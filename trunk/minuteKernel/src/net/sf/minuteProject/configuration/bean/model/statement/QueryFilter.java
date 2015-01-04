package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryFilter extends QueryAdapter {

	private QueryParams queryParams;
	private String connectionWord="and";

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
	}

	public String getConnectionWord() {
		return connectionWord;
	}

	public void setConnectionWord(String connectionWord) {
		this.connectionWord = connectionWord;
	}


	
}
