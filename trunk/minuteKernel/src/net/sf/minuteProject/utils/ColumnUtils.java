package net.sf.minuteProject.utils;

import java.math.BigInteger;
import java.util.List;

import org.apache.velocity.texen.util.PropertiesUtil;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Property;

public class ColumnUtils {
	
	public static String CHECK_CONSTRAINT_PROPERTY_TAG = "checkconstraint";
	
	public static Column getColumn(Table table, String columnName) {
		if (table!=null) {
			int maxColumn = table.getColumns().length;
			for (int i = 0; i < maxColumn; i++) {
				Column column = table.getColumns()[i];
				if (column.getName().equals(columnName))
					return column;
			}
		}
		return null;		
	}
	
	public static boolean isNaturalPk(Column column) {
		if (column.isPrimaryKey() && 
		   !(
			column.getType().equals("INT") || 
			column.getType().equals("BIGINT") || 
			column.getType().equals("INTEGER") ||
			column.getType().equals("NUMBER") ||
			column.getType().equals("DECIMAL") ||
			column.getType().equals("REAL")
			)
			)
			return true;
		return false;
	}
	
	public static boolean isPkUserProvided (Column column) {
		return isNaturalPk(column);
	}
	
	public static boolean isUnique (Column column) {
		return TableUtils.isUnique(column.getTable(), column);
	}
	
	public static boolean isForeignKey (Column column) {
		Table table = column.getTable();
		return isForeignKey(column, table);
	}

	public static boolean isForeignKey (Column column, Table table) {
		Reference[] reference = table.getParents();
		for (int i = 0; i < reference.length; i++) {
			if (reference[i].getLocalColumnName().equals(column.getName()))
				return true;
		}
		return false;
	}
	
	public static Table getForeignTable (Column column) {
		Reference reference = getReference(column);
		return (reference!=null)? reference.getForeignTable():null;
	}

	private static Reference getReference (Column column) {
		return ReferenceUtils.getReference(column);
	}
	
	public static boolean isLengthPrecisionColumn(Column column) {
		if (column==null || column.getType()==null) return false;
		if (
			column.getType().equals("CHAR") || 
			column.getType().equals("CHAR2") ||
			column.getType().equals("VARCHAR") ||
			column.getType().equals("VARCHAR2") ||
			column.getType().equals("VARGRAPHIC") ||
			column.getType().equals("VARGRAPHIC2") ||
			column.getType().equals("CLOB")
			)
			return true;
		return false;
	}
	
	public static String getMethodInputParameters (Column columns[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (i!=0)
				sb.append(", ");
			sb.append(CommonUtils.getFullType2(columns[i]));
			sb.append(" ");
			sb.append(CommonUtils.getJavaVariableName(columns[i].getName()));
		}
		return sb.toString();
	}
	
	public static String getDefaultStuffingForColumn (Column column) {
		if (column.getType().equals("CHAR") || 
			column.getType().equals("CHAR2") ||
			column.getType().equals("VARCHAR") ||
			column.getType().equals("VARCHAR2") ||
			column.getType().equals("VARGRAPHIC") ||
			column.getType().equals("VARGRAPHIC2") ||
			column.getType().equals("CLOB")
				)
			return "\"\"";
		if (column.getType().equals("INT") ||
			column.getType().equals("INTEGER") )
			return "Integer.valueOf(\"-1\")";
		if (column.getType().equals("BIGINT") || 
			column.getType().equals("LONG")   ||
			column.getType().equals("NUMBER") ||
			column.getType().equals("DECIMAL") )
			return "Long.valueOf(\"-1\")";
		return "\"\"";
	}
	

	public static boolean isTimeColumn (Column column) {
		if (column.getType().equals("DATE") || 
			column.getType().equals("TIMESTAMP") )
			return true;		
		return false;
	}
	
	public static Property getCheckConstraintProperty (Column column) {
		return column.getPropertyByTag(CHECK_CONSTRAINT_PROPERTY_TAG);
	}

	public static List<Property> getCheckConstraintValues (Column column) {
		Property checkConstraint = getCheckConstraintProperty(column);
		if (checkConstraint!=null)
			return checkConstraint.getProperties();
		return null;
	}
	
	public static boolean hasCheckConstraint (Column column) {
		if (getCheckConstraintProperty(column)!=null)
			return true;
		return false;
	}

	public static boolean hasFormulaStereotype (Column column) {
		if (hasStereotype(column) && column.getStereotype().getFormula()!=null)
			return true;
		return false;
	}
	
	public static boolean hasStereotype (Column column) {
		if (column.getStereotype()!=null)
			return true;
		return false;		
	}
	
}
