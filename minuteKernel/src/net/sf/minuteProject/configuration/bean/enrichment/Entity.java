package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.security.EntitySecuredAccess;

public class Entity extends AbstractConfiguration {
	
	private String alias;
	private String structure;
	private VirtualPrimaryKey virtualPrimaryKey;
	private List<Field> fields;
	private Enrichment enrichment;
	private String contentType; //pseudo-static, reference, life-business-data
	private SemanticReference semanticReference;
	private boolean isLinkEntity;
	private EntitySecuredAccess entitySecuredAccess;
	
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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
	
}
