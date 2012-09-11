package net.sf.minuteProject.plugin.oracle;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;

public class AdfUtils {

	public String getAdfType(Column column) {
		if (ColumnUtils.isTimeColumn(column))
			return "oracle.jbo.domain.Date";
		if (ColumnUtils.isNumeric(column))
			return "oracle.jbo.domain.Number";
		return "java.lang.String";
	}
	
	public String getAdfSQLType(Column column) {
		if (ColumnUtils.isTimeColumn(column))
			return "DATE";
		if (ColumnUtils.isNumeric(column))
			return "NUMERIC";
		return "VARCHAR";
	}
}
