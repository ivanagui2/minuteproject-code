package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.presentation.Presentation;

public class Configuration {
	
	private Model model;
	private Target target;
	private Presentation presentation;

	public Presentation getPresentation() {
		return presentation;
	}

	public void setPresentation(Presentation presentation) {
		presentation.setConfiguration(this);
		this.presentation = presentation;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		target.setConfiguration(this);
		this.target = target;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		model.setConfiguration(this);
		this.model = model;
	}
	
}
