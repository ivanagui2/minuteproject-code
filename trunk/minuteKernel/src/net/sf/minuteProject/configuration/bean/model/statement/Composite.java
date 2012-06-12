package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;

public class Composite extends AbstractConfiguration{

	private Composites composites;
	private Composite inputComposite, outputComposite;
	private List<Table> entities;
	private String executionType;
	
	private List<CompositeQueryElement> queries;
	
	public void setComposites(Composites composites) {
		this.composites = composites;
	}

	public String getExecutionType() {
		return executionType;
	}

	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}

	public List<CompositeQueryElement> getQueries() {
		if (queries==null)
			queries = new ArrayList<CompositeQueryElement>();
		return queries;
	}

	public void setQueries(List<CompositeQueryElement> queries) {
		this.queries = queries;
	}

	public Composites getComposites() {
		return composites;
	}

	public void addQuery (CompositeQueryElement query) {
		query.setComposite(this);
		getQueries().add(query);
	}
	
	public Composite getComposite(Direction direction) {
		if (Direction.IN.equals(direction))
			return getInputComposite();
		else
			return getOutputComposite();
	}

	private Composite getInputComposite() {
		if (inputComposite!=null)
			return inputComposite;
		return retrieveComposite(Direction.IN);
	}
	
	private Composite getOutputComposite() {
		if (outputComposite!=null)
			return outputComposite;
		return retrieveComposite(Direction.OUT);
	}

	private Composite retrieveComposite (Direction dir) {
		Composite composite = new Composite();
		composite.setName(this.name);
		composite.setExecutionType(this.executionType);
		for (CompositeQueryElement query : getQueries()) {
			Query q = query.getQuery();
			Table table = q.getEntity(dir);
			table.setName(q.getName());
			table.setPackage(q.getPackage());
			composite.getEntities().add(table);
		}
		return composite;
	}

	public List<Table> getEntities() {
		if (entities==null)
			entities = new ArrayList<Table>();
		return entities;
	}

	public void setEntities(List<Table> entities) {
		this.entities = entities;
	}
	
	public String getTechnicalPackage(Template template) {
		net.sf.minuteProject.configuration.bean.Package p = getPackage();
		if (p == null)
			return "ERROR_PACKAGE_IS_NULL";
		return p.getTechnicalPackage(template);
	}
	
}
