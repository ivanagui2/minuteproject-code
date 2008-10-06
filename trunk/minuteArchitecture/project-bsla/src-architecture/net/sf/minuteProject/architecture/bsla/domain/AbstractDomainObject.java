package net.sf.minuteProject.architecture.bsla.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AbstractDomainObject implements DomainObject {
    
	/**
	 * Default toString
	 */
	public String toString() {
 	   return ToStringBuilder.reflectionToString(this);
    } 
	
	public String toXML () {
	   return null;
	}
}
