package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;

@Data
public class ValidationPattern extends ValidationBean implements FieldValidation{

	private String type;
	private String regex;
	
}
