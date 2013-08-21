package net.sf.minuteProject.configuration.bean.model.statement;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Field;

public class QueryParamLink extends AbstractConfiguration{
	
	private String entityName, fieldName;
	private QueryParam queryParam;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	public QueryParam getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(QueryParam queryParam) {
		this.queryParam = queryParam;
	}

}
