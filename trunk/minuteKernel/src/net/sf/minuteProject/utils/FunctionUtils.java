package net.sf.minuteProject.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.FunctionColumn;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.FunctionColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.FunctionDDLUtils;

public class FunctionUtils {
	
	public static List<Function> getFunctions(DataModel dataModel) {
		
		Connection connection = ConnectionUtils.getConnection(dataModel);
		if (connection!=null) {
			String schema = dataModel.getSchema();
			return getFunctions (connection, schema);
		}
		return new ArrayList<Function>();
	}
	
	public static List<Function> getFunctions(Connection connection, String schema) {
		Function function = null;
		try {
			List<Function> functions = new ArrayList<Function>();
		    
			DatabaseMetaData dbMetaData = connection.getMetaData();
		    ResultSet rs = dbMetaData.getProcedureColumns(connection.getCatalog(), schema, null, null);
	
		    String previousProcedureCatalog = "";
		    String previousProcedureName    = "";
		    while(rs.next()) {
		      // get stored procedure metadata
		      String procedureCatalog     = rs.getString(1);
		      String procedureSchema      = rs.getString(2);
		      String procedureName        = rs.getString(3);
		      String columnName           = rs.getString(4);
		      short  columnReturn         = rs.getShort(5);
		      int    columnDataType       = rs.getInt(6);
		      String columnReturnTypeName = rs.getString(7);
		      int    columnPrecision      = rs.getInt(8);
		      int    columnByteLength     = rs.getInt(9);
		      short  columnScale          = rs.getShort(10);
		      short  columnRadix          = rs.getShort(11);
		      short  columnNullable       = rs.getShort(12);
		      String columnRemarks        = rs.getString(13);
	
		      
		      if (!procedureCatalog.equals(previousProcedureCatalog) || !procedureName.equals(previousProcedureName)) {
		    	 if (function!=null)
		    		 functions.add(function);
		    	 function = new FunctionDDLUtils();
		         function.setCatalog(procedureCatalog);
		         function.setName(procedureName);
		      }
		      previousProcedureCatalog = (procedureCatalog!=null)?procedureCatalog:"";
		      previousProcedureName    = (procedureName!=null)?procedureName:"";
		      
		      FunctionColumn functionColumn = new FunctionColumnDDLUtils();
		      functionColumn.setName(columnName);
		      functionColumn.setDirection(getDirection(columnReturn));
		      functionColumn.setTypeCode(columnDataType);
		      functionColumn.setType(columnReturnTypeName);//getType(columnDataType));
		      functionColumn.setPrecisionRadix(columnRadix);
		      functionColumn.setSizeAndScale(columnByteLength, columnScale);
		      functionColumn.setSize(columnByteLength+"");
		      functionColumn.setPrecision(columnPrecision);
		      functionColumn.setRequired((columnNullable==0)?true:false);
		      functionColumn.setDescription(columnRemarks);
		      
		      function.addColumn(functionColumn);
		      
//		      System.out.println("procedureCatalog =" + procedureCatalog +" - "+"procedureName =" + procedureName +" - "+columnName);
		    }
		    functions.add(function);
		    connection.close();
		    return functions;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return new ArrayList<Function>();
	}
	
	private static Direction getDirection(int direction) {
		if (direction==1)
			return Direction.IN;
		if (direction==4)
			return Direction.OUT;
		return Direction.INOUT;
	}
	
	private static String getType(int codeType) {
//		java.sql.Types.
		return "VARCHAR";
	}
}
