package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryParam extends QueryAdapter {

	private boolean isMandatory;
	private String type, sample;
	private int size, scale;
	private QueryParamOptionalSections queryParamOptionalSections;
	
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getSample() {
		return sample;
	}
	public void setSample(String sample) {
		this.sample = sample;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	
}
