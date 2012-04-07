package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.model.statement.Queries;

import org.apache.log4j.Logger;

public class StatementModel {
	private static Logger logger = Logger.getLogger(StatementModel.class);
	private Queries queries;
	
	private Model model;
	
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Queries getQueries() {
		return queries;
	}
	public void setQueries(Queries queries) {
		this.queries = queries;
	}
	
	
}
