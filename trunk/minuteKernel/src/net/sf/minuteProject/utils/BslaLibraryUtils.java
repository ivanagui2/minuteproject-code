package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.view.View;

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
		return getPackageName(model, table, template, targetTemplateName) +"."+ getTemplateClassName (table, model, targetTemplateName);
	}

	private static String getEntityTemplateFullClassPath(Model model, Table table, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getEntityLevelTemplateFullPath(model, table, template, targetTemplateName));
	}
	
	public static String getDomainObjectImport (Model model, Table table, Template template) {
		return getEntityLevelTemplateFullPath(model, table, template, BslaDomainObjectTemplateName);
	}
	
	public static String getDomainObjectImport2 (Model model, Table table, Template template) {
		if (table!=null)
			return getEntityLevelTemplateFullPath(model, table, template, BslaDomainObjectTemplateName);
		return "ERROR_TABLE_NULL while getting domain import";
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
	}
	
	public static String getIbatisDaoSqlImplImport (Model model, Table table, Template template) {
		return getEntityLevelTemplateFullPath(model, table, template, BslaIbatisDaoSqlImplTemplateName);
	}	
}
