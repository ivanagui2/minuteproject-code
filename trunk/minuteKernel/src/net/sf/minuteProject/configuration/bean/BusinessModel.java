package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.minuteProject.application.ModelGenerator;
import net.sf.minuteProject.configuration.bean.enrichment.Enrichment;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.enrichment.VirtualPrimaryKey;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.service.Scope;
import net.sf.minuteProject.configuration.bean.service.Service;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ComponentUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ServiceUtils;
import net.sf.minuteProject.utils.TableUtils;


public class BusinessModel {
	
	private static Logger logger = Logger.getLogger(BusinessModel.class);
	private Service service;
	private Model model;
	private GenerationCondition generationCondition;
	private BusinessPackage businessPackage; 
	private Enrichment enrichment;
	//private List <Table> tables;

	public void complementDataModel () {
		complementDataModelWithTables();
		complementDataModelWithViews();
		complementService();
	}

	public void complementDataModelWithTables () {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
			businessPackage.setPackages(model, database);
		}
	}
	
	public void complementDataModelWithViews () {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
			businessPackage.setPackageViews(model, database);
		}
		complementDataModelWithViewsEnrichment();
	}

	private void complementDataModelWithViewsEnrichment () {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
//			businessPackage.setPackageViews(model, database);
			// for all the view
			// set virtual pk, realpk
			Enrichment enrichment = model.getBusinessModel().getEnrichment(); 
			if (enrichment != null) {
				for (Entity entity : enrichment.getEntities()) {
					View view = TableUtils.getView(database, entity.getName());
					if (view!=null){
						complementDataModelWithViewEnrichment(view, entity);
					}
				}
			}
		}
	}
	
	public void complementService() {
		Database database = model.getDataModel().getDatabase();
		for (Scope scope : model.getBusinessModel().getService().getScopes()) {
			net.sf.minuteProject.configuration.bean.model.data.Table table = TableUtils.getTable(database, scope.getEntity());
			if (table!=null)
				scope.setTableEntity(table);
			else {
				View view = TableUtils.getView(database, scope.getEntity());
				if (view!=null)
					scope.setViewEntity(view);
				else
					// log nothing to
					logger.warn("--> Nothing to add for service "+scope.getEntity());
			}
		}		
	}
	
	private void complementDataModelWithViewEnrichment (View view, Entity entity) {
		complementWithViewVirtualPrimaryKey(view, entity);
		complementWithViewComponent(view, entity);
	}
	
	private void complementWithViewVirtualPrimaryKey(View view, Entity entity) {
		VirtualPrimaryKey virtualPrimaryKey = entity.getVirtualPrimaryKey();
		if (virtualPrimaryKey!=null) {
			for (Property property : virtualPrimaryKey.getProperties()) {
				if (property.getName().equals("virtualPrimaryKey")) {
					Column column = ColumnUtils.getColumn(view, property.getValue());
					if (column!=null)
						view.addVirtualPrimaryKey(column);					
				}
			}
		}
	}
	
	private void complementWithViewComponent(View view, Entity entity) {
		String structure = entity.getStructure();
		String alias = entity.getAlias();
		if (alias!=null && !alias.equals(""))
			view.setAlias(alias);
		if (structure!=null && structure.equals("hierarchy")) {
			view.setComponents(ComponentUtils.getComponent(view));
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

	public Enrichment getEnrichment() {
		return enrichment;
	}

	public void setEnrichment(Enrichment enrichment) {
		this.enrichment = enrichment;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		service.setBusinessModel(this);
		this.service = service;
	}
	
	
}
