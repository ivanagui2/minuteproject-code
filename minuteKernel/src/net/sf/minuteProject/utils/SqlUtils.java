package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;

public class SqlUtils {
	
	public static boolean isQuotedColumn (Column column) {
		if (column.getType().equals("INT") 
			|| column.getType().equals("NUMBER")
			|| column.getType().equals("INTEGER")
			|| column.getType().equals("LONG")
			|| column.getType().equals("BIGINT"))
			return false;
		return true;
			
	}

}
