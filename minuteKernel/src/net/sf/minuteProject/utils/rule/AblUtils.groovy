package net.sf.minuteProject.utils.rule

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Constraint;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Derivation;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

class AblUtils {

	AblUtils(){}
	
	String getPackageList (Model model) {
		"todo"
	}
	
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
			if (table.getActions()!=null && table.getActions().size()>0)
				return true
			if (table.getConstraints()!=null && table.getConstraints().size()>0)
				return true
			return hasDerivation(table)
		}
		return false
	}

	boolean hasDerivation(Table table) {
		for (Column column : table.getAttributes()) {
			if (column.getDerivations()!=null && column.getDerivations().size()>0)
				return true
		}
		return false
	}
	
}
