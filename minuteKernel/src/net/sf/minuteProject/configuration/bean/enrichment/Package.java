package net.sf.minuteProject.configuration.bean.enrichment;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.security.SecurityColor;

public class Package extends AbstractConfiguration {

	private SecurityColor securityColor;
	private Enrichment enrichment;
	private String alias;
	
	public SecurityColor getSecurityColor() {
		return securityColor;
	}

	public void setSecurityColor(SecurityColor securityColor) {
		this.securityColor = securityColor;
	}

	public Enrichment getEnrichment() {
		return enrichment;
	}

	public void setEnrichment(Enrichment enrichment) {
		this.enrichment = enrichment;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}
