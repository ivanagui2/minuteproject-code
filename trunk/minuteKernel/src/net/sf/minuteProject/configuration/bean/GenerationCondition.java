package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenerationCondition extends AbstractConfiguration {
	private String defaultType;
	private List<Condition> conditions;
	
	public void addCondition (Condition condition) {
		if (conditions==null)
			conditions = new ArrayList<Condition>();
		conditions.add(condition);
	}

	public List<Condition> getConditions() {
		if (conditions== null)
			conditions = new ArrayList<Condition>();
		return conditions;
	}
	
	public boolean isAddable (String valueToTest) {
		if (defaultType==null || !defaultType.equals("exclude"))
			return areConditionsTrueInclude(valueToTest);
		else
			return areConditionsTrueExclude(valueToTest);		
	}
	
	public boolean areConditionsTrue(String valueToTest) {
		if (defaultType==null || !defaultType.equals("exclude"))
			return areConditionsTrueInclude(valueToTest);
		else
			return areConditionsTrueExclude(valueToTest);
	}
	
	public boolean areConditionsTrueInclude(String valueToTest) {
		for (Iterator iter = getConditions().iterator(); iter.hasNext();){
			Condition condition = (Condition)iter.next();
			if (condition.getType().equals("exclude") && !condition.isConditionTrue(valueToTest))
				return false;
		}
		return true;
	}
	
	public boolean areConditionsTrueExclude(String valueToTest) {
		for (Iterator iter = getConditions().iterator(); iter.hasNext();){
			Condition condition = (Condition)iter.next();
			if (condition.getType().equals("include") && condition.isConditionTrue(valueToTest))
				return true;
		}
		return false;
	}

	

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}
	
	
}
