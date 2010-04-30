package net.sf.minuteProject.model.dummy;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhere;
import net.sf.minuteProject.model.data.criteria.type.DateCriteria;

public class DummyDo extends AbstractDomainObjectWhere {

	public DummyDo() {
		super();
		setBeanPath("dummyDo"); //entity name
	}

	private DateCriteria birthdate;// = new DateCriteria();

	public DateCriteria getBirthdate() {
		birthdate = new DateCriteria(wdoc.getWfc());
		return birthdate;
	}

//	public void setBirthdate(DateCriteria birthdate) {
//		this.birthdate = birthdate;
//	}
	
	
}
