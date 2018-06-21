package net.sf.minuteProject.utils.sql;

import net.sf.minuteProject.configuration.bean.model.statement.QueryPagination;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;

public class PaginationUtils {
	
	public static String getPaginationPrefix(QueryPagination queryPagination) {
		return getPaginationPrefix(queryPagination, "MYSQL");
	}
	
	public static String getPaginationSuffix(QueryPagination queryPagination) {
		return getPaginationSuffix(queryPagination, "MYSQL");
	}
	
	public static String getPaginationPrefix(QueryPagination queryPagination, String databaseType) {
		//FOR MYSQL
		return "";
	}
	
	public static String getPaginationSuffix(QueryPagination queryPagination, String databaseType) {
		StringBuilder sb = new StringBuilder();
		if (queryPagination.getMaxResult()>0) {
			sb.append(" "+queryPagination.getMaxResultParam()+" ?");
		}
		if (queryPagination.getOffset()>0) {
			sb.append(" "+queryPagination.getOffsetParam()+" ?");
		}
		return sb.toString();
	}

	public static void addQueryParams(QueryPagination queryPagination, QueryParams queryParams) {
		
		if (queryPagination.getMaxResult()>0) {
			QueryParam qf = new QueryParam();
			qf.setName(queryPagination.getMaxResultParam());
			qf.setType("INTEGER");
			queryParams.getQueryParams().add(qf);
		}
		if (queryPagination.getOffset()>0) {
			QueryParam qf = new QueryParam();
			qf.setName(queryPagination.getOffsetParam());
			qf.setType("INTEGER");
			queryParams.getQueryParams().add(qf);
		}
	}
}
