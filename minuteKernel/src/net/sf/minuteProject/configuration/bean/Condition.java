package net.sf.minuteProject.configuration.bean;

import org.apache.commons.lang.StringUtils;

public class Condition extends AbstractConfiguration {
	private String type;
	private String startsWith;
	private String regex;
	private String result;
	
	public boolean isConditionTrue (String valueToTest) {
		//TODO reformat to be more flexible
		if (type.equals("exclude") && startsWith!=null){
			return !net.sf.minuteProject.utils.StringUtils.startsWithIgnoreCase(valueToTest, startsWith);
		}
		if (type.equals("package") && startsWith!=null) {
			return net.sf.minuteProject.utils.StringUtils.startsWithIgnoreCase(valueToTest, startsWith);
		}
		if (type.equals("include") && startsWith!=null){
			return net.sf.minuteProject.utils.StringUtils.startsWithIgnoreCase(valueToTest, startsWith);
		}
		return false;
	}
	
	public String getConditionResult(String valueToTest) {
		if (isConditionTrue(valueToTest))
			return result;
		return null;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getStartsWith() {
		return startsWith;
	}
	public void setStartsWith(String startsWith) {
		this.startsWith = startsWith;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
