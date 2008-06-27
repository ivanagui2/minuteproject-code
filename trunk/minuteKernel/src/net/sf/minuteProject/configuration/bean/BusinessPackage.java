package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.DBTemplateUtils;
import net.sf.minuteProject.utils.ModelUtils;

public class BusinessPackage extends AbstractConfiguration {
	
	private BusinessModel businessModel;
	private String defaultPackage;
	private List <Condition> conditions;
	private List packages;
	private List tables;
	private List packageViews;
	private List views;

	private List packageServices;
	private List services;
	
	void setPackageServices (Model model, Database database) {
		packageServices = new ArrayList();
		Hashtable ht = new Hashtable();
		View [] views = database.getViews();
		for (int i = 0; i < views.length; i++) {
			View view = views[i];
			view.setDatabase(database);
			if (ModelUtils.isToGenerate(businessModel, view)) {
				String packageName = CommonUtils.getBusinessPackageName(model, view);
				Package pack = (Package)ht.get(packageName);
				if (pack==null) {
					pack = new Package();
					pack.setBusinessPackage(this);
					pack.setName(packageName);
				}
				pack.addView(view);
				ht.put(packageName, pack);
			}
		}
		Enumeration enumeration = ht.elements();
		while (enumeration.hasMoreElements()) {
			packageServices.add(enumeration.nextElement());
		}
	}
	
	public List getServices () {
		if (views == null) {
			views = new ArrayList();
			for (Iterator<Package> iter = packageServices.iterator(); iter.hasNext();) {
				for (Iterator<Table> iter2 = ((Package)iter.next()).getListOfViews().iterator(); iter2.hasNext(); ){
					views.add(iter2.next());
				}
			}
		}
		return views;
	}

	void setPackageViews (Model model, Database database) {
		packageViews = new ArrayList();
		Hashtable ht = new Hashtable();
		View [] views = database.getViews();
		for (int i = 0; i < views.length; i++) {
			View view = views[i];
			view.setDatabase(database);
			if (ModelUtils.isToGenerate(businessModel, view)) {
				String packageName = CommonUtils.getBusinessPackageName(model, view);
				Package pack = (Package)ht.get(packageName);
				if (pack==null) {
					pack = new Package();
					pack.setBusinessPackage(this);
					pack.setName(packageName);
				}
				pack.addView(view);
				ht.put(packageName, pack);
			}
		}
		Enumeration enumeration = ht.elements();
		while (enumeration.hasMoreElements()) {
			packageViews.add(enumeration.nextElement());
		}
	}
	
	public List getViews () {
		if (views == null) {
			views = new ArrayList();
			for (Iterator<Package> iter = packageViews.iterator(); iter.hasNext();) {
				for (Iterator<Table> iter2 = ((Package)iter.next()).getListOfViews().iterator(); iter2.hasNext(); ){
					views.add(iter2.next());
				}
			}
		}
		return views;
	}
	
	void setPackages (Model model, Database database) {
		packages = new ArrayList();
		Hashtable ht = new Hashtable();
		Table [] tables = database.getTables();
		for (int i = 0; i < tables.length; i++) {
			Table table = tables[i];
			table.setDatabase(database);
			if (ModelUtils.isToGenerate(businessModel, table)) {
				String packageName = CommonUtils.getBusinessPackageName(model, table);
				Package pack = (Package)ht.get(packageName);
				if (pack==null) {
					pack = new Package();
					pack.setBusinessPackage(this);
					pack.setName(packageName);
				}
				pack.addTable(table);
				ht.put(packageName, pack);
			}
		}
		Enumeration enumeration = ht.elements();
		while (enumeration.hasMoreElements()) {
			packages.add(enumeration.nextElement());
		}
	}
	
	public List getTables () {
		if (tables == null) {
			tables = new ArrayList();
			for (Iterator<Package> iter = packages.iterator(); iter.hasNext();) {
				for (Iterator<Table> iter2 = ((Package)iter.next()).getListOfTables().iterator(); iter2.hasNext(); ){
					tables.add(iter2.next());
				}
			}
		}
		return tables;
	}
	
	public List getPackages() {
		return packages;
	}

	public List getPackageViews() {
		return packageViews;
	}

	public void addCondition (Condition condition) {
		if (conditions==null)
			conditions = new ArrayList<Condition>();
		conditions.add(condition);
	}

	public List<Condition> getConditions() {
		return conditions;
	}
	public String getPackage(String value) {
		return getConditionsResult(value);
	}
	private String getConditionsResult(String valueToTest) {
		for (Iterator iter = conditions.iterator(); iter.hasNext();){
			Condition condition = (Condition)iter.next();
			if (condition.getConditionResult(valueToTest)!=null) {
				return condition.getResult();
			}
		}
		return defaultPackage;
	}

	public String getDefaultPackage() {
		return defaultPackage;
	}

	public void setDefaultPackage(String defaultPackage) {
		this.defaultPackage = defaultPackage;
	}

	public BusinessModel getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(BusinessModel businessModel) {
		this.businessModel = businessModel;
	}	
}
