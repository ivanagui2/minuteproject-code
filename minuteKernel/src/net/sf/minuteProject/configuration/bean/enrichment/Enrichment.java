package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.BusinessModel;

public class Enrichment extends AbstractConfiguration {
	
	private BusinessModel businessModel;
	private List <Entity> entities;
	
	public void setEntity (Entity entity) {
		addEntity(entity);
	}
	
	public void addEntity (Entity entity) {
		entity.setEnrichment(this);
		getEntities().add(entity);
	}

	public List<Entity> getEntities() {
		if (entities==null)
			entities = new ArrayList<Entity> ();
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public BusinessModel getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(BusinessModel businessModel) {
		this.businessModel = businessModel;
	}
	
	

}
