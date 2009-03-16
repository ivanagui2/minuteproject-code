package net.sf.minuteProject.configuration.bean;

public abstract class AbstractConfigurationRoot extends AbstractConfiguration{
	
	private Target target;
	private String projectname;
	
	public String getProjectname() {
		if (this.projectname == null)
			return getName();
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		target.setAbstractConfigurationRoot(this);
		this.target = target;
	}

}
