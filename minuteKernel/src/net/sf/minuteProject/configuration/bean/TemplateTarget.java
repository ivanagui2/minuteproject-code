package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

public class TemplateTarget extends AbstractConfiguration{
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
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getOutputdir() {
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
	
	
}
