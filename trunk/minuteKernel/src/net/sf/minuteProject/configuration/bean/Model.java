package net.sf.minuteProject.configuration.bean;

public class Model  extends AbstractConfiguration{
	
	private String packageRoot;
	private String version;
	
	private DataModel dataModel;
	private BusinessModel businessModel;
	private FunctionModel functionModel;
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
	
	public boolean hasBusinessModel() {
		if (businessModel!=null)
			return true;
		return false;
	}

	public DataModel getDataModel() {
		return dataModel;
	}

	public FunctionModel getFunctionModel() {
		return functionModel;
	}

	public void setFunctionModel(FunctionModel functionModel) {
		functionModel.setModel(this);
		this.functionModel = functionModel;
	}
	
	public boolean hasFunctionModel() {
		if (functionModel!=null)
			return true;
		return false;
	}

	public void setDataModel(DataModel dataModel) {
		dataModel.setModel(this);
		this.dataModel = dataModel;
	}

	
	public String getTechnicalPackage(Template template) {
		StringBuffer sb = new StringBuffer(template.getPackageRoot());
		if ((template.getAddModelDirName()==null 
			|| template.getAddModelDirName().equals("")
			|| template.getAddModelDirName().equals("true"))	
			&& 
			!(template.getApplicationSpecific()!=null && template.getApplicationSpecific().equals("true"))
			)
			sb.append("."+getName());
		if (template.getTechnicalPackage()!=null && !template.getTechnicalPackage().equals(""))
			sb.append("."+template.getTechnicalPackage());
		return sb.toString();
	}

	

	public String getPackageRoot() {
		return packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}		
	
	
}
