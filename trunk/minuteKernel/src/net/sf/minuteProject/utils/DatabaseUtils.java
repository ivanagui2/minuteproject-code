package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicy;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicyPattern;

public class DatabaseUtils {

	
	public static String providePrimaryKeyLookUpString (Table table) {
		Database database = table.getDatabase();
		if (database.getType().equals("DB2")){
			return "SELECT NEXTVAL FOR "+provideSequence(table)+" AS ID FROM SYSIBM.SYSDUMMY1";
		} else if (database.getType().equals("ORACLE")){
			return "SELECT "+provideSequence(table)+".NEXTVAL AS ID FROM DUAL";
		} else if (database.getType().equals("MYSQL")){
			return "SELECT LAST_INSERT_ID() AS value";
		} else
		return "ERROR_ON_LOOK_UP for PK";
	}
	
	private static String provideSequence (Table table) {
		PrimaryKeyPolicy primaryKeyPolicy = table.getDatabase().getDataModel().getPrimaryKeyPolicy();
		if (primaryKeyPolicy==null) {
			//TODO log should provide a policy pattern
			return "NO LOOK UP for PK";
		}
		PrimaryKeyPolicyPattern primaryKeyPolicyPattern = primaryKeyPolicy.getFirstPrimaryKeyPolicyPattern();
		if (primaryKeyPolicyPattern==null) {
			//TODO log should provide a policy pattern
			return "NO LOOK UP for PK : no pattern found";
		}
		if (primaryKeyPolicy.isOneGlobal()) {
			return primaryKeyPolicyPattern.getPrefix()+primaryKeyPolicyPattern.getSequenceName()+primaryKeyPolicyPattern.getSuffix();
		} else
			return table.getName()+"_SEQ";
	}
	
	public boolean isPrimayKeyLookUpStringNeeded (Table table) {
		if (TableUtils.getPrimaryKeyType(table).equals("INT") ||
				TableUtils.getPrimaryKeyType(table).equals("INTEGER") ||
				TableUtils.getPrimaryKeyType(table).equals("DECIMAL") ||
				TableUtils.getPrimaryKeyType(table).equals("BIGINT") 
				)
			return true;
		return false;
			
	}
	
}
