package net.sf.minuteProject.model.dummy;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhere;
import net.sf.minuteProject.model.data.criteria.type.DateCriteria;

public class DummyDo extends AbstractDomainObjectWhere {

	public DummyDo() {
		super("dummyDo");
		setBeanPath("dummyDo"); //entity name
	}

	private DateCriteria birthdate;// = new DateCriteria();

	public DateCriteria getBirthdate() {
		birthdate = new DateCriteria(wdoc.getWfc("birthdate"));
		return birthdate;
	}

//	public void setBirthdate(DateCriteria birthdate) {
//		this.birthdate = birthdate;
//	}
	
	
}
