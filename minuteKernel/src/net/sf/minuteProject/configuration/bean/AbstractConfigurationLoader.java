package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.utils.FormatUtils;

public abstract class AbstractConfigurationLoader extends AbstractConfiguration{
	
	// put abstract methods
	
	//
	public String getGeneratedBeanName() {
		return getName();
	}
}
