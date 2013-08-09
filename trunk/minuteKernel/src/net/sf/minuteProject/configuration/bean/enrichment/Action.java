package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Rule;
import net.sf.minuteProject.configuration.bean.enumeration.CRUDEnum;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.FormatUtils;

public class Action extends Rule<Table> {

	private String defaultImplementation;
	private List<Field> fields;
	
	public String getTechnicalPackage(Template template) {
		if (getParent()!=null)
			return getParent().getPackage().getTechnicalPackage(template)+"."+StringUtils.lowerCase(FormatUtils.getJavaName(getParent().getName()));
		return super.getTechnicalPackage(template);
	}

	public String getDefaultImplementation() {
		return defaultImplementation;
	}

	public void setDefaultImplementation(String defaultImplementation) {
		this.defaultImplementation = defaultImplementation;
	}

	public List<Field> getFields() {
		if (fields == null) fields = new ArrayList<Field>();
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public void addField(Field field) {
		getFields().add(field);
	}
	
	public boolean hasDefaultImplementation () {
		return CRUDEnum.isInCRUDEnum(defaultImplementation);
	}
}
