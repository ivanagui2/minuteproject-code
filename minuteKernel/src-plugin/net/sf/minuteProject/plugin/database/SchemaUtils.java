package net.sf.minuteProject.plugin.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;

/**
 * @author florian
 *
 */
public class SchemaUtils {

	/**
	 * @param database
	 * @return List<Table>
	 */
	public List<Table> getTableDeleteOrder (Database database) {
		List<Table> workingSet = getTables(database); //copy of the tables + ref
		List<Table> deleteOrderList = getNonReferencedTable(workingSet);
//		int nbInDeleteOrderList = deleteOrderList.size();
		int deleteOrderListLastSize = 0; //init
		while (workingSet.size()>0 && deleteOrderList.size()>deleteOrderListLastSize) {
			deleteOrderListLastSize = deleteOrderList.size();
			getTableDeleteOrder(workingSet, deleteOrderList);
		}
		/*
		if (workingSet.size()==0) {
			//done
			return deleteOrderList;
		} else {
			//circular reference update the nullable fk to null
			getTableDeleteOrder(workingSet, deleteOrderList);
		}
		*/
		// in workingSet are only the referenced tables 
		// remove the one whose child is in deleteOrderList
		return deleteOrderList;
	}

	
	/**
	 * @param workingSet
	 * @param deleteOrderList
	 */
	public void getTableDeleteOrder (List<Table> workingSet, List<Table> deleteOrderList) {
		List<Table> tablesToRemove = new ArrayList<Table>();
		for (Table table : workingSet) {
			if (table.getChildren()==null || table.getChildren().length==0 || areAllReferenceInDeleteOrderList(table, deleteOrderList)) {
				deleteOrderList.add(table);
				tablesToRemove.add(table);
			}
			
		}
		for (Table table : tablesToRemove) {
			workingSet.remove(table);
		}
//		return deleteOrderList;
	}
	
	private boolean areAllReferenceInDeleteOrderList (Table table, List<Table> deleteOrderList) {
		for (Reference reference : table.getChildren()) {
			if (isStillReferenceInWorkingSet(reference, deleteOrderList))
				return false;
		}
		return true;
	}
	
	private boolean isStillReferenceInWorkingSet (Reference reference, List<Table> deleteOrderList) {
		return !isNotReferenceInDeleteOrderList(reference, deleteOrderList);
	}
	
	private boolean isNotReferenceInDeleteOrderList (Reference reference, List<Table> deleteOrderList) {
		for (Table tableDelete : deleteOrderList) {
			if (reference.getForeignTableName().equals(tableDelete.getName()))
				return true;
		}		
		return false;
	}
	
	private List<Table> getTables (Database database) {
		List<Table> tables = new ArrayList<Table>();
		for (int i = 0; i < database.getTables().length; i++) {
			tables.add(database.getTables()[i]);
		}
		return tables;
	}
	
	private List<Table> getNonReferencedTable (List<Table> tables) {
		List<Table> nonReferencedTables = new ArrayList<Table>();
		List<Table> tablesToRemove = new ArrayList<Table>();

		for (Table table : tables) {
			if (table.getChildren()==null || table.getChildren().length==0) {
				nonReferencedTables.add(table);
				tablesToRemove.add(table);
			}
		}

		for (Table table : tablesToRemove) {
			tables.remove(table);
		}
		
		return nonReferencedTables;
	}
}
