package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.enrichment.Field;

@Data
public class RangeValidation<T> extends ValidationBean implements Validation<Field> {
	
	private Range<T> range;

	private T min; 
	private T max;
	
}
