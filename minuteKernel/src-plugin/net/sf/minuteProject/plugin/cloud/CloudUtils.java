package net.sf.minuteProject.plugin.cloud;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.Template;

public class CloudUtils {
//TODO add to ReleaseUtils
//TODO get the release app from the technology-target
	public static String getAppName (Template template) {
		return template.getPropertyValue("cloud-name");
	}
	
	public static String getReleaseFileName (Template template, Model model) {
		Targets t = template.getTemplateTarget().getTarget().getTargets();
		if (t!=null && t.getCatalogEntry()!=null) {
			String catalogEntry = t.getCatalogEntry();
			if (catalogEntry.equalsIgnoreCase("openxava"))
				return "../dist/"+model.getName()+".war";
			if (catalogEntry.equalsIgnoreCase("primefaces-spring"))
				return "JSF-Primefaces/target/"+model.getName()+"App.war";
			if (catalogEntry.equalsIgnoreCase("primefaces-jee"))
				return "JSF-Primefaces/target/"+model.getName()+"App.war";
		}
			
		return template.getPropertyValue("release.name");
	}
	
}
