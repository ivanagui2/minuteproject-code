package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryChunkValue extends QueryAdapter{

	private boolean isDefault;
	private QueryChunk queryChunk;

	public boolean isDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public void setQueryChunk(QueryChunk queryChunk) {
		this.queryChunk = queryChunk;
	}
	
}
