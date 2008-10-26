package net.sf.minuteProject.utils;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.minuteProject.application.ModelGenerator;
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
	
	private static Logger logger = Logger.getLogger(CommonUtils.class);
	
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
		return getJavaClassName(table.getName());
	}
	
	public static String getJavaClassName (String entityName) {
		return FormatUtils.getJavaName(entityName);
	}
	
	public static String getPackageClassName (Package pack) {
		return FormatUtils.getJavaName(pack.getName());
	}
	
	public static String getTableVariableName (Table table){
		return getJavaVariableName(table.getName());
	}
	
	public static String getJavaVariableName (String string) {
		return FormatUtils.getJavaNameVariable(string);
		//return FormatUtils.getJavaNameVariableFirstLetter(string);
	}

	public static String getJavaNameVariableFirstLetter (String string) {
		//return FormatUtils.getJavaNameVariable(string);
		return FormatUtils.getJavaNameVariableFirstLetter(string);
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
	public static String getClassName (GeneratorBean bean, Template template) {
		//return template.getOutputFileMain(getTableClassName(bean));
		return template.getOutputFileMain(bean);
	}
	
//	public static String getClassName (Table table, Template template) {
//		String className = template.getOutputFileMain(getTableClassName(table));
//		return className;
//	}
//	
	public static String getClassName2 (Table table, Template template) {
		String className = template.getOutputFileMain(table);
		return className;
	}
//	
//	public static String getClassName (Package pack, Template template) {
//		return template.getOutputFileMain(getPackageClassName(pack));
//	}
//	///
//	
//	public static String getClassName (Model model, Template template) {
//		return template.getOutputFileMain(FormatUtils.getJavaName(model.getName()));
//	}	
//	
	public static String getVariableName(Table table, Template template) {
		return FormatUtils.getJavaNameVariable(getClassName(table, template));
	}

	public static String getJavaType (String type) {
		return ConvertUtils.getJavaTypeFromDBType(type);
	}
	
	public static String getJavaType (Column column) {
		if (column == null) {
			logger.error("ERROR column is null");
			return "ERROR column is null";
		}
		return ConvertUtils.getJavaTypeFromDBType(column);
	}
	
	// TODO refactor 4 times
	protected static String getTemplateClassName (AbstractConfiguration bean, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			logger.debug("ConfigFile not ok");
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(bean, templateTarget);
	}
	
	protected static String getTemplateClassName (Table table, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			logger.debug("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(table, templateTarget);
	}
	
	public static String getTemplateClassName (Table table, Model model, String targetTemplateName) {
		//Template templateTarget = getTargetTemplate(template, targetTemplateName);
		Template templateTarget = getTargetTemplate(model, targetTemplateName);
		if (templateTarget==null) {
			logger.debug("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(table, templateTarget);
	}
	
	public static String getTemplateClassName (Table table, String targetTemplateName) {
		//Template templateTarget = getTargetTemplate(template, targetTemplateName);
		Template templateTarget = getTargetTemplate(table.getDatabase().getDataModel().getModel(), targetTemplateName);
		if (templateTarget==null) {
			logger.debug("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(table, templateTarget);
	}	

	protected static String getTemplateClassName (Package pack, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			logger.debug("ERROR on config file : missing "+targetTemplateName);
			return "ERROR on config file : missing "+targetTemplateName;
		}
		return getClassName(pack, templateTarget);
	}

	public static String getTemplateClassName (Model model, Template template, String targetTemplateName) {
		Template templateTarget = getTargetTemplate(template, targetTemplateName);
		if (templateTarget==null) {
			logger.debug("ERROR on config file : missing "+targetTemplateName);
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
	// TODO remove this method
	protected static String getPackageName (Model model, Table table, Template template, String targetTemplateName) {
		return getPackageName(model, getTargetTemplate(model, targetTemplateName), table);
	}

	protected static String getPackageName (Model model, Table table, String targetTemplateName) {
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
	
	public static String getFileName (Template template, GeneratorBean bean) {
		return template.getOutputFileName(bean);
	}
	
	public static String getFileMainName (Template template, GeneratorBean bean) {
		return template.getOutputFileMain(bean);
	}
	
	public static String getClasspathName (Template template, GeneratorBean bean) {
		return template.getOutputFileName(bean);
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
		if (tableName==null || model==null)
			return "getAssociatedBusinessPackageName returns null";
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
			return getFullType2(TableUtils.getPrimaryFirstColumn(table));
		return "ERROR-NO PK found for table "+table.getName();
	}		
	
	public static String getType (Column column) {
		if (column == null) {
			logger.debug("ERROR the column is null");
			return "ERROR column is null";
		}
		return ConvertUtils.getJavaTypeFromDBType(column.getType());
	}	
	
	public static String getFullType2 (Column column) {
		if (column == null) {
			logger.debug("ERROR the column is null");
			return "ERROR column is null";
		}
		return ConvertUtils.getJavaTypeFromDBFullType(column);
	}	
	
	public static String getPK (Table table) {
		return TableUtils.getPrimaryKey(table);
	}	
	
	public static Column getPrimaryKeyFirstColumn (Table table) {
		return TableUtils.getPrimaryFirstColumn(table);
	}
	public static boolean hasPrimaryKey (Table table) {
		return table.hasPrimaryKey();
	}
	/**
	 * returns true if the table has a or more primary and all of the pk are user provided 
	 * @param table
	 * @return
	 */
	public static boolean isPkUserProvided (Table table) {
		if (table.isManyToMany())
			return true;
		if (table.hasPrimaryKey()) {
			Column [] columns = table.getPrimaryKeyColumns();
			for (int i = 0; i < columns.length; i++) {
				Column column = columns[i];
				if (!ColumnUtils.isPkUserProvided(column))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public static String getLevelTemplateFullPath (AbstractConfiguration bean, Template template, String targetTemplateName) {
		return getPackageName(bean, template, targetTemplateName) +"."+ getTemplateClassName (bean, template, targetTemplateName);
	}

	public static String getLevelTemplateFullClassPath (AbstractConfiguration bean, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getLevelTemplateFullPath(bean, template, targetTemplateName));
	}
	
	public static String getArtifactRelativePathDirAndFullName(Template template, Table table) {
//		 TODO refactor may have side effect check with the Template implementation
		String classpathName = getPackageName(table, template);
		String filename = getFileName(template, table);
		return FormatUtils.getDirFromPackage(classpathName)+"/"+filename;
	}

	public static String getArtifactRelativePathDirAndFullName(Template template, Model model) {
		// TODO refactor may have side effect check with the Template implementation
		String classpathName = getPackageName(model, template);
		String filename = getFileName(template, model);
		return FormatUtils.getDirFromPackage(classpathName)+"/"+filename;
	}
	
	public static String getArtifactRelativePathDirAndFullName(Table table, String templateName) {
		Template template = getTemplate(table.getDatabase().getDataModel().getModel().getConfiguration(), templateName);
		return getArtifactRelativePathDirAndFullName(template, table);
	}
	
	public static String getArtifactRelativePathDirAndFullName(Model model, String templateName) {
		Template template = getTemplate(model.getConfiguration(), templateName);
		return getArtifactRelativePathDirAndFullName(template, model);
	}
	
	public static String getArtifactFullClasspath(Table table, String templateName) {
		Template template = getTemplate(getModel(table).getConfiguration(), templateName);
		return getEntityLevelTemplateFullPath(getModel(table), table, template, templateName);
	}	

	public static String getArtifactFullClasspath(Model model, String templateName) {
		return getLevelTemplateFullPath(model, getTemplate(model.getConfiguration(), templateName), templateName);
	}	
	
	public static String getTemplateArtifactName (Model model, String templateName) {
		Template template = getTemplate(model.getConfiguration(), templateName);
		return getFileName(template, model);
	}
	
	public static String getTemplateArtifactName (Table table, String templateName) {
		Template template = getTemplate(getModel(table).getConfiguration(), templateName);
		return getFileName(template, table);
	}
	
	public static String getTemplateArtifactMainName (Table table, String templateName) {
		Template template = getTemplate(getModel(table).getConfiguration(), templateName);
		return getFileMainName(template, table);
	}
	
	public static String getTemplateArtifactDirName (Model model, String templateName) {
		Template template = getTemplate(model.getConfiguration(), templateName);
		return FormatUtils.getDirFromPackage(getPackageName(model, template));
	}
	
	private static Model getModel(Table table) {
		return table.getDatabase().getDataModel().getModel();
	}
	
	public static String getEntityLevelTemplateFullPath(Model model, Table table, Template template, String targetTemplateName) {
		return getPackageName(model, table, template, targetTemplateName) +"."+ getTemplateClassName (table, model, targetTemplateName);
	}
	
	public static String getTemplateJavaVariableName(Table table, Model model, String targetTemplateName) {
		String result= getTemplateClassName(table, model, targetTemplateName);
		return CommonUtils.getJavaVariableName(result);
	}

	public static String getJavaNameVariableFirstLetter(Table table, Model model, String targetTemplateName) {
		String result= getTemplateClassName(table, model, targetTemplateName);
		return CommonUtils.getJavaNameVariableFirstLetter(result);
	}
	
	private static String getPackageLevelTemplateFullPath(Model model, Package pack, Template template, String targetTemplateName) {
		return getPackageName(model, pack, template, targetTemplateName) +"."+ getTemplateClassName (pack, template, targetTemplateName);
	}

	public static String getPackageLevelTemplateFullClassPath(Model model, Package pack, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getPackageLevelTemplateFullPath(model, pack, template, targetTemplateName));
	}

	private static String getModelLevelTemplateFullPath(Model model, Template template, String targetTemplateName) {
		return getPackageName(model, template, targetTemplateName) +"."+ getTemplateClassName (model, template, targetTemplateName);
	}
	
	public static String getModelLevelTemplateFullClassPath(Model model, Template template, String targetTemplateName) {
		return FormatUtils.getDirFromPackage(getModelLevelTemplateFullPath(model, template, targetTemplateName));
	}
	/**
	 * comment utils mark the minute template the a minute comment
	 */
	public static String getComment (Template template) {
		StringBuffer sb = new StringBuffer();
		String comment = "This code has been generated by MinuteProject with the template "+template.getName();
		if (template.getFileExtension().equals("java")) {
			sb.append("/*\n");
			sb.append("*");
			sb.append(comment);
			sb.append("*/\n");
		} else
		if (template.getFileExtension().equals("xml") || template.getFileExtension().equals("html") || template.getFileExtension().equals("jsp") ) {
			sb.append("<!--\n");
			sb.append(comment);
			sb.append("-->\n");
		} else
		if (template.getFileExtension().equals("properties") ) {
			sb.append("#");
			sb.append(comment);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static int getPrimaryKeyCount (Table table) {
		return table.getPrimaryKeyColumns().length;
	}
	
	public static List<TemplateTarget> getDistinctTemplateTargetList (Model model) {
		return TemplateTargetUtils.getDistinctTemplateTargetDirs(model); 
	}
	
	public static boolean isModelNull (Model model) {
		return (model == null);
	}
	
}
