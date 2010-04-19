package net.sf.minuteProject.model.data.criteria;

public class EvaluationCriteria <T> implements Criteria {

	private T t;
	
	public void eq(T t) {
		// with template
		this.t = t;
	}
	
	public void in (T t) {
		
	}
//
//	public void in (SubQuery list<Pk>) { // subquery 4 businessInjection (duplicate)...
//		
//	}
}
