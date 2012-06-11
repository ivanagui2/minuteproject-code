package net.sf.minuteProject.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ConnectionUtils;
import net.sf.minuteproject.model.db.type.FieldType;

import org.apache.commons.lang.StringUtils;

public class QueryUtils {

	private static final String QUESTION_MARK = "?";

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
		ResultSet rs = prest.executeQuery();
		return getQueryParams(rs.getMetaData());
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
	
	public static String getQueryQuestionMark(Query query) {
		return query.getQueryBody().getValue();
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
		return StringUtils.replace(text, QUESTION_MARK, value, 1);
	}

	private static List<String> getSamples(Query query) {
		List<String> list = new ArrayList<String>();
		if (query.getQueryParams() != null) {
			for (QueryParam qp : query.getQueryParams().getQueryParams()) {
				list.add(getParamSample(qp));
			}
		}
		return list;
	}

	private static String getParamSample(QueryParam queryParam) {
		String type = queryParam.getType();
		String sample = queryParam.getSample();
		if (StringUtils.isEmpty(type))
			return sample;
		if (type.equals(FieldType.CHAR))
			return "'"+sample+"'";
		if (type.equals(FieldType.DATE))
			return "'"+sample+"'";
		return sample;
	}

	public static QueryParams getInputParams(Query query) {
		return query.getQueryParams();
	}

}
