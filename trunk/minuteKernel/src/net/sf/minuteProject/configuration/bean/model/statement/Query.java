package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.sql.QueryUtils;

public class Query extends AbstractConfiguration {
	
	private Queries queries;
	private QueryBody queryBody;
	private QueryWhat queryWhat;
	private QueryWhere queryWhere;
	private QueryParams queryParams;
	private QueryParams outputParams;
	private boolean isSet = false;
	private Package pack;
	private String type, category;
	private Table tableIn, tableOut;
	private List<Action> actions;
	private String packageName;
	
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
		if (queryParams==null) queryParams=new QueryParams();
		return queryParams;
	}
	
	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
		this.queryParams.setQuery(this);
	}
	
	public Table getInputBean () {
		if (tableIn==null)
			tableIn = getEntityFromDirection(Direction.IN);
		return tableIn;
	}
	
	public Table getOutputBean () {
		if (tableOut==null)
			tableOut = getEntityFromDirection(Direction.OUT);
		return tableOut;
	}
	// remove duplication
	public Table getEntityFromDirection(Direction dir) {
		Table entity = getEntityRoot(dir);
		if (dir.equals(Direction.IN))
			complementFields(entity,queryParams);
		return entity;
	}
	
	// remove duplication
	public Table getEntity(Direction dir) {
//		Table entity = getEntityRoot(dir);
//		if (dir.equals(Direction.IN))
//			complementFields(entity,queryParams);
//		return entity;$
		if (Direction.IN.equals(dir))
			return getInputBean();
		return getOutputBean();
	}
	
	private void complementFields(Table table, QueryParams queryParams) {
		List<QueryParam> list = getColumns(Direction.IN);
		for (QueryParam queryParam : list) {
			Column column = ColumnUtils.getColumn(table, queryParam.getName());
			if (column!=null) {
				column.setStereotype(queryParam.getStereotype());
//				if (queryParam.isId()) {
//					table.setPrimaryKeys(new Column[] {column});
//				}

			}
		}
		for (Column column : table.getColumns()) {
			if (ColumnUtils.hasTransientColumnName(column)) {
				column.setHidden(true);
				column.setEditable(false);
				column.setRequired(false);
				column.setTransient(true);
			}
		}
	}

	public Table getEntityRoot(Direction dir) {
		org.apache.ddlutils.model.Table table = new org.apache.ddlutils.model.Table();
		Database database = getQueries().getStatementModel().getModel().getDataModel().getDatabase();
		setTableName(table, dir);
//		table.setName(getName());
//		table.setCatalog(catalog);
		table.setType(Table.TABLE);
		addColumns(table, dir);
		
		Table entity = new TableDDLUtils(table);
		initFieldAndRelationship(dir, database, table);	
		entity.setPackage(getPackage());
//		entity.getTechnicalPackage(template)
		entity.setDatabase(database);
		complementColumn(entity, dir);
		return entity;
	}

	private void initFieldAndRelationship(Direction dir, Database database,
			org.apache.ddlutils.model.Table table) {
		if (dir.equals(Direction.IN)) {
			List<QueryParam> list = getColumns(Direction.IN);
			for (QueryParam queryParam : list) {
				Entity.assignForeignKey (database, table, queryParam.getLinkField());
			}
		}
	}

	private void setTableName(org.apache.ddlutils.model.Table table,
			Direction dir) {
		String queryparamName = getQueryParams().getName();
		if (dir.equals(Direction.IN) && !StringUtils.isEmpty(queryparamName))
			table.setName(formatTableName(queryparamName));
		else
			table.setName(formatTableName(getName()));
	}
	

	private String formatTableName(String name) {
		return FormatUtils.getUppercaseUnderscore(name);
	}

	public Package getPackage() {
		return pack;
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

	public String getTechnicalPackage(Template template) {
		net.sf.minuteProject.configuration.bean.Package p = getPackage();
		if (p == null)
			return "ERROR_PACKAGE_IS_NULL";
			
		//return CommonUtils.getSDDPackageName(this);
		return p.getTechnicalPackage(template)+"."+CommonUtils.getSDDPackageName(this);
	}
/*	
	public String getTechnicalPackage3(Template template) {
		return CommonUtils.getSDDPackageName(this);
	}
*/
	private void addColumns(org.apache.ddlutils.model.Table table,
			Direction direction) {
		List<QueryParam> list = getColumns(direction);
		for (QueryParam queryParam : list) {
			if (!queryParam.isLink()) {
				table.addColumn(getColumn(queryParam));
			} else {
				table.addColumn(getColumnTransient(queryParam));
			}
		}
	}

	private void complementColumn(Table table, Direction direction) {
		List<QueryParam> list = getColumns(direction);
		for (QueryParam queryParam : list) {
			for (Column column : table.getColumns()) {
				if (column.getName().equals(queryParam.getName())) {
					column.setProperties(queryParam.getProperties());
					column.setStereotype(queryParam.getStereotype());
				}
			}
		}
		
	}

	private List<QueryParam> getColumns(Direction direction) {
		if (Direction.IN.equals(direction))
			return getInputParams().getFlatQueryParams();
		if (getOutputParams()!=null)
			return getOutputParams().getQueryParams();
		return new ArrayList<QueryParam>();
	}
	
	private org.apache.ddlutils.model.Column getColumn(QueryParam queryParam, String name) {
		org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();
		column.setName(name);
		String type = queryParam.getType();
		column.setType(convertType(type));
		column.setSize(queryParam.getSizeOrDefault());
		column.setScale(queryParam.getScale());
		column.setDefaultValue(queryParam.getDefaultValue());
		if (ConvertUtils.DB_DECIMAL_TYPE.equals(type) && queryParam.getScale()>0) {
			column.setType(ConvertUtils.DB_DOUBLE_TYPE);
		}
//		column.setPrecisionRadix(queryParam.getPrecisionRadix());
		// column.setTypeCode(fc.getTypeCode());
		column.setPrimaryKey(queryParam.isId()); //cannot be set here
		column.setRequired(queryParam.isMandatory());
		return column;
	}

	private org.apache.ddlutils.model.Column getColumn(QueryParam queryParam) {
		return getColumn(queryParam, queryParam.getName());
	}
	
	private org.apache.ddlutils.model.Column getColumnTransient(QueryParam queryParam) {
		return getColumn(queryParam, ColumnUtils.getTransientName(getTransientRoot(queryParam)));
	}
	
	private String getTransientRoot(QueryParam queryParam) {
		if (queryParam.getQueryParamLink()!=null && queryParam.getQueryParamLink().getFieldName()!=null)
			return queryParam.getQueryParamLink().getFieldName();
		return queryParam.getName();
	}

	private String convertType(String type) {
		return ConvertUtils.getDDLUtilsTypeFromDBType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
		
	public List<Action> getActions() {
		if (actions==null) actions = new ArrayList<Action>();
		return actions;
	}
	
	public void addAction (Action action) {
		getActions().add(action);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
