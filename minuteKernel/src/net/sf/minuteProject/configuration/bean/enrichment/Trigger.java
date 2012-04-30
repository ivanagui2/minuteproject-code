package net.sf.minuteProject.configuration.bean.enrichment;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class Trigger extends AbstractConfiguration  {

	public static final String UPDATE = "UPDATE";
	public static final String INSERT = "INSERT";
	public static final String CURRENT_TIME = "current-time";
	
	public String action, className, value;

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
