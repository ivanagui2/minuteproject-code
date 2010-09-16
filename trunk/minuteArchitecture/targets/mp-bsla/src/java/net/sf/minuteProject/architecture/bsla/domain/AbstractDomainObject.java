package net.sf.minuteProject.architecture.bsla.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AbstractDomainObject implements DomainObject {
    
	/**
	 * Default toString
	 */
	public String toString(Object object) {
		ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE); //SHORT_PREFIX_STYLE
	 	return  ToStringBuilder.reflectionToString(object);
	} 
	
//	public int hashCode () {
//		return this.hashCode();
//	}
	
	public int toHashCode(Object object) {
	 	return HashCodeBuilder.reflectionHashCode(object);
	}
	
	public boolean toEquals(Object thiz, Object object) {
	 	return EqualsBuilder.reflectionEquals(thiz, object);
	}
	
	private Long count__;

	public long getCount__() {
		return (count__==null)?1:count__;
	}

	public void setCount__(long count) {
		this.count__ = count;
	}
	
}	
