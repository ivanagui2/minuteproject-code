package net.sf.minuteProject.plugin.maven;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.connection.Driver;
import net.sf.minuteProject.configuration.bean.system.Property;

public class MavenUtils {

	public static List<MavenModule> getModules (Template template) {
		//ideally:
		// check each target to see if there is a pom.xml generated
		// and add a module whose name is the directory

		// The current implementation imposes to masterpom template as the last generated artifact)
		List<MavenModule> list = new ArrayList<MavenModule>();
		String s =template.getOutputdir();
		File dir = new File(s);
		FileFilter fileFilter = new FileFilter() {
		    public boolean accept(File file) {
		    	//TODO add a check that the dir below contains a pom.xml
		        return file.isDirectory();
		    }
		};

		for (File child : dir.listFiles(fileFilter)) {
			list.add(new MavenModule(child.getName()));
		}
		return list;
	}
	


	public static String getRootPackage(Template template, Model model) {
		String packageRoot = model.getPackageRoot();
		if (packageRoot==null)
			return template.getTemplateTarget().getPackageRoot();
		return packageRoot;
	}
	
	public static Driver getDriver (Model model) {
		return model.getDataModel().getDriver();
	}
	
	public static String getModelVersion (Model model) {
		return model.getVersion();
	}
	
	public static String getGroupId (Model model) {
		return model.getName()+"BackEnd";
	}
	
	public static String getArtifactId (Model model) {
		return getGroupId(model);
	}
	
	public static String getVersion (Model model) {
		return getModelVersion(model);
	}
	
	public static String getDbApiName (Model model) {
		return getArtifactId(model);
	}	
	
	public static MavenDependency getDependency(String stringFmtDep) {
		return new MavenDependency();
	}
	
	public static List<MavenDependency> getDependencies (Template template) {
		List<Property> depencies = template.getPropertyListByTag("dependency");
		return getDepency(depencies);
	}

	public static List<MavenDependency> getDepency(List<Property> depencies) {
		List<MavenDependency> mList = new ArrayList<MavenDependency>();
		for (Property property : depencies) {
			mList.add(getDepency(property));
		}
		return mList;
	}
	
	public static MavenDependency getDepency(Property dependency) {
		return getDependency(dependency.getValue());
	}
}
