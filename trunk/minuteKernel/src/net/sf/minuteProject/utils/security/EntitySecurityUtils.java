package net.sf.minuteProject.utils.security;

import net.sf.minuteProject.configuration.bean.model.data.Table;

public class EntitySecurityUtils {

	public static boolean isEntityRoleSecured(Table table) {
		return (table.getEntitySecuredAccess()!=null && table.getEntitySecuredAccess().getRoles()!=null)?true:false;
	}
	
	public static String getEntitySecuredRoles(Table table) {
		return (table.getEntitySecuredAccess()!=null)?table.getEntitySecuredAccess().getRoles():null;
	}	
	
}
