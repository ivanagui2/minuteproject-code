package net.sf.minuteProject.model.dummy;

import java.util.Date;
import java.util.List;

import net.sf.minuteProject.model.data.criteria.order.OrderCriteria;

public class DummyApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DummyDo dummyDo = new DummyDo();

		dummyDo.getBirthdate().between(new Date(), new Date());
		dummyDo.getBirthdate().between(new Date(), new Date());
		dummyDo.getBirthdate().lt(new Date());
//		Or(dummyDo.getBirthdate().lt(new Date()));
		dummyDo.or(dummyDo.getBirthdate().le(new Date()));
		dummyDo.or(dummyDo.getBirthdate().le(new Date()).
				and(dummyDo.getBirthdate().gt(new Date())).
				and(dummyDo.getBirthdate().ge(new Date())));
		String s = dummyDo.popWhereCriteria();
		

		System.out.println(">>>\n"+s);
		System.out.println(">>> TODO algo to correctly depile the stack: reverse polish notation!!!");
		
		DummyWhatDo dummyWhatDo = new DummyWhatDo();
		dummyWhatDo.setSalary().mask().order(OrderCriteria.ASC);
		dummyWhatDo.setSalary().avg();		
		dummyWhatDo.count();
		
		System.out.println(">>>\n"+dummyWhatDo.popWhatCriteria());
		
		List<DummyWhatDo> list = getDummyWhatDo (dummyWhatDo, dummyDo);
		
		for (DummyWhatDo dummyWhatDo2 : list) {
			// we know the output type.
			dummyWhatDo2.getSalary().value();
//			dummyWhatDo2.getSalary().avg().value();
			
		}
	}

	private static List<DummyWhatDo> getDummyWhatDo(DummyWhatDo dummyWhatDo,
			DummyDo dummyDo) {
		// TODO Auto-generated method stub
		return null;
	}

}
