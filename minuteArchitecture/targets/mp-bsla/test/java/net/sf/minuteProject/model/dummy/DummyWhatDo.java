package net.sf.minuteProject.model.dummy;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhat;
import net.sf.minuteProject.model.data.criteria.function.IntegerFunction;

public class DummyWhatDo extends AbstractDomainObjectWhat{

	public DummyWhatDo() {
		super("dummyDo");
	}

	private IntegerFunction salary;

	public IntegerFunction getSalary() {
		//salary = new IntegerFunction(getWdoc().getWfc("salary"));
		return salary;
	}

	public IntegerFunction setSalary() {
		salary = new IntegerFunction(getWdoc().getWfc("salary"));
		return salary;
	}
	
}
