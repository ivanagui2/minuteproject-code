package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class ViewPrimaryKeyConvention extends Convention{

	public static final String APPLY_DEFAULT_PK_OTHERWISE_FIRST_FIELD_IS_PK = "apply-default-primary-key-otherwise-first-one";
	public String type;
	public String defaultPrimaryKeyNames;
	
	
}
