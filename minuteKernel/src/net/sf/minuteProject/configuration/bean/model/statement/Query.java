package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.FunctionColumn;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.sql.QueryUtils;

public class Query extends AbstractConfiguration {

	private Queries queries;
	private QueryBody queryBody;
	private QueryWhat queryWhat;
	private QueryWhere queryWhere;
	private QueryParams queryParams;
	private QueryParams outputParams;
	private boolean isSet = false;
	
	public QueryParams getInputParams () {
		return QueryUtils.getInputParams(this);
	}
	
	public QueryParams getOutputParams (){
		if (outputParams==null && !isSet) {
			try {
				outputParams = QueryUtils.getOutputParams(this);
			} catch (MinuteProjectException e) {
				isSet=true;
				//TODO log error
			}
			isSet = true;
		}
		return outputParams;
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
//	
//	public String getTechnicalPackage(Template template) {
//		return getModel().getTechnicalPackage(template);
//	}
	
	private Model getModel() {
		return getQueries().getStatementModel().getModel();
	}
	
	public Table getInputBean () {
		return getEntity(Direction.IN);
	}
	
	public Table getOutputBean () {
		return getEntity(Direction.OUT);
	}

	public Table getEntity(Direction dir) {
		org.apache.ddlutils.model.Table table = new org.apache.ddlutils.model.Table();
		table.setName(getName());
//		table.setCatalog(catalog);
		table.setType(Table.TABLE);
		addColumns(table, dir);
		Table entity = new TableDDLUtils(table);
		entity.setPackage(getPackage());
//		entity.getTechnicalPackage(template)
		entity.setDatabase(getQueries().getStatementModel().getModel().getDataModel().getDatabase());
		return entity;
	}
	
	public net.sf.minuteProject.configuration.bean.Package getPackage() {
		return getModel().getBusinessModel().getBusinessPackage().getPackage();
	}
	
	public String getTechnicalPackage(Template template) {
		net.sf.minuteProject.configuration.bean.Package p = getPackage();
		if (p == null)
			return "ERROR_PACKAGE_IS_NULL";
		return p.getTechnicalPackage(template);
	}

	private void addColumns(org.apache.ddlutils.model.Table table,
			Direction direction) {
		List<QueryParam> list = getColumns(direction);
		for (QueryParam queryParam : list) {
			table.addColumn(getColumn(queryParam));
		}
	}

	private List<QueryParam> getColumns(Direction direction) {
		if (Direction.IN.equals(direction))
			return getInputParams().getQueryParams();
		if (getOutputParams()!=null)
			return getOutputParams().getQueryParams();
		return new ArrayList<QueryParam>();
	}

	private org.apache.ddlutils.model.Column getColumn(QueryParam queryParam) {
		org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();
		column.setName(queryParam.getName());
		column.setType(convertType(queryParam.getType()));
//		column.setScale(queryParam.getScale());
		column.setSize(queryParam.getSize()+"");
//		column.setPrecisionRadix(queryParam.getPrecisionRadix());
		// column.setTypeCode(fc.getTypeCode());
		return column;
	}
	
	private String convertType(String type) {
		return ConvertUtils.getDDLUtilsTypeFromDBType(type);
	}
	
}
