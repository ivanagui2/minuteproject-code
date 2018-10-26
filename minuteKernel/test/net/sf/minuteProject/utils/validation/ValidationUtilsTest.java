package net.sf.minuteProject.utils.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationAmongValue;

public class ValidationUtilsTest {

	@Test
	public void testAnnotations() {
		FieldValidationAmongValue fvav = new FieldValidationAmongValue();
		fvav.setValues("red,green,blue");
		List<String> javaValidationAnnotations = ValidationUtils.getJavaValidationAnnotations(fvav);
		assertThat(javaValidationAnnotations, hasSize(1));
		//error runtime java.lang.NoSuchMethodError: org.hamcrest.Matcher.describeMismatch(Ljava/lang/Object;Lorg/hamcrest/Description;)
		//assertThat(javaValidationAnnotations.get(0), is(equalTo("@Pattern (regexp=\"red|green|blue\"")));
		String regex = "@Pattern (regexp=\"red|green|blue\", flags=Pattern.Flag.CASE_INSENSITIVE)";
		assertTrue("expect "+regex+"\nbut got:\n"+javaValidationAnnotations.get(0), javaValidationAnnotations.get(0).equals(regex));
	}
}
