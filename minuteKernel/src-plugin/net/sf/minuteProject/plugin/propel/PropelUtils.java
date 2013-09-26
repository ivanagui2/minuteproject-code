package net.sf.minuteProject.plugin.propel;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.plugin.php.PhpUtils;
import net.sf.minuteproject.model.db.type.FieldType;

public class PropelUtils extends PhpUtils{

	public static String getType(Column column) {
////		text: Type that maps an SQL CLOB to a PHP string.
		if (column.getType().equals(FieldType.VARCHAR.toString())||
			column.getType().equals(FieldType.VARCHAR2.toString())
		)
			return "VARCHAR";	
		if (column.getType().equals(FieldType.BIT.toString()))
			return "BOOLEAN";	
		return column.getType();
//		return PhpUtils.getType(column);
	}
	
	public static String getDsn(Model model) {
		String databaseName = PhpUtils.getDatabaseName(model);
		if (databaseName!=null) {
			if ("mysql".equals(databaseName))
				return "mysql:host=localhost;dbname=my_db_name";
		}
		return "no DSN provide for DB type = "+databaseName;
	}
}
