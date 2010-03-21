package net.sf.minuteProject.model.data.criteria.type;

import net.sf.minuteProject.model.data.criteria.EvaluationCriteria;

public class StringCriteria extends EvaluationCriteria{

	public static final String CONTAINS   = "CONTAINS";
	public static final String STARTSWITH = "STARTSWITH";
	public static final String ENDSWITH   = "ENDSWITH";
	public static final String LIKE       = "LIKE";
	
	private String value;
	private String method;
	
	public void contains(String value) {
		setValueAndMethod (value, CONTAINS);
	}
	
	public void startsWith(String value) {
		setValueAndMethod (value, STARTSWITH);
	}
	
	public void endsWith(String value) {
		setValueAndMethod (value, ENDSWITH);	
	}
	
	public void like(String value) {
		setValueAndMethod (value, LIKE);	
	}
	
	private void setValueAndMethod(String value, String method) {
		this.value = value;
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public String getValue() {
		return value;
	}
	
	
}
