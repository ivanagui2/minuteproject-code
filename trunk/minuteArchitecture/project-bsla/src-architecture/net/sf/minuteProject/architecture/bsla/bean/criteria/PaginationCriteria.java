package net.sf.minuteProject.architecture.bsla.bean.criteria;

import java.util.List;

import net.sf.minuteProject.architecture.bsla.domain.DomainObject;

public class PaginationCriteria {

	private int startPosition;
	private int numberOfRowsReturned;
	private String direction;
	private List orderList;
	private DomainObject searchCriteria;
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getNumberOfRowsReturned() {
		return numberOfRowsReturned;
	}
	public void setNumberOfRowsReturned(int numberOfRowsReturned) {
		this.numberOfRowsReturned = numberOfRowsReturned;
	}
	public List getOrderList() {
		return orderList;
	}
	public void setOrderList(List orderList) {
		this.orderList = orderList;
	}
	public DomainObject getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(DomainObject searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public int getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	
	
}
