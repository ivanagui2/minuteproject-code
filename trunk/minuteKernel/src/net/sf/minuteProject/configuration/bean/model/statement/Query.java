package net.sf.minuteProject.configuration.bean.model.statement;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.FunctionColumn;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.sql.QueryUtils;

public class Query extends AbstractConfiguration {

	private Queries queries;
	private QueryBody queryBody;
	private QueryWhat queryWhat;
	private QueryWhere queryWhere;
	private QueryParams queryParams;
	
	public QueryParams getInputParams () {
		return QueryUtils.getInputParams(this);
	}
	
	public QueryParams getOutputParams () throws MinuteProjectException {
		return QueryUtils.getOutputParams(this);
	}
	
	public Queries getQueries() {
		return queries;
	}
	public void setQueries(Queries queries) {
		this.queries = queries;
	}
	public QueryBody getQueryBody() {
		return queryBody;
	}
	public void setQueryBody(QueryBody queryBody) {
		this.queryBody = queryBody;
	}
	public void setQueryBody(String s) {
		queryBody = new QueryBody();
		queryBody.setValue(s);
	}
	public QueryWhat getQueryWhat() {
		return queryWhat;
	}
	public void setQueryWhat(QueryWhat queryWhat) {
		this.queryWhat = queryWhat;
	}
	public void setQueryWhat(String s) {
		queryWhat = new QueryWhat();
		queryWhat.setValue(s);
	}
	public QueryWhere getQueryWhere() {
		return queryWhere;
	}
	public void setQueryWhere(QueryWhere queryWhere) {
		this.queryWhere = queryWhere;
	}
	public void setQueryWhere(String s) {
		queryWhere = new QueryWhere();
		queryWhere.setValue(s);
	}
	public QueryParams getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
	}
	
	public String getTechnicalPackage(Template template) {
		return getModel().getTechnicalPackage(template);
	}
	
	private Model getModel() {
		return getQueries().getStatementModel().getModel();
	}
	
}
