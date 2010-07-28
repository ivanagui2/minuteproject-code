package net.sf.minuteProject.utils.format;

import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.utils.FormatUtils;
import junit.framework.TestCase;

public class I18nUtilsTest extends TestCase {

	private static final String nameWithUnderscore = "TEST_TEST";
	private static final String nameWithoutUnderscore = "TESTTEST";
	private static final String test = "Test";
	private static final String testtest = "Testtest";
	
	public void testGetI18nFromDBNameStripPrefix () {
		String s = I18nUtils.getI18nFromDBNameStripPrefix(nameWithUnderscore);
		assertTrue("result = "+s+" while input = "+nameWithUnderscore,test.equals(s));
		s = I18nUtils.getI18nFromDBNameStripPrefix(nameWithoutUnderscore);
		assertTrue(nameWithoutUnderscore.equals(s));
		s = I18nUtils.getI18nFromDBNameStripPrefix(null);
	}
	
	
	public void testGetI18nFromDBNameStripSufix () {
		String s = I18nUtils.getI18nFromDBNameStripSufix(nameWithoutUnderscore);
		assertTrue("result = "+s+" while input = "+nameWithoutUnderscore, testtest.equals(s));
	}
}
