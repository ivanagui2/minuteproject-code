package net.sf.minuteProject.configuration.bean.model.statement;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.TableUtils;

public class QueryParam extends Field {

	private QueryParams queryParams;
//	private boolean isMandatory =true; TODO set in constructor
	private boolean isImplicit  =false;
	private boolean isContext   =false;
	private boolean hasBeenDuplicated   =false;
	private String sample;
	private String refname;
	private int size, scale;
	private String converter;
	private String likeMode;
	private QueryParamLink queryParamLink;
	private boolean isFilter=false, isArray=false;
//	private Stereotype stereotype;
	
	public String getSizeOrDefault() {
		return String.valueOf(getSize());
	}
//	public boolean isMandatory() {
//		return isMandatory;
//	}
//	public void setIsMandatory(boolean isMandatory) {
//		this.isMandatory = isMandatory;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getSample() {
		return sample;
	}
	public void setSample(String sample) {
		this.sample = sample;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getConverter() {
		return converter;
	}
	public void setConverter(String converter) {
		this.converter = converter;
	}
	public QueryParamLink getQueryParamLink() {
		return queryParamLink;
	}
	public void setQueryParamLink(QueryParamLink queryParamLink) {
		this.queryParamLink = queryParamLink;
		this.queryParamLink.setQueryParam(this);
	}

	public Field getLinkField() {
		if (queryParamLink!=null && queryParamLink.getEntityName()!=null) {
			setLinkToTargetEntity(queryParamLink.getEntityName());
			Column column = ColumnUtils.getColumn(TableUtils.getTable(getQueryParams().getQuery().getQueries().getStatementModel().getModel().getDataModel().getDatabase(), queryParamLink.getEntityName()), queryParamLink.getFieldName());
			if (column!=null) {
				setType(column.getType());
				setDefaultValue(column.getDefaultValue());
				setSize(column.getSizeAsInt());
				Column primaryFirstColumn = TableUtils.getPrimaryFirstColumn(column.getTable());
				if (primaryFirstColumn!=null) {
					String pk = primaryFirstColumn.getName();
					setLinkToTargetField(pk);	
				}
			}
			
		}
		return this;
	}
	
	public boolean isLink() {
		return (queryParamLink!=null)?true:false;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
	}
	public String getLikeMode() {
		return likeMode;
	}
	public void setLikeMode(String likeMode) {
		this.likeMode = likeMode;
	}
	public boolean isImplicit() {
		return isImplicit;
	}
	public void setIsImplicit(boolean isImplicit) {
		this.isImplicit = isImplicit;
	}
	public boolean isContext() {
		return isContext;
	}
	public void setIsContext(boolean isContext) {
		this.isContext = isContext;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRefname() {
		return refname;
	}
	public void setRefname(String refname) {
		this.refname = refname;
	}
	public boolean hasBeenDuplicated() {
		return hasBeenDuplicated;
	}
	public void setHasBeenDuplicated(boolean hasBeenDuplicated) {
		this.hasBeenDuplicated = hasBeenDuplicated;
	}
	public boolean isFilter() {
		return queryParams.isFilter();
	}
	public String getFilterName() {
		return null;
	}
	
	public boolean isArray () {
		return isArray;
	}
	
	public void setIsArray(boolean isArray) {
		this.isArray = isArray;
	}
}
