package net.sf.minuteProject.configuration.bean.enrichment;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class Entity extends AbstractConfiguration{
	private VirtualPrimaryKey virtualPrimaryKey;

	public VirtualPrimaryKey getVirtualPrimaryKey() {
		return virtualPrimaryKey;
	}

	public void setVirtualPrimaryKey(VirtualPrimaryKey virtualPrimaryKey) {
		this.virtualPrimaryKey = virtualPrimaryKey;
	}
	
	
}
