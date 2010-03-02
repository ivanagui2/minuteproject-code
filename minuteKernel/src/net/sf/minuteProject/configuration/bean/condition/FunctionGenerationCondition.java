package net.sf.minuteProject.configuration.bean.condition;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.GenerationCondition;

import org.apache.log4j.Logger;

public class FunctionGenerationCondition extends AbstractConfiguration {

	private static Logger logger = Logger.getLogger(FunctionGenerationCondition.class);
	private GenerationCondition generationCondition;
	
	public GenerationCondition getGenerationCondition() {
		return generationCondition;
	}
	public void setGenerationCondition(GenerationCondition generationCondition) {
		this.generationCondition = generationCondition;
	}
}
