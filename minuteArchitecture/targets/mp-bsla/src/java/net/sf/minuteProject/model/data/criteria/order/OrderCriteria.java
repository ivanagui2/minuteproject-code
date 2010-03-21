package net.sf.minuteProject.model.data.criteria.order;

public class OrderCriteria {

	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	
	private String order;
	
	public void ascending() {
		order = ASC;
	}
	
	public void descending() {
		order = DESC;
	}

	public String getOrder() {
		return order;
	}
	
}
