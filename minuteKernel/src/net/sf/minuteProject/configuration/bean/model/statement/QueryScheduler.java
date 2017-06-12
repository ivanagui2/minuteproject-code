package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryScheduler extends QueryAdapter {

	private QueryParams queryParams;
	private Query query;
	private String reportChannel, reportFrom, reportTo, cron;
	private int reportRecordThreshold;

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
		this.queryParams.setFilterName(getName());
		for (QueryParam queryParam : getQueryParams().getQueryParams()) {
			queryParam.setMandatory(false);
		}
	}

	public String getReportChannel() {
		return reportChannel;
	}

	public void setReportChannel(String reportChannel) {
		this.reportChannel = reportChannel;
	}

	public String getReportFrom() {
		return reportFrom;
	}

	public void setReportFrom(String reportFrom) {
		this.reportFrom = reportFrom;
	}

	public String getReportTo() {
		return reportTo;
	}

	public void setReportTo(String reportTo) {
		this.reportTo = reportTo;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public int getReportRecordThreshold() {
		return reportRecordThreshold;
	}

	public void setReportRecordThreshold(int reportRecordThreshold) {
		this.reportRecordThreshold = reportRecordThreshold;
	}
	
}
