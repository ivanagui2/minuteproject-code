package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.WhatFieldCollector;

public class DigitFunctionCriteria <T> extends FunctionCriteria<T> {

	public static final String AVG 			= "AVG";
	public static final String MAX	 		= "MAX";
	public static final String MIN  		= "MIN";
//	public static final String COUNT 		= "COUNT";

	public DigitFunctionCriteria(WhatFieldCollector wfc) {
		super(wfc);
	}
	
	public WhatFieldCollector avg() {
		return addElement(AVG);
	}
	
	public WhatFieldCollector max () {
		return addElement(MAX);
	}
	
	public WhatFieldCollector min () {
		return addElement(MIN);
	}
	
//	public WhatFieldCollector count () {
//		return addElement(COUNT);
//	}
//	
	
}
