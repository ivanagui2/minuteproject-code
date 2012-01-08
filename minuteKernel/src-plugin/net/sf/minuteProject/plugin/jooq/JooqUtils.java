package net.sf.minuteProject.plugin.jooq;

public class JooqUtils {

	public static String getDatabaseJavaPackage (String database) {
		if ("MYSQL".equals(database)) return "org.jooq.util.mysql.MySQLDatabase";
		return "to implement";
	}
}
