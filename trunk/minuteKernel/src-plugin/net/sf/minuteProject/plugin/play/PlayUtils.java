package net.sf.minuteProject.plugin.play;

import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.Column;

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
		String stereo = stereotype.getStereotype();
		if (getStereotype(stereo)!=null)
			return true;
		return false;
	}

	private String getStereotype(String stereo) {
		if ("url".equals(stereo)) return "URL";
		if ("email".equals(stereo)) return "Email";
		return null;
	}
	
}
