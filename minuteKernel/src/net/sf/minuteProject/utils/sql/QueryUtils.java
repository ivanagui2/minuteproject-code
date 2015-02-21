package net.sf.minuteProject.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.minuteProject.application.ModelGenerator;
import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.statement.Composite;
import net.sf.minuteProject.configuration.bean.model.statement.CompositeQueryElement;
import net.sf.minuteProject.configuration.bean.model.statement.Queries;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryChunk;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;
import net.sf.minuteProject.configuration.bean.model.statement.QueryFilter;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConnectionUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteproject.model.db.type.FieldType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class QueryUtils {

	private static final String QUESTION_MARK = "?";
	private static final String ELLIPSIS = "...";
	private static final String QUESTION_IN_MARK = QUESTION_MARK+ELLIPSIS;
	private static Logger logger = Logger.getLogger(QueryUtils.class);

	public static QueryParams getOutputParams(Query query) throws MinuteProjectException {
		DataModel dataModel = query.getQueries().getStatementModel().getModel()
				.getDataModel();
		Connection connection = ConnectionUtils.getConnection(dataModel);
		if (connection != null) {
			String q = getFullQuerySample(query);
			try {
				return getOutputParams(connection, q, dataModel.getDatabase());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new MinuteProjectException("Query Not working "+query,"QUERY_NOT_WORKING");
			}
		}
		return null;
	}

	private static QueryParams getOutputParams(Connection connection, String query,
			Database database) throws SQLException {
		PreparedStatement prest = connection.prepareStatement(query);
		try {
			ResultSet rs = prest.executeQuery();
			return getQueryParams(rs.getMetaData());
		} catch (SQLException e) {
			logger.error("error executing query : "+query);
			logger.error("error sql : "+e.getMessage());
			return new QueryParams();
		}
	}

	private static QueryParams getQueryParams(ResultSetMetaData metaData) throws SQLException {
		QueryParams queryParams = new QueryParams();
		queryParams.setQueryParams(getQueryParamsList(metaData));
		return queryParams;
	}

	private static List<QueryParam> getQueryParamsList(ResultSetMetaData metaData) throws SQLException {
		List<QueryParam> list = new ArrayList<QueryParam>();
		int size = metaData.getColumnCount();
		for (int i = 1; i < size+1; i++) {
			list.add(getQueryParam(metaData, i));
		}
		return list;
	}

	private static QueryParam getQueryParam(ResultSetMetaData metaData, int i) throws SQLException {
		QueryParam qp = new QueryParam();
		qp.setName(metaData.getColumnName(i));
		qp.setSize(metaData.getColumnDisplaySize(i));
		qp.setScale(metaData.getScale(i));
		qp.setType(metaData.getColumnTypeName(i));
		return qp;
	}

	public static String getFullQueryQuestionMark(Query query) {
		return StringUtils.replace(getQueryQuestionMark(query), "\n", " ");
	}
	
	public static String getQueryBodyQuestionMark(Query query) {
		return StringUtils.replace(query.getQueryBody().getValue(), "\n", " ");
	}
	
	public static String getQueryQuestionMark(Query query) {
			String queryRaw = query.getQueryBody().getValue();
		//1 get query body
		//sb.append(queryRaw);
		//for each query where reference
		
		// as a simplification it is considered that there is just one 'where' possible where the filter
		// are set: so when the first filter with connection word 'where' set the other are 'and'
		boolean isWhereDone = false;
		for (QueryFilter filter : query.getQueryFilters()) {
			//  get query where string with question mark
			//  append (where or and) to query
			final String name = "$"+filter.getName();
			final String replacement = queryAndWhere(filter, isWhereDone)+filter.getValue();
			if (!StringUtils.isEmpty(name) && queryRaw.contains(name)) {
				queryRaw = StringUtils.replace(queryRaw, name, replacement);
			} else {
				queryRaw = queryRaw + replacement;
			}
			isWhereDone = true;
		}
		for (QueryChunk chunk : query.getQueryChunks()) {
			//  get query where string with question mark
			//  append (where or and) to query
			final String name = "$"+chunk.getName();
			final String replacement = getChunkReplacement (chunk);
			if (!StringUtils.isEmpty(name) && queryRaw.contains(name)) {
				queryRaw = StringUtils.replace(queryRaw, name, replacement);
			}
			
		}
		return queryRaw;
	}
	
	private static String getChunkReplacement(QueryChunk chunk) {
		return chunk.getSampleValue();
	}

	private static String queryAndWhere(QueryFilter queryWhere, boolean isWhereDone) {
		return " "+((isWhereDone)?QueryFilter.AndWhere.AND.toString():queryWhere.getConnectWord().toString())+" ";
	}

	public static String getFullQuerySample(Query query) {
		String querySt = getQueryQuestionMark(query);
		List<String> samples = getSamples (query);
		int samplesSize = samples.size();
		int queryArgSize = StringUtils.countMatches(querySt, QUESTION_MARK);
		assert (samplesSize==queryArgSize);
		for (int i = 0; i < samplesSize; i++) {
			querySt = replaceFirstArgWith(querySt, samples.get(i));
		}
		return querySt;
	}

	private static String replaceFirstArgWith(String text, String value) {
		return isIn(text)?StringUtils.replace(text, QUESTION_IN_MARK, value, 4):StringUtils.replace(text, QUESTION_MARK, value, 1);
	}

	private static boolean isIn(String text) {
		int i = text.indexOf(QUESTION_MARK);
		if (i==-1)
			return false;
		int j = text.indexOf(QUESTION_IN_MARK);
		if (j==-1)
			return false;
		return i==j;
	}

	private static List<String> getSamples(Query query) {
		List<String> list = new ArrayList<String>();
		if (query.getQueryParams() != null) {
			addFilters(list, query.getQueryParams());
		}
		//TODO append query where
		for (QueryFilter filter : query.getQueryFilters()) {
			if (filter.getQueryParams() != null) {
				addFilters(list, filter.getQueryParams());
			}
		}
		return list;
	}

	private static void addFilters(List<String> list, QueryParams params) {
		for (QueryParam qp : params.getFlatQueryParams()) {
			list.add(getParamSample(qp));
		}
	}

	private static String getParamSample(QueryParam queryParam) {
		String type = queryParam.getType();
		String sample = queryParam.getSample();
		if (StringUtils.isEmpty(type))
			return sample;
		type = type.toUpperCase();
		if (type.equals(FieldType.CHAR))
			return "'"+sample+"'";
		if (type.equals(FieldType.DATE))
			return "'"+sample+"'";
		return sample;
	}

	public static QueryParams getInputParams(Query query) {
		return query.getQueryParams();
	}

	public static List<Column> getInputCompositeFull(Composite composite) {
		List<Column> list = new ArrayList<Column>();
		for (CompositeQueryElement q : composite.getInputComposite().getQueries()) {
			Query query = q.getQuery();
			for (Column column:query.getInputBean().getColumns()) {
				list.add(column);
			}
		}
		return list;
	
	}
	
	public static Map<String,List<Column>> getInputCompositeDistinct(Composite composite) {
		Map<String,List<Column>> map = new HashMap<String, List<Column>>();
		for (CompositeQueryElement q : composite.getInputComposite().getQueries()) {
			Query query = q.getQuery();
			for (Column column:query.getInputBean().getColumns()) {
				//TODO check that the type of the column is the same, not only the name
				List<Column> list = map.get(column.getName());
				if (list==null)
					list = new ArrayList<Column>();
				list.add(column);
				map.put(column.getName(), list);
			}
		}
		return map;
	}
	
	public static boolean isChartLayout(Query query) {
		return "dashboard".equals(query.getType());
	}
	
	public static boolean isPieChart(Query query) {
		return "pie-chart".equals(query.getCategory());
	}
	
	public static boolean isBarChart(Query query) {
		return "bar-chart".equals(query.getCategory());
	}
	
	public static boolean isChart(Query query) {
		return isPieChart(query) || isBarChart(query);
	}
	
	
	
	public static Column getOutputBeanDimension(Query query, int i) {
		if (query.getOutputBean().getColumnCount()>=i+1)
			return query.getOutputBean().getColumn(i);
		return null;
	}
	
	public static List<Column> getOutputBeanDimension(Query query) {
		List<Column> dimensions = new ArrayList<Column>();
		int cpt=0;
		for(Column column : query.getOutputBean().getColumns()) {
			if (cpt>0) {
				dimensions.add(column);
			}
			cpt++;
		}
		return dimensions;
	}

	public static Column getOutputBeanValue(Query query) {
		if (query.getOutputBean().getColumns().length==0)
			return null;
		return query.getOutputBean().getColumn(query.getOutputBean().getColumnCount()-1);
	}
	
	public static String getColumnVariable(Query query, int i) {
		Table table = query.getOutputBean();
		if (table.getColumns().length>i){
			return FormatUtils.getJavaNameVariable(table.getColumn(i).getName());
		}
		if (table.getColumns().length>0){
			return FormatUtils.getJavaNameVariable(table.getColumn(i-1).getName());
		}
		return "no column index "+i+"of query input bean!";
	}
	public static String getColumnVariable(Query query, String name) {
		Table table = query.getOutputBean();
		return FormatUtils.getJavaNameVariable(ColumnUtils.getColumn(table, name).getName());
	}
	
	public static String getColumnTextVariable(Query query, String columnName) {
		if (query==null)
			return "QUERY is null!";
		return getColumnVariable(query, 1);
	}
	public static String getColumnTextVariable(Query query) {
		if (query==null)
			return "QUERY is null!";
		return getColumnVariable(query, 1);
	}
	
	public static String getColumnValueVariable(Query query) {
		if (query==null)
			return "QUERY is null!";
		return getColumnVariable(query, 0);
	}
	
	public static String getContentType(Query query) {
		return query.getContentType();
	}
	
	public static boolean isAdminContentType(Query query) {
		return query.getContentType()!=null 
				&& (TableUtils.masterDataContentType.equals(query.getContentType())
					|| TableUtils.referenceDataContentType.equals(query.getContentType()));
	}
	
	public static Query getQueryByIdOrName(Model model, String queryId) {
		return model.getStatementModel().getQueryByIdOrName(queryId);
	}
	
	public static Query getQueryByIdOrName(Application application, String queryId) {
		for (Model model : application.getModels()) {
			Query query = model.getStatementModel().getQueryByIdOrName(queryId);
			if (query!=null)
				return query;
		}
		return null;
	}
}
