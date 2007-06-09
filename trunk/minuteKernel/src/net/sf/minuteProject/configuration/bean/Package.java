package net.sf.minuteProject.configuration.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;


public class Package extends AbstractConfiguration{

	private List listOfTables;
	private String name;
	private BusinessPackage businessPackage;
	
	public List getListOfTables() {
		return listOfTables;
	}
	
	public void addTable(net.sf.minuteProject.configuration.bean.model.data.Table table) {
		if (listOfTables==null)
			listOfTables = new ArrayList();
		table.setPackage(this);
		listOfTables.add(table);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}
	public void setBusinessPackage(BusinessPackage businessPackage) {
		this.businessPackage = businessPackage;
	}
	
	public String getTechnicalPackage(Template template) {
		StringBuffer sb = new StringBuffer(getBusinessPackage().getBusinessModel().getModel().getTechnicalPackage(template));
		sb.append("."+getName());
		return sb.toString();
	}
	
}
