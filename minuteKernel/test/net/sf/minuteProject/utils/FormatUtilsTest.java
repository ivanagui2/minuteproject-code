package net.sf.minuteProject.utils;

import junit.framework.TestCase;

public class FormatUtilsTest extends TestCase {

	private String name1 = "this is a test";
	private String shortNameName1 = "ThisIsATest";

	private String name2 = "this-is another-test";
	private String shortNameName2 = "ThisIsAnotherTest";

	private String expressionToTrim1 = "____bbbbbbbasdasd____dasdsa___daddddddsd__";
	private String expressionToTrim2 = "123456789012345678901234567890----";
	private String expressionToTrim2Assert = "123456789012345678901234567890";
	
	private final String getterSetterClassName = "TSecUser";
	private final String getterSetterClassName2 = "TtSecUser";
	private final String getterSetterVariableName2 = "ttSecUser";
	private final String getterSetterClassName3 = "t";
	private final String getterSetterVariableName3 = "t";
	
	public void testGetterSetterVariable () {
		String s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName);
		assertTrue ("error '"+s+"' should be equal to "+getterSetterClassName,s.equals(getterSetterClassName));
		s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName2);
		assertTrue ("error '"+s+"' should be equal to "+getterSetterClassName,s.equals(getterSetterVariableName2));
		s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName3);
		assertTrue ("error '"+s+"' should be equal to "+getterSetterClassName,s.equals(getterSetterVariableName3));
	}
	public void testFirstUpperCaseOnly () {
		String s = FormatUtils.firstUpperCaseOnly("s");
		assertTrue ("S".equals(s));
		s = FormatUtils.firstUpperCaseOnly("PRODUCTID");
		assertTrue ("Productid".equals(s));
	}	

	public void testGetShortNameFromVerbose() {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		assertTrue(resultName1, resultName1.equals(shortNameName1));

		String resultName2 = FormatUtils.getShortNameFromVerbose(name2);
		assertTrue(resultName2, resultName2.equals(shortNameName2));
	}

	public void testGetEachWordFirstLetterUpper() {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		assertTrue(resultName1, resultName1.equals(shortNameName1));
	}

	public void testEliminateMultipleSequenceOfChar() {
		String result = FormatUtils.eliminateMultipleSequenceOfChar(expressionToTrim1, '_', 'b', 'd');
		assertTrue(result.equals("_basdasd_dasdsa_dadsd_"));
		result = FormatUtils.trimExpression(result, "_");
		assertTrue(result.equals("basdasd_dasdsa_dadsd"));
	}


	public void testStripToSizeRemovingBackend () {
		String result = FormatUtils.stripToSizeRemovingLeft(expressionToTrim2, 30);
		assertTrue (result.equals(expressionToTrim2Assert));
	}
}
