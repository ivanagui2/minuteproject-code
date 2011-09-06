package net.sf.minuteProject.utils;

import java.io.File;
import java.util.List;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
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
	
	public static boolean isUpdatable (Template template, GeneratorBean bean) {
		if (!template.isUpdatable()) return false;
		File file = new File(template.getGeneratorOutputFileNameForConfigurationBean(bean, template));
		return file.exists();
	}
	
	public static List <String> getAddedAreas (File file) {
		// if java get import & inner
	}
}
