package net.sf.minuteProject.configuration.bean.model.data.impl.UMLNotation;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.ColumnAbstract;
import net.sf.minuteProject.utils.FormatUtils;

public class ColumnUMLNotation extends ColumnAbstract{
	
	public ColumnUMLNotation(Column column, Table table) {
		super(column, table);
	}
	
    public String getName()
    {
    	return super.getName();
    	//return FormatUtils.remove_ID_patternFromColumnName(super.getName());
    }
    
}
