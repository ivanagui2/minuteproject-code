package net.sf.minuteProject.configuration.bean.enrichment.convention;

import static net.sf.minuteProject.utils.TableUtils.liveBusinessDataContentType;
import static net.sf.minuteProject.utils.TableUtils.masterDataContentType;
import static net.sf.minuteProject.utils.TableUtils.pseudoStaticDataContentType;
import static net.sf.minuteProject.utils.TableUtils.referenceDataContentType;

import java.util.stream.Collectors;

import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class SddCacheConvention extends SddContentTypeConvention {

	public static final String APPLY_CACHE_TO_ENTITY_STARTING_WITH 				= "apply-cache-to-entity-starting-with";
	public static final String APPLY_CACHE_TO_ENTITY_ENDING_WITH   				= "apply-cache-to-entity-ending-with";
	public static final String APPLY_CACHE_TO_ENTITY_BELONGING_TO_PACKAGE  		= "apply-cache-to-entity-belonging-to-package";
	public static final String APPLY_CACHE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE  	= "apply-cache-to-entity-not-belonging-to-package";
	public static final String APPLY_CACHE_TO_CONTENT_TYPE  					= "apply-cache-to-content-type";
	
	protected void apply(Query<?> query) {
		if (isMatch (query)) {
			query.setCache(true);
		}
	}

	protected boolean isMatch(Query<?> query) {
		if (APPLY_CACHE_TO_ENTITY_STARTING_WITH.equals(type))
			return isBeginningMatch(query);
		else if (APPLY_CACHE_TO_ENTITY_ENDING_WITH.equals(type))
			return isEndingMatch(query);
		else if (APPLY_CACHE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type))
			return isBelongsToPackage(query);
		else if (APPLY_CACHE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE.equals(type))
			return doesNotBelongToPackage(query);
		else if (APPLY_CACHE_TO_CONTENT_TYPE.equals(type))
			return isOfContentType(query);
		return false;
	}

	protected boolean isOfContentType(Query<?> query) {
		if (query.getContentType()==null)
			return false;
		return query.getContentType().equals(getPattern());
	}
	
	protected boolean isValid () {
		if ((APPLY_CACHE_TO_ENTITY_ENDING_WITH.equals(type) 
			|| APPLY_CACHE_TO_ENTITY_STARTING_WITH.equals(type) 
			|| APPLY_CACHE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type)
			|| APPLY_CACHE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE.equals(type)
			|| APPLY_CACHE_TO_CONTENT_TYPE.equals(type)
			) 
			&& getPattern()!=null
			)
			return true;
		return false;
	}

}
