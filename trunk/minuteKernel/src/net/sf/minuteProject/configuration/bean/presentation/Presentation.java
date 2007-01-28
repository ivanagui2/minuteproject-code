package net.sf.minuteProject.configuration.bean.presentation;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Configuration;

public class Presentation extends AbstractConfiguration{
	
	private List <PresentationBlock> presentationBlocks;
	private Configuration configuration;
	
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void addPresentationBlock (PresentationBlock presentationBlock) {
		presentationBlock.setPresentation(this);
		if (presentationBlocks==null)
			presentationBlocks = new ArrayList();
		presentationBlocks.add(presentationBlock);
	}

	public void setPresentationBlocks(List<PresentationBlock> presentationBlocks) {
		this.presentationBlocks = presentationBlocks;
	}

	public List<PresentationBlock> getPresentationBlocks() {
		return presentationBlocks;
	}
	
	
}
