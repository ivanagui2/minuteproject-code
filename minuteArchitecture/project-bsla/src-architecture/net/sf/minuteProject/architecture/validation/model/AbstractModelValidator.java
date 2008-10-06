package net.sf.minuteProject.architecture.validation.model;

import net.sf.minuteProject.architecture.holder.element.ValidationModelError;

public class AbstractModelValidator {
	
    protected ValidationModelError validateLength (String fieldName, String fieldValue, int acceptedLength) {
        int fieldLength = fieldValue.length();
    	if (fieldLength > acceptedLength )
           return new ValidationModelError(ValidationModelError.LENGTH_TOO_HIGH ,fieldName, new Integer(fieldLength), new Integer(acceptedLength));
        return null;
     }

    protected ValidationModelError validatePresence (String fieldName, Object fieldValue, boolean isTobePresent) {
        if (isTobePresent == true && fieldValue!=null)
           return new ValidationModelError(ValidationModelError.ABSENT ,fieldName);
        return null;
     }
}
