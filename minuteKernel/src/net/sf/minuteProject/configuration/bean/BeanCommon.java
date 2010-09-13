package net.sf.minuteProject.configuration.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BeanCommon {

	public String toString() {
		ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE); 
	 	return  ToStringBuilder.reflectionToString(this);
	}
	
}
