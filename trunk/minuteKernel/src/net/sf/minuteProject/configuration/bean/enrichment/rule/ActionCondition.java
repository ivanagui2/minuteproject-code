package net.sf.minuteProject.configuration.bean.enrichment.rule;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.Field;

public class ActionCondition {

	private Action action;
	
	private List<Field> fields;
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public List<Field> getFields() {
		if (fields == null) fields = new ArrayList<Field>();
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public void addField(Field field) {
		getFields().add(field);
	}
}
