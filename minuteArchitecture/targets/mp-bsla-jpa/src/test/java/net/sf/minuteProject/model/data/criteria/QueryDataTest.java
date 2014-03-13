package net.sf.minuteProject.model.data.criteria;

import junit.framework.Assert;
import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;
import net.sf.minuteProject.model.data.criteria.order.OrderCriteria;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryDataTest {

	QueryData<DummyDo> queryData;
	QueryData<DummyDo> previousQueryData;
	private int start = 0;
	private int max = 10;
	DummyDo a = new DummyDo();
	DummyDo b = new DummyDo();
	EntitySort<DummyDo> entitySort;
	EntityCriteria<DummyDo> entityCriteria;
	
	@Before
	public void init() {
		entitySort = new EntitySort<DummyDo>(a, QuerySortOrder.ASC);
		entityCriteria = new EntityCriteria<DummyDo>(a, EntityMatchType.ALL, OperandType.EQUALS, true);
	}
	
	@Test
	public void testNeedsNewCount() {
		queryData = new QueryData<DummyDo>(start, max, entitySort, entityCriteria, a);
		previousQueryData = new QueryData<DummyDo>(20, 30, entitySort, entityCriteria, b);
		assertFalse (queryData.needsNewCount(previousQueryData));
	}
	
	private class DummyDo extends AbstractDomainObject{
		private String something;

		public String getSomething() {
			return something;
		}

		public void setSomething(String something) {
			this.something = something;
		}

		@Override
		public boolean equalsMask(Object object) {
			if (object == null) return false;	
			if (object == this) return true;
			if (!(object instanceof DummyDo)) return false;
			DummyDo dummyDo = (DummyDo) object;
			if (dummyDo.something==null || !dummyDo.something.equals(something)) return false;
			return true;
		}
		
	}
}
