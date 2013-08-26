package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.convention.Conventions;
import net.sf.minuteProject.configuration.bean.enrichment.convention.ModelConvention;

public class Enrichment<T> extends AbstractConfiguration {
	
//	private BusinessModel businessModel;
	private T t;
	private Conventions conventions;
	

//	public BusinessModel getBusinessModel() {
//		return businessModel;
//	}
//
//	public void setBusinessModel(BusinessModel businessModel) {
//		this.businessModel = businessModel;
//	}

	public Conventions getConventions() {
		return conventions;
	}

	public T getModel() {
		return t;
	}

	public void setModel(T t) {
		this.t = t;
	}

	public void setConventions(Conventions conventions) {
		this.conventions = conventions;
	}

}
