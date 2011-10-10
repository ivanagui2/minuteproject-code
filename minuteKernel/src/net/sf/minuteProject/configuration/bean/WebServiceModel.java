package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.webservice.Wsdl;

public class WebServiceModel extends AbstractConfiguration{

	private Wsdl wsdl;
	private Model model;
	
	public Wsdl getWsdl() {
		if (wsdl==null) {
			wsdl=new Wsdl();
			wsdl.setWebServiceModel(this);
		}		
		return wsdl;
	}
	public void setWsdl(Wsdl wsdl) {
		this.wsdl = wsdl;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}


}
