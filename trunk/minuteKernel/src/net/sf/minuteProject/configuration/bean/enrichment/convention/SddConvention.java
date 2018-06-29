package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.stream.Collectors;

import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.parser.ParserUtils;

public abstract class SddConvention extends Convention<StatementModel>{
	
	@Override
	public void apply(StatementModel model) {
		if (isValid()) {
			model.getSddPackage().getQueryPackages()
			.stream()
			.flatMap(t -> t.getListOfQueries().stream())
			.collect(Collectors.toList())
			.stream()
			.forEach (t -> apply(t));
		}
	}
	
	protected abstract boolean isValid();
	protected abstract void apply(Query<?> query);
	
	protected boolean isBeginningMatch(Query<?> query, String pattern) {
		String tableNameLc = query.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.startsWith(s))
				return true;
		}
		return false;
	}
	
	protected boolean isEndingMatch(Query<?> query, String pattern) {
		String tableNameLc = query.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.endsWith(s))
				return true;
		}
		return false;
	}
	
	protected boolean isBelongsToPackage(Query<?> query, String pattern) {
		String packageNameLowerCase = query.getPackage().getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (packageNameLowerCase.endsWith(s))
				return true;
		}
		return false;
	}
	
	protected boolean doesNotBelongToPackage(Query<?> query, String pattern) {
		return !isBelongsToPackage(query, pattern);
	}
}
