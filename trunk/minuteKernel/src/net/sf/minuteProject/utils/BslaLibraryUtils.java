package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;

import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;

public class BslaLibraryUtils extends CommonUtils{
	
	// use introspection instead
	public static final String BslaDomainObjectTemplateName = "BslaDomainObject";
	public static final String BslaIbatisDaoSqlImplTemplateName = "BslaIbatisDaoSqlImpl";
	public static final String BslaDaoInterfaceTemplateName = "BslaDaoInterface";
	
	public static String getDomainObjectImport (Model model, Table table, Template template) {
		return getPackageName(model, table, template, BslaDomainObjectTemplateName) +"."+ getClassName (table, template, BslaDomainObjectTemplateName);
	}

	public static String getDaoInterfaceName (Table table, Template template) {
		return getClassName (table, template, BslaDaoInterfaceTemplateName);
	}
	
	public static String getDaoInterfaceVariableName (Table table, Template template) {
		return FormatUtils.getJavaNameVariableFirstLetter(getClassName (table, template, BslaDaoInterfaceTemplateName));
	}	
	
	public static String getIbatisDaoSqlImplName (Table table, Template template) {
		return getClassName (table, template, BslaIbatisDaoSqlImplTemplateName);
	}	
	public static String getDaoInterfaceImport (Model model, Table table, Template template) {
		return getPackageName(model, table, template, BslaDaoInterfaceTemplateName) +"."+ getDaoInterfaceName (table, template);		
	}
	
	public static String getIbatisDaoSqlImplImport (Model model, Table table, Template template) {
		return getPackageName(model, table, template, BslaIbatisDaoSqlImplTemplateName) +"."+ getIbatisDaoSqlImplName (table, template);		
	}	
}
