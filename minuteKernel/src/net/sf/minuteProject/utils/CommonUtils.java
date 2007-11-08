package net.sf.minuteProject.utils;

import java.util.Iterator;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.view.View;


public class CommonUtils {
	
	// TODO refactor (4 times)
	public static String getTableClassName (AbstractConfiguration bean) {
		return FormatUtils.getJavaName(bean.getName());
	}
	
	public static String getTableClassName (Table table) {
		//System.out.println ("table name"+FormatUtils.getJavaName(table.getName()));
		//return FormatUtils.getJavaName(table.getName());
		return getTcn(table);
	}
	
	public static String getTcn (Table table) {
		return FormatUtils.getJavaName(table.getName());
	}
	
	public static String getPackageClassName (Package pack) {
		return FormatUtils.getJavaName(pack.getName());
	}
	
	public static String getTableVariableName (Table table){
		return FormatUtils.getJavaNameVariable(table.getName());
	}

	// 4 times (model, package, table, view) use hierachy instead
	// TODO refactor
	public static String getPackageName (GeneratorBean bean, Template template) {
		if (bean ==null || template==null)
			return "PACKAGENAME_ERROR";
		return ModelUtils.getPackage(bean, template);
	}
	
	public static String getPackageName (Model model, Template template, Table table) {
		if (model ==null || template==null || table==null)
			return "PACKAGENAME_ERROR";
		return ModelUtils.getPackage(model, template, table);
	}

	public static String getPackageName (Model model, Template template, Package pack) {
		if (model ==null || template==null || pack==null)
			return "PACKAGENAME_ERROR";
		return ModelUtils.getPackage(model, template, pack);
	}

	public static String getPackageName (Model model, Template template) {
		if (model ==null || template==null)
			return "PACKAGENAME_ERROR";
		return ModelUtils.getPackage(model, template);
	}
	//
	public static String getPackageDirName (Model model, Template template, Table table) {
		return FormatUtils.getDirFromPackage(getPackageName(model, template, table));
	}	
	public static String getPackageDirName (AbstractConfiguration bean, Template template) {
		return FormatUtils.getDirFromPackage(getPackageName(bean, template));
	}	
	// TODO refactor 4 times
	public static String getClassName (AbstractConfiguration bean, Template template) {
		return template.getOutputFileMain(getTableClassName(bean));
	}
	
	public static String getClassName (Table table, Template template) {
		String className = template.getOutputFileMain(getTableClassName(table));
		return className;
	}
	
	public static String getClassName2 (Table table, Template template) {
		String className = template.getOutputFileMain(getTableClassName(table));
		return className;
	}
	
	public static String getClassName (Package pack, Template template) {
		return template.getOutputFileMain(getPackageClassName(pack));
	}
	///
	
	public static String getClassName (Model model, Template template) {
		return template.getOutputFileMain(FormatUtils.getJavaName(model.getName()));
	}	
	
	public static String getVariableName(Table table, Template template) {
		return FormatUtils.getJavaNameVariable(getClassName(table, template));
	}

	public static String getJavaType (String type) {
		return ConvertUtils.getJavaTypeFromDBType(type);
	}
	
	// TODO refactor 4 times
	protected static String getTemplateClassName (AbstractConfiguration bean, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			System.out.println("ConfigFile not ok");
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(bean, templateTarget);
	}
	
	protected static String getTemplateClassName (Table table, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			System.out.println("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(table, templateTarget);
	}
	
	protected static String getTemplateClassName (Table table, Model model, String targetTemplateName) {
		//Template templateTarget = getTargetTemplate(template, targetTemplateName);
		Template templateTarget = getTargetTemplate(model, targetTemplateName);
		if (templateTarget==null) {
			System.out.println("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(table, templateTarget);
	}

	protected static String getTemplateClassName (Package pack, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			System.out.println("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(pack, templateTarget);
	}

	protected static String getTemplateClassName (Model model, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			System.out.println("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(model, templateTarget);
	}
	
	public static Template getTargetTemplate (Template template, String targetTemplateName) {
		return template.getTemplateTarget().getTarget().getTemplate(targetTemplateName);
		//return template.getTemplateTarget().getTemplate(targetTemplateName);
	}
	
	public static Template getTargetTemplate (Model model, String targetTemplateName) {
		return model.getConfiguration().getTarget().getTemplate(targetTemplateName);
		//return template.getTemplateTarget().getTemplate(targetTemplateName);
	}	
	
	// get all the package either for table, package or model
	protected static String getPackageName (Model model, Table table, Template template, String targetTemplateName) {
		return getPackageName(model, getTargetTemplate(model, targetTemplateName), table);
	}

	protected static String getPackageName (AbstractConfiguration bean, Template template, String targetTemplateName) {
		return getPackageName(bean, getTargetTemplate(template, targetTemplateName));
	}
	
	protected static String getPackageName (Model model, Template template, String targetTemplateName) {
		return getPackageName(model, getTargetTemplate(template, targetTemplateName));
	}

	protected static String getPackageName (Model model, Package pack, Template template, String targetTemplateName) {
		return getPackageName(model, getTargetTemplate(template, targetTemplateName), pack);
	}
	
	protected static String getBusinessPackage(Model model, Table table) {
		return model.getBusinessModel().getBusinessPackage().getPackage(table.getName());
	}
	
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

	/*
	public static String getBusinessPackageName(Model model, net.sf.minuteProject.configuration.bean.Table table){
		return getBusinessPackage(model, table.getTable());
	}*/
	
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

	public static String getPrimaryKeyType (Table table) {
		if (table.hasPrimaryKey())
			return getType(TableUtils.getPrimaryFirstColumn(table));
		return "ERROR-NO PK found for table "+table.getName();
	}	
	
	public static String getPrimaryKeyFullType (Table table) {
		if (table.hasPrimaryKey())
			return getFullType(TableUtils.getPrimaryFirstColumn(table));
		return "ERROR-NO PK found for table "+table.getName();
	}		
	
	public static String getType (Column column) {
		return ConvertUtils.getJavaTypeFromDBType(column.getType());
	}	
	
	public static String getFullType (Column column) {
		return ConvertUtils.getJavaTypeFromDBFullType(column.getType());
	}	
	
	public static String getPK (Table table) {
		return TableUtils.getPrimaryKey(table);
	}	
	public static boolean hasPrimaryKey (Table table) {
		return table.hasPrimaryKey();
	}
	
	public static String getLevelTemplateFullPath (AbstractConfiguration bean, Template template, String targetTemplateName) {
		return getPackageName(bean, template, targetTemplateName) +"."+ getTemplateClassName (bean, template, targetTemplateName);
	}

	public static String getLevelTemplateFullClassPath (AbstractConfiguration bean, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getLevelTemplateFullPath(bean, template, targetTemplateName));
	}
	
	public static String getArtifactRelativePathDirAndFullName(Template template, Table table) {
		String classpathName = getPackageName(table, template);
		String filename = getFileName(template, table.getName());
		return FormatUtils.getDirFromPackage(classpathName)+"/"+filename;
	}
	
}
