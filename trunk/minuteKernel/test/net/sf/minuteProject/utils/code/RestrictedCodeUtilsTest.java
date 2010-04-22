package net.sf.minuteProject.utils.code;

import junit.framework.TestCase;

public class RestrictedCodeUtilsTest extends TestCase {

	public static final String EXPECTED_RESULT = "Narrative__________Pa_rt_s___and_A___________nnexes_wro__ng__def__i_________ned__";
	public static final String INPUT_VALUE = "Narrative#$,,,.... Pa.rt.s #$and A^^&^$##@*&^nnexes_wro,,ng,,def,.i,.!@!#$#@ned.,";

	public void testConvertToValidJava() {
		assertEquals(RestrictedCodeUtils.convertToValidJava(INPUT_VALUE),
				EXPECTED_RESULT);
	}
	
	public void testConvertToValidJavaWithUpperCase() {
		assertEquals(RestrictedCodeUtils.convertToValidJavaWithUpperCase(INPUT_VALUE),
				EXPECTED_RESULT.toUpperCase());
	}
		

}
