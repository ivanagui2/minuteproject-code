package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;

public class ViewForeignKeyConvention extends Convention {

	public static final String APPLY_DEFAULT_FK_BY_ENTITY_NAME_AND_SUFFIX = "apply-default-foreign-key-by-entity-name-and-suffix";
	public String defaultSuffix;
	
	public String getDefaultSuffix() {
		return defaultSuffix;
	}
	public void setDefaultSuffix(String defaultSuffix) {
		this.defaultSuffix = defaultSuffix;
	}
	@Override
	public void apply(BusinessModel model) {
		
	}  
	
}
