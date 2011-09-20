package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;

public class TargetConvention extends KernelConvention{
	
	public static final String DISABLE_UPDATABLE_CODE = "disable-updatable-code-feature";

	@Override
	public void apply(Configuration configuration) {
		if (DISABLE_UPDATABLE_CODE.equals(type))
			applyDisableUpdatableCode(configuration);
	}

	private void applyDisableUpdatableCode(Configuration configuration) {
		Target target = configuration.getTarget();
		applyDisableUpdatableCode(target);
		for (Target t : configuration.getTargets().getTargets())
			applyDisableUpdatableCode(t);
	}

	private void applyDisableUpdatableCode(Target target) {
		// TODO Auto-generated method stub
		for (TemplateTarget tt : target.getTemplateTargets()) {
			for (Template t : tt.getTemplates())
				applyDisableUpdatableCode(t);
		}
	}

	private void applyDisableUpdatableCode(Template t) {
		t.setUpdatable(false);
	}
}
