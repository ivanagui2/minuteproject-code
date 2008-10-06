package net.sf.minuteProject.architecture.holder.element;

public class AbstractValidationError implements ValidationError{
	
	private String errorString;
	private Object errorObject, errorValue, acceptedValue;
	
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

	
	
}
