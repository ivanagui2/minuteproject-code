package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;

@Data
public class ValidationPattern extends ValidationBean implements FieldValidation{

	private String type;
	private String regex;
	
	public enum ValidationType {
		EMAIL
	}
	
	public ValidationType getValidationType() {
		if (type==null) return null;
		for(ValidationType vt : ValidationType.values()) {
			if (type.equalsIgnoreCase(vt.name())) {
				return vt;
			}
		}
		return null;
	}
	
}
