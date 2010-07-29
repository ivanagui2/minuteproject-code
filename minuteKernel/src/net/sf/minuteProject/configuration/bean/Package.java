package net.sf.minuteProject.configuration.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.security.SecurityColor;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;


public class Package extends AbstractConfiguration{

	private List listOfTables;
	private List listOfViews;
	private String name;
	private BusinessPackage businessPackage;
	private SecurityColor securityColor;

	public List getListOfViews() {
		return listOfViews;
	}
	
	public void addView(View view) {
		if (listOfViews==null)
			listOfViews = new ArrayList();
		view.setPackage(this);
		listOfViews.add(view);
	}
	
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
		else
			sb.append("."+getName());
		return sb.toString();
	}

	public SecurityColor getSecurityColor() {
		return securityColor;
	}

	public void setSecurityColor(SecurityColor securityColor) {
		this.securityColor = securityColor;
	}
	
	
}
