package net.sf.minuteProject.utils;

import org.apache.ddlutils.model.Table;

public class TableUtils {

	public static String getPrimaryKey (Table table) {
		if (table.getForeignKeyCount()<1)
			return ""; //ID is the default pk
		return table.getForeignKey(0).getFirstReference().getForeignColumnName();
	}

}
