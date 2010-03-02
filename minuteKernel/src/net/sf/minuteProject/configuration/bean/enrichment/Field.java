package net.sf.minuteProject.configuration.bean.enrichment;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class Field extends AbstractConfiguration {

	private Entity entity;
	private String linkToTargetEntity, linkToTargetField;
	private String bidirectional;
	private Stereotype stereotype;

	private boolean isBidirectional () {
		if (bidirectional!=null && bidirectional.equals("true"))
			return true;
		return false;
	}
	
	public String getLinkToTargetEntity() {
		return linkToTargetEntity;
	}

	public void setLinkToTargetEntity(String linkToTargetEntity) {
		this.linkToTargetEntity = linkToTargetEntity;
	}

	public String getLinkToTargetField() {
		return linkToTargetField;
	}

	public void setLinkToTargetField(String linkToTargetField) {
		this.linkToTargetField = linkToTargetField;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getBidirectional() {
		return bidirectional;
	}

	
	public void setBidirectional(String bidirectional) {
		this.bidirectional = bidirectional;
	}

	public Stereotype getStereotype() {
		return stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

}
