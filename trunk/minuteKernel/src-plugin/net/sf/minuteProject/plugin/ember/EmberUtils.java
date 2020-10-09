package net.sf.minuteProject.plugin.ember;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.utils.FormatUtils;

public class EmberUtils {

	public static String getEmberFileName(Template template, GeneratorBean bean) {
		if (bean.getName()!=null) {
			StringBuilder sb = new StringBuilder();
			if (!StringUtils.isEmpty(template.getFilePrefix())) {
				sb.append(template.getFilePrefix()+"_");
			}
			sb.append(bean.getName());
			if (!StringUtils.isEmpty(template.getFileSuffix())) {
				sb.append("_"+template.getFileSuffix());
			}
			return sb.toString().replaceAll("_", "-").toLowerCase();
		}
		return "name not converted";
	}
	
	public static String getEmberClass(String name) {
		return FormatUtils.getJavaName(name.replaceAll("-", "_"));
	}
	
	
	public static String getEmberClassVariable(String name) {
		return FormatUtils.getJavaNameVariable(name.replaceAll("-", "_"));
	}

}
