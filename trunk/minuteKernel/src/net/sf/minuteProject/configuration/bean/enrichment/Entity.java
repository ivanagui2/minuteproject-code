package net.sf.minuteProject.configuration.bean.enrichment;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class Entity extends AbstractConfiguration{
	private String alias;
	private String structure;
	private VirtualPrimaryKey virtualPrimaryKey;

	public VirtualPrimaryKey getVirtualPrimaryKey() {
		return virtualPrimaryKey;
	}

	public void setVirtualPrimaryKey(VirtualPrimaryKey virtualPrimaryKey) {
		this.virtualPrimaryKey = virtualPrimaryKey;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}
	
	
}
