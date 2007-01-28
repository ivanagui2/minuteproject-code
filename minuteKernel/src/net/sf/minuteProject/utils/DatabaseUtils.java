package net.sf.minuteProject.utils;

import org.apache.ddlutils.model.Table;

public class DatabaseUtils {

	public static String providePrimaryKeyLookUpString (String databaseType, Table table) {
		if (databaseType.equals("DB2")){
			return "SELECT NEXTVAL FOR "+provideSequence(table)+" AS ID FROM SYSIBM.SYSDUMMY1";
		}
		return "ERROR_ON_LOOK_UP for PK";
	}
	
	private static String provideSequence (Table table) {
		return table.getName()+"_SEQ";
	}
	
}
