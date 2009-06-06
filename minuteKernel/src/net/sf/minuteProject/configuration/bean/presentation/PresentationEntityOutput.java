package net.sf.minuteProject.configuration.bean.presentation;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

/**
 * @author florian adler
 *
 */

public class PresentationEntityOutput extends AbstractConfiguration{

	private PresentationEntityOutputs presentationEntityOutputs;
	private List<PresentationField> presentationFields;

	public List<PresentationField> getPresentationFields() {
		if (presentationFields==null)
			presentationFields = new ArrayList<PresentationField>();
		return presentationFields;
	}

	public void setPresentationFields(List<PresentationField> presentationFields) {
		this.presentationFields = presentationFields;
	}
	
	public void addPresentationField (PresentationField presentationField) {
		getPresentationFields().add(presentationField);
	}

	public PresentationEntityOutputs getPresentationEntityOutputs() {
		return presentationEntityOutputs;
	}

	public void setPresentationEntityOutputs(
			PresentationEntityOutputs presentationEntityOutputs) {
		this.presentationEntityOutputs = presentationEntityOutputs;
	}


	
}
