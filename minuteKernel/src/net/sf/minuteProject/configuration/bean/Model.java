package net.sf.minuteProject.configuration.bean;

public class Model  extends AbstractConfiguration{
	
	private String packageRoot;
	
	private DataModel dataModel;
	private BusinessModel businessModel;
	private Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public BusinessModel getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(BusinessModel businessModel) {
		businessModel.setModel(this);
		this.businessModel = businessModel;
	}

	public DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		dataModel.setModel(this);
		this.dataModel = dataModel;
	}

	public String getPackageRoot() {
		return packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}
	
	public String getTechnicalPackage(Template template) {
		StringBuffer sb = new StringBuffer(template.getPackageRoot());
		sb.append("."+getConfiguration().getProjectname());
		sb.append("."+template.getTechnicalPackage());
		//sb.append("."+getName());
		return sb.toString();	
	}		
}
