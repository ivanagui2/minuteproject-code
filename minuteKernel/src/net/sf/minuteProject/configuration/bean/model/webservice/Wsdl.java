package net.sf.minuteProject.configuration.bean.model.webservice;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.WebServiceModel;

public class Wsdl extends AbstractConfiguration{

	WebServiceModel webServiceModel;
	private String locationUri;
	private String rootdir, dir, file;

	public String getRootdir() {
		return rootdir;
	}

	public void setRootdir(String rootdir) {
		this.rootdir = rootdir;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getLocationUri() {
		return locationUri;
	}

	public void setLocationUri(String locationUri) {
		this.locationUri = locationUri;
	}

	public WebServiceModel getWebServiceModel() {
		return webServiceModel;
	}

	public void setWebServiceModel(WebServiceModel webServiceModel) {
		this.webServiceModel = webServiceModel;
	}
	
	
}
