package net.sf.minuteproject.fitnesse.fixture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class DbSddFixture extends DbTableFixture{
	public static int SDD_INPUT_ROW_VALUE = FIELD_ROW_INDEX + 1;
	public static int SDD_OUPUT_GENERAL_ROW_INDEX = SDD_INPUT_ROW_VALUE + 1;
	public static int SDD_OUTPUT_ROW_INDEX = SDD_OUPUT_GENERAL_ROW_INDEX + 1;
	public static int SDD_OUTPUT_ROW_VALUE = SDD_OUTPUT_ROW_INDEX + 1; 
	
	protected void checkResultSet () {
		int rowToCheck = getRowToCheck(SDD_OUTPUT_ROW_VALUE);
		for (int i = 1; i <= rowToCheck; i++) {
			checkResultSetRow (i+SDD_OUTPUT_ROW_INDEX);
		}		
	}
	protected void checkCount (String actual) {
		check (SDD_OUPUT_GENERAL_ROW_INDEX, ROW_COUNT_COLUMN_INDEX, actual);
	}

	
	@Override
	protected String getTable() {
		return "to fill";
	}
	
	protected Object[][] getResultSet(ResultSet rs) throws SQLException {
		int len = rs.getMetaData().getColumnCount();
		
		List<Object[]> list = new ArrayList<Object[]>() ;
		while (rs.next())  {
			Object [] row = new Object[len];
			for (int j = 0; j < len; j++) {
				Object o = rs.getObject(j+1);
				if (o==null)
					o = new String (">null value returned<");
				row[j]=o;
			}
			list.add(row);
		}
		Object [][] table =  new Object[list.size()][];
		for (int i = 0; i< list.size(); i++) {
			table[i]=list.get(i);
		}
		return table;
	}
	protected void check (int row, int column, String actual) {
		if (actual!=null){
			if (actual.equals(getText(row, column)))
				right(row, column);
			else
				wrong(row, column, actual);
		} else {
			if (getText(row, column).equals(NOT_PRESENT))
				right(row, column);
			else
				wrong (row, column, NOT_PRESENT);
		}
	}

	
	protected int getRowToCheck(int row) {
		int rowToCheck = 0;
		int i = row;
		String previousText = new String();
		while (i<100) {
			String text = getText(i, 0);
			if (text.startsWith(ROW_VALUE_INDENTIFIER) && !previousText.equals(text))
				rowToCheck++;
			else {
				break;
			}
			previousText = text;
			i++;
		}
		return rowToCheck;
	}
	
	protected void checkResultSetRow (int row) {
		int length = getColumnIndex().size();
		for (int i = 1; i <= length; i++) {
			check (row, i, getResultSetCell(row, i));
		}		
	}
	
	protected String getResultSetCell (int row, int column) {
		return getResultSetObject(row, column).toString();
	}
	
	protected int getMaxReturnRow() {
		maxReturnRow = getInt(GENERAL_ROW_INDEX, MAX_RETURN_ROW_COLUMN_INDEX);
		return maxReturnRow;
	}
	
	private void initColumnIndex() {
		int size = getNumberOfRealColumn();
		for (int i = 1; i <= size; i++) {
			String text = getText(SDD_OUTPUT_ROW_INDEX, i);
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
	
	protected Object getResultSetObject(int row, int column) {
		int len = resultSet.length;
		row = row-SDD_OUPUT_GENERAL_ROW_INDEX-2;
		column = column - 1;
		if (row < len) {
			System.out.println("row = "+row);
			int lenrow = resultSet[row].length;
			if (column < lenrow) {
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
		for (int i = 1; i <= getNumberOfColumn(); i++) {
			String text = getText(SDD_OUTPUT_ROW_INDEX, i);
			if (text.equals(previousColumnValue)) {
				break;
			}
			previousColumnValue = new String (text);
			size = i;
		}	
		return size;
	}

	
	public Map<String, String> getColumnValue() {
		if (columnValue==null) {
			columnValue = new HashMap<String, String>(getInputNumberOfColumn());
			setColumnValues(columnValue, SDD_INPUT_ROW_VALUE);
		}
		return columnValue;
	}
	
	protected void setColumnValues (Map<String, String> outputMap, int row) {
		Set<Entry<Integer, String>> set = getInputColumnIndex().entrySet();
		for (Entry<Integer, String> entry : set) {
			String text = getText(row, entry.getKey());
			outputMap.put(entry.getValue(), text);
		}
	}
	public Map<Integer, String> getInputColumnIndex() {
		if (columnIndex==null) {
			columnIndex = new HashMap<Integer, String>();
			initInputColumnIndex();
		}
		return columnIndex;
	}
	private void initInputColumnIndex() {
		int size = getNumberOfRealColumn();
		for (int i = 1; i <= size; i++) {
			String text = getText(FIELD_ROW_INDEX, i);
			columnIndex.put(i, text);
		}
	}

	protected abstract int getInputNumberOfColumn(); 
}
