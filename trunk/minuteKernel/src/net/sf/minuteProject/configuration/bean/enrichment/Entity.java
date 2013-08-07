package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.group.FieldGroup;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Constraint;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Rule;
import net.sf.minuteProject.configuration.bean.enrichment.security.EntitySecuredAccess;
import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.DatabaseDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteProject.configuration.bean.query.Ordering;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.StringUtils;
import net.sf.minuteProject.utils.TableUtils;

import org.apache.ddlutils.alteration.ColumnOrderChange;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Reference;

public class Entity extends AbstractConfiguration {
	
	private String structure;
	private VirtualPrimaryKey virtualPrimaryKey;
	private List<Field> fields;
	private List<Action> actions;
	private Enrichment enrichment;
	private String contentType; //pseudo-static, reference, life-business-data
	private SemanticReference semanticReference;
	private boolean isLinkEntity, isTransferEntity, isSearchable=true, isEditable=true;
	private EntitySecuredAccess entitySecuredAccess;
	private String type, masterRelationshipField;
	private List<FieldGroup> fieldGroups;
	private List<Constraint> constraints;
	private String mainEntity;
	
	public String getMainEntity() {
		return mainEntity;
	}

	public void setMainEntity(String mainEntity) {
		this.mainEntity = mainEntity;
	}

	public Boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public EntitySecuredAccess getEntitySecuredAccess() {
		return entitySecuredAccess;
	}

	public void setEntitySecuredAccess(EntitySecuredAccess entitySecuredAccess) {
		this.entitySecuredAccess = entitySecuredAccess;
	}

	public VirtualPrimaryKey getVirtualPrimaryKey() {
		return virtualPrimaryKey;
	}

