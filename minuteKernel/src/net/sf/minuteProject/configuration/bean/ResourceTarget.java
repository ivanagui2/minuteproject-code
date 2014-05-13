package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

public class ResourceTarget extends AbstractConfiguration {
	
	private Target target;
	List<Resource> resources;
	private String outputDir;
	
	public List<Resource> getResources() {
		if (resources==null)
			resources = new ArrayList<Resource>();
		return resources;
	}
	
	public void addResource (Resource resource) {
		resource.setResourceTarget(this);
		getResources().add(resource);
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Target getTarget() {
		return target;
	}
	
}
