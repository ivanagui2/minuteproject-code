package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.FunctionColumn;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;

public class RoutineUtils {

	public static boolean isReturn (Function function, Column column) {
		for (FunctionColumn fc : function.getFunctionColumns()) {
			if (fc.getName().toLowerCase().equals(column.getName().toLowerCase()))
				return fc.isReturn();
		}
		return false;
	}
	public static Column getColumn(FunctionColumn functionColumn) {
		if (functionColumn==null) return null;
		Table table = functionColumn.getFunction().getEntity(Direction.ANY);
		for (Column column : table.getColumns()) {
			if (functionColumn.getName().toLowerCase().equals(column.getName().toLowerCase()))
				return column;
		}
		return null;
	}
}
