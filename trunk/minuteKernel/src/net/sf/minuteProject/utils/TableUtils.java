package net.sf.minuteProject.utils;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Index;
import org.apache.ddlutils.model.Table;

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
	
}
