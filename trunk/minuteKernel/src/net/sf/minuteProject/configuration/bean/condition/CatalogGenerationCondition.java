package net.sf.minuteProject.configuration.bean.condition;

import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.FunctionModel;
import net.sf.minuteProject.configuration.bean.GenerationCondition;

public class CatalogGenerationCondition extends AbstractConfiguration {

	private static Logger logger = Logger.getLogger(CatalogGenerationCondition.class);
	private GenerationCondition generationCondition;
	
	public GenerationCondition getGenerationCondition() {
		return generationCondition;
	}
	public void setGenerationCondition(GenerationCondition generationCondition) {
		this.generationCondition = generationCondition;
	}
}
