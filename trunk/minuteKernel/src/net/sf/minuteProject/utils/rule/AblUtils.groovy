package net.sf.minuteProject.utils.rule

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Constraint;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Derivation;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

class AblUtils {

	String getConstraintName (Table table, Constraint constraint) {
		constraint.getName table 
	}
	
	String getActionName (Table table, Action action) {
		action.getName table 
	}
	
	String getDerivationName (Column column, Derivation derivation) {
		derivation.getName column
	}
	
	boolean isToGenerateBasedRulePresence (Template template, GeneratorBean bean) {
		if (bean instanceof Table) {
			Table table = (Table) bean
			return (table.getActions().size()>0 || table.getConstraints().size()>0 || hasDerivation(table))?true:false
		}
		return false
	}

	boolean hasDerivation(Table table) {
		for (Column column : table.getAttributes()) {
			if (column.getDerivations.size()>0)
				return true
		}
		return false
	}
	
}
