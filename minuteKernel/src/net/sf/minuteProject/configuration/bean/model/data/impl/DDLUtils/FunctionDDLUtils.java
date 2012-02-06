package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.FunctionColumn;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.utils.ConvertUtils;

import org.apache.commons.lang.StringUtils;

public class FunctionDDLUtils extends AbstractConfiguration implements Function {

	private List<FunctionColumn> functionColumns;
	private List<FunctionColumn> inputColumns;
	private List<FunctionColumn> outputColumns;
	private Direction direction;
	private Table inputEntity, outputEntity;
	private Package pack;
	private Database database;
	
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
			//inputColumns = new ArrayList<FunctionColumn>();
			setDirection();
		}
		return (FunctionColumn[]) inputColumns.toArray(new FunctionColumn[inputColumns.size()]);
	}

	public List<FunctionColumn> getColumns(Direction dir) {
		if (Direction.IN.equals(dir) || Direction.INOUT.equals(dir) || Direction.NONE.equals(dir)) {
			if (inputColumns!=null) {
				inputColumns = getFunctionColumn (functionColumns, dir); 
			}
			return inputColumns;	
		}
		if (dir.equals(Direction.OUT)) {
			if (outputColumns!=null) {
				outputColumns = getFunctionColumn (functionColumns, dir); 
			}
			return inputColumns;	
		}
		if (dir.equals(Direction.ANY))
			return getFunctionColumn();
		return getFunctionColumn();
	}
	
	private List<FunctionColumn> getFunctionColumn() {
		return getFunctionColumnArray();
	}

	private List<FunctionColumn> getFunctionColumn(List<FunctionColumn> functionColumns, Direction dir) {
		List<FunctionColumn> list = new ArrayList<FunctionColumn>();
		if (functionColumns!=null) {
			for (FunctionColumn functionColumn : functionColumns) {
				if (functionColumn.getDirection().equals(dir)||functionColumn.getDirection().equals(Direction.INOUT)) 
					list.add(functionColumn);
			}
		}
		return list;
	}

	public FunctionColumn[] getOutputColumns() {
		if (outputColumns==null) {
//			outputColumns = new ArrayList<FunctionColumn>();
			setDirection();
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

	public FunctionColumn [] getColumns() {
		if (direction!=null && Direction.OUT.equals(direction))
			return getOutputColumns();
		return getInputColumns();
	}
	
	public String getTechnicalPackage(Template template)
	{
		return StringUtils.lowerCase(getCatalog());
	}

	@Override
	public Direction getDirection() {
		if (direction!=null)
			return direction;
		boolean isInput = false;
		boolean isOutput = false;
		if (getInputColumns().length>1)
			isInput = true;
		if (getOutputColumns().length>1)
			isOutput = true;	
		if (isInput && isOutput)
			return Direction.INOUT;
		if (isInput && !isOutput)
			return Direction.IN;		
		if (!isInput && isOutput)
			return Direction.OUT;			
		return Direction.NONE;			
	}

	public Table getInputEntity() {
		if (inputEntity==null)
		   inputEntity= getEntityDirection(Direction.IN);
		return inputEntity;
	}

	public Table getOutputEntity() {
		if (outputEntity==null)
			outputEntity= getEntityDirection(Direction.OUT);
			return outputEntity;
	}
	
	public Table getEntity(Direction dir) {
		if (dir.equals(Direction.IN))
			return getInputEntity();
		if (dir.equals(Direction.OUT))
			return getOutputEntity();		
		return getEntityDirection(dir);
	}
	
	private Table getEntityDirection(Direction dir) {
		org.apache.ddlutils.model.Table table = new org.apache.ddlutils.model.Table();
		table.setName(getName());
		table.setCatalog(catalog);
		table.setType(Table.TABLE);
		addColumn (table, dir);
		Table entity = new TableDDLUtils(table);
		entity.setPackage(getPackage());
		entity.setDatabase(database);
		return entity;
	}
	
	private void addColumn(org.apache.ddlutils.model.Table table, Direction direction) {
		for (FunctionColumn fc : getColumns (direction)) {
			table.addColumn(getColumn (fc));
		}
	}

	private org.apache.ddlutils.model.Column getColumn(FunctionColumn fc) {
		org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();
		column.setName(fc.getName());
		column.setType(convertType(fc.getType()));
		column.setScale(fc.getScale());
		column.setSize(fc.getSize());
		column.setPrecisionRadix(fc.getPrecisionRadix());
		column.setTypeCode(fc.getTypeCode());
		return column;
	}

	private String convertType(String type) {
		return ConvertUtils.getDDLUtilsTypeFromDBType(type);
	}

	public Package getPackage() {
		return pack;
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

}
