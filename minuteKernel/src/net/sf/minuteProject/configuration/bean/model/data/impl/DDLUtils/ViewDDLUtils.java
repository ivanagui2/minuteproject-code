package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Component;
import net.sf.minuteProject.configuration.bean.model.data.View;

public class ViewDDLUtils extends TableDDLUtils implements View{

	private ArrayList<Column> realPrimaryKeys;
	private ArrayList<Column> virtualPrimaryKeys;
	private ArrayList<Column> noVirtualPrimaryKeyColumns;
	private ArrayList<Component> components;
	
	
	public ViewDDLUtils (org.apache.ddlutils.model.Table table) {
		super(table);
	}

	public Column[] getRealPrimaryKeys() {
		if (realPrimaryKeys==null)
			realPrimaryKeys = new ArrayList<Column>();
		return (Column[])realPrimaryKeys.toArray();
	}

	public Column[] getVirtualPrimaryKeys() {
		// TODO Auto-generated method stub
		if (virtualPrimaryKeys==null)
			virtualPrimaryKeys = new ArrayList<Column>();
		return (Column[])virtualPrimaryKeys.toArray(new Column[virtualPrimaryKeys.size()]);
//		return (Column[])virtualPrimaryKeys.toArray();
	}

	public void setVirtualPrimaryKeys(Column[] virtualPrimaryKeys) {
		// TODO Auto-generated method stub
		//this.virtualPrimaryKeys = virtualPrimaryKeys;
	}

	public void setRealPrimaryKeys(Column[] realPrimaryKeys) {
		// TODO Auto-generated method stub
		//this.realPrimaryKeys = realPrimaryKeys;
	}

	public void addRealPrimaryKey(Column realPrimaryKey) {
		if (realPrimaryKeys==null)
			realPrimaryKeys = new ArrayList<Column>();
		realPrimaryKeys.add(realPrimaryKey);
	}

	public void addVirtualPrimaryKey(Column virtualPrimaryKey) {
		if (virtualPrimaryKeys==null)
			virtualPrimaryKeys = new ArrayList<Column>();
		virtualPrimaryKeys.add(virtualPrimaryKey);	
	}
	
	public boolean hasPrimaryKey () {
		return !virtualPrimaryKeys.isEmpty();
	}
	
	public Column[] getPrimaryKeyColumns() {
		return getVirtualPrimaryKeys();
	}
	
	public Column [] getAttributes () {
		return getNoVirtualPrimaryKeyColumns();
	}
	
	private Column [] getNoVirtualPrimaryKeyColumns() {
		if (noVirtualPrimaryKeyColumns==null) {
			noVirtualPrimaryKeyColumns = new ArrayList<Column>();
			boolean present;
			for (Column column : super.getAttributes()) {
				present=false;
				for (Column column2 : getVirtualPrimaryKeys()) {
					if (column2.getName().equals(column.getName())) {
						present=true;
						break;
					}
				}
				if (!present)
					noVirtualPrimaryKeyColumns.add(column);
			}
		}
		return (Column[])noVirtualPrimaryKeyColumns.toArray(new Column[noVirtualPrimaryKeyColumns.size()]);
	}
	
	public Column [] getNoPrimaryKeyNoForeignKeyColumns() {
		return getAttributes();
	}

	public Component[] getComponents() {
		// if nothing => the view itself is a component
		if (components==null) {
			components = new ArrayList<Component>();
		}
		return (Component[])components.toArray(new Component[components.size()]);
	}
	
	public void setComponents(List<Component> components) {
		this.components = new ArrayList<Component>(components);
	}



	
}
