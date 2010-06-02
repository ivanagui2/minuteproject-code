package net.sf.minuteProject.model.data.criteria.order;

public class OrderCriteria {

	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final String NA = "NA";
	
	private String order;
	
	
	public OrderCriteria() {
		order = NA;
	}
	
	public OrderCriteria(String order) {
		this.order = order;
	}
	
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
