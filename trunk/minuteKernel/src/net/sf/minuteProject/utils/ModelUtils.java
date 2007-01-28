package net.sf.minuteProject.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;


public class ModelUtils {
	
	public static boolean isToGenerate(BusinessModel businessModel, Table table) {
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

	public static String getTechnicalPackage(Model model, Template template) {
		StringBuffer sb = new StringBuffer(template.getPackageRoot());
		sb.append("."+model.getName());
		sb.append("."+template.getTechnicalPackage());
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
	
	public static List getParents (Table table) {
		List list = new ArrayList();
		org.apache.ddlutils.model.Reference ref;
		Reference reference;
		ForeignKey [] foreignKeys = table.getForeignKeys();
		for (int i = 0; i < foreignKeys.length; i++) {
			ref = foreignKeys[i].getFirstReference();
			reference = new Reference();
			reference.setTableName(foreignKeys[i].getForeignTableName());
			reference.setColumnName(ref.getLocalColumnName());
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
