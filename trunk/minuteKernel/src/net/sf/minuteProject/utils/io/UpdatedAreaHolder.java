package net.sf.minuteProject.utils.io;

public class UpdatedAreaHolder {
	
	private String beginSnippet, endSnippet;
	private boolean isUpdated;
	
	public UpdatedAreaHolder(){}
	public String getBeginSnippet() {
		return beginSnippet;
	}
	public void setBeginSnippet(String beginSnippet) {
		this.beginSnippet = beginSnippet;
	}
	public boolean isUpdated() {
		return isUpdated;
	}
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	public String getEndSnippet() {
		return endSnippet;
	}
	public void setEndSnippet(String endSnippet) {
		this.endSnippet = endSnippet;
	} 
	
}