	public void setVirtualPrimaryKey(VirtualPrimaryKey virtualPrimaryKey) {
		this.virtualPrimaryKey = virtualPrimaryKey;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public void setField (Field field) {
		addField(field);
	}
	
	public void addField (Field field) {
		field.setEntity(this);
		getFields().add(field);
	}
	
	public List<Field> getFields() {
		if (fields==null)
			fields = new ArrayList<Field> ();
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Enrichment getEnrichment() {
		return enrichment;
	}

	public void setEnrichment(Enrichment enrichment) {
		this.enrichment = enrichment;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public SemanticReference getSemanticReference() {
		return semanticReference;
	}

	public void setSemanticReference(SemanticReference semanticReference) {
		this.semanticReference = semanticReference;
	}

	public boolean isLinkEntity() {
		return isLinkEntity;
	}

	public void setLinkEntity(boolean isLinkEntity) {
		this.isLinkEntity = isLinkEntity;
	}

	public boolean isTransferEntity() {
		return isTransferEntity;
	}

	public void setTransferEntity(boolean isTransferEntity) {
		this.isTransferEntity = isTransferEntity;
	}

	public Boolean isSearchable() {
		return isSearchable;
	}

	public void setSearchable(boolean isSearchable) {
		this.isSearchable = isSearchable;
	}

	public String getType() {
		return (type==null)?Table.TABLE:type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FieldGroup> getFieldGroups() {
		if (fieldGroups==null) fieldGroups = new ArrayList<FieldGroup>();
		return fieldGroups;
	}
	
	public void addFieldGroup (FieldGroup fieldGroup) {
		getFieldGroups().add(fieldGroup);
	}
	
	public List<Action> getActions() {
		if (actions==null) actions = new ArrayList<Action>();
		return actions;
	}
	
	public void addAction (Action action) {
		getActions().add(action);
	}

	public List<Constraint> getConstraints() {
		if (constraints==null)
			constraints = new ArrayList<Constraint>();
		return constraints;
	}

	public void addConstraint (Constraint constraint) {
		getConstraints().add(constraint);
	}
	
	//use for m2m in grails, jpa2 xml rootnavigation
	public String getMasterRelationshipField() {
		return masterRelationshipField;
	}

	public void setMasterRelationshipField(String masterRelationshipField) {
		this.masterRelationshipField = masterRelationshipField;
	}

	public Table getTable (Database database) {
		Table table = (mainEntity!=null)?getFromMainEntity(database.findTable(mainEntity,false), database):new TableDDLUtils(getTable(this, database));
//		Table table = new TableDDLUtils(getTable(this, database));
		for (Action action : this.getActions()) {
			action.setParent(table);
		}
		table.setActions (this.getActions());
		table.setConstraints(this.getConstraints());
		table.setFieldGroups(this.getFieldGroups());
		//
		setFieldSpecifics(table);
		table.setSearchable(isSearchable);
		return table;
	}
	
	private void setFieldSpecifics(Table table) {
		for (Field field : fields) {
			net.sf.minuteProject.configuration.bean.model.data.Column column = ColumnUtils.getColumn(table, field.getName());
			if (column!=null) {
				column.setHidden(field.isHidden());
				column.setEditable(getEditable (field));
				column.setSearchable(getSearchable (field));
				column.setStereotype(field.getStereotype());//todo
			}
		}
	}

	private boolean getEditable(Field field) {
		if (field.isEditable()!=null) {
			return field.isEditable();
		}
		if (isEditable()!=null) {
			return isEditable();
		}
		return true;
	}
	private boolean getSearchable(Field field) {
		if (field.isSearchable()!=null) {
			return field.isSearchable();
		}
		if (isSearchable()!=null) {
			return isSearchable();
		}
		return isSearchable;
	}

	private Table getFromMainEntity(Table foundTable, Database database) {
		List<Field> excludedFields = new ArrayList<Field>();
		org.apache.ddlutils.model.Table t = new org.apache.ddlutils.model.Table();
		t.setName(getName());
		t.setType(foundTable.getType());
		t.setDescription(foundTable.getDescription());
		//add relationships
		foundTable.setDatabase(database);
		for (net.sf.minuteProject.configuration.bean.model.data.Reference reference : foundTable.getParents()) {
			//set reference in entity.field to reuse the construction mechanism field.ref towards table.ref
//			Field foundField = getField (reference);
			String localColumnName = reference.getLocalColumnName();
			Field foundField=null;
			for (Field field : fields) {
				if (StringUtils.equalsIgnoreCase(localColumnName, field.getName())) {
					foundField = field;
					excludedFields.add(foundField);
					break;
				}
			}
			if (foundField!=null) {
				foundField.setLinkToTargetEntity(reference.getForeignTableName());
				foundField.setLinkToTargetField(reference.getForeignColumnName());
			}
		}
		initRelationship(this, database, t);
		//
		for (net.sf.minuteProject.configuration.bean.model.data.Column column : foundTable.getColumns()) {
			// exclude or include according to options
			Field field = getField(column, excludedFields);
			if (field!=null) {
				Column c = new Column();
				c.setName(column.getName());
				c.setType(column.getType());
				c.setScale(column.getScale());
				c.setDefaultValue((field!=null)?field.getDefaultValue():column.getDefaultValue());
				c.setSize(column.getSize());
				c.setTypeCode(column.getTypeCode());
				c.setRequired(column.isRequired());
				c.setPrimaryKey(column.isPrimaryKey());
				t.addColumn(c);
				excludedFields.add(field);
			}
		}
		//add other field
		for (Field field: fields) {
			if (!excludedFields.contains(field))
				t.addColumn(getColumn(field));
		}
		
		Table table = new TableDDLUtils(t);
		table.setAlias(getName());
		table.setName(foundTable.getName());
		//setTableSpecifics(foundTable, table);
		
		return table;
	}
	
	private void setTableSpecifics(Table input, Table output) {
		for (Field field : fields) {
			net.sf.minuteProject.configuration.bean.model.data.Column columnInput = ColumnUtils.getColumn(input, field.getName());
			net.sf.minuteProject.configuration.bean.model.data.Column columnOutput = ColumnUtils.getColumn(output, field.getName());
			if (columnInput!=null && columnOutput!=null) {
				columnOutput.setStereotype(getStereotype(field, columnInput));//todo
			}
		}
	}
	private Stereotype getStereotype(
			Field field,
			net.sf.minuteProject.configuration.bean.model.data.Column columnInput) {
		if (field.getStereotype()!=null)
			return field.getStereotype();
		return columnInput.getStereotype();
	}

	private Field getField(net.sf.minuteProject.configuration.bean.model.data.Column column, List<Field> excludedFields) {
		Field f = getField(column);
		if (f!=null && !excludedFields.contains(f)) {
//			for (Field fi : excludedFields){
//				if (fi.getName().equals(f.getName()))
//					return null;
//			}
			return f;
		}
			
		return null;
	}
	private Field getField(net.sf.minuteProject.configuration.bean.model.data.Column column) {
		for (Field field : fields) {
			if (StringUtils.equalsIgnoreCase(field.getName(),column.getName()))
				return field;
		}
		return null;
	}

	private org.apache.ddlutils.model.Table getTable(Entity entity, Database database) {
		org.apache.ddlutils.model.Table table = getTable (getMainEntity());//new org.apache.ddlutils.model.Table();
		table.setType(Table.TABLE);
		table.setName(entity.getName());
		initFieldAndRelationship(entity, database, table);			
		return table;
	}

	private void initFieldAndRelationship(Entity entity, Database database,
			org.apache.ddlutils.model.Table table) {
		initField(entity, table);	
		initRelationship(entity, database, table);
	}

	private void initField(Entity entity, org.apache.ddlutils.model.Table table) {
		for (Field field : entity.getFields()) {
			table.addColumn(getColumn(field));
		}
	}

	private void initRelationship(Entity entity, Database database,
			org.apache.ddlutils.model.Table table) {
		for (Field field : entity.getFields()) {
			if (isForeignKey(field))
				table.addForeignKey(getForeignKey(field, database));
		}
	}

	private org.apache.ddlutils.model.Table getTable(String mainEntity) {
		return new org.apache.ddlutils.model.Table();
	}

	private boolean isForeignKey(Field field) {
		return (field.getLinkToTargetEntity()!=null && !field.getLinkToTargetEntity().trim().equals(""));
	}

	private ForeignKey getForeignKey(Field field, Database database) {
		ForeignKey foreignKey = new ForeignKey();
		foreignKey.addReference(getReference (field));
		foreignKey.setForeignTable(getForeignTable(field, database));
		foreignKey.setForeignTableName(field.getLinkToTargetEntity());
		return foreignKey;
	}

	private org.apache.ddlutils.model.Table getForeignTable(Field field, Database database) {
		Table table = TableUtils.getTable(database, field.getLinkToTargetEntity());
		org.apache.ddlutils.model.Table t = new org.apache.ddlutils.model.Table();
		t.setName(table.getName());
		return null;
	}

	private Reference getReference(Field field) {
		Reference reference = new Reference();
		Column localColumn = new Column();
		localColumn.setName(field.getName());
		reference.setLocalColumn(localColumn);
		reference.setLocalColumnName(field.getName());

		Column foreignColumn = new Column();
		foreignColumn.setName(field.getLinkToTargetField());
		reference.setForeignColumn(foreignColumn);
		reference.setForeignColumnName(field.getLinkToTargetField());
//		reference.setLocalColumn(localColumn);
//		reference.setForeignColumn(foreignColumn)
		return reference;
	}

	private org.apache.ddlutils.model.Column getColumn(Field field) {
		org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();
		column.setName(field.getName());
		column.setType(ConvertUtils.getDBFullTypeFromUMLType(field.getType()));
		column.setRequired(field.isMandatory());
		column.setSize(field.getLength());
		column.setPrimaryKey(field.isId());
		//column.setHidden(field.isHidden());
		return column;
	}

	public List<Ordering> getOrdering() {
		List <Ordering> list = new ArrayList<Ordering>();
		for (Field field : getFields()) {
			if (field.getOrdering()!=null) {
				Ordering ordering = new Ordering();
				ordering.setColumnName(field.getName());
				ordering.setOrder(field.getOrder());
				list.add(ordering);
			}
		}
		return list;
	}
	
}
