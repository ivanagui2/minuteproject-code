package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.presentation.Presentation;

public class Configuration extends AbstractConfigurationRoot{
	
	private Model model;
	private Presentation presentation;
	
	public Presentation getPresentation() {
		return presentation;
	}

	public void setPresentation(Presentation presentation) {
		presentation.setConfiguration(this);
		this.presentation = presentation;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		model.setConfiguration(this);
		this.model = model;
	}
	
	public String getTechnicalPackage(Template template) {
		return template.getPackageRoot();
	}
	
	public String getName () {
		if (super.getName()==null)
			return model.getName();
		return super.getName();
	}

	public String getGeneratedBeanName() {
		return getName();
	}
	
}
