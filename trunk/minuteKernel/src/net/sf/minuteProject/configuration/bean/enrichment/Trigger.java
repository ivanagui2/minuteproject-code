package net.sf.minuteProject.configuration.bean.enrichment;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class Trigger extends AbstractConfiguration  {

	public String action, className;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
