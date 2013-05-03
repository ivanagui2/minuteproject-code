package net.sf.minuteProject.model.dao;

import java.util.List;

import net.sf.minuteProject.model.data.criteria.EntityCriteria;
import net.sf.minuteProject.model.data.criteria.EntitySort;
import net.sf.minuteProject.model.data.criteria.QueryData;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;

public abstract class GenericDaoImpl <T> implements GenericDao<T> {

	@Override
	public void find (QueryData<T> data) {
		EntityCriteria<T> filter = data.getEntityCriteria();
		T criteriaMask = filter.getEntity();
		int start = data.getStart();
		int max = data.getMax();
		EntitySort<T> entitySort = data.getEntitySort();
		QuerySortOrder sortOrder = entitySort.getOrder();
		T sortMask = entitySort.getEntity();
		
		data.setResult(find(criteriaMask, sortMask, filter.getMatchType(), filter.getOperandType(), filter.getCaseSensitivenessType(), sortOrder, start, max));
		Long totalResultCount = count(criteriaMask, filter.getMatchType(), filter.getOperandType(), filter.getCaseSensitivenessType());
		data.setTotalResultCount(totalResultCount);
	}
	

	protected abstract Long count(T criteriaMask, EntityMatchType matchType,
			OperandType operandType, Boolean caseSensitivenessType) ;


	protected abstract List<T> find(T criteriaMask, T sortMask, EntityMatchType matchType,
			OperandType operandType, Boolean caseSensitivenessType,
			QuerySortOrder sortOrder, int start, int max) ;


	@Override
	public void getList(T mask, T sortMask, QuerySortOrder order) {
		// TODO Auto-generated method stub
		
	}

}
