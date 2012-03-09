package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.group.Group;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.DBTemplateUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.TableUtils;

public class BusinessPackage extends BusinessPackageAdapter {

	protected BusinessModel businessModel;
	protected List<Package> packages, packageViews, packageTransferEntities;
	
	private List<Table> tables, entities, transferEntities;
	private List<View> views;	
	private List packageServices;
	private List services;

	void setPackageServices(Model model, Database database) {
		packageServices = new ArrayList();
		Hashtable<String, Package> ht = new Hashtable();
		View[] views = database.getViews();
		for (int i = 0; i < views.length; i++) {
			View view = views[i];
			view.setDatabase(database);
			store (ht, model, view);
//			if (ModelUtils.isToGenerate(businessModel, view)) {
//				String packageName = CommonUtils.getBusinessPackageName(model,	view);
//				Package pack = (Package) ht.get(packageName);
//				if (pack == null) {
//					pack = new Package();
//					pack.setBusinessPackage(this);
//					pack.setName(packageName);
//				}
//				pack.addView(view);
//				ht.put(packageName, pack);
//			}
		}
		Enumeration enumeration = ht.elements();
		while (enumeration.hasMoreElements()) {
			packageServices.add(enumeration.nextElement());
		}
	}

	public List<View> getServices() {
		return getViews();
	}

	void setPackageViews(Model model, Database database) {
		packageViews = new ArrayList<Package>();
		Hashtable<String, Package> ht = new Hashtable();
		View[] views = database.getViews();
		for (int i = 0; i < views.length; i++) {
			View view = views[i];
			view.setDatabase(database);
			store (ht, model, view);
//			if (ModelUtils.isToGenerate(businessModel, view)) {
//				String packageName = CommonUtils.getBusinessPackageName(model, view);
//				Package pack = (Package) ht.get(packageName);
//				if (pack == null) {
//					pack = new Package();
//					pack.setBusinessPackage(this);
//					pack.setName(packageName);
//				}
//				pack.addView(view);
//				ht.put(packageName, pack);
//			}
		}
		Enumeration<Package> enumeration = ht.elements();
		while (enumeration.hasMoreElements()) {
			packageViews.add(enumeration.nextElement());
		}
	}

	public List<View> getViews() {
		if (views == null || views.isEmpty()) {
			views = new ArrayList<View>();
			for (Iterator<Package> iter = getPackageViews().iterator(); iter.hasNext();) {
				for (Iterator<View> iter2 = ((Package) iter.next()).getListOfViews().iterator(); iter2.hasNext();) {
					views.add(iter2.next());
				}
			}
		}
		return views;
	}

	void setPackages(Model model, Database database) {
//		packages = new ArrayList<Package>();
		Hashtable<String, Package> ht = new Hashtable<String, Package>();
		Table[] tables = database.getTables();
		for (int i = 0; i < tables.length; i++) {
			Table table = tables[i];
			table.setDatabase(database);
			store (ht, model, table);
//			if (ModelUtils.isToGenerate(businessModel, table)) {
//				String packageName = CommonUtils.getBusinessPackageName(model, table);
//				Package pack = (Package) ht.get(packageName);
//				if (pack == null) {
//					pack = new Package();
//					pack.setBusinessPackage(this);
//					pack.setName(packageName);
//				}
//				pack.addTable(table);
//				ht.put(packageName, pack);
//			}
		}
		Enumeration<Package> enumeration = ht.elements();
		while (enumeration.hasMoreElements()) {
			getPackages().add(enumeration.nextElement());
		}
	}

	private void store (Hashtable<String, Package> ht, Model model, Table table) {
		if (ModelUtils.isToGenerate(businessModel, table)) {
			String packageName = CommonUtils.getBusinessPackageName(model, table);
			Package pack = (Package) ht.get(packageName);
			if (pack == null) {
				pack = new Package();
				pack.setBusinessPackage(this);
				pack.setName(packageName);
			}
			if (table.getType().equals(Table.TABLE))
				pack.addTable(table);
			if (table.getType().equals(Table.VIEW))
				pack.addView((View)table);			
			ht.put(packageName, pack);
		}		
	}
	
    public void addTransferEntity (Model model, Table table) {
    	getTransferEntities().add(table);
    	String packageName = CommonUtils.getBusinessPackageName(model, table);
    	boolean isTableAddedToPackage = false;
    	for (Package pack : getPackageTransferEntities()) {
    		if (pack.getName().equals(packageName)) {
    			fillTableIntoPackage (table, pack, packageName);
    			isTableAddedToPackage = true;
    		}
    	}
    	if (isTableAddedToPackage==false) {
    		Package pack = new Package();
    		fillTableIntoPackage (table, pack, packageName);
    		getPackageTransferEntities().add(pack);
    	}
    }
	
	private void fillTableIntoPackage(Table table, Package pack, String packageName) {
		table.setPackage(pack);
		pack.setName(packageName);
		pack.addTable(table);
		pack.setBusinessPackage(this);
	}

