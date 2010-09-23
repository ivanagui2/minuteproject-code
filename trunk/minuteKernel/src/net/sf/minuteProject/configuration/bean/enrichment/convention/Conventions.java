package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Enrichment;

public class Conventions extends AbstractConfiguration{

	private Enrichment enrichment;
	public List<Convention> conventions;
	
	public List<Convention> getConventions() {
		if (conventions == null) conventions = new ArrayList<Convention>();
		return conventions;
	}
	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
	public Enrichment getEnrichment() {
		return enrichment;
	}
	public void setEnrichment(Enrichment enrichment) {
		this.enrichment = enrichment;
	}
	
	public void addConvention (Convention convention) {
		getConventions().add(convention);
	}
	
//	public void addConvention (ViewPrimaryKeyConvention convention) {
//		getConventions().add(convention);
//	}	
}
