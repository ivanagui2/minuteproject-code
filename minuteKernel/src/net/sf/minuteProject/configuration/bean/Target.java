package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

public class Target {
	private ArchitectureTarget architectureTarget;
	//private TemplateTarget templateTarget;
	private List templateTargets;
	private Configuration configuration;
	
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public ArchitectureTarget getArchitectureTarget() {
		return architectureTarget;
	}
	public void setArchitectureTarget(ArchitectureTarget architectureTarget) {
		this.architectureTarget = architectureTarget;
	}
	/*public TemplateTarget getTemplateTarget() {
		return templateTarget;
	}
	public void setTemplateTarget(TemplateTarget templateTarget) {
		this.templateTarget = templateTarget;
	}*/
	public Template getTemplate(String name) {
		List list = getTemplateTargets();
		for (int i = 0; i<list.size();i++) {
			TemplateTarget templateTarget = (TemplateTarget)list.get(i);
			Template template;
			if ((template = templateTarget.getTemplate(name))!=null)
				return template;
		}
		return null;
	}
	
	public void addTemplateTarget (TemplateTarget templateTarget) {
		if (templateTargets==null)
			templateTargets = new ArrayList();
		templateTarget.setTarget(this);
		templateTargets.add(templateTarget);
	}
	
	public List getTemplateTargets() {
		return templateTargets;
	}
	public void setTemplateTargets(List templateTargets) {
		this.templateTargets = templateTargets;
	}
	
}
