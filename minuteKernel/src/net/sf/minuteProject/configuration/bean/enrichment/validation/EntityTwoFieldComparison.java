package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityTwoFieldComparison extends EntityValidationTwoField {
	
	private String operand; //GREATER_THAN, ...
	
}
