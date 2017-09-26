package net.sf.minuteProject.configuration.bean.enrichment.convention;

import static net.sf.minuteProject.utils.TableUtils.liveBusinessDataContentType;
import static net.sf.minuteProject.utils.TableUtils.masterDataContentType;
import static net.sf.minuteProject.utils.TableUtils.pseudoStaticDataContentType;
import static net.sf.minuteProject.utils.TableUtils.referenceDataContentType;

import java.util.stream.Collectors;

import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class SddContentTypeConvention extends SddConvention {

	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH = "apply-content-type-to-entity-starting-with";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH   = "apply-content-type-to-entity-ending-with";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE  = "apply-content-type-to-entity-belonging-to-package";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE  = "apply-content-type-to-entity-not-belonging-to-package";
	
	private String pattern, contentType;
	
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

	private void apply(Query<?> query) {
		if (isMatch (query))
			query.setContentType(getContentType());
	}

	private boolean isMatch(Query<?> query) {
		if (APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH.equals(type))
			return isBeginningMatch(query);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH.equals(type))
			return isEndingMatch(query);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type))
			return isBelongsToPackage(query);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE.equals(type))
			return doesNotBelongToPackage(query);
		return false;
	}

	private boolean isBeginningMatch(Query<?> query) {
		String tableNameLc = query.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.startsWith(s))
				return true;
		}
		return false;
	}
	
	private boolean isEndingMatch(Query<?> query) {
		String tableNameLc = query.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.endsWith(s))
				return true;
		}
		return false;
	}
	
	private boolean isBelongsToPackage(Query<?> query) {
		String packageNameLowerCase = query.getPackage().getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (packageNameLowerCase.endsWith(s))
				return true;
		}
		return false;
	}
	
	private boolean doesNotBelongToPackage(Query<?> query) {
		return !isBelongsToPackage(query);
	}

	private boolean isValid () {
		if ((APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH.equals(type) 
			|| APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH.equals(type) 
			|| APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type)
			|| APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE.equals(type)
			) 
			&& getPattern()!=null 
			&& (   pseudoStaticDataContentType.equals(getContentType())
			    || masterDataContentType.equals(getContentType())
			    || liveBusinessDataContentType.equals(getContentType())
			    || referenceDataContentType.equals(getContentType())
			    )
			)
			return true;
		return false;
	}
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
