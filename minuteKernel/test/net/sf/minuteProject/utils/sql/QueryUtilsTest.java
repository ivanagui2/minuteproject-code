package net.sf.minuteProject.utils.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryBody;
import net.sf.minuteProject.configuration.bean.model.statement.QueryFilter;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;
import junit.framework.TestCase;

public class QueryUtilsTest {

	public static final String query1Jdbc = "SELECT A, B from T where C = ? and D = ?";
	public static final String query1Full = "SELECT A, B from T where C = 1 and D = 'test'";
	public static final String query1SampleParam1 = "1";
	public static final String query1SampleParam2 = "'test'";
	public static final String query1Param1Type = "int";
	public static final String query1Param2Type = "string";
	
	//
	public static final String query1Filter1 = "E = ?";
	public static final String query1Filter1Param1Type = "string";
	public static final String query1Filter1Param1Sample = "'e'";
	public static final String query1Filter1Full = "E = 'e'";
	
	//public static final String query1JdbcFilter1 = query1Jdbc + " "+query1Filter1;
	public static final String query1FullFilter1 = query1Full + " and "+query1Filter1Full;
	
	//
	public static final String query1Filter2 = "(F = ? or G = ?)";
	public static final String query1Filter2Param1Type = "string";
	public static final String query1Filter2Param2Type = "string";
	public static final String query1Filter2Param1Sample = "'f'";
	public static final String query1Filter2Param2Sample = "'g'";
	public static final String query1Filter2Full = "(F = 'f' or G = 'g')";
	
	public static final String query1JdbcFilter2 = query1Jdbc + " "+query1Filter2;
	public static final String query1FullFilter2 = query1Full + " and "+query1Filter2Full;
	
	//
	public static final String query2Jdbc = "SELECT A, B from T where C = 'c' $filter1 order by A desc";
	public static final String query2Full = "SELECT A, B from T where C = 'c'  and H = 'h' order by A desc";
	
	public static final String query2FilterName1 = "filter1";
	public static final String query2FilterValue1 = "H = ?";
	public static final String query2Filter1Param1Type = "string";
	public static final String query2Filter1Param1Sample = "'h'";
	
	Query query1;
	
	@Before
	public void setUp() {
		query1=new Query();
		query1.setQueryBody(getQueryBody(query1Jdbc));
		query1.setQueryParams(getQueryParams(getQueryParam1(), getQueryParam2()));
	}
	
	private QueryParams getQueryParams(QueryParam ... filter) {
		QueryParams queryParams = new QueryParams();
		queryParams.setQueryParams(getQueryParamList(filter));
		return queryParams;
	}

	private List<QueryParam> getQueryParamList(QueryParam ... filter) {
		List<QueryParam> list = new ArrayList<QueryParam>();
		for (QueryParam queryParam : filter) {
			list.add(queryParam);
		}
		return list;
	}

	private QueryParam getQueryParam(String type, String sample) {
		QueryParam queryParam = new QueryParam();
		queryParam.setType(type);
		queryParam.setSample(sample);
		return queryParam;
	}
	
	private QueryParam getQueryParam1() {
		return getQueryParam(query1Param1Type, query1SampleParam1);
	}

	private QueryParam getQueryParam2() {
		return getQueryParam(query1Param2Type, query1SampleParam2);
	}

	private QueryBody getQueryBody(String jdbc) {
		QueryBody qb = new QueryBody();
		qb.setValue(jdbc);
		return qb;
	}

	private Query getQueryWithFilter(String jdbc, String name, String filter,
			String filterparamtype, String filterparamsample) {
		Query query=new Query();
		query.setQueryBody(getQueryBody(jdbc));
		query.addQueryFilter(getQueryFilter(name, filter, filterparamtype, filterparamsample));
		return query;
	}

	private QueryFilter getQueryFilter(String name, String filter,String filterparamtype,
			String filterparamsample) {
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.setName(name);
		queryFilter.setValue(filter);
		queryFilter.setQueryParams(getQueryParams(getQueryParam(filterparamtype,filterparamsample)));
		return queryFilter;
	}
	//
	@Test
	public void simpleFullQuery() {
		QueryUtils queryUtils = new QueryUtils();
		String s = queryUtils.getFullQuerySample(query1);
		assertTrue(s +" but expect :"+query1Full, query1Full.equals(s));
	}
	
	@Test
	public void queryWithImplicitFilter() {
		//when query 2
		QueryUtils queryUtils = new QueryUtils();
		Query query2 = getQueryWithFilter(query1Jdbc, "", query1Filter1, query1Filter1Param1Type, query1Filter1Param1Sample);
		query2.getQueryParams().addQueryParam(getQueryParam1());
		query2.getQueryParams().addQueryParam(getQueryParam2());
		String s = queryUtils.getFullQuerySample(query2);
		assertTrue(s+" but expect :"+query1FullFilter1, query1FullFilter1.equals(s));
	}
	
	@Test
	public void queryWithImplicitFilter2() {
		QueryUtils queryUtils = new QueryUtils();
		Query query = getQueryWithFilter(query1Jdbc, "", query1Filter2, query1Filter2Param1Type, query1Filter2Param1Sample);
		query.getQueryParams().addQueryParam(getQueryParam1());
		query.getQueryParams().addQueryParam(getQueryParam2());
		query.getQueryFilters().get(0).getQueryParams().addQueryParam(getQueryParam(query1Filter2Param2Type, query1Filter2Param2Sample));
		String s = queryUtils.getFullQuerySample(query);
		assertTrue(s+" but expect :"+query1FullFilter2, query1FullFilter2.equals(s));
	}

	
	@Test
	public void query2WithExplicitFilter() {
		//StringUtils.replace(query2Jdbc, query2FilterName1, query2FilterValue1);
		QueryUtils queryUtils = new QueryUtils();
		Query query4 = getQueryWithFilter(query2Jdbc, query2FilterName1, query2FilterValue1, query2Filter1Param1Type, query2Filter1Param1Sample);
		String s = queryUtils.getFullQuerySample(query4);
		assertTrue(s +" but expect :"+query2Full, query2Full.equals(s));
	}
	
	
	@Test
	public void query2WithExplicitWhereFilter() {
		//TODO with where-and word pickup
		//assertTrue(s, query2Full.equals(s));
	}

	
}
