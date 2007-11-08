package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;

import org.apache.ddlutils.model.Index;
import org.apache.ddlutils.model.IndexColumn;


public class TableUtils {

	public static Column getPrimaryFirstColumn (Table table) {
		Column primaryKeyColumn [] = table.getPrimaryKeyColumns();
		if (primaryKeyColumn.length<1)
			return null; //ID is the default pk
		return primaryKeyColumn[0];		
	}
	public static String getPrimaryKey (Table table) {
		Column primaryKeyColumn=getPrimaryFirstColumn(table);
		if (primaryKeyColumn!=null)
			return primaryKeyColumn.getName();
		return "";
	}
	
	public static String getPrimaryKeyType(Table table) {
		return getPrimaryFirstColumn(table).getType();
	}

	public static String getUnique (Table table) {
		Index indexes [] = table.getUniqueIndices();
		if (indexes.length<1)
			return ""; //ID is the default pk
		// to change when the indexes will be used.
		return indexes[0].getName();
		//return table.getForeignKey(0).getFirstReference().getForeignColumnName();
	}	
	
	public static Table getTable(Database database, String tablename){
		int maxTable = database.getTables().length;
		for (int i = 0; i < maxTable; i++) {
			Table table = database.getTables()[i];
			if (table.getName().equals(tablename))
				return table;
		}
		return null;
	}
	
	public static boolean isUnique (Table table, Column column) {
		Index indexes [] = table.getUniqueIndices();
		for (int i = 0; i < indexes.length; i++) {
			IndexColumn[] indexColumn = indexes[i].getColumns();
			for (int j = 0; j < indexColumn.length; j++) {
				if (indexColumn.length==1 && indexColumn[j].getName().equals (column.getName()))
					return true;
			}
		}
		return false;
	}
	/*
	public static Table getOtherEnd (Table origin, Table many2many) {
		if (many2many.isManyToMany()) {		
			Reference [] references = many2many.getParents();
			for (int i = 0; i < references.length; i++) {
				if (references[i].getForeignTable().equals(origin))
					return references[i].getForeignTable();
			}
		}
		return null;
	}*/
	
}
