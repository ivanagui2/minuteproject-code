package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.target.ImportTargets;
import net.sf.minuteProject.configuration.bean.target.TargetParams;
import net.sf.minuteProject.utils.io.FileUtils;

/**
 * @author Florian Adler
 *
 */
public class Target extends AbstractConfiguration{
	
	private String dir;
	private String fileName;
	private String canonicalDir;
	private String canonicalFileName;	
	private ArchitectureTarget architectureTarget;
	private List <TemplateTarget> templateTargets;
	private AbstractConfigurationRoot abstractConfigurationRoot;
	private List <Target> dependency;
	private List <Plugin> plugins;
	private TargetParams targetParams;
	private ImportTargets importTargets;
	private String outputdirRoot;
	
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
	
	public List<TemplateTarget> getTemplateTargets() {
		return templateTargets;
	}
	public void setTemplateTargets(List<TemplateTarget> templateTargets) {
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
//		return FileUtils.getFileFullPathWithoutFileName(dir, fileName);
		
//		if (dir==null) {
//			return FileUtils.getFileFullPathWithoutFileName()
//			// if environment MP_HOME is available
//			// -> 
//			//get the configuration file in the classpath.
//			// strip of the filename => root dir
//			
//		}
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getFileName() {
//		return FileUtils.stripRelativePath(fileName);
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
	public String getCanonicalDir() {
		return canonicalDir;
	}
	public void setCanonicalDir(String canonicalDir) {
		this.canonicalDir = canonicalDir;
	}
	public String getCanonicalFileName() {
		return canonicalFileName;
	}
	public void setCanonicalFileName(String canonicalFileName) {
		this.canonicalFileName = canonicalFileName;
	}
	public ImportTargets getImportTargets() {
		return importTargets;
	}
	public void setImportTargets(ImportTargets importTargets) {
		this.importTargets = importTargets;
	}
	public TargetParams getTargetParams() {
		return targetParams;
	}
	public void setTargetParams(TargetParams targetParams) {
		this.targetParams = targetParams;
	}
	public String getOutputdirRoot() {
		return outputdirRoot;
	}
	public void setOutputdirRoot(String outputdirRoot) {
		this.outputdirRoot = outputdirRoot;
	}	
	
}
