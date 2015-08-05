package net.sf.minuteProject.configuration.bean.model.statement;

import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.exception.MinuteProjectException;

public class CmisQueryModel extends QueryModel {


	public void setEntityModelSpecific(Query query, Direction dir,
			org.apache.ddlutils.model.Table table, Table entity) {
	}

	@Override
	public QueryParams getOutputParams(Query query)
			throws MinuteProjectException {
		// TODO Auto-generated method stub
		return new QueryParams();
	}
	
}
