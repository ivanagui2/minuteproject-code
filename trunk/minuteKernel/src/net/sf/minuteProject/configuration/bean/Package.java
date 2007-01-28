package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.ddlutils.model.Table;

public class Package {

	private List listOfTables;
	private String name;
	
	public List getListOfTables() {
		return listOfTables;
	}
	public void addTable(Table table) {
		if (listOfTables==null)
			listOfTables = new ArrayList();
		listOfTables.add(table);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
