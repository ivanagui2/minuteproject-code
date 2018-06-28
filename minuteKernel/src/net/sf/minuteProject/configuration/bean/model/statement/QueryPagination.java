package net.sf.minuteProject.configuration.bean.model.statement;

import lombok.Data;

@Data
public class QueryPagination {
	
//	private int maxResult, offset;
	private int maxResultDefault;
	private Query query;
	private String maxResultParam = "limit";
	private String offsetParam = "offset";
	
}
