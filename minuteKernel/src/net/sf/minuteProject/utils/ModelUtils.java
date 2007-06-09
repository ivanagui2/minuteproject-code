package net.sf.minuteProject.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Reference;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.presentation.EntityBlock;
import net.sf.minuteProject.configuration.bean.presentation.EntityBlocks;
import net.sf.minuteProject.configuration.bean.presentation.Presentation;
import net.sf.minuteProject.configuration.bean.presentation.PresentationBlock;
import net.sf.minuteProject.configuration.bean.view.Function;
import net.sf.minuteProject.configuration.bean.view.Service;
import net.sf.minuteProject.configuration.bean.view.View;

import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;


public class ModelUtils {
	
	// deprecated
	public static boolean isToGenerate(BusinessModel businessModel, Table table) {
		if (businessModel.getGenerationCondition()!=null)
			return businessModel.getGenerationCondition().areConditionsTrue(table.getName());
		return true;
	}
	
	public static boolean isToGenerate(BusinessModel businessModel, net.sf.minuteProject.configuration.bean.model.data.Table table) {
		if (businessModel.getGenerationCondition()!=null)
			return businessModel.getGenerationCondition().areConditionsTrue(table.getName());
		return true;
	}	
	
	public static String getPackage(Model model, Template template, Table table) {
		StringBuffer sb = new StringBuffer(getTechnicalPackage(model, template));
		sb.append("."+CommonUtils.getBusinessPackage(model, table));
		return sb.toString();		
	}
	
	public static String getPackage(Model model, Template template, Package pack) {
		StringBuffer sb = new StringBuffer(getTechnicalPackage(model, template));
		sb.append("."+pack.getName());
		return sb.toString();		
	}

	public static String getPackage(Model model, Template template) {
		StringBuffer sb = new StringBuffer(getTechnicalPackage(model, template));
		// no need to add the subpackage since it is the model level
		//sb.append("."+model.getName());
		return sb.toString();		
	}

	public static String getPackage(AbstractConfiguration bean, Template template) {
		StringBuffer sb = new StringBuffer(bean.getTechnicalPackage(template));
		return sb.toString();		
	}
	
	public static String getTechnicalPackage(Model model, Template template) {
		StringBuffer sb = new StringBuffer(template.getPackageRoot());
		sb.append("."+model.getName());
		if (template.getTechnicalPackage()!=null && !template.getTechnicalPackage().equals(""))
			sb.append("."+template.getTechnicalPackage());
		return sb.toString();		
	}
	
	public static String getTechnicalPackage(View view, Template template) {
		StringBuffer sb = new StringBuffer(template.getPackageRoot());
		sb.append("."+view.getProjectname());
		sb.append("."+template.getTechnicalPackage());
		sb.append("."+view.getName());
		return sb.toString();		
	}	
	
	public static String getTechnicalPackage(Service service, Template template) {
		StringBuffer sb = new StringBuffer(getTechnicalPackage(service.getView(), template));
		sb.append("."+service.getName());
		return sb.toString();		
	}		
	//TODO getPackage and technicalPackage must be in the configuration bean interface
	public static String getTechnicalPackage(Function function, Template template) {
		StringBuffer sb = new StringBuffer(getTechnicalPackage(function.getService(), template));
		sb.append("."+function.getName());
		return sb.toString();		
	}		
	
	public static String getPackageDir(Model model, Template template, Table table) {
		return FormatUtils.getDirFromPackage(getPackage(model, template, table));		
	}

	public static String getPackageDir(Model model, Template template, Package pack) {
		return FormatUtils.getDirFromPackage(getPackage(model, template, pack));		
	}
	
	public static String getTechnicalPackageDir(Model model, Template template) {
		return FormatUtils.getDirFromPackage(getTechnicalPackage(model, template));		
	}	
	
	public static List getParents (Database database, Table table) {
		List list = new ArrayList();
		org.apache.ddlutils.model.Reference ref;
		Reference reference;
		ForeignKey [] foreignKeys = table.getForeignKeys();
		for (int i = 0; i < foreignKeys.length; i++) {
			ref = foreignKeys[i].getFirstReference();
			String tablename = foreignKeys[i].getForeignTableName();
			reference = new Reference();
			reference.setTableName(tablename);
			reference.setColumnName(ref.getLocalColumnName());
			//reference.setTable(foreignKeys[i].getForeignTable());
			reference.setTable(TableUtils.getTable(database,tablename));
			list.add(reference);				
		}
		return list;
	}	
	public static List getParents (Table table) {
		List list = new ArrayList();
		org.apache.ddlutils.model.Reference ref;
		Reference reference;
		ForeignKey [] foreignKeys = table.getForeignKeys();
		for (int i = 0; i < foreignKeys.length; i++) {
			ref = foreignKeys[i].getFirstReference();
			String tablename = foreignKeys[i].getForeignTableName();
			reference = new Reference();
			reference.setTableName(tablename);
			reference.setColumnName(ref.getLocalColumnName());
			reference.setTable(foreignKeys[i].getForeignTable());
			list.add(reference);				
		}
		return list;
	}
	public static List getChildren (Database database, Table table) {
		List list = new ArrayList();
		String columnRef;
		org.apache.ddlutils.model.Reference ref;
		Reference reference;
		Table [] tables = database.getTables();
    	for (int i = 0; i < tables.length; i++) {
    		ForeignKey [] fk = tables[i].getForeignKeys();
        	for (int j = 0; j < fk.length; j++) {
        		String tableName = fk[j].getForeignTableName();
        		if (tableName!=null) {
	        		if (tableName.equals(table.getName())) {
	        			columnRef = new String();
	        			ref = fk[j].getReference(0);
	        			columnRef = ref.getLocalColumnName();
	        			reference = new Reference();
	        			reference.setTableName(tables[i].getName());
	        			reference.setColumnName(columnRef);
	        			reference.setTable(tables[i]);
	        			list.add(reference);
	        		}
        		}
        	}    	
        }		
    	return list;
	}
	
	public static boolean hasParentReferenceSymbols(Model model, Table table) {
		if (getParentReferenceSymbols(model,table)!=null)
			return true;
		return false;
	}
	
	public static boolean hasParentReferenceSymbols(EntityBlock entityBlock) {
		if (entityBlock!=null)
			return true;
		return false;
	}	
	
	public static EntityBlock getParentReferenceSymbols(Model model, Table table) {
		Presentation presentation = model.getConfiguration().getPresentation();
		for (Iterator iter = presentation.getPresentationBlocks().iterator(); iter.hasNext();) {
			for (Iterator iter2 = ((PresentationBlock)iter.next()).getEntityBlockss().iterator(); iter2.hasNext();)
				for (Iterator iter3 = ((EntityBlocks)iter2.next()).getEntityBlocks().iterator(); iter3.hasNext();) {
					EntityBlock entityBlock = (EntityBlock)iter3.next();
					if (entityBlock.getType()!= null && entityBlock.getName()!= null
						&&	entityBlock.getType().equals("entity-block-parent-reference") 
						&& entityBlock.getEntity().equals(table.getName()))
						return entityBlock;
				}
		}
		return null;
	}
	
}
