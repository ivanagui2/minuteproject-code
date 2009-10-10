package net.sf.minuteProject.architecture.holder.element;

public interface ValidationError {
	
	public String getMessage();
	
	public String getErrorString ();
	
	public String getErrorField ();
	
	public Object getErrorObject();
	
	public String getPath();
}
