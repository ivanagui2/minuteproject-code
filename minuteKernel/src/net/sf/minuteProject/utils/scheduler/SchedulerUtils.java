package net.sf.minuteProject.utils.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.statement.QueryScheduler;

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
	
	public static List<QueryScheduler> getSchedulerEntries(Model model) {
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
}
