package net.sf.minuteProject.strategy.service;

import java.util.ArrayList;

import org.apache.commons.lang.text.StrTokenizer;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.service.Scope;
import net.sf.minuteProject.configuration.bean.service.Strategy;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.ViewUtils;

public class ServiceView {

	public String getStrategyMethod (Strategy strategy) {
		for (Property property : strategy.getProperties()) {
			if (property.getName().equals("method"))
				return property.getValue();
		}
		return "NO METHOD FOR STRATEGY";
	}
	
	public String getStrategyOutputParam (Strategy strategy) {
		if (strategy.getName().equals("SearchOnLogicalIdBaseOnVirtualPrimaryKey")
		   || strategy.getName().equals("SearchOnLogicalId"))
			return getEntityNameStrategyOutputParam(strategy);
		if (strategy.getName().equals("SearchListBasedOnPrototype"))
			return getEntityListStrategyOutputParam(strategy);
		return "NO OUTPUT PARAM FOR STRATEGY";		
	}
	
	private String getEntityNameStrategyOutputParam (Strategy strategy) {
		return CommonUtils.getJavaClassName(strategy.getScope().getName());
	}
	
	private String getEntityListStrategyOutputParam (Strategy strategy) {
		return "java.util.List <"+getEntityNameStrategyOutputParam(strategy)+">";
	}
	
	public String getStrategyInputParam (Strategy strategy) {
		if (strategy.getName().equals("SearchOnLogicalIdBaseOnVirtualPrimaryKey"))
			return getSearchOnLogicalIdBaseOnVirtualPrimaryKeyInputParameters(strategy);
		if (strategy.getName().equals("SearchOnLogicalId"))
			return getSearchOnLogicalIdInputParameters(strategy);
		if (strategy.getName().equals("SearchListBasedOnPrototype"))
			return getSearchListBasedOnPrototype(strategy);
		return "NO METHOD FOR STRATEGY";
	}
	
	private String getSearchListBasedOnPrototype (Strategy strategy) {
		return CommonUtils.getJavaClassName(strategy.getScope().getName()) + " "+CommonUtils.getJavaVariableName(strategy.getScope().getName());
	}
	
	private View getView (Strategy strategy) {
		String viewname = strategy.getScope().getEntity();
		Database database = strategy.getScope().getService().getBusinessModel().getModel().getDataModel().getDatabase();
		return TableUtils.getView(database, viewname);	
	}
	
	private String getSearchOnLogicalIdBaseOnVirtualPrimaryKeyInputParameters(Strategy strategy) {
		View view = getView(strategy);
		return getMethodInputParameter(view.getVirtualPrimaryKeys());
	}

	private String getSearchOnLogicalIdInputParameters(Strategy strategy) {
		Column columns[] = getStrategyLogicalIdColumns(strategy);
		return getMethodInputParameter(columns);
	}
	
	private String getMethodInputParameter (Column columns []) {
		return ColumnUtils.getMethodInputParameters(columns);
	}
	
	private Column [] getStrategyLogicalIdColumns (Strategy strategy) {
		Scope scope = strategy.getScope();
		Database database = strategy.getScope().getService().getBusinessModel().getModel().getDataModel().getDatabase();
		ArrayList<Column> list = new ArrayList<Column>();
		for (Property property : strategy.getProperties()) {
			if (property.getName().equals("logicalIdField")) {
				//TODO with tables also
				Column column = ColumnUtils.getColumn(TableUtils.getView(database, scope.getName()), property.getValue());
				list.add(column);
			}
		}
		return (Column[])list.toArray(new Column[list.size()]);
	}
}
