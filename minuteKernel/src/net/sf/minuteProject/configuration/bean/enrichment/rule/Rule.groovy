package net.sf.minuteProject.configuration.bean.enrichment.rule

import org.apache.tools.ant.types.selectors.ExtendSelector;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.utils.FormatUtils;

class Rule <T extends GeneratorBean> extends AbstractConfiguration{

	String getName (T t) {
		if (getName()!=null)
			FormatUtils.getJavaName t.getAlias()
		FormatUtils.getJavaName getAlias()
	}
	
	String value, errorMessage
	
}
