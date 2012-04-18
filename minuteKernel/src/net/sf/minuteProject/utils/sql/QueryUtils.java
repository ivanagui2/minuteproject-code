package net.sf.minuteProject.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ConnectionUtils;

public class QueryUtils {

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

	private static QueryParams getQueryParams(ResultSetMetaData metaData) {
		return null;
	}

	private static String getFullQuerySample(Query query) {
		StringBuffer sb = new StringBuffer();
		sb.append(query.getQueryBody());
		if (query.getQueryParams() != null) {
			for (QueryParam qp : query.getQueryParams().getQueryParams()) {
				sb.append(getParamSample(qp));
			}
		}
		return sb.toString();
	}

	private static Object getParamSample(QueryParam queryParam) {
		StringBuffer sb = new StringBuffer();
		//TODO
		return sb;
	}

	public static QueryParams getInputParams(Query query) {
		return null;
	}

}
