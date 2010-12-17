package net.sf.minuteProject.plugin.play;

import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.plugin.grails.GrailsUtils;

public class PlayUtils {

	public boolean hasStereotype (Column column) {
		if (column!=null) {
			Stereotype stereotype = column.getStereotype();
			if (isPlayStereotype(stereotype))
				return true;
		}
		return false;
	}

	private boolean isPlayStereotype(Stereotype stereotype) {
		if (stereotype!=null) {
			String stereo = stereotype.getStereotype();
			if (getStereotype(stereo)!=null)
				return true;
		}
		return false;
	}

	private String getStereotype(String stereo) {
		if ("url".equals(stereo)) return "URL";
		if ("email".equals(stereo)) return "Email";
		return null;
	}
	
	public static String getToString (Table table) {
		return GrailsUtils.getToString(table);
	}
}
