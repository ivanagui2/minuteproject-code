package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class RangeCriteria <T> extends ComparisonCriteria<T> {

	public RangeCriteria (WhereFieldCollector wfc) {
		super(wfc);
	}

	public void between (T t1, T t2) {
//		cc.push("between");
//		wfc.addElement("testbeanpath", "field", "op", t1, t2);
		wfc.addElement("op", t1, t2);
//		System.out.println("addElement");
//		System.out.println("wfc ref1 = "+wfc);
	}
	
}
