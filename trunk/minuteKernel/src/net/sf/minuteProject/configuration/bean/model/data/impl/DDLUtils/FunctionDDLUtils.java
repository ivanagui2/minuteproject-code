package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.FunctionColumn;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.utils.FormatUtils;

public class FunctionDDLUtils extends AbstractConfiguration implements Function {

	private List<FunctionColumn> functionColumns;
	private List<FunctionColumn> inputColumns;
	private List<FunctionColumn> outputColumns;
	
	private String catalog;
	
	public void addColumn(FunctionColumn functionColumn) {
		getFunctionColumnArray().add(functionColumn);
	}

	public String getCatalog() {
		return catalog;
	}

	private void setDirection () {
		if (functionColumns!=null) {
			inputColumns = new ArrayList<FunctionColumn>();
			outputColumns = new ArrayList<FunctionColumn>();
			for (FunctionColumn functionColumn : functionColumns) {
				if (functionColumn.getDirection().equals(Direction.IN)||functionColumn.getDirection().equals(Direction.INOUT)) 
					inputColumns.add(functionColumn);
				if (functionColumn.getDirection().equals(Direction.OUT)||functionColumn.getDirection().equals(Direction.INOUT)) 
					outputColumns.add(functionColumn);
			}		
		}
	}
	
	public FunctionColumn[] getInputColumns() {
		if (inputColumns==null) {
			inputColumns = new ArrayList<FunctionColumn>();
		}
		return (FunctionColumn[]) inputColumns.toArray(new FunctionColumn[inputColumns.size()]);
	}

	public FunctionColumn[] getOutputColumns() {
		if (outputColumns==null) {
			outputColumns = new ArrayList<FunctionColumn>();
		}
		return (FunctionColumn[]) outputColumns.toArray(new FunctionColumn[outputColumns.size()]);
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	private List<FunctionColumn> getFunctionColumnArray() {
		if (functionColumns==null) {
			functionColumns = new ArrayList<FunctionColumn>();
		}
		return functionColumns;
	}
	
	public FunctionColumn [] getFunctionColumns() {
		List<FunctionColumn> columns = getFunctionColumnArray();
		return (FunctionColumn []) columns.toArray(new FunctionColumn[columns.size()]);
	}

	public void setFunctionColumns(List<FunctionColumn> functionColumns) {
		this.functionColumns = functionColumns;
	}
	
	public String getTechnicalPackage(Template template)
	{
		return StringUtils.lowerCase(getCatalog());
	}
}
