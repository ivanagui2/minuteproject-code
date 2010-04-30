package net.sf.minuteProject.model.dummy;

import java.util.Date;

public class DummyApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DummyDo dummyDo = new DummyDo();
		dummyDo.getBirthdate().between(new Date(), new Date());
		String s = dummyDo.popWhereCriteria();
		System.out.println(">>"+s);
	}

}
