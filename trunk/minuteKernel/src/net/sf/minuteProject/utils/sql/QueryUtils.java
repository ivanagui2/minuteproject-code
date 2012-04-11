package net.sf.minuteProject.utils.sql;

import java.sql.Connection;
import java.util.ArrayList;

import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;
import net.sf.minuteProject.utils.ConnectionUtils;

public class QueryUtils {

	public static QueryParams getOutputParams(Query query) {
		DataModel dataModel = query.getQueries().getStatementModel().getModel().getDataModel();
		Connection connection = ConnectionUtils.getConnection(dataModel);
		if (connection!=null) {
			String q = getFullQuerySample(query);
			return getOutputParams (connection, q, dataModel.getDatabase());
		}
		return null;
	}
	
	private static QueryParams getOutputParams(Connection connection, String q,
			Database database) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getFullQuerySample(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	public static QueryParams getInputParams(Query query) {
		
	}

}
