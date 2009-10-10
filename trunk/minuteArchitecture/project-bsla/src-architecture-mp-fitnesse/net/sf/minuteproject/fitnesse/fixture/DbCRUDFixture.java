package net.sf.minuteproject.fitnesse.fixture;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import net.sf.minuteproject.utils.database.DatabaseUtils;
import net.sf.minuteproject.utils.query.QueryUtils;

import fitnesse.fixtures.TableFixture;

public abstract class DbCRUDFixture extends TableFixture {

	public static int GENERAL_ROW_INDEX = 0;
	public static int FIELD_ROW_INDEX = GENERAL_ROW_INDEX + 1;
	public static int EXPRESSION_ROW_INDEX = FIELD_ROW_INDEX + 1;
	public static int VALUE_ROW_INDEX = EXPRESSION_ROW_INDEX + 1;
	public static int ORDER_ROW_INDEX = VALUE_ROW_INDEX + 1;
	
	public static int OUPUT_GENERAL_ROW_INDEX = ORDER_ROW_INDEX + 1;
	
	public static int ROW_VALUE_INDEX = OUPUT_GENERAL_ROW_INDEX + 1;
	
	public static int MAX_RETURN_ROW_COLUMN_INDEX = 2;
	public static int ROW_COUNT_COLUMN_INDEX = 2;
	
	public static String ROW_VALUE_INDENTIFIER = "row#";
	protected int maxReturnRow;
	
	protected Map<Integer, String> columnIndex = null;
	protected Map<String, String>  columnValue = null;
	protected Map<String, String>  columnExpressionValue = null;
	protected Map<String, String>  columnOrderValue = null;
	
	protected Object[][] resultSet;
	
	public final String NOT_PRESENT = "NOT_PRESENT";
	
//	protected void check (int row, int column, String actual) {
//		if (actual!=null){
//			if (actual.equals(getText(row, column)))
//				right(row, column);
//			else
//				wrong(row, column, actual);
//		} else {
//			if (getText(row, column).equals(NOT_PRESENT))
//				right(row, column);
//			else
//				wrong (row, column, NOT_PRESENT);
//		}
//	}
	
//	protected void checkCount (String actual) {
//		check (OUPUT_GENERAL_ROW_INDEX, ROW_COUNT_COLUMN_INDEX, actual);
//	}

//	protected void checkResultSet () {
//		int rowToCheck = getRowToCheck(ROW_VALUE_INDEX);
//		System.out.println("rowToCheck "+rowToCheck);
//		for (int i = 1; i <= rowToCheck; i++) {
//			checkResultSetRow (i+OUPUT_GENERAL_ROW_INDEX);
//		}		
//	}
	
	protected int getRowToCheck(int row) {
		int rowToCheck = 0;
		int i = row;
		String previousText = new String();
		while (i<100) {
			String text = getText(i, 0);
			System.out.println("text = "+text);
			if (text.startsWith(ROW_VALUE_INDENTIFIER) && !previousText.equals(text))
				rowToCheck++;
			else {
//				System.out.println("break text="+text+", previousText ="+previousText);
				break;
			}
			previousText = text;
			i++;
		}
		return rowToCheck;
	}
//	protected void checkResultSetRow (int row) {
//		int length = getColumnIndex().size();
//		for (int i = 1; i <= length; i++) {
//			check (row, i, getResultSetCell(row, i));
//		}		
//	}
	
	protected String getResultSetCell (int row, int column) {
		return getResultSetObject(row, column).toString();
	}
	
	protected int getMaxReturnRow() {
		maxReturnRow = getInt(GENERAL_ROW_INDEX, MAX_RETURN_ROW_COLUMN_INDEX);
		return maxReturnRow;
	}
	
	private void initColumnIndex() {
		int size = getNumberOfRealColumn();
//		System.out.println("size="+size);
		for (int i = 1; i <= size; i++) {
			String text = getText(FIELD_ROW_INDEX, i);
//			System.out.println("text = '"+text+"'");
			columnIndex.put(i, text);
		}
	}
	
	public Map<Integer, String> getColumnIndex() {
		if (columnIndex==null) {
			columnIndex = new HashMap<Integer, String>();
			initColumnIndex();
		}
		return columnIndex;
	}

