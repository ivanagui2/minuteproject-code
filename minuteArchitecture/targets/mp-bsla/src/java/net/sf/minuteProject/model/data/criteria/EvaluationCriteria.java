package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.CriteriaCollector;
import net.sf.minuteProject.model.data.criteria.collector.WhereCollector;
import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class EvaluationCriteria <T> implements Criteria {

	private T t;
	protected WhereFieldCollector wfc;
	
	public EvaluationCriteria(T t, WhereFieldCollector wfc) {
		this.t = t;
		this.wfc = wfc;
	}
	
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
