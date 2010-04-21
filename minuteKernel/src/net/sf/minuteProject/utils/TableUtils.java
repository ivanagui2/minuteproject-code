package net.sf.minuteProject.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Index;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;

import org.apache.commons.lang.StringUtils;


public class TableUtils {

	public static final String pseudoStaticDataContentType = "pseudo-static-data";
	public static final String referenceDataContentType = "reference-data";
	public static final String liveBusinessDataContentType = "live-business-data";
	
	public static Column getPrimaryFirstColumn (Table table) {
		if (table==null)
			return null;
		Column primaryKeyColumn [] = table.getPrimaryKeyColumns();
		if (primaryKeyColumn.length<1)
			return null; //ID is the default pk
		return primaryKeyColumn[0];		
	}
	public static String getPrimaryKey (Table table) {
		Column primaryKeyColumn=getPrimaryFirstColumn(table);
		if (primaryKeyColumn!=null)
			return primaryKeyColumn.getName();
			// check if there is a virtual primary key
//		String virtualPrimaryKey = getVirtualPrimaryKey(table);
//		if (virtualPrimaryKey!=null) {
//			return virtualPrimaryKey;
//		}
		return "";
	}
	
	public static String getPrimaryKeyType(Table table) {
		return getPrimaryFirstColumn(table).getType();
	}

//	public static String getUnique (Table table) {
//		Index indexes [] = table.getUniqueIndices();
//		if (indexes.length<1)
//			return ""; //ID is the default pk
//		// to change when the indexes will be used.
//		return indexes[0].getName();
//		//return table.getForeignKey(0).getFirstReference().getForeignColumnName();
//	}	
	
	public static Table getTable(Database database, String tablename){
		return getEntity(database, tablename);
	}
	
	public static Table getTableOnly(Database database, String tablename){
		int maxTable = database.getTables().length;
		for (int i = 0; i < maxTable; i++) {
			Table table = database.getTables()[i];
			if (table.getName().equals(tablename))
				return table;
		}
		return null;
	}
	
	private static String getVirtualPrimaryKey (Table table) {
		if (table instanceof View) {
			Column virtualPrimaryKey = getVirtualPrimaryKeyFirstColumn((View)table);
			return getVirtualPrimaryKeyFirstColumn((View)table).getName();
		}
		return null;
	}
	
	private static Column getVirtualPrimaryKeyFirstColumn (View view) {
		Column primaryKeyColumn [] = view.getVirtualPrimaryKeys();
		if (primaryKeyColumn.length<1)
			return null; //ID is the default pk
		return primaryKeyColumn[0];				
	}
	
	public static boolean hasOnlyOnePrimaryKey (Table table) { 
		return (table.getPrimaryKeyColumns()!=null && table.getPrimaryKeyColumns().length==1)?true:false;
	}
	
	public static boolean hasUniqueKey (Table table) {
		return (table.getUniqueIndices()!=null && table.getUniqueIndices().length>0)?true:false;
	}
	
	public static boolean isUnique (Table table, Column column) {
		if (table==null)
			return false;
		if (column==null)
			return false;
		Index indexes [] = table.getUniqueIndices();
		if (indexes!=null) {
			for (int i = 0; i < indexes.length; i++) {
				Column[] indexColumn = indexes[i].getColumns();
				if (indexColumn!=null) {
//					for (int j = 0; j < indexColumn.length; j++) {
						if (indexColumn.length==1 
							&& indexColumn[0]!=null
							&& indexColumn[0].getName()!=null
							&& indexColumn[0].getName().equals (column.getName()))
							return true;
//					}
				}
			}
		}
		return false;
	}
	
	public static boolean isMany2Many (Table table) {
		return table.isManyToMany();
	}
	
	public static boolean isColumnPk (Column column, Table table) {
		Column [] pks = table.getPrimaryKeyColumns();
		for (int i = 0; i < pks.length; i++) {
			if (pks[i].getName().equals(column.getName()))
				return true;
		}
		return false;
	}

	public static View getView(Database database, String viewname){
		int maxView = database.getViews().length;
		for (int i = 0; i < maxView; i++) {
			View view = database.getViews()[i];
			viewname = StringUtils.upperCase(viewname);
			String viewName = StringUtils.upperCase(view.getName());
			if (viewName.equals(viewname))
				return view;
		}
		return null;
	}

	public static String getTargetType(Database database, Entity entity) {
		if (getTableOnly(database, entity.getName())!=null)
			return Table.TABLE;
		else if (getView(database, entity.getName())!=null)
			return Table.VIEW;
		return "NO_TYPE";
	}
	
	public static Table getEntity(Database database, String name) {
		Table table = getTableOnly(database, name);
		if (table==null)
			table = (Table) getView(database, name);
		return table;
	}
	
	public static boolean isView(Table table) {
	   if (table instanceof View) {
		   return true;
		}
	    return false;
	}
	
	public static boolean isView(Database database, String viewname) {
	   View view = getView(database, viewname);
	   return (view==null)?false:true;
	}
	
	public static boolean isTableOfContentType(Table table, String contentType) {
		if (table!=null && contentType.equals(table.getContentType()))
			return true;
		return false;
	}
	
	public static boolean isReferenceDataContentType(Table table) {
		return isTableOfContentType(table, referenceDataContentType);
	}

	public static boolean isPseudoStaticDataContentType(Table table) {
		return isTableOfContentType(table, pseudoStaticDataContentType);
	}
	
	public static boolean isLiveBusinessDataContentType(Table table) {
		return isTableOfContentType(table, liveBusinessDataContentType);
	}
	
	public static boolean hasSemanticReference (Table table) {
		SemanticReference semanticReference = table.getSemanticReference();
		if (semanticReference!=null) {
			if (!semanticReference.getSemanticReferenceBeanPath().isEmpty())
				return true;
		}
		return false;
	}
	
	public static List<Column> getSemanticReferenceColumns(Table table) {
		List<Column> columns = new ArrayList<Column>();
		SemanticReference semanticReference = table.getSemanticReference();
		if (semanticReference!=null) {
			for (String sqlPath : semanticReference.getSemanticReferenceSqlPath()) {
				Column column = ColumnUtils.getColumn(table, sqlPath);
				if (column!=null)
					columns.add(column);
			}
		}
		return columns;
	}
	
	public static List<Column> getLinkSemanticReferenceColumns(Table table) {
		List<Column> columns = new ArrayList<Column>();
		for (Table child : getParents(table)) {
			List <Column> list = getSemanticReferenceColumns(child);
			columns.addAll(list);
		}
		return columns;		
	}
	
	public static List<Table> getChildren (Table table) {
		List<Table> children = new ArrayList<Table> ();
		for (Reference reference : table.getChildren()) {
			children.add(reference.getForeignTable());
		}
		return children;
	}
	
	public static List<Table> getParents (Table table) {
		List<Table> parents = new ArrayList<Table> ();
		for (Reference reference : table.getParents()) {
			parents.add(reference.getForeignTable());
		}
		return parents;
	}
}
