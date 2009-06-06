package net.sf.minuteProject.configuration.bean.presentation;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Configuration;

public class Presentation extends AbstractConfiguration{
	
	private List <PresentationEntity> presentationEntitys;
	private List <PresentationBlock> presentationBlocks;
	
	private PresentationEntityInputs presentationEntityInputs;
	private PresentationEntityOutputs presentationEntityOutputs;
	private PresentationEntityMappings presentationEntityMappings;
	
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

	public void addPresentationEntity (PresentationEntity presentationEntity) {
		presentationEntity.setPresentation(this);
		if (presentationEntitys==null)
			presentationEntitys = new ArrayList<PresentationEntity>();
		presentationEntitys.add(presentationEntity);
	}
	
	public List<PresentationEntity> getPresentationEntitys() {
		return presentationEntitys;
	}

	public void setPresentationEntitys(List<PresentationEntity> presentationEntitys) {
		this.presentationEntitys = presentationEntitys;
	}

	public PresentationEntityInputs getPresentationEntityInputs() {
		return presentationEntityInputs;
	}

	public void setPresentationEntityInputs(
			PresentationEntityInputs presentationEntityInputs) {
		this.presentationEntityInputs = presentationEntityInputs;
	}

	public PresentationEntityOutputs getPresentationEntityOutputs() {
		return presentationEntityOutputs;
	}

	public void setPresentationEntityOutputs(
			PresentationEntityOutputs presentationEntityOutputs) {
		this.presentationEntityOutputs = presentationEntityOutputs;
	}
	
	
	
}
