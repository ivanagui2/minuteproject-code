package net.sf.minuteProject.utils;

import java.util.Iterator;

import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;

public class CommonUtils {
	
	public static String getTableClassName (Table table) {
		return FormatUtils.getJavaName(table.getName());
	}
	
	public static String getTableVariableName (Table table){
		return FormatUtils.getJavaNameVariable(table.getName());
	}

	public static String getPackageName (Model model, Template template, Table table) {
		if (model ==null || template==null || table==null)
			return "PACKAGENAME_ERROR";
		return ModelUtils.getPackage(model, template, table);
	}

	public static String getPackageDirName (Model model, Template template, Table table) {
		return FormatUtils.getDirFromPackage(getPackageName(model, template, table));
	}	
	
	public static String getClassName (Table table, Template template) {
		return template.getOutputFileMain(getTableClassName(table));
	}
	
	public static String getClassName (Model model, Template template) {
		return template.getOutputFileMain(FormatUtils.getJavaName(model.getName()));
	}	
	
	public static String getVariableName(Table table, Template template) {
		return FormatUtils.getJavaNameVariable(getClassName(table, template));
	}

	public static String getJavaType (String type) {
		return ConvertUtils.getJavaTypeFromDBType(type);
	}
	
	protected static String getClassName (Table table, Template template, String targetTemplateName) {
		Template templateDomainObject = getTargetTemplate(template, targetTemplateName);
		if (templateDomainObject==null) {
			System.out.println("ConfigFile not ok");
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(table, templateDomainObject);
	}	
	
	protected static Template getTargetTemplate (Template template, String targetTemplateName) {
		return template.getTemplateTarget().getTarget().getTemplate(targetTemplateName);
		//return template.getTemplateTarget().getTemplate(targetTemplateName);
	}
	
	protected static String getPackageName (Model model, Table table, Template template, String targetTemplateName) {
		return getPackageName(model, getTargetTemplate(template, targetTemplateName), table);
	}
		
	protected static String getBusinessPackage(Model model, Table table) {
		return model.getBusinessModel().getBusinessPackage().getPackage(table.getName());
	}
	
	/*protected static String getBusinessPackageName(Model model, Table table) {
		return model.getName();
		//return model.getBusinessModel().getBusinessPackage().getPackage(table.getName());
	}
	
	public static String getPackageName (Model model, Table table, Template template, String targetTemplateName){
		return getPackageName(model, template, targetTemplateName) + "." +getBusinessPackage(model, table);
	}
	
	public static String getPackageName (Model model, Template template, Table table){
		return getPackageName(model, template) + "." +getBusinessPackage(model, table);
	}*/
	
	public static Template getTemplate (Configuration configuration, String name) {
		Template template=null;
		for (Iterator iter = configuration.getTarget().getTemplateTargets().iterator(); iter.hasNext(); ) {
			template = ((TemplateTarget)iter.next()).getTemplate(name);
			if (template != null) 
				break;
		}
		return template;
	}
	
	public static String getFileName (Template template, String name) {
		return template.getOutputFileName(FormatUtils.getJavaName(name));
	}
	
	public static String getClasspathName (Template template, String name) {
		return template.getOutputFileName(FormatUtils.getJavaName(name));
	}
	
	public static String getForeignKeyTableName (Column column, Table table) {
		ForeignKey [] foreignKey = table.getForeignKeys();
    	for (int j = 0; j < foreignKey.length; j++) {
    		String fkName = foreignKey[j].getReferences()[0].getLocalColumnName();
    		if (fkName!=null) {
        		if (fkName.equals(column.getName())) {
        			return foreignKey[j].getForeignTableName();
        		}
    		}
    	}
		return "";
	}	
	
	public static String getBusinessPackageName(Model model, Table table){
		return getBusinessPackage(model, table);
		//String a= model.getBusinessModel().getBusinessPackage().getPackage(table.getName());
		//return a;
	}
	
	public static String getAssociatedBusinessPackageName(Model model, String tableName){
		String a= model.getBusinessModel().getBusinessPackage().getPackage(tableName);
		return a;
	}		
	
	public static String getPrimaryKey (Table table) {
		return FormatUtils.getJavaName(TableUtils.getPrimaryKey(table));
	}	
	
	public static boolean hasPrimaryKey (Table table) {
		return table.getForeignKeyCount()>0;
	}
	
}
