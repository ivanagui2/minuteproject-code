package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.utils.FormatUtils;

public abstract class BeanNamingConvention <T extends GeneratorBean> extends ModelConvention{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3918614254625282929L;
	
	public final static String APPLY_FIELD_ALIAS_BASED_ON_CAMEL_CASE ="apply-field-alias-based-on-camelcase";

	@Override
	public void apply(BusinessModel model) {
		if (APPLY_FIELD_ALIAS_BASED_ON_CAMEL_CASE.equals(type))  {
			for (T	t : getBeans(model)) {
				applyCamelCaseAlias (t);
			}
		}

	}

	protected abstract List<T> getBeans(BusinessModel model);
	
	protected void applyCamelCaseAlias(T t)  {
		if (FormatUtils.isCamelCaseAlias(t)) {
			t.setAlias(FormatUtils.decamelCase(t.getName()));
		}

	}
	
}
