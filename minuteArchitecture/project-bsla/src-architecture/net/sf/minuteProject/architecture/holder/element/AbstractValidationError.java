package net.sf.minuteProject.architecture.holder.element;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class AbstractValidationError implements ValidationError{
	
	protected String errorString, path;
	protected Object errorObject, errorValue, acceptedValue;
	
	public AbstractValidationError (){}
	
	public AbstractValidationError (String errorString, Object errorObject, Object errorValue, Object acceptedValue){
		this.errorString = errorString;
		this.errorObject = errorObject;
		this.errorValue = errorValue;
		this.acceptedValue = acceptedValue;
	}
	
	public AbstractValidationError (String errorString, Object errorObject) {
		this.errorString = errorString;
		this.errorObject = errorObject;		
	}

	public String toString() {
		ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
	 	return  ToStringBuilder.reflectionToString(this);
	} 
	
	public Object getAcceptedValue() {
		return acceptedValue;
	}

	public void setAcceptedValue(Object acceptedValue) {
		this.acceptedValue = acceptedValue;
	}

	public Object getErrorObject() {
		return errorObject;
	}

	public void setErrorObject(Object errorObject) {
		this.errorObject = errorObject;
	}

	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	public Object getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(Object errorValue) {
		this.errorValue = errorValue;
	}

	public abstract String getMessage() ;


}
