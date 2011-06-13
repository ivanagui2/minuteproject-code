package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.system.Property;

public class TemplateUtils {

	public static boolean hasTag(String tag, Template template) {
		return template.getPropertyByTag(tag)!=null;		
	}
	
	public static boolean hasTagValue(String tag, String value, Template template) {
		Property property = template.getPropertyByTag(tag);
		if (property!=null)
		   return value.equals(property.getValue());
		return false;
	}
}
