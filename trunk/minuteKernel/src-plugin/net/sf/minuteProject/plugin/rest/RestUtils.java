package net.sf.minuteProject.plugin.rest;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.utils.FormatUtils;

public class RestUtils {
	
	public static final String GRAILS_TECHNO = "grails";
	public static final String PLAY_TECHNO = "play";
	public static final String RESTXMLURL_ROOT = "/rest";
	public static final String RESTXMLURL_SERVERROOT = "/xml";
	public static final String RESTJSONURL_SERVERROOT = "/json";
	public static final String RESTXMLURL_CLIENTROOT = "/client/xml";
	public static final String RESTJSONURL_CLIENTROOT = "/client/json";
	
	public static String getRestUrlServerXml (Table table, String techno) {
		return getTechnoRoot(techno) + RESTXMLURL_SERVERROOT + "/" + getTableLable (table);
	}

	public static String getRestUrlServerJson (Table table, String techno) {
		return getTechnoRoot(techno) + RESTJSONURL_SERVERROOT + "/" + getTableLable (table);
	}
	
	public static String getRestUrlClientXml (Table table, String techno) {
		return getTechnoRoot(techno) + RESTXMLURL_CLIENTROOT + "/" + getTableLable (table);
	}

	public static String getRestUrlClientJson (Table table, String techno) {
		return getTechnoRoot(techno) + RESTJSONURL_CLIENTROOT + "/" + getTableLable (table);
	}
 	
	private static String getTechnoRoot() {
		return RESTXMLURL_ROOT;
	}
	
    private static String getTechnoRoot(String techno) {
		if (GRAILS_TECHNO.equals(techno))
			return "${request.contextPath}"+RESTXMLURL_ROOT;
		if (PLAY_TECHNO.equals(techno))
			return "@{Rest.rest}";
		return getTechnoRoot();
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
