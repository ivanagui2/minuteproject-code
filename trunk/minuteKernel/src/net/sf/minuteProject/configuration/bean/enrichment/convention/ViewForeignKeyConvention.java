package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class ViewForeignKeyConvention extends Convention{

	public static final String APPLY_DEFAULT_FK_BY_ENTITY_NAME_AND_SUFFIX = "apply-default-foreign-key-by-entity-name-and-suffix";
	public String type;
	public String defaultSuffix;
	
	
}
