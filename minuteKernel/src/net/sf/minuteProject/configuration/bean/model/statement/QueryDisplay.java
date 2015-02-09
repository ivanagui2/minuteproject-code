package net.sf.minuteProject.configuration.bean.model.statement;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class QueryDisplay extends AbstractConfiguration{

	public String resultRowDisplay, resultDetailDisplay, inputDisplay;

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
	
}
