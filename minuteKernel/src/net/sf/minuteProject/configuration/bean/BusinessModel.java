package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.minuteProject.application.ModelGenerator;
import net.sf.minuteProject.configuration.bean.enrichment.Enrichment;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.enrichment.Package;
import net.sf.minuteProject.configuration.bean.enrichment.VirtualPrimaryKey;
import net.sf.minuteProject.configuration.bean.enrichment.XmlEnrichment;
import net.sf.minuteProject.configuration.bean.enrichment.convention.Convention;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.presentation.Presentation;
import net.sf.minuteProject.configuration.bean.service.Scope;
import net.sf.minuteProject.configuration.bean.service.Service;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ComponentUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.ForeignKeyUtils;
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
	private Presentation presentation;
	
	// for xml manipulation
	private XmlEnrichment xmlEnrichment;
	
	public void complementDataModel () {
		complementDataModelWithTables();
		complementDataModelWithViews();
		complementService();
	}

	public void complementDataModelWithTables () {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
			getBusinessPackage().setPackages(model, database);
		}
		complementDataModelWithTablesEnrichment();
	}
	
	public void complementDataModelWithViews () {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
			getBusinessPackage().setPackageViews(model, database);
		}
		complementDataModelWithViewsEnrichment();
	}

	private void complementDataModelWithViewsEnrichment () {
	   complementDataModelWithEntitiesEnrichment(Table.VIEW);
	}
	
	private void complementDataModelWithTablesEnrichment () {
		complementDataModelWithEntitiesEnrichment(Table.TABLE);
	}

	private void complementDataModelWithEntitiesEnrichment (String type) {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
			// for all the view
			// set virtual pk, realpk
			Enrichment enrichment = model.getBusinessModel().getEnrichment(); 
			if (enrichment != null) {
				if (enrichment.getEntities()!=null) {
					for (Entity entity : enrichment.getEntities()) {
						String typeEntity = TableUtils.getTargetType(database, entity);
						if ((Table.VIEW.equals(type) && Table.VIEW.equals(typeEntity)))
							complementView(entity, database);
						if (type.equals(Table.TABLE) && Table.TABLE.equals(typeEntity))
//						else
							complementTable(entity,database); 
					}
				}
				if (enrichment.getPackages()!=null) {
					for (Package pack : enrichment.getPackages()) {
						complementPackage (pack, model);
					}
				}
			}
			
		}
	}
	
	public void secureEntityType () {
		Database database = model.getDataModel().getDatabase();
		if (database!=null) {
			// for all the view
			// set virtual pk, realpk
			Enrichment enrichment = model.getBusinessModel().getEnrichment(); 
			if (enrichment != null) {
				if (enrichment.getEntities()!=null) {
					for (Entity entity : enrichment.getEntities()) {
						String typeEntity = TableUtils.getTargetType(database, entity);
						if (Table.VIEW.equals(entity.getType())&& Table.TABLE.equals(typeEntity))
							convertTableToView(database, TableUtils.getTable(database, entity.getName()));
					}
				}
			}
			
		}
	}

	private void convertTableToView(Database database, Table table) {
		database.addView(table);
	}

	private void complementPackage(Package pack, Model model) {
		for (net.sf.minuteProject.configuration.bean.Package p : model.getBusinessModel().getBusinessPackage().getPackages()) {
			if (p.getName().equals(pack.getName()))
				complementPackage (p, pack);
		}
	}

	private void complementPackage(net.sf.minuteProject.configuration.bean.Package p, Package pack) {
		p.setSecurityColor (pack.getSecurityColor());
	}

	private void complementView(Entity entity, Database database) {
		View view = TableUtils.getView(database, entity.getName());
//		if (view==null) view = TableUtils.getTable(database, entity.getName());
		if (view!=null){
			complementDataModelWithViewEnrichment(view, entity);
			complementEntityWithProperties(view, entity);
		} 
	}
	
	private void complementTable(Entity entity, Database database) {
		net.sf.minuteProject.configuration.bean.model.data.Table table = TableUtils.getTable(database, entity.getName());
		if (table!=null){
			complementEntityWithProperties(table, entity);
		}		
	}
	
	public void complementService() {
		Database database = model.getDataModel().getDatabase();
		Service service = model.getBusinessModel().getService();
		if (service!=null) {
			List <Scope> scopes = service.getScopes();
			if (scopes!=null) {
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
		}
	}
	
	private void complementEntityWithProperties(net.sf.minuteProject.configuration.bean.model.data.Table table, Entity entity) {
		convertEntityInfoIntoTable(entity, table);
		List<Field> fields = entity.getFields();
		Column[] columns = table.getColumns();
		Column[] attributes = table.getAttributes();
		for (Field field : fields) {
			for (Column column : columns) {
				if (field.getName().equals(column.getName()))
					//TODO set stereotype, contentType, semanticReference
					convertFieldInfoToColumn(field, column);
			}	
			for (Column column : attributes) {
				if (field.getName().equals(column.getName()))
					convertFieldInfoToColumn(field, column);
			}			
		}
	}
	
	private void convertEntityInfoIntoTable(Entity entity, Table table) {
		table.setProperties(entity.getProperties());
		table.setAlias(entity.getAlias());
		table.setContentType(entity.getContentType());
		table.setSemanticReference(entity.getSemanticReference());
		table.setLinkEntity(entity.isLinkEntity());
		table.setEntitySecuredAccess(entity.getEntitySecuredAccess());
	}
	
	private void convertFieldInfoToColumn (Field field, Column column) {
		column.setProperties(field.getProperties());
		column.setStereotype(field.getStereotype());
	}
	
	private void complementDataModelWithViewEnrichment (View view, Entity entity) {
		complementWithViewVirtualPrimaryKey(view, entity);
		complementWithViewField(view, entity);
		// there is a bug in complementWithViewComponent => change the column name of the view .
		//complementWithViewComponent(view, entity);
	}
	
	private void complementWithViewVirtualPrimaryKey(View view, Entity entity) {
		VirtualPrimaryKey virtualPrimaryKey = entity.getVirtualPrimaryKey();
		if (virtualPrimaryKey!=null) {
			if (virtualPrimaryKey.getColumnName()!=null) {
				addVirtualPrimaryKey(view, virtualPrimaryKey.getColumnName());
			}
			if (virtualPrimaryKey.getProperties()!=null)
				for (Property property : virtualPrimaryKey.getProperties()) {
					if (property.getName().equals("virtualPrimaryKey")) {
						addVirtualPrimaryKey(view, property.getValue());
					}
				}
		}
	}
	
	private void addVirtualPrimaryKey(View view, String columnName) {
		Column column = ColumnUtils.getColumn(view, columnName);
		if (column!=null) {
			//Column col = column.
			view.addVirtualPrimaryKey(column);
		}		
	}
	
	private void complementWithViewField(View view, Entity entity) {
		List<Field> fields = entity.getFields();
		for (Field field : fields) {
			ForeignKey foreignKey = getForeignKey(field);
			if (field.getLinkToTargetEntity()!=null && foreignKey!=null) {
				view.setForeignKey (foreignKey);
				// remove it from attribute
				//view.getAttributes()
			}
		}
	}
	
	private ForeignKey getForeignKey (Field field) {
		return ForeignKeyUtils.getForeignKey (field);
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

	public GenerationCondition getGenerationCondition() {
		return generationCondition;
	}

	public void setGenerationCondition(GenerationCondition generationCondition) {
		this.generationCondition = generationCondition;
	}

	public BusinessPackage getBusinessPackage() {
		if (businessPackage==null) {
			businessPackage=new BusinessPackage();
			businessPackage.setBusinessModel(this);
		}
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
		enrichment.setBusinessModel(this);
		this.enrichment = enrichment;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		service.setBusinessModel(this);
		this.service = service;
	}

	public XmlEnrichment getXmlEnrichment() {
		return xmlEnrichment;
	}

	public void setXmlEnrichment(XmlEnrichment xmlEnrichment) {
		this.xmlEnrichment = xmlEnrichment;
	}

	public Presentation getPresentation() {
		return presentation;
	}

	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
	}

	public void applyConvention() {
		if (enrichment!=null && enrichment.getConventions()!=null) {
			for (Convention convention : enrichment.getConventions().getConventions()) {
				convention.apply (this);
			}
		}		
	}
	
	
}
