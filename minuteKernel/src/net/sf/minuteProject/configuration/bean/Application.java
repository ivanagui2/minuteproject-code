package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

public class Application {

	private List<Model> models;

	public List<Model> getModels() {
		if (models == null) models = new ArrayList<Model>();
		return models;
	}
	
	public void addModel (Model model) {
		model.setApplication(this);
		getModels().add(model);
	}
	
}
