package net.sf.minuteProject.configuration.bean.model.data.impl.UMLNotation;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.ReferenceAbstract;

public class ReferenceUMLNotation extends ReferenceAbstract {

	TableUMLNotation foreignTable;
	ColumnUMLNotation foreignColumn;
	
	public ReferenceUMLNotation(Reference reference) {
		super(reference);
		foreignTable = new TableUMLNotation (reference.getForeignTable());
		foreignColumn = new ColumnUMLNotation (reference.getForeignColumn());
	}

	public Table getForeignTable () {
		return foreignTable;
	}
	
	public Column getForeignColumn () {
		return foreignColumn;
	}
	
	public String getForeignColumnName () {
		return foreignColumn.getName();
	}
	
}
