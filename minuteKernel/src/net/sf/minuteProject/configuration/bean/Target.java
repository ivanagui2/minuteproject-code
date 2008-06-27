package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.system.Plugin;

/**
 * @author Florian Adler
 *
 */
public class Target extends AbstractConfiguration{
	
	private String dir;
	private String fileName;
	private ArchitectureTarget architectureTarget;
	private List <TemplateTarget> templateTargets;
	private AbstractConfigurationRoot abstractConfigurationRoot;
	private List <Target> dependency;
	private List <Plugin> plugins;

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
	
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void addPlugin (Plugin plugin) {
		getPlugins().add(plugin);
	}
	public List<Plugin> getPlugins() {
		if (plugins==null)
			setPlugins(new ArrayList<Plugin>());
		return plugins;
	}
	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}
	
	
}
