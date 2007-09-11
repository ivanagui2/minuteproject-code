package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.model.data.Database;


public class BusinessModel {
	
	private Model model;
	private GenerationCondition generationCondition;
	private BusinessPackage businessPackage; 
	//private List <Table> tables;

	public void complementDataModel () {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
			businessPackage.setPackages(model, database);
		}
	}
	
	/*public void addTable (Table table){
		if (tables==null){
			tables = new ArrayList();
		}
		tables.add(table);
	}
	
	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}*/

	public GenerationCondition getGenerationCondition() {
		return generationCondition;
	}

	public void setGenerationCondition(GenerationCondition generationCondition) {
		this.generationCondition = generationCondition;
	}

	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}

	public void setBusinessPackage(BusinessPackage businessPackage) {
		businessPackage.setBusinessModel(this);
		this.businessPackage = businessPackage;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	
}
