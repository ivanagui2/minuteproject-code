package net.sf.minuteProject.configuration.bean.model.data.impl;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteProject.configuration.bean.model.data.BaseColumn;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParamLink;

public abstract class ColumnBaseAbstract extends AbstractConfiguration implements BaseColumn{

	private String typeAlias;
	private boolean isHidden=false, isTransient=false, isSearchable=true, isEditable=true;
	private boolean isContext=false, isImplicit=false, hasBeenDuplicated=false, isArray = false, isOutputParam=false;
	private String filterName, sessionParamName;
	private QueryParamLink queryParamLink;
	
	@Override
	public String getTypeAlias() {
		if (typeAlias==null)
			typeAlias=getType();
		return typeAlias;
	}
	
	public void setTypeAlias(String typeAlias){
		this.typeAlias = typeAlias;
	}
	
	@Override
	public boolean isHidden() {
		return isHidden;
	}
	@Override
	public void setHidden(Boolean isHidden) {
		if (isHidden!=null)
		this.isHidden = isHidden;
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}
	@Override
	public void setEditable(Boolean isEditable) {
		if (isEditable!=null)
			this.isEditable = isEditable;
	}
	@Override
	public boolean isSearchable() {
		return isSearchable;
	}
	@Override
	public void setSearchable(Boolean isSearchable) {
		if (isSearchable!=null)
			this.isSearchable = isSearchable;
	}
	@Override
	public boolean isTransient() {
		return isTransient;
	}
	@Override
	public void setTransient(Boolean isTransient) {
		if (isTransient!=null)
			this.isTransient = isTransient;
	}

	public boolean isContext() {
		return isContext;
	}

	public void setContext(boolean isContext) {
		this.isContext = isContext;
	}

	public boolean isImplicit() {
		return isImplicit || !StringUtils.isEmpty(sessionParamName);
	}

	public void setImplicit(boolean isImplicit) {
		this.isImplicit = isImplicit;
	}

	public boolean hasBeenDuplicated() {
		return hasBeenDuplicated;
	}

	public void setHasBeenDuplicated(boolean hasBeenDuplicated) {
		this.hasBeenDuplicated = hasBeenDuplicated;
	}
	
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public String getFilterName() {
		return filterName;
	}

	public boolean isFilter() {
		return filterName!=null;
	}
	
	public QueryParamLink getQueryParamLink () {
		return queryParamLink;
	}
	
	public void setQueryParamLink (QueryParamLink queryParamLink) {
		this.queryParamLink = queryParamLink;
	}
	
	public void setIsOutputParam (boolean isOutputParam) {
		this.isOutputParam = isOutputParam;
	}
	
	public boolean isOutputParam() {
		return isOutputParam;
	}
	
	
	public boolean isArray () {
		return isArray;
	}
	
	public void setIsArray(boolean isArray) {
		this.isArray = isArray;
	}

	public String getSessionParamName() {
		return sessionParamName;
	}

	public void setSessionParamName(String sessionParamName) {
		this.sessionParamName = sessionParamName;
	}

}
