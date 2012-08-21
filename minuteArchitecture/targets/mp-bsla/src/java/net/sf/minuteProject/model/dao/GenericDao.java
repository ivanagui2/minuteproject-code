package net.sf.minuteProject.model.dao;

import net.sf.minuteProject.model.data.criteria.QueryData;

public interface GenericDao <T> {

	
	/**
	 * Find persistence data according to search criteria
	 * @param queryData
	 */
	public void find (QueryData<T> queryData);
	
}
