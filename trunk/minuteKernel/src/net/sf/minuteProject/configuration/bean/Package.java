package net.sf.minuteProject.configuration.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.security.SecurityColor;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.model.data.Table;

public class Package extends AbstractConfiguration{

	private List<Table> listOfTables;
	private List<View> listOfViews;
	private String name;
	private BusinessPackage businessPackage;
	private FunctionPackage functionPackage;
	private List<Function> listOfFunctions;
	
	private SecurityColor securityColor;
	private String alias;

	public List<View> getListOfViews() {
		if (listOfViews==null) listOfViews = new ArrayList<View>();
		return listOfViews;
	}
	
	public void addView(View view) {
		if (listOfViews==null)
			listOfViews = new ArrayList<View>();
		view.setPackage(this);
		listOfViews.add(view);
	}
	
	public List<net.sf.minuteProject.configuration.bean.model.data.Table> getListOfTables() {
		if (listOfTables==null) listOfTables = new ArrayList<Table>();
		return listOfTables;
	}
	
	public void addTable(net.sf.minuteProject.configuration.bean.model.data.Table table) {
		if (listOfTables==null)
			listOfTables = new ArrayList<Table>();
		table.setPackage(this);
		listOfTables.add(table);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public FunctionPackage getFunctionPackage() {
		return functionPackage;
	}

	public void setFunctionPackage(FunctionPackage functionPackage) {
		this.functionPackage = functionPackage;
	}

	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}
	public void setBusinessPackage(BusinessPackage businessPackage) {
		this.businessPackage = businessPackage;
	}
	
	public String getTechnicalPackage(Template template) {
		StringBuffer sb = new StringBuffer();
//		if (template.getAddModelDirName()!=null && template.getAddModelDirName().equals("false"))
		if (template.getAddTechnicalDirName()!=null && template.getAddTechnicalDirName().equals("false")) {
//			return getName();
			sb.append(template.getPackageRoot());
		}
		else
//		StringBuffer sb = new StringBuffer(getBusinessPackage().getBusinessModel().getModel().getTechnicalPackage(template));
			sb.append(getTechPackage(template));
		if (template.getAddBusinessPackageDirName()!=null && template.getAddBusinessPackageDirName().equals("false")) {}
		else {
			String name = getName();
			if (name!=null && !name.equals(""))
				sb.append("."+name);
		}
		return sb.toString();
	}

	private String getTechPackage(Template template) {
		if (getBusinessPackage()!=null) return getBusinessPackage().getBusinessModel().getModel().getTechnicalPackage(template);
		if (getFunctionPackage()!=null) return getFunctionPackage().getFunctionModel().getModel().getTechnicalPackage(template);
		return "";
	}

	public SecurityColor getSecurityColor() {
		return securityColor;
	}

	public void setSecurityColor(SecurityColor securityColor) {
		this.securityColor = securityColor;
	}

	public String getAlias() {
		if (alias==null)
			alias = getName();
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void addFunction(Function function) {
		if (listOfFunctions==null)
			listOfFunctions = new ArrayList<Function>();
		function.setPackage(this);
		listOfFunctions.add(function);
	}
	
	
	
}
