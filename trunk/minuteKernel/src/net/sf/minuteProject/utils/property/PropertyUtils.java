package net.sf.minuteProject.utils.property;

import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.code.RestrictedCodeUtils;

public class PropertyUtils {

	public static String getConstraintPropertyValue (Property property) {
		return RestrictedCodeUtils.convertToValidJavaWithUpperCase(property);
	}
	
	public static String getPropertyValue (Property property) {
		return (property.getValue()!=null)?property.getValue():property.getName();
	}
}
