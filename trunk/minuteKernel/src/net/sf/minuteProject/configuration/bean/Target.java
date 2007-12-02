package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Adler
 *
 */
public class Target extends AbstractConfiguration{
	
	private ArchitectureTarget architectureTarget;
	private List <TemplateTarget> templateTargets;
	private AbstractConfigurationRoot abstractConfigurationRoot;
	private List <Target> dependency;

	public AbstractConfigurationRoot getAbstractConfigurationRoot() {
		return abstractConfigurationRoot;
	}
	public void setAbstractConfigurationRoot(
			AbstractConfigurationRoot abstractConfigurationRoot) {
		this.abstractConfigurationRoot = abstractConfigurationRoot;
	}
	public ArchitectureTarget getArchitectureTarget() {
		return architectureTarget;
	}
	public void setArchitectureTarget(ArchitectureTarget architectureTarget) {
		this.architectureTarget = architectureTarget;
	}

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
			templateTargets = new ArrayList<TemplateTarget>();
		templateTarget.setTarget(this);
		templateTargets.add(templateTarget);
	}
	
	public List getTemplateTargets() {
		return templateTargets;
	}
	public void setTemplateTargets(List templateTargets) {
		this.templateTargets = templateTargets;
	}
	
	public void addDependency (String dependencies) {
		if (getDependency()==null)
			setDependency(new ArrayList<Target>());
		//Target target = getTarget
	}
	
	public List<Target> getDependency() {
		return dependency;
	}
	
	private void setDependency(List<Target> dependency) {
		this.dependency = dependency;
	}
	
	
}
