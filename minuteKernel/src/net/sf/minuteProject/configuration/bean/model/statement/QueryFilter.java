package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryFilter extends QueryAdapter {

	private QueryParams queryParams;
	public enum AndWhere { AND, WHERE };
	private AndWhere connectWord = AndWhere.AND;

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
		this.queryParams.setFilterName(getName());
		for (QueryParam queryParam : getQueryParams().getQueryParams()) {
			queryParam.setMandatory(false);
		}
	}

	public AndWhere getConnectWord() {
		return connectWord;
	}

	public void setConnectionWord(String connectionWord) {
		if (connectionWord.toUpperCase().equals("AND")) {
			connectWord = AndWhere.AND;
		} else {
			connectWord = AndWhere.WHERE;
		}
	}

	public boolean isWhere() {
		return connectWord.equals(AndWhere.WHERE);
	}

	public void setConnectWord(AndWhere andWhere) {
		this.connectWord = andWhere;
	}

	
}
