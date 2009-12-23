package net.sf.minuteproject.fitnesse.fixture;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import net.sf.minuteproject.model.db.Column;
import net.sf.minuteproject.utils.database.DatabaseUtils;
import net.sf.minuteproject.utils.query.QueryUtils;
import fit.ColumnFixture;

public abstract class DbInsertFixture extends ColumnFixture{

	public String insert() {
		
		String query = QueryUtils.buildInsertStatement(getTable(), getColumns(), getColumnValue());
		return insert(query);
	}
	
	protected String insert(String query) {
		Connection connection;
		try {
			connection = DatabaseUtils.getConnection();
			if (connection ==null)
				System.out.println("connection is null");
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (e.getMessage());
		} 
		return "OK";
	}
	
	protected abstract String getTable();
	
	protected abstract Map<Integer, Column> getColumns();
	
	protected abstract Map<Integer, String> getColumnIndex () ;
	
	protected abstract Map<String, String> getColumnValue () ;

}
