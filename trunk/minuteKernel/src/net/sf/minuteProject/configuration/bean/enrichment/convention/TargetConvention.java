package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.Resource;
import net.sf.minuteProject.configuration.bean.ResourceTarget;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;

public class TargetConvention extends KernelConvention{
	
	public static final String ENABLE_UPDATABLE_CODE = "enable-updatable-code-feature";
	public static final String DISABLE_TIMESTAMP_COMMENT_MARKER = "disable-timestamp-comment-marker";
	public static final String ENABLE_RESOURCE_COPY = "enable-resource-copy";

	@Override
	public void apply(Configuration configuration) {
		if (ENABLE_UPDATABLE_CODE.equals(type) || 
			DISABLE_TIMESTAMP_COMMENT_MARKER.equals(type) ||
			ENABLE_RESOURCE_COPY.equals(type) )
			applyConvention(configuration);
	}

	private void applyConvention(Configuration configuration) {
		Target target = configuration.getTarget();
		applyConvention(target);
		for (Target t : configuration.getTargets().getTargets())
			applyConvention(t);
	}

	private void applyConvention(Target target) {
		// TODO Auto-generated method stub
		for (TemplateTarget tt : target.getTemplateTargets()) {
			for (Template t : tt.getTemplates())
				apply(t);
		}
		for (ResourceTarget rt : target.getResourceTargets()) {
			for (Resource r : rt.getResources())
				r.setGenerable(false);
		}
	}

	private void apply(Template t) {
		if (ENABLE_UPDATABLE_CODE.equals(type)) 
			t.setUpdatable(true);
		if (DISABLE_TIMESTAMP_COMMENT_MARKER.equals(type)) 
			t.setTimestampMarkerEnabled(false);
	}
}
