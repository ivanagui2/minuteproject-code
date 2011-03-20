package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Reference;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ReferenceDDLUtils;

public class ReferenceUtils {

	public static void setReferenceColumnAlias(Column column, String name, String newName) {
		for (net.sf.minuteProject.configuration.bean.model.data.Reference ref : column.getTable().getParents()) {
			if (name.equals(ref.getLocalColumn().getName())) 
				ref.getLocalColumn().setAlias(newName);
		}
	}
	
	public static net.sf.minuteProject.configuration.bean.model.data.Reference getReference (Column column) {
		Table table = column.getTable();
		net.sf.minuteProject.configuration.bean.model.data.Reference[] reference = table.getParents();
		for (int i = 0; i < reference.length; i++) {
			if (reference[i].getLocalColumnName().equals(column.getName()))
				return reference[i];
		}
		return null;		
	}
	
	public Reference getReference(Table table, Column column) {
		Reference reference = new Reference();
		reference.setTableName(table.getName());
		reference.setColumnName(column.getName());
		reference.setTable(table);
		reference.setColumn(column);
		return reference;
	}
	
	public Reference getReference(Table table, Column column, String tableName, String columnName) {
		Reference reference = new Reference();
		reference.setTableName(tableName);
		reference.setColumnName(columnName);
		reference.setTable(table);
		reference.setColumn(column);
		return reference;
	}	
	
	public static String getParentLink (String foreignTableName, String localColumnName) {
		return FormatUtils.getJavaName(foreignTableName)+"_"+FormatUtils.getJavaName(localColumnName);
	}
	
	public static String getParentLinkUML (String foreignTableName, String localColumnName) {
		return FormatUtils.getJavaName(foreignTableName)+"_"+FormatUtils.getJavaName(localColumnName);
	}
	
	public static net.sf.minuteProject.configuration.bean.model.data.Reference getReference(Field field) {
		org.apache.ddlutils.model.Reference referenceDDLUtils = new org.apache.ddlutils.model.Reference();
//		referenceDDLUtils.setForeignColumn(foreignColu)
		net.sf.minuteProject.configuration.bean.model.data.Reference reference = new ReferenceDDLUtils(referenceDDLUtils);
		String tableName = field.getEntity().getName();
		String columnName = field.getName();
		String foreignTableName = field.getLinkToTargetEntity();

		Database database = field.getEntity().getEnrichment().getBusinessModel().getModel().getDataModel().getDatabase();
		
		// is it a view ?
		Table table= TableUtils.getTable(database, tableName);
		if (table==null) 
			table = TableUtils.getView(database, tableName);
			
		Column column = ColumnUtils.getColumn(table, columnName);
		
		// is it a view
		Table foreignTable= TableUtils.getTable(database, foreignTableName);
		if (foreignTable==null) 
			foreignTable = TableUtils.getView(database, foreignTableName);
			
		String foreignColumnName = field.getLinkToTargetField();
		if (foreignColumnName==null)
			foreignColumnName = TableUtils.getPrimaryKey(foreignTable);
		Column foreignColumn = ColumnUtils.getColumn(foreignTable, foreignColumnName);
		
		if (table!=null && column != null && foreignTable!=null && foreignColumn!=null) {
			reference.setLocalTable(table);
			reference.setLocalColumn(column);
			reference.setForeignTable(foreignTable);
			reference.setForeignColumn(foreignColumn);
			
			reference.setLocalColumnName(columnName);
			reference.setLocalTableName(tableName);
			reference.setForeignColumnName(foreignColumnName);
			reference.setForeignTableName(foreignTableName);
			return reference;
		}
		return null;
		
	}
	
	public static net.sf.minuteProject.configuration.bean.model.data.Reference getReference (Table origin, Table target) {
		net.sf.minuteProject.configuration.bean.model.data.Reference [] refs = origin.getParents();
		for (int i = 0; i < refs.length; i++) {
			net.sf.minuteProject.configuration.bean.model.data.Reference ref = refs[i];
			if (ref.getForeignTable().equals(target))
				return ref;
		}
		return null;
	}

	public static boolean isSimilarReference(
			net.sf.minuteProject.configuration.bean.model.data.Reference reference,
			net.sf.minuteProject.configuration.bean.model.data.Reference otherReference) {
		if (reference!=null && otherReference!=null) {
			return (reference.getForeignTableName().equals(otherReference.getForeignTableName()) &&
					  reference.getForeignColumnName().equals(otherReference.getForeignColumnName()) && 
					  reference.getLocalTableName().equals(otherReference.getLocalTableName()) &&
					  reference.getLocalColumnName().equals(otherReference.getLocalColumnName()) 
					  )
					  ||
					  (reference.getForeignTableName().equals(otherReference.getLocalTableName()) &&
						reference.getForeignColumnName().equals(otherReference.getLocalColumnName()) && 
						reference.getForeignTableName().equals(otherReference.getLocalTableName()) &&
						reference.getForeignColumnName().equals(otherReference.getLocalColumnName()) 
							  
					  )
					  ;
		}
		return false;
	}
}
