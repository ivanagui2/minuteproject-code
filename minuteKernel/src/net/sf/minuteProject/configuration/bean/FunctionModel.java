package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.condition.CatalogGenerationCondition;
import net.sf.minuteProject.configuration.bean.condition.FunctionGenerationCondition;

import org.apache.log4j.Logger;

public class FunctionModel {

	private static Logger logger = Logger.getLogger(FunctionModel.class);
	
	private Model model;
	private CatalogGenerationCondition catalogGenerationCondition;
	private FunctionGenerationCondition functionGenerationCondition;
	private BusinessPackage businessPackage;
	
	public CatalogGenerationCondition getCatalogGenerationCondition() {
		return catalogGenerationCondition;
	}
	public void setCatalogGenerationCondition(
			CatalogGenerationCondition catalogGenerationCondition) {
		this.catalogGenerationCondition = catalogGenerationCondition;
	}
	public FunctionGenerationCondition getFunctionGenerationCondition() {
		return functionGenerationCondition;
	}
	public void setFunctionGenerationCondition(
			FunctionGenerationCondition functionGenerationCondition) {
		this.functionGenerationCondition = functionGenerationCondition;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}
	public void setBusinessPackage(BusinessPackage businessPackage) {
		this.businessPackage = businessPackage;
	}
	
	
}
