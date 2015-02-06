package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
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

import org.apache.commons.lang.StringUtils;

public class Query extends AbstractConfiguration {

	public static final String DUPLICATED_TAG ="<DUPLICATED>";
	private Queries queries;
	private QueryBody queryBody;
	private List<Field> queryFields;
	private List<QueryFilter> queryFilters;
	private QueryParams queryParams;
	private QueryParams outputParams;
	private boolean isSet = false;
	private boolean cache = false;
	private Package pack;
	private String type, category, executeLabel;
	private Table tableIn, tableOut;
	private List<Action> actions;
	private List<Action> inputActions;
	private List<QueryChunk> queryChunks;
	private String packageName;
	private boolean isWrite = false;

	public QueryParams getInputParams() {
		return QueryUtils.getInputParams(this);
	}

	public QueryParams getOutputParams() {
		if (outputParams == null && !isSet) {
			try {
				outputParams = QueryUtils.getOutputParams(this);
			} catch (MinuteProjectException e) {
				isSet = true;
				// TODO log error
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

	public List<QueryChunk> getQueryChunks() {
		if (queryChunks==null) {
			queryChunks = new ArrayList<QueryChunk>();
		}
		return queryChunks;
	}
	
	public void addQueryChunk(QueryChunk queryChunk) {
		getQueryChunks().add(queryChunk);
	}
	
	public List<QueryFilter> getQueryFilters() {
		if (queryFilters==null) {
			queryFilters = new ArrayList<QueryFilter>();
		}
		return queryFilters;
	}

	public void addQueryFilter(QueryFilter queryWhere) {
		getQueryFilters().add(queryWhere);
	}
	
	public List<Field> getQueryFields() {
		if (queryFields==null) {
			queryFields = new ArrayList<Field>();
		}
		return queryFields;
	}
	
	public void addField(Field field) {
		getQueryFields().add(field);
	}

	public QueryParams getQueryParams() {
		if (queryParams == null)
			queryParams = new QueryParams();
		return queryParams;
	}

	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
		this.queryParams.setQuery(this);
	}

	public Table getInputBean() {
		if (tableIn == null)
			tableIn = getEntityFromDirection(Direction.IN);
		return tableIn;
	}

	public Table getOutputBean() {
		if (tableOut == null)
			tableOut = getEntityFromDirection(Direction.OUT);
		return tableOut;
	}

	// remove duplication
	public Table getEntityFromDirection(Direction dir) {
		Table entity = getEntityRoot(dir);
		if (dir.equals(Direction.IN))
			complementFields(entity, queryParams);
		return entity;
	}

	// remove duplication
	public Table getEntity(Direction dir) {
		if (Direction.IN.equals(dir))
			return getInputBean();
		return getOutputBean();
	}

	private void complementFields(Table table, QueryParams queryParams) {
		List<QueryParam> list = getColumns(Direction.IN);
		for (QueryParam queryParam : list) {
			//cannot search on column name in case of duplicated columns
			Column column = ColumnUtils.getColumn(table, queryParam.getName());
			if (column != null) {
				column.setStereotype(queryParam.getStereotype());
				//column.setHasBeenDuplicated(queryParam.hasBeenDuplicated());
				column.setFilterName(queryParam.getQueryParams().getFilterName());
				// if (queryParam.isId()) {
				// table.setPrimaryKeys(new Column[] {column});
				// }
				column.setQueryParamLink(queryParam.getQueryParamLink());

			}
		}
		for (Column column : table.getColumns()) {
			if (ColumnUtils.hasTransientColumnName(column)) {
				column.setHidden(true);
				column.setEditable(false);
				column.setRequired(false);
				column.setTransient(true);
			}
			if (column.getName().contains(DUPLICATED_TAG)) {
				column.setHasBeenDuplicated(true);
				column.setName(column.getName().replace(DUPLICATED_TAG, ""));
			}
		}
	}

	private Table getEntityRoot(Direction dir) {
		org.apache.ddlutils.model.Table table = new org.apache.ddlutils.model.Table();
		Database database = getQueries().getStatementModel().getModel()
				.getDataModel().getDatabase();
		setTableName(table, dir);
		// table.setName(getName());
		// table.setCatalog(catalog);
		table.setType(Table.TABLE);
		addColumns(table, dir);

		Table entity = new TableDDLUtils(table);
		initFieldAndRelationship(dir, database, table);
		entity.setPackage(getPackage());
		// entity.getTechnicalPackage(template)
		entity.setDatabase(database);
		complementColumn(entity, dir);
		return entity;
	}

	private void initFieldAndRelationship(Direction dir, Database database,
			org.apache.ddlutils.model.Table table) {
		if (dir.equals(Direction.IN)) {
			List<QueryParam> list = getColumns(Direction.IN);
			for (QueryParam queryParam : list) {
				Entity.assignForeignKey(database, table,
						queryParam.getLinkField());
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
		return p.getTechnicalPackage(template) + "."
				+ CommonUtils.getSDDPackageName(this);
	}

	private void addColumns(org.apache.ddlutils.model.Table table,
			Direction direction) {
		List<QueryParam> list = getColumns(direction);
		for (QueryParam queryParam : list) {
			if (!queryParam.isLink()) {
				table.addColumn(getColumn(table, queryParam));
			} else {
				///table.addColumn(getColumnTransient(queryParam));
				table.addColumn(getColumn(table, queryParam));
			}
		}
	}

	private void complementColumn(Table table, Direction direction) {
		List<QueryParam> list = getColumns(direction);
		//TODO correct double loop error with duplicated columns
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
		if (Direction.IN.equals(direction)) {
			List<QueryParam> list = new ArrayList<QueryParam>();
			list.addAll(getInputParams().getFlatQueryParams());
			for (QueryFilter filter : getQueryFilters()) {
				list.addAll(filter.getQueryParams().getFlatQueryParams());
			}
			return list;
		}
		if (getOutputParams() != null)
			return getOutputParams().getQueryParams();
		return new ArrayList<QueryParam>();
	}

//	private org.apache.ddlutils.model.Column getColumn(QueryParam queryParam,
//			String name) {
//		org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();
//		column.setName(name);
//		String type = queryParam.getType();
//		if (type==null) type = "string";
//		column.setType(convertType(type));
//		column.setSize(queryParam.getSizeOrDefault());
//		column.setScale(queryParam.getScale());
//		column.setDefaultValue(queryParam.getDefaultValue());
//		if (ConvertUtils.DB_DECIMAL_TYPE.equals(type)
//				&& queryParam.getScale() > 0) {
//			column.setType(ConvertUtils.DB_DOUBLE_TYPE);
//		}
//		// column.setPrecisionRadix(queryParam.getPrecisionRadix());
//		// column.setTypeCode(fc.getTypeCode());
//		column.setPrimaryKey(queryParam.isId()); // cannot be set here
//		column.setRequired(queryParam.isMandatory());
//		return column;
//	}

	private org.apache.ddlutils.model.Column getColumn(org.apache.ddlutils.model.Table table, QueryParam queryParam) {
		org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();
		column.setName(getColumnName(table, queryParam));
		String type = queryParam.getType();
		if (type==null) type = "string";
		column.setType(convertType(type));
		column.setSize(queryParam.getSizeOrDefault());
		column.setScale(queryParam.getScale());
		column.setDefaultValue(queryParam.getDefaultValue());
		if (ConvertUtils.DB_DECIMAL_TYPE.equals(type)
				&& queryParam.getScale() > 0) {
			column.setType(ConvertUtils.DB_DOUBLE_TYPE);
		}
		// column.setPrecisionRadix(queryParam.getPrecisionRadix());
		// column.setTypeCode(fc.getTypeCode());
		column.setPrimaryKey(queryParam.isId()); // cannot be set here
		column.setRequired(queryParam.isMandatory());
		return column;
	}
	
	private String getColumnName(org.apache.ddlutils.model.Table table, QueryParam queryParam) {
		if (!queryParam.hasBeenDuplicated())
			return queryParam.getName();
		else {
			return queryParam.getName()+DUPLICATED_TAG;
		}
	}

//	private org.apache.ddlutils.model.Column getColumnTransient(
//			QueryParam queryParam) {
//		return getColumn(queryParam,
//				ColumnUtils.getTransientName(getTransientRoot(queryParam)));
//	}

//	private String getTransientRoot(QueryParam queryParam) {
//		if (queryParam.getQueryParamLink() != null
//				&& queryParam.getQueryParamLink().getFieldName() != null)
//			return queryParam.getQueryParamLink().getFieldName();
//		return queryParam.getName();
//	}

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
		if (actions == null)
			actions = new ArrayList<Action>();
		return actions;
	}

	public void addAction(Action action) {
		getActions().add(action);
	}
	
	public List<Action> getInputActions() {
		if (inputActions == null)
			inputActions = new ArrayList<Action>();
		return inputActions;
	}
	
	public void addInputAction(Action action) {
		getInputActions().add(action);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public boolean isWrite() {
		return isWrite;
	}

	public void setWrite(boolean isWrite) {
		this.isWrite = isWrite;
	}

	public String getExecuteLabel() {
		return executeLabel;
	}

	public void setExecuteLabel(String executeLabel) {
		this.executeLabel = executeLabel;
	}
	
	@Override
	public String getGeneratedBeanName() {
		return StringUtils.replace (getName(), " ", "_");
	}
	
	@Override
	public String toString() {
		return "query [name='"+name+"', id='"+id+"']";
	}
}
