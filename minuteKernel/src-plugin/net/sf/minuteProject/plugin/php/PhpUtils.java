package net.sf.minuteProject.plugin.php;

import net.sf.minuteProject.configuration.bean.model.data.Column;

public class PhpUtils {

	public static String getType(Column column) {
		//http://docs.doctrine-project.org/en/2.0.x/reference/basic-mapping.html
//		string: Type that maps an SQL VARCHAR to a PHP string.
//		integer: Type that maps an SQL INT to a PHP integer.
//		smallint: Type that maps a database SMALLINT to a PHP integer.
//		bigint: Type that maps a database BIGINT to a PHP string.
//		boolean: Type that maps an SQL boolean to a PHP boolean.
//		decimal: Type that maps an SQL DECIMAL to a PHP double.
//		date: Type that maps an SQL DATETIME to a PHP DateTime object.
//		time: Type that maps an SQL TIME to a PHP DateTime object.
//		datetime: Type that maps an SQL DATETIME/TIMESTAMP to a PHP DateTime object.
//		text: Type that maps an SQL CLOB to a PHP string.
//		object: Type that maps a SQL CLOB to a PHP object using serialize() and unserialize()
//		array: Type that maps a SQL CLOB to a PHP object using serialize() and unserialize()
//		float: Type that maps a SQL Float (Double Precision) to a PHP double. IMPORTANT: Works only with locale settings that use decimal points as separator.
		return "TOIMPLEMENT";
	}
}
