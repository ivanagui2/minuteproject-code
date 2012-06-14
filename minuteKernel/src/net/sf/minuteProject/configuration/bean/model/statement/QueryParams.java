package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class QueryParams extends AbstractConfiguration {

	private List<QueryParam> queryParams;
	private Query query;

	public List<QueryParam> getQueryParams() {
		if (queryParams == null) {
			if (StringUtils.isEmpty(refid)) {
				queryParams = getReferenceQueryParams(refid);
			}
			if (queryParams == null)
				queryParams = new ArrayList<QueryParam>();
		}
		return queryParams;
	}

	private List<QueryParam> getReferenceQueryParams(String refid) {
		if (query != null) {
			Queries queries = query.getQueries();
			if (queries != null)
				for (Query q : queries.getQueries()) {
					if (refid.equals(q.getId()))
						return q.getQueryParams().getQueryParams();
				}
		}
		return null;
	}

	public void setQueryParams(List<QueryParam> queryParams) {
		this.queryParams = queryParams;
	}

	public void addQueryParam(QueryParam queryParam) {
		getQueryParams().add(queryParam);
	}

	public void setQuery(Query query) {
		this.query = query;
	}
}
