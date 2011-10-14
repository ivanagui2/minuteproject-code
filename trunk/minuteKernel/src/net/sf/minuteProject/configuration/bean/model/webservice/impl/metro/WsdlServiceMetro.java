package net.sf.minuteProject.configuration.bean.model.webservice.impl.metro;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.model.webservice.Service;

public class WsdlServiceMetro extends AbstractConfiguration implements Service{

	private com.sun.tools.ws.processor.model.Service service;
	
	public WsdlServiceMetro(com.sun.tools.ws.processor.model.Service service) {
		this.service = service;
	}
	
	public String getName(){
		return service.getName().toString();
	}

}