	public Map<String, String> getColumnExpressionValue() {
		if (columnExpressionValue==null) {
			columnExpressionValue = new HashMap<String, String>(getNumberOfColumn());
			setColumnValues(columnExpressionValue, EXPRESSION_ROW_INDEX);
		}
		return columnExpressionValue;
	}

	public void setColumnExpressionValue(Map<String, String> columnExpressionValue) {
		this.columnExpressionValue = columnExpressionValue;
	}

	public Map<String, String> getColumnOrderValue() {
		if (columnOrderValue==null) {
			columnOrderValue = new HashMap<String, String>(getNumberOfColumn());
			setColumnValues(columnOrderValue, ORDER_ROW_INDEX);
		}
		return columnOrderValue;
	}

	public void setColumnOrderValue(Map<String, String> columnOrderValue) {
		this.columnOrderValue = columnOrderValue;
	}

	public Map<String, String> getColumnValue() {
		if (columnValue==null) {
//			System.out.println("new columnValue");
			columnValue = new HashMap<String, String>(getNumberOfColumn());
			setColumnValues(columnValue, VALUE_ROW_INDEX);
		}
		return columnValue;
	}

	public void setColumnValue(Map<String, String> columnValue) {
		this.columnValue = columnValue;
	}

	public void setColumnIndex(Map<Integer, String> columnIndex) {
		this.columnIndex = columnIndex;
	}

	private void setColumnValues (Map<String, String> outputMap, int row) {
		Set<Entry<Integer, String>> set = getColumnIndex().entrySet();
		for (Entry<Integer, String> entry : set) {
			String text = getText(row, entry.getKey());
//			System.out.println("text ="+text);
			outputMap.put(entry.getValue(), text);
		}
	}
	
	protected Object[][] executeQuery(
			Map<Integer, String> columnIndex,
			Map<String, String>  columnExpressionValue,
			Map<String, String>  columnValue,
			Map<String, String>  columnOrderValue) 
		throws SQLException {
		// call factory
		String query = QueryUtils.buildQuery(getTable(), columnIndex, columnExpressionValue, columnValue,  columnOrderValue);
//		System.out.println("query="+query);
		Connection connection = DatabaseUtils.getConnection();
		if (connection ==null)
			System.out.println("connection is null");
		Statement ps = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery(query);
		int len = rs.getMetaData().getColumnCount();
		rs.last();
		int size = rs.getRow();
		rs.first();
		
//		System.out.println("size "+size+", len "+len);
		Object [][] table = new Object [size][];
		for (int i = 0; i < size; i++) {
			Object [] row = new Object[len];
			for (int j = 0; j < len; j++) {
				Object o = rs.getObject(j+1);
				if (o==null)
					o = new String (">null value returned<");
//				System.out.println("i="+i+",j="+j+",obj="+o.toString());		
				row[j]=o;
			}
			table[i] = row;
			rs.next();
		}
		connection.close();
		return table;
	}
	

	
	
	protected Object getResultSetObject(int row, int column) {
		int len = resultSet.length;
		row = row-OUPUT_GENERAL_ROW_INDEX-1;
		column = column - 1;
		if (row < len) {
			System.out.println("row = "+row);
			int lenrow = resultSet[row].length;
			if (column < lenrow) {
				System.out.println("len = "+len+", row = "+row+", lenrow = "+lenrow+", column = "+column);
				return resultSet [row] [column];
			}
		}
		return new String("NOT_PRESENT");
	}
	
	/**
	 * due to thwart Fitnesse potential ambiguity
	 * @return
	 */
	protected int getNumberOfRealColumn() {
		String previousColumnValue = null;
		int size = 0;
		System.out.println("getNumberOfColumn = "+getNumberOfColumn());
		for (int i = 1; i <= getNumberOfColumn(); i++) {
			size = i;
			String text = getText(FIELD_ROW_INDEX, i);
			if (text.equals(previousColumnValue)) {
				break;
			}
			previousColumnValue = new String (text);
		}		
		return size;
	}
	
	protected abstract int getNumberOfColumn();
	
	protected abstract String getTable();
	
//	protected abstract Object getResultSetObject(int row, int column);
	
}
