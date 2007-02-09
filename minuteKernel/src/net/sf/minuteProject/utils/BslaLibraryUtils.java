package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.Package;

import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;

public class BslaLibraryUtils extends CommonUtils{
	
	// use introspection instead
	public static final String BslaDomainObjectTemplateName = "BslaDomainObject";
	public static final String BslaIbatisDaoSqlImplTemplateName = "BslaIbatisDaoSqlImpl";
	public static final String BslaDaoInterfaceTemplateName = "BslaDaoInterface";

	private static String getModelLevelTemplateFullPath (Model model, Template template, String targetTemplateName) {
		return getPackageName(model, template, targetTemplateName) +"."+ getTemplateClassName (model, template, targetTemplateName);
	}

	public static String getModelLevelTemplateFullClassPath (Model model, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getModelLevelTemplateFullPath(model, template, targetTemplateName));
	}
	
	private static String getPackageLevelTemplateFullPath(Model model, Package pack, Template template, String targetTemplateName) {
		return getPackageName(model, pack, template, targetTemplateName) +"."+ getTemplateClassName (pack, template, targetTemplateName);
	}

	public static String getPackageLevelTemplateFullClassPath(Model model, Package pack, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getPackageLevelTemplateFullPath(model, pack, template, targetTemplateName));
	}
	
	private static String getEntityLevelTemplateFullPath(Model model, Table table, Template template, String targetTemplateName) {
		return getPackageName(model, table, template, targetTemplateName) +"."+ getTemplateClassName (table, template, targetTemplateName);
	}

	private static String getEntityTemplateFullClassPath(Model model, Table table, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getEntityLevelTemplateFullPath(model, table, template, targetTemplateName));
	}
	
	public static String getDomainObjectImport (Model model, Table table, Template template) {
		return getEntityLevelTemplateFullPath(model, table, template, BslaDomainObjectTemplateName);
		//return getPackageName(model, table, template, BslaDomainObjectTemplateName) +"."+ getClassName (table, template, BslaDomainObjectTemplateName);
	}

	public static String getDaoInterfaceName (Table table, Template template) {
		return getTemplateClassName (table, template, BslaDaoInterfaceTemplateName);
	}
	
	public static String getDaoInterfaceVariableName (Table table, Template template) {
		return FormatUtils.getJavaNameVariableFirstLetter(getTemplateClassName (table, template, BslaDaoInterfaceTemplateName));
	}	
	
	public static String getIbatisDaoSqlImplName (Table table, Template template) {
		return getTemplateClassName (table, template, BslaIbatisDaoSqlImplTemplateName);
	}	
	public static String getDaoInterfaceImport (Model model, Table table, Template template) {
		return getEntityLevelTemplateFullPath(model, table, template, BslaDaoInterfaceTemplateName);
		//return getPackageName(model, table, template, BslaDaoInterfaceTemplateName) +"."+ getDaoInterfaceName (table, template);		
	}
	
	public static String getIbatisDaoSqlImplImport (Model model, Table table, Template template) {
		return getEntityLevelTemplateFullPath(model, table, template, BslaIbatisDaoSqlImplTemplateName);
		//return getPackageName(model, table, template, BslaIbatisDaoSqlImplTemplateName) +"."+ getIbatisDaoSqlImplName (table, template);		
	}	
}
