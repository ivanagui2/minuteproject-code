package net.sf.minuteProject.utils.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import net.sf.minuteProject.configuration.bean.enrichment.validation.EntityValidationTwoFieldDependency;
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
	
	@Test
	public void testEntityValidationTwoFieldDependency() {
		EntityValidationTwoFieldDependency evtfd = new EntityValidationTwoFieldDependency();
		evtfd.setFirstFieldName("ageMin");
		evtfd.setSecondFieldName("ageMax");
		evtfd.setOperand("GREATER_THAN");
		List<String> javaValidationAnnotations = ValidationUtils.getJavaValidationAnnotations(evtfd);
		//assertThat(javaValidationAnnotations, hasSize(1));
		assertTrue("expected size = 1, but current = "+javaValidationAnnotations.size(), javaValidationAnnotations.size()==1);
		//error runtime java.lang.NoSuchMethodError: org.hamcrest.Matcher.describeMismatch(Ljava/lang/Object;Lorg/hamcrest/Description;)
		//assertThat(javaValidationAnnotations.get(0), is(equalTo("@Pattern (regexp=\"red|green|blue\"")));
		String regex = "@FieldCompare (first=\"ageMin\", second=\"ageMax\", operator=CompareOperatorEnum.GREATER_THAN)";
		assertTrue("expect "+regex+"\nbut got:\n"+javaValidationAnnotations.get(0), javaValidationAnnotations.get(0).equals(regex));
	}
}
