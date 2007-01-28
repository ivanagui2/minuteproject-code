package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.DBTemplateUtils;
import net.sf.minuteProject.utils.ModelUtils;

import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;

public class BusinessPackage extends AbstractConfiguration {
	
	private BusinessModel businessModel;
	private String defaultPackage;
	private List <Condition> conditions;
	private List packages;
	
	void setPackages (Model model, Database database) {
		packages = new ArrayList();
		Hashtable ht = new Hashtable();
		Table [] tables = database.getTables();
		for (int i = 0; i < tables.length; i++) {
			Table table = tables[i];
			if (ModelUtils.isToGenerate(businessModel, table)) {
				//String packageName = DBTemplateUtils.getSubPackage(table);
				String packageName = CommonUtils.getBusinessPackageName(model, table);
				Package pack = (Package)ht.get(packageName);
				if (pack==null) {
					pack = new Package();
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
	
	public List getPackages() {
		return packages;
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
