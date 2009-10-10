package net.sf.minuteproject.fitnesse.fixture;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import net.sf.minuteproject.utils.database.DatabaseUtils;
import net.sf.minuteproject.utils.query.QueryUtils;
import fit.ColumnFixture;

public abstract class DbInsertUpdateDeleteFixture extends ColumnFixture{

	public String insert() {
		
		String query = QueryUtils.buildInsertStatement(getTable(), getColumnIndex(), getColumnValue());
		return action(query);
	}
	
	public String update() {
		String query = QueryUtils.buildUpdateStatement(getTable(), getColumnIndex(), getColumnValue(), getColumnWhereIndex(), getColumnWhereValue());
		return action(query);
	}
	
	public String delete() {
		String query = QueryUtils.buildDeleteStatement(getTable(), getColumnWhereIndex(), getColumnWhereValue());
		return action(query);
	}
	
	protected String action(String query) {
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
	
	protected abstract Map<Integer, String> getColumnIndex () ;
	
	protected abstract Map<String, String> getColumnValue () ;
	
	protected abstract Map<Integer, String> getColumnWhereIndex () ;
	
	protected abstract Map<String, String> getColumnWhereValue () ;

}
