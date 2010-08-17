package net.sf.minuteProject.plugin.roo;

public class RooColumn {
	private String rooConsoleType, 
	               minSizeChunk,
	               sizeMaxChunk,
	               notNullChunk,
	               typeChunk;

	public String getRooConsoleType() {
		return rooConsoleType;
	}

	public void setRooConsoleType(String rooConsoleType) {
		this.rooConsoleType = rooConsoleType;
	}

	public String getMinSizeChunk() {
		return minSizeChunk;
	}

	public void setMinSizeChunk(String minSizeChunk) {
		this.minSizeChunk = minSizeChunk;
	}

	public String getSizeMaxChunk() {
		return sizeMaxChunk;
	}

	public void setSizeMaxChunk(String sizeMaxChunk) {
		this.sizeMaxChunk = sizeMaxChunk;
	}

	public String getNotNullChunk() {
		return notNullChunk;
	}

	public void setNotNullChunk(String notNullChunk) {
		this.notNullChunk = notNullChunk;
	}

	public String getTypeChunk() {
		return typeChunk;
	}

	public void setTypeChunk(String typeChunk) {
		this.typeChunk = typeChunk;
	}

	@Override
	public String toString() {
		return "RooColumn [minSizeChunk=" + minSizeChunk + ", notNullChunk="
				+ notNullChunk + ", rooConsoleType=" + rooConsoleType
				+ ", sizeMaxChunk=" + sizeMaxChunk + ", typeChunk=" + typeChunk
				+ "]";
	}

}
