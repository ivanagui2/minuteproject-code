package net.sf.minuteproject.fitnesse.fixture;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.minuteproject.model.db.Column;
import net.sf.minuteproject.utils.database.DatabaseUtils;
import net.sf.minuteproject.utils.query.QueryUtils;
import fit.ColumnFixture;

public abstract class DbInsertUpdateDeleteFixture extends ColumnFixture{

	Logger log = Logger.getLogger(this.getClass());
	
	public String insert() {
		String query = QueryUtils.buildInsertStatement(getTable(), getColumns(), getColumnValue(), false);
		log.debug("insert query = "+query);
//		System.out.println("insert query = "+query);
		
		return action(query);
	}
	
	public String insertWithTimeAsFunction() {
		String query = QueryUtils.buildInsertStatement(getTable(), getColumns(), getColumnValue(), true);
		log.debug("insert query = "+query);
//		System.out.println("insert query = "+query);
		
		return action(query);
	}
	
	public String update() {
		String query = QueryUtils.buildUpdateStatement(getTable(), getColumnIndex(), getColumnValue(), getColumnWhereIndex(), getColumnWhereValue());
		log.debug("update query = "+query);
		return action(query);
	}
	
	public String delete() {
		String query = QueryUtils.buildDeleteStatement(getTable(), getColumnWhereIndex(), getColumnWhereValue());
		log.debug("delete query = "+query);
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
			connection.close();
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
	
	protected abstract Map<Integer, String> getColumnWhereIndex () ;
	
	protected abstract Map<String, String> getColumnWhereValue () ;

}
