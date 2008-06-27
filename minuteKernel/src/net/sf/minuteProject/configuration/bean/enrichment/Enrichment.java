package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class Enrichment extends AbstractConfiguration {
	
	private List <Entity> entities;
	
	public void addEntity (Entity entity) {
		if (entities==null)
			entities = new ArrayList<Entity> ();
		entities.add(entity);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

}
