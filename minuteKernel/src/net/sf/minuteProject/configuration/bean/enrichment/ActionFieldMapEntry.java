package net.sf.minuteProject.configuration.bean.enrichment;

import org.apache.commons.lang.StringUtils;

public class ActionFieldMapEntry {

	private String outputField;
	private String staticValue;
	private String filterField;
	private String columnField;
	private String toActionField;
	private String convertCase;

	public String getOutputField() {
		return outputField;
	}

	public void setOutputField(String outputField) {
		this.outputField = outputField;
	}

	public String getToActionField() {
		return toActionField;
	}

	public void setToActionField(String toActionField) {
		this.toActionField = toActionField;
	}

	public String getFilterField() {
		return filterField;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public boolean isOutputFieldMapping () {
		return !StringUtils.isEmpty(outputField);
	}
	
	public String getConvertCase() {
		return convertCase;
	}

	public void setConvertCase(String convertCase) {
		this.convertCase = convertCase;
	}

	public boolean isFilterFieldMapping () {
		return !isOutputFieldMapping () && !StringUtils.isEmpty(filterField);
	}
	
	public boolean isColumnFieldMapping () {
		return !isFilterFieldMapping () && !StringUtils.isEmpty(columnField);
	}
	
	public boolean isUpperCaseConvert () {
		return "uppercase".equals(convertCase);
	}

	public String getColumnField() {
		return columnField;
	}

	public void setColumnField(String columnField) {
		this.columnField = columnField;
	}

	public String getStaticValue() {
		return staticValue;
	}

	public void setStaticValue(String staticValue) {
		this.staticValue = staticValue;
	}
	
}
