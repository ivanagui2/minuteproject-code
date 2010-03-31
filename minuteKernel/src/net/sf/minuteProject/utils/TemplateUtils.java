package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Template;

public class TemplateUtils {

	public boolean hasTag(String tagValue, Template template) {
		return template.getPropertyByTag(tagValue)!=null;		
	}
}
