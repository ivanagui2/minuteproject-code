package net.sf.minuteProject.plugin.maven;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.GeneratorQualifier;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.connection.Driver;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.CommonUtils;

public class MavenUtils {
	
	public static final String artifactFinalName = "maven-artifact-finalName-suffix";
	
	public static String getPackaging(Template template) {
		return template.getPropertyValue("maven-packaging", "jar");
	}
	
	public static String getFinalName (Template template, GeneratorQualifier model) {
		return getArtifactId(template,model);
	}
	
	public static String getArtifactId (Template template, GeneratorQualifier model) {
		String finalNameSuffix = template.getPropertyValue(artifactFinalName);
		return  (finalNameSuffix!=null)? model.getName()+finalNameSuffix:model.getName()+"App";
	}
	
	public static String getJarArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "jar-maven-template", "maven-artifact-finalName-suffix");
	}
	
	public static String getDependentJarArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "jar-maven-template", "dependent-maven-artifact-finalName-suffix");
	}

	public static String getWebArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "web-maven-template", "maven-artifact-finalName-suffix");
	}
	
	public static String getEjbArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "ejb-maven-template", "maven-artifact-finalName-suffix");
	}
	
	public static String getArtifactName (Template template, GeneratorQualifier bean, String mavenTemplate, String mavenArtifactFinalName) {
		//property from catalog
		String markerTemplate = template.getPropertyValue(mavenTemplate);
		if (markerTemplate!=null && bean.getConfiguration()!=null) {
			Template marker = CommonUtils.getTemplate(bean.getConfiguration(), markerTemplate);
			return bean.getName()+marker.getPropertyValue(mavenArtifactFinalName);
		}
		return null;
	}
	
	public static List<MavenModule> getModules (Template template) {
		// The current implementation imposes to masterpom template as the last generated artifact)
		List<MavenModule> list = new ArrayList<MavenModule>();
		String s =template.getOutputdir();
		File dir = new File(s);
		FileFilter fileFilter = new FileFilter() {
		    public boolean accept(File file) {
		        return file.isDirectory() && directoryContainsPom(file);
		    }

			private boolean directoryContainsPom(File file) {
				try {
					File pom = new File(file.getCanonicalPath()+"/"+"pom.xml");
					return pom.isFile();
				} catch (IOException e) {
					return false;
				}
			}
		};

		for (File child : dir.listFiles(fileFilter)) {
			list.add(new MavenModule(child.getName()));
		}
		return list;
	}
	
    public static String getOutputDirForJavaOrGroovy (Template template, GeneratorBean bean) {
		if (template.getPropertyValue("groovify", false))
			return "src/main/groovy";		
		return "src/main/java";
    }

	public static String getRootPackage(Template template, GeneratorQualifier bean) {
		return getRootPackage(template, bean.getPackageRoot());
	}	

	
	public static String getRootPackage(Template template, String value) {
		String packageRoot = value;
		if (packageRoot==null)
			return template.getTemplateTarget().getPackageRoot();
		return packageRoot;
	}
	
	public static Driver getDriver (Model model) {
		return model.getDataModel().getDriver();
	}

	public static Boolean hasDriver (Model model) {
		return getDriver(model)!=null;
	}
	
	public static String getModelVersion (Model model) {
		return model.getVersion();
	}
	
	public static String getDbApiGroupId (GeneratorBean bean) {
		return bean.getName()+"BackEnd";
	}
	
	public static String getDbApiArtifactId (GeneratorBean bean) {
		return getDbApiGroupId(bean);
	}
	
	public static String getVersion (Model model) {
		return getModelVersion(model);
	}
	
	public static String getVersion (Application application) {
		final String version = application.getVersion();
		return version==null?"1.0":version;
	}
	
	public static String getDbApiName (GeneratorBean bean) {
		return getDbApiArtifactId(bean);
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
