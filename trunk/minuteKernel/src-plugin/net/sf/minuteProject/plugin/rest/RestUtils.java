package net.sf.minuteProject.plugin.rest;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.utils.FormatUtils;

public class RestUtils {
	
	public static final String RESTXMLURL_SERVERROOT = "/rest/xml";
	public static final String RESTJSONURL_SERVERROOT = "/rest/json";
	public static final String RESTXMLURL_CLIENTROOT = "/rest/client/xml";
	public static final String RESTJSONURL_CLIENTROOT = "/rest/client/json";
	
	public static String getRestUrlServerXml (Table table) {
		return RESTXMLURL_SERVERROOT + "/" + getTableLable (table);
	}

	public static String getRestUrlServerJson (Table table) {
		return RESTJSONURL_SERVERROOT + "/" + getTableLable (table);
	}
	
	public static String getRestUrlClientXml (Table table) {
		return RESTXMLURL_CLIENTROOT + "/" + getTableLable (table);
	}

	public static String getRestUrlClientJson (Table table) {
		return RESTJSONURL_CLIENTROOT + "/" + getTableLable (table);
	}
		
//	#set ($tableLabel=$i18nUtils.getI18nFromDBObject($formatUtils.getJavaNameVariable($table.alias), false))
//	#set ($controllerLabel=$i18nUtils.plurialize(${tableLabel}))
	private static String getTableLable(Table table) {
		String tableLabel=I18nUtils.getI18nFromDBObject(FormatUtils.getJavaNameVariable(table.getAlias()), false);
		return I18nUtils.plurialize(tableLabel);
	}

	
	public static String getControllerName (Template template, GeneratorBean bean) {
		Table table = (Table)bean;
		return I18nUtils.plurialize(FormatUtils.getJavaName(table.getAlias()));
	}	

	public static String getRenderingPackageName(Template template, GeneratorBean bean) {
		String root = template.getPackageRoot()==null ? "":template.getPackageRoot()+".";
		return  root+ getControllerName (template, bean);
	}

}
