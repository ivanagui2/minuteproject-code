package net.sf.minuteProject.configuration.bean.enrichment;

import org.apache.commons.lang.StringUtils;

public class ActionFieldMapEntry {

	private String outputField;
	private String filterField;
	private String toActionField;

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
	
	public boolean isFilterFieldMapping () {
		return !isOutputFieldMapping () && !StringUtils.isEmpty(filterField);
	}
}
