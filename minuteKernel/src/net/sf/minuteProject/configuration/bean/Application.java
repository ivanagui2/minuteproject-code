package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

public class Application extends AbstractConfiguration{

	private Configuration configuration;
	private List<Model> models;
	private String version, packageRoot;
	
	public List<Model> getModels() {
		if (models == null) models = new ArrayList<Model>();
		return models;
	}
	
	public void addModel (Model model) {
		model.setApplication(this);
		getModels().add(model);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPackageRoot() {
		return packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}
	
}
