package net.sf.minuteProject.configuration.bean.strategy.datamodel;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.DataModel;

public class PrimaryKeyPolicyPattern extends AbstractConfiguration {
	
	public static final String SEQUENCE = "sequencePattern";
	public static final String IDENTITY = "identityPattern";
	public static final String OTHER = "not defined";
	
	private PrimaryKeyPolicy primaryKeyPolicy;
	private String suffix;
	private String prefix;
	private String sequenceName;
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public PrimaryKeyPolicy getPrimaryKeyPolicy() {
		return primaryKeyPolicy;
	}
	public void setPrimaryKeyPolicy(PrimaryKeyPolicy primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}
	
	public PrimaryKeyPolicyPatternEnum getPrimaryKeyPolicyPatternEnum () {
		if (getName()!=null) {
			if (getName().equals(SEQUENCE))
				return PrimaryKeyPolicyPatternEnum.SEQUENCE;
			if (getName().equals(IDENTITY))
				return PrimaryKeyPolicyPatternEnum.IDENTITY;
			return PrimaryKeyPolicyPatternEnum.OTHER;
		}
		return PrimaryKeyPolicyPatternEnum.OTHER;
	}
	
	
	
}
