package net.sf.minuteProject.configuration.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.security.SecurityColor;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.model.data.Table;

public class Package extends AbstractConfiguration{

	private List<Table> listOfTables;
	private List<View> listOfViews;
	private String name;
	private BusinessPackage businessPackage;
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
			sb.append(getBusinessPackage().getBusinessModel().getModel().getTechnicalPackage(template));
		if (template.getAddBusinessPackageDirName()!=null && template.getAddBusinessPackageDirName().equals("false")) {}
		else {
			String name = getName();
			if (name!=null && !name.equals(""))
				sb.append("."+name);
		}
		return sb.toString();
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
	
	
	
}
