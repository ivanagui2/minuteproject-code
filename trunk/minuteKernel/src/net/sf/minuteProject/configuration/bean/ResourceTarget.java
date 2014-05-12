package net.sf.minuteProject.configuration.bean;

import java.util.List;

public class ResourceTarget extends AbstractConfiguration {
	List<Resource> resources;

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
}