	public List<Table> getTables() {
		if (tables == null) {
			tables = new ArrayList<Table>();
			for (Iterator<Package> iter = getPackages().iterator(); iter.hasNext();) {
				for (Iterator<Table> iter2 = ((Package) iter.next()).getListOfTables().iterator(); iter2.hasNext();) {
					tables.add(iter2.next());
				}
			}
		}
		return tables;
	}

	public List<Table> getEntities() {
		if (entities==null) {
			entities = new ArrayList<Table>();
			entities.addAll(getTables());
			entities.addAll(getViews());
		}
		return entities;
	}
	
	public List<Table> refreshEntities() {
		List <Table> entities = new ArrayList<Table>();
		entities.addAll(getTables());
		entities.addAll(getViews());
		return entities;
	}
	
	public void resetEntities () {
		entities=null;
	}

	public Table[] getEntitiesArray() {
		List<Table> tables = getEntities();
		return (Table[]) tables.toArray(new Table[tables.size()]);
	}
	
	public List<Table> getTransferEntities() {
		if (transferEntities==null) transferEntities = new ArrayList<Table>();
		return transferEntities;
	}
	

	public void addTransferEntity(Table transferEntity) {
		getTransferEntities().add(transferEntity);
	}
//	public List<Package> getPackages() {
//		if (packages == null)
//			packages = new ArrayList<Package>();
//		return packages;
//	}
//
//	public List<Package> getPackageViews() {
//		if (packageViews == null)
//			packageViews = new ArrayList<Package>();
//		return packageViews;
//	}
//
//	public void addCondition(Condition condition) {
//		if (conditions == null)
//			conditions = new ArrayList<Condition>();
//		conditions.add(condition);
//	}
//
//	public List<Condition> getConditions() {
//		return conditions;
//	}
//
//	public void setConditions(List<Condition> conditions) {
//		this.conditions = conditions;
//	}
//	
//	public String getPackage(String value) {
//		return getConditionsResult(value);
//	}
//
//	private String getConditionsResult(String valueToTest) {
//		if (conditions!=null) {
//			for (Condition condition : conditions) {
//				if (condition.getConditionResult(valueToTest) != null) {
//					return condition.getResult();
//				}
//			}
//		}
//		return getDefaultPackage();
//	}
//
//	public String getDefaultPackage() {
//		if (defaultPackage==null)
//			return businessModel.getModel().getName();
//		return defaultPackage;
//	}	
//	
//	public List<Table> getTransferEntities() {
//		if (transferEntities==null) transferEntities = new ArrayList<Table>();
//		return transferEntities;
//	}
//
//	public void addTransferEntity(Table transferEntity) {
//		getTransferEntities().add(transferEntity);
//	}
//
//	public List<Package> getPackageTransferEntities() {
//		if (packageTransferEntities==null) packageTransferEntities = new ArrayList<Package>();
//		return packageTransferEntities;
//	}
//
//
//	public void setDefaultPackage(String defaultPackage) {
//		this.defaultPackage = defaultPackage;
//	}
//
//	public BusinessModel getBusinessModel() {
//		return businessModel;
//	}
//
//	public void setBusinessModel(BusinessModel businessModel) {
//		this.businessModel = businessModel;
//	}
//
//	public String getAutoPackageType() {
//		return autoPackageType;
//	}
//
//	public void setAutoPackageType(String autoPackageType) {
//		this.autoPackageType = autoPackageType;
//	}
//
//	public String getDefaultPackageType() {
//		return defaultPackageType;
//	}
//
//	public void setDefaultPackageType(String defaultPackageType) {
//		this.defaultPackageType = defaultPackageType;
//	}
//	
	

	
	public List<Package> getPackages() {
		if (packages == null)
			packages = new ArrayList<Package>();
		return packages;
	}

	public List<Package> getPackageViews() {
		if (packageViews == null)
			packageViews = new ArrayList<Package>();
		return packageViews;
	}

	public String getDefaultPackage() {
		if (defaultPackage==null) {
			Model model = businessModel.getModel();
			if (model.isUsingDefaultName())
				return "defaultpackage";
			return businessModel.getModel().getName();
		}
		return defaultPackage;
	}	

	public List<Package> getPackageTransferEntities() {
		if (packageTransferEntities==null) packageTransferEntities = new ArrayList<Package>();
		return packageTransferEntities;
	}

//	@Override
//	protected List<GeneratorBean> convertGroupToElement(Group t) {
//		List<GeneratorBean> tables = new ArrayList<GeneratorBean>();
//		for (String element : t.getList()) {
//			Table table = TableUtils.getTable(getBusinessModel().getModel().getDataModel().getDatabase(), element);
//			if (table!=null)
//				tables.add(table);
//		}
//		return tables;
//	}
//
//	@Override
//	public List<GeneratorBean> getElements() {
//		return null;
//	}
	
	public BusinessModel getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(BusinessModel businessModel) {
		this.businessModel = businessModel;
	}

}
