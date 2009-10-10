package net.sf.minuteProject.architecture.holder;

import net.sf.minuteProject.architecture.holder.element.ValidationError;

public class ResultHolder {

	private ValidationHolder validationHolder;
	private Object object;

	public void addValidationError (ValidationError validationError) {
		getValidationHolder().add(validationError);
	}
	public Object getObject() {
		return object;
	}
	public ResultHolder putObject(Object object) {
		this.setObject(object);
		return this;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}

	public ValidationHolder getValidationHolder() {
		if (validationHolder==null)
			validationHolder = new ValidationHolder();
		return validationHolder;
	}

	public void setValidationHolder(ValidationHolder validationHolder) {
		this.validationHolder = validationHolder;
	}
	
	
	
}
