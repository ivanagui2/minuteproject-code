package net.sf.minuteProject.architecture.holder.element;

public class ValidationModelError extends AbstractValidationError{
	public static final String LENGTH_TOO_HIGH = "LENGTH_TOO_HIGH";
	public static final String ABSENT = "LENGTH_TOO_HIGH";
	
	public ValidationModelError() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ValidationModelError(String errorString, Object errorObject, Object errorValue, Object acceptedValue) {
		super(errorString, errorObject, errorValue, acceptedValue);
		// TODO Auto-generated constructor stub
	}
	public ValidationModelError(String errorString, Object errorObject) {
		super(errorString, errorObject);
		// TODO Auto-generated constructor stub
	}
	
	
}
