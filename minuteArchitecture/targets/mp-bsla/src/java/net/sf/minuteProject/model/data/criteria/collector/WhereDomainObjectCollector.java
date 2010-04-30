package net.sf.minuteProject.model.data.criteria.collector;

import java.util.Enumeration;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhere;
import net.sf.minuteProject.model.data.criteria.holder.WhereHolder;

public class WhereDomainObjectCollector extends WhereCollector<AbstractDomainObjectWhere>{

	private WhereFieldCollector wfc;
	
	public WhereDomainObjectCollector (String entityPath) {
		wfc = new WhereFieldCollector();
	}

	public WhereFieldCollector getWfc() {
		return wfc;
	}

	public void setWfc(WhereFieldCollector wfc) {
		this.wfc = wfc;
	}
	
	public String popToString () {
		StringBuffer sb = new StringBuffer();
		System.out.println("wfc ref3 = "+wfc);
		WhereHolder wh = wfc.getElements();
//		while (e.hasMoreElements()) {
//			sb.append(e.nextElement());
//		}
		return wh.toString();
	}
}
