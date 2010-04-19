package net.sf.minuteProject.model.data.criteria.type;

import java.util.Date;

import net.sf.minuteProject.model.data.criteria.ComparisonCriteria;

public class DateCriteria extends ComparisonCriteria{

	public DateCriteria between (Date startDate, Date endDate) {
		return this;
	}
}
