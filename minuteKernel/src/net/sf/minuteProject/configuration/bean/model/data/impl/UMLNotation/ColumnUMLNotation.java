package net.sf.minuteProject.configuration.bean.model.data.impl.UMLNotation;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.ColumnAbstract;

public class ColumnUMLNotation extends ColumnAbstract{
	
	public ColumnUMLNotation (Column column, Table table) {
		super(column, table);
		setProperties(column.getProperties());
		setStereotype(column.getStereotype());
		setSearchable(column.isSearchable());
		setVersion(column.isVersion());
		setName(column.getName());
		setAlias(column.getAlias());
		setComment(column.getComment());
		setDescription(column.getDescription());
	}
//	
//    public String getName() {
//    	return super.getName();
//    }
    
//    public String getGeneratedBeanName() {
//    	return super.getGeneratedBeanName();
//    }
    
}
