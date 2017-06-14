package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryScheduler extends QueryAdapter {

	private QueryParams queryParams;
	private Query query;
	private String reportChannel, reportFrom, reportTo, reportCc, reportBcc, cron;
	private int reportThresholdMinRecord;

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

	public int getReportThresholdMinRecord() {
		return reportThresholdMinRecord;
	}

	public void setReportThresholdMinRecord(int reportThresholdMinRecord) {
		this.reportThresholdMinRecord = reportThresholdMinRecord;
	}

	public String getReportCc() {
		return reportCc;
	}

	public void setReportCc(String reportCc) {
		this.reportCc = reportCc;
	}

	public String getReportBcc() {
		return reportBcc;
	}

	public void setReportBcc(String reportBcc) {
		this.reportBcc = reportBcc;
	}
	
	public boolean hasThresholdMinRecord() {
		return reportThresholdMinRecord != 0;
	}
	
}
