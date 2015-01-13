package net.sf.minuteProject.utils.action

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.FormatUtils;

class ActionUtils {

	String getActionLabel (Action action) {
		if (!StringUtils.isEmpty(action.name)) 
			action.name
		else
			action.description
	}
	
	String getActionMethod (Action action) {
		FormatUtils.getEachWordFirstLetterUpper(action.name, " ")
	}
	
	String getTargetUrl (Action action, Model model) {
		String queryId = action.getQueryId()
		Query query = model.getStatementModel().getQueryById(queryId)
		FormatUtils.getEachWordFirstLetterUpper(action.name, " ")
	}
}
