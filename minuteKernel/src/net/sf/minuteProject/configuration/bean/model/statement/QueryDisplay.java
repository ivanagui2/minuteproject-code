package net.sf.minuteProject.configuration.bean.model.statement;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class QueryDisplay extends AbstractConfiguration{

	public String resultRowDisplay, resultDetailDisplay, inputDisplay;
	public String resultRowLinkRootUrl, resultRowLinkField, resultRowLinkRootUrlAppendedField;
	public int resultDetailDisplayColumns=1;

	public String getResultRowDisplay() {
		return resultRowDisplay;
	}

	public void setResultRowDisplay(String resultRowDisplay) {
		this.resultRowDisplay = resultRowDisplay;
	}

	public String getResultDetailDisplay() {
		return resultDetailDisplay;
	}

	public void setResultDetailDisplay(String resultDetailDisplay) {
		this.resultDetailDisplay = resultDetailDisplay;
	}

	public String getInputDisplay() {
		return inputDisplay;
	}

	public void setInputDisplay(String inputDisplay) {
		this.inputDisplay = inputDisplay;
	}

	public String getResultRowLinkRootUrl() {
		return resultRowLinkRootUrl;
	}

	public void setResultRowLinkRootUrl(String resultRowLinkRootUrl) {
		this.resultRowLinkRootUrl = resultRowLinkRootUrl;
	}

	public String getResultRowLinkField() {
		return resultRowLinkField;
	}

	public void setResultRowLinkField(String resultRowLinkField) {
		this.resultRowLinkField = resultRowLinkField;
	}

	public String getResultRowLinkRootUrlAppendedField() {
		return resultRowLinkRootUrlAppendedField;
	}

	public void setResultRowLinkRootUrlAppendedField(
			String resultRowLinkRootUrlAppendedField) {
		this.resultRowLinkRootUrlAppendedField = resultRowLinkRootUrlAppendedField;
	}

	public int getResultDetailDisplayColumns() {
		return resultDetailDisplayColumns;
	}

	public void setResultDetailDisplayColumns(int resultDetailDisplayColumns) {
		this.resultDetailDisplayColumns = resultDetailDisplayColumns;
	}
	
}
