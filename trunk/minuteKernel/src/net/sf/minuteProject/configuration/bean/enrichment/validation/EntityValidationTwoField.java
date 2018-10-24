package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityValidationTwoField implements EntityValidation {

	private String firstFieldName;
	private String secondFieldName;
}
