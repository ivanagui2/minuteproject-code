package net.sf.minuteProject.configuration.bean;

public class Resource extends AbstractConfiguration{
	public String resourceFileName;
	private ResourceTarget resourceTarget;

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	public void setResourceTarget(ResourceTarget resourceTarget) {
		this.resourceTarget = resourceTarget;
	}

	public ResourceTarget getResourceTarget() {
		return resourceTarget;
	}

}
