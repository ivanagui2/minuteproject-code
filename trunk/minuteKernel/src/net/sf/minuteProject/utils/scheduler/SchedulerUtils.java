package net.sf.minuteProject.utils.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryChunk;
import net.sf.minuteProject.configuration.bean.model.statement.QueryModel;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryScheduler;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class SchedulerUtils {

	public static boolean hasSchedulerEntry(Model model) {
		return model.getStatementModel()
			.getQueries()
			.getQueries()
			.stream()
			.filter(u -> u.getQuerySchedulers().size()>0)
			.findFirst()
			.isPresent()
			;
	}
	
	public static List<Query> getQueryWithSchedulers(Model model) {
		List<Query> list = new ArrayList<>();
		model.getStatementModel()
				.getQueries()
				.getQueries()
				.stream()
				.filter(u -> u.getQuerySchedulers().size()>0)
				.forEach(u -> list.add(u))
				;
		return list;
	}
	
	public static boolean hasSchedulerEntry(Application application) {
		return application
				.getModels()
				.stream()
				.filter(u -> hasSchedulerEntry(u))
				.findFirst()
				.isPresent()
				;
	}
	
	public static boolean isToGenerateBasedOnSchedulerEntry (Template template, GeneratorBean bean) {
		if (bean instanceof Model) {
			return hasSchedulerEntry((Model)bean);
		} 
		if (bean instanceof Application) {
			return hasSchedulerEntry((Application)bean);
		} 
		return false;
	}
	
	public static List<QueryScheduler> getSchedulerEntries2(Model model) {
		List<QueryScheduler> list = new ArrayList<>();
		model.getStatementModel()
			.getQueries()
			.getQueries()
			.stream()
			.filter(u -> u.getQuerySchedulers().size()>0)
			.forEach(u -> {
				list.addAll(u.getQuerySchedulers());
			});
			;
		return list;
	}
	
	public static List<QueryScheduler> getSchedulerEntries(Model model) {
		//List<QueryScheduler> list = new ArrayList<>();
		return (List<QueryScheduler>) model.getStatementModel()
			.getQueries()
			.getQueries()
			.stream()
			.flatMap(c -> c.getQuerySchedulers().stream())
			.collect(Collectors.toList())
			;
	}
	
	public static List<String> getSchedulerDistinctActionQueryIds (Model model) {
		List<Action> actions = new ArrayList<>();
		List<QueryScheduler> qs = getSchedulerEntries(model);
		qs.stream()
			.forEach(u -> actions.addAll(u.getActions()));
		return actions.stream()
				.map (a -> a.getQueryId())
				.collect(Collectors.toList())
				.stream()
				.distinct()
				.collect(Collectors.toList());
	}
	
	public static String getParamValue (QueryScheduler queryScheduler, String columnName) {
		if (Arrays.asList(queryScheduler.getQuery().getInputBean().getColumns())
			.stream()
			.filter(u -> u.getName().equalsIgnoreCase(columnName))
			.findFirst()
			.isPresent()
			) {
			return getSchedulerParamValue(queryScheduler, columnName);
		}
		//QueryUtils.getColumnTextVariable(query, columnName)
		return "";
	}
	
	public static String getChunkValue (QueryScheduler queryScheduler, String columnName) {
		List<QueryChunk> queryChunks = queryScheduler.getQuery().getQueryChunks();
		if (queryChunks
				.stream()
				.filter(u -> u.getName().equalsIgnoreCase(columnName))
				.findFirst()
				.isPresent()
				) {
			return getSchedulerParamValue(queryScheduler, columnName);
		}
		//QueryUtils.getColumnTextVariable(query, columnName)
		return "\"config-error-"+columnName+" not found\"";
	}

	private static String getSchedulerParamValue(QueryScheduler queryScheduler, String columnName) {
	
		if (queryScheduler.getQueryParams()==null)
			return "null";
		Optional<QueryParam> qpOpt = queryScheduler.getQueryParams().getQueryParams()
		.stream()
		.filter(u -> u.getName().equalsIgnoreCase(columnName))
		.findFirst()
		;
		if (qpOpt.isPresent()) {
			//todo if column.type boolean or integer or long (do not add quote)
			return "\""+qpOpt.get().getValue()+"\"";
		} else {
			return "null";
		}
			
	}
	
	public static List<String> getCrons(QueryScheduler queryScheduler) {
		return ParserUtils.getListFromCommaSeparated(queryScheduler.getCron());
	}
	
	public static List<String> getReportTos(QueryScheduler queryScheduler) {
		return ParserUtils.getList(queryScheduler.getReportTo());
	}
}
