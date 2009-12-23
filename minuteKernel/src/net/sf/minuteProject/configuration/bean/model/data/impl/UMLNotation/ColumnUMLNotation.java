package net.sf.minuteProject.configuration.bean.model.data.impl.UMLNotation;

import java.util.List;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.ColumnAbstract;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.FormatUtils;

public class ColumnUMLNotation extends ColumnAbstract{
	
	public ColumnUMLNotation (Column column, Table table) {
		super(column, table);
		setProperties(column.getProperties());
	}
	
    public String getName() {
    	return super.getName();
    }
    
}
