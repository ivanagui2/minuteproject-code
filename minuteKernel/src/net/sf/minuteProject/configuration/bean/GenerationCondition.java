package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenerationCondition extends AbstractConfiguration {
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
	
	public boolean areConditionsTrue(String valueToTest) {
		for (Iterator iter = getConditions().iterator(); iter.hasNext();){
			Condition condition = (Condition)iter.next();
			if (!condition.isConditionTrue(valueToTest))
				return false;
		}
		return true;
	}
}
