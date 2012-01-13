package net.sf.minuteProject.configuration.bean;

import org.apache.commons.lang.StringUtils;

public class Condition extends AbstractConfiguration {
	
	public static final String REGEX = "regex";
	public static final String ENDS_WITH = "endsWith";
	public static final String STARTS_WITH = "startsWith";
	public static final String INCLUDE = "include";
	public static final String PACKAGE = "package";
	public static final String EXCLUDE = "exclude";
	private String type;
	private String startsWith;
	private String endsWith;
	private String regex;
	private String result;

	public boolean isConditionTrue(String valueToTest) {
//		System.out.println("isConditionTrue");
		if (isConditionStartsWithTrue(valueToTest, startsWith)==true) return true;
		if (isConditionEndsWithTrue(valueToTest, endsWith)==true) return true;
		if (isConditionRegexTrue(valueToTest, regex)==true) return true;
		return false;
	}
	public boolean isConditionStartsWithTrue (String valueToTest, String pattern) {
		System.out.println("valueToTest "+valueToTest+" isConditionStartsWithTrue = "+"startsW "+startsWith+" result : "+isConditionTrue(valueToTest, STARTS_WITH, pattern));
		return isConditionTrue(valueToTest, STARTS_WITH, pattern);
	}
	public boolean isConditionEndsWithTrue (String valueToTest, String pattern) {
		System.out.println(">>>>>>valueToTest "+valueToTest+" isConditionEndsWithTrue = "+"endsW "+endsWith+" result : "+isConditionTrue(valueToTest, ENDS_WITH, pattern));
		
		return isConditionTrue(valueToTest, ENDS_WITH, pattern);
	}
	public boolean isConditionRegexTrue (String valueToTest, String pattern) {
		return isConditionTrue(valueToTest, REGEX, pattern);
	}
	public boolean isConditionTrue (String valueToTest, String expression, String pattern) {
//		if ((INCLUDE.equals(type)||EXCLUDE.equals(type)||PACKAGE.equals(type)) && expression!=null){
//			return net.sf.minuteProject.utils.StringUtils.checkExpression(valueToTest, expression);
//		}
		if (EXCLUDE.equals(type) && expression!=null){
			return !net.sf.minuteProject.utils.StringUtils.checkExpression(valueToTest, expression, pattern);
		}
		if (PACKAGE.equals(type) && expression!=null) {
			return net.sf.minuteProject.utils.StringUtils.checkExpression(valueToTest, expression, pattern);
		}
		if (INCLUDE.equals(type) && expression!=null){
			return net.sf.minuteProject.utils.StringUtils.checkExpression(valueToTest, expression, pattern);
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
	public String getEndsWith() {
		return endsWith;
	}
	public void setEndsWith(String endsWith) {
		this.endsWith = endsWith;
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
