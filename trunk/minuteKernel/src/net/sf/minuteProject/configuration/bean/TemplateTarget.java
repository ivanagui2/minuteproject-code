package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.utils.io.FileUtils;

public class TemplateTarget extends AbstractConfiguration{
	
	public static final String MP_GENERATION_OUTPUT = "MP-GENERATION-OUTPUT";
	private String rootdir, absoluteRootDir, canonicalDir;
	private String templatedir;
	private String dir;
	private String outputdir;
	private String tool;
	private String libdir;
	private Target target;
	// TODO change list into a map to ensure uniqueness of template by name
	private List <Template> templates;
	private String packageRoot;
	private boolean belongToPackage;
	
	public String getPackageRoot() {
		if (packageRoot==null){
			Configuration configuration = (Configuration) getTarget().getAbstractConfigurationRoot();
			setPackageRoot(configuration.getModel().getPackageRoot());
		}
		return packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}

	public void addTemplate (Template template) {
		if (templates==null)
			templates=new ArrayList<Template>();
		template.setTemplateTarget(this);
		template.setOutputdir(getOutputdir());
		if (template!=null && template.getPackageRoot()==null)
			template.setPackageRoot(getPackageRoot());
		templates.add(template);
	}
	
	public List<Template> getTemplates() {
		return templates;
	}
	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}
	
	public String getDir() {
		if (getRootdir()==null && getTemplatedir()==null)
			return dir;
		return getRootdir();//+"/"+getTemplatedir();
	}
	
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public String getOutputdir() {
		if (outputdir==null) {
			String name;
			if (getTarget().getAbstractConfigurationRoot()!=null 
				&& getTarget().getAbstractConfigurationRoot().getName()!=null) 
				name = getTarget().getAbstractConfigurationRoot().getName();
			else
				name = "project";
			outputdir = getDir()+"/"+MP_GENERATION_OUTPUT+"/"+name;
		
			
		}
		return outputdir;
	}
	
	public void setOutputdir(String outputdir) {
		this.outputdir = outputdir;
	}

	public String getLibdir() {
		return libdir;
	}

	public void setLibdir(String libdir) {
		this.libdir = libdir;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}
	
	public Template getTemplate(String name) {
		List list = getTemplates();
		for (int i = 0; i<list.size();i++) {
			Template template = (Template)list.get(i);
			if (template.getName().equals(name))
				return template;
		}
		return null;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public boolean isBelongToPackage() {
		return belongToPackage;
	}

	public void setBelongToPackage(boolean belongToPackage) {
		this.belongToPackage = belongToPackage;
	}

	public String getRootdir() {
		if (rootdir == null)
			rootdir = getTarget().getCanonicalDir();
		return rootdir;
	}
	
	public String getAbsoluteRootDir() {
		if (absoluteRootDir==null)
			absoluteRootDir = getAbsoluteRootDir(getRootdir());
		return absoluteRootDir;
	}
	
	private String getAbsoluteRootDir(String rootDir) {
		if (rootDir==null)
			rootDir = getRootdir();
		String targetDir = target.getDir();
		absoluteRootDir = FileUtils.getAbsolutePathFromPath(rootDir, targetDir);		
		return absoluteRootDir;
	}

	public void setRootdir(String rootdir) {
		this.rootdir = rootdir;
	}

	public String getTemplateFullDir() {
		if (templatedir!=null)
			return getRootdir()+"/"+templatedir;
		return getRootdir();
	}

	public void setTemplatedir(String templatedir) {
		this.templatedir = templatedir;
	}

	public String getTemplatedir() {
		return templatedir;
	}

	public String getCanonicalDir() {
		return canonicalDir;
	}

	public void setCanonicalDir(String canonicalDir) {
		this.canonicalDir = canonicalDir;
	}
	
	
}
