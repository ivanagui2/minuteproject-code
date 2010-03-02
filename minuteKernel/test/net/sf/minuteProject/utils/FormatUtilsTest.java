package net.sf.minuteProject.utils;

import junit.framework.TestCase;

public class FormatUtilsTest extends TestCase{

	private String name1 = "this is a test";
	private String shortNameName1 = "ThisIsATest";
	
	private String name2 = "this-is another-test";
	private String shortNameName2 = "ThisIsAnotherTest";
	
	
	public void testGetShortNameFromVerbose () {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		assertTrue(resultName1, resultName1.equals(shortNameName1));
		
		String resultName2 = FormatUtils.getShortNameFromVerbose(name2);
		assertTrue(resultName2, resultName2.equals(shortNameName2));
	}
	
	public void testGetEachWordFirstLetterUpper () {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		assertTrue(resultName1, resultName1.equals(shortNameName1));		
	}
}
