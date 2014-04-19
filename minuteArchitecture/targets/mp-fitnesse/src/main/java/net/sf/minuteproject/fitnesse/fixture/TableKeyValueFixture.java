package net.sf.minuteproject.fitnesse.fixture;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TableKeyValueFixture extends DbSddFixture{

	@Override
	protected int getInputNumberOfColumn() {
		return 0;
	}

	@Override
	protected int getNumberOfColumn() {
		return 2;
	}

	@Override
	protected void doStaticTable(int arg0) {
		// TODO Auto-generated method stub
	}

    public static Map<String, String> getKeyValueStringMap(String tableName, String keyColumn, String valueColumn) throws SQLException{
        // select key value column
        Map<String, String> map = new HashMap<String, String>();
        String query = "select "+ keyColumn + " , "+ valueColumn +" from "+tableName;
        Object[][] table = executeQuery(query);
        for (int i = 0; i < table.length; i++) {
            Object [] row = table[i];
            map.put(row[0].toString(), row[1].toString());
        }
        return map;
    }
}
