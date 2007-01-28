package net.sf.minuteProject.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Pool
{
    private static DataSource dataSource;
    private static DataSource pldaDataSource;
    private static DataSource sadbelDataSource;
    private static DataSource dataSourcePLDACCFFDEV2;

    static
    {
    	DriverManagerDataSource ds1 = new DriverManagerDataSource();
    	ds1.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
    	ds1.setPassword("Password_1");
    	ds1.setUsername("db2admin");
    	ds1.setUrl("jdbc:db2:PLDALC");    	
    	Properties propPlda = new Properties();
    	ds1.setConnectionProperties(propPlda);    	
    	pldaDataSource = ds1;
    	
    	
    	DriverManagerDataSource ds2 = new DriverManagerDataSource();
    	/*ds2.setDriverClassName("org.hsqldb.jdbcDriver");
    	ds2.setPassword("");
    	ds2.setUsername("sa");
    	ds2.setUrl("jdbc:hsqldb:hsql://localhost:9002");
*/
    	ds2.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
    	ds2.setPassword("Password_1");
    	ds2.setUsername("db2admin");
    	ds2.setUrl("jdbc:db2:SADBEL");    	
    	Properties prop = new Properties();
    	ds2.setConnectionProperties(prop);
    	
        DriverAdapterCPDS cpds = new DriverAdapterCPDS();
        try {
			cpds.setDriver("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        cpds.setUrl("jdbc:hsqldb:hsql://localhost:9002");
        cpds.setUser("sa");
        cpds.setPassword("");

        SharedPoolDataSource tds = new SharedPoolDataSource();
        tds.setConnectionPoolDataSource(cpds);
        tds.setMaxActive(10);
        tds.setMaxWait(50);

        //dataSource = tds;
        dataSource = ds2;
        
        //
        DriverManagerDataSource dsPLDACCFFDEV2 = new DriverManagerDataSource();
        dsPLDACCFFDEV2.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        dsPLDACCFFDEV2.setPassword("pldausr0");
        dsPLDACCFFDEV2.setUsername("pldausr0");
        dsPLDACCFFDEV2.setUrl("jdbc:db2://10.2.30.92:50006/PLDACONT");    	
    	Properties propCCFFDEV2 = new Properties();
    	dsPLDACCFFDEV2.setConnectionProperties(propCCFFDEV2);
    	dataSourcePLDACCFFDEV2 = dsPLDACCFFDEV2;
    }

	protected static DataSource getOperaDataSource() {
    	DriverManagerDataSource ds1 = new DriverManagerDataSource();
    	ds1.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
    	ds1.setPassword("");
    	ds1.setUsername("");
    	ds1.setUrl("jdbc:odbc:liege");    	
    	Properties propPlda = new Properties();
    	ds1.setConnectionProperties(propPlda);    	
    	return ds1;	
	}
	
    private static DataSource getDataSource (String driver, String user, String password, String url) {
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName(driver);
        datasource.setPassword(user);
        datasource.setUsername(password);
        datasource.setUrl(url);    	
    	Properties prop = new Properties();
    	datasource.setConnectionProperties(prop);
    	return datasource;
    }
    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public static Connection getConnectionPLDACCFFDEV2() throws SQLException
    {
        return dataSourcePLDACCFFDEV2.getConnection();
    }

	public static DataSource getDataSource(String dataSourceName) {
		if (dataSourceName.equals("plda"))
			return pldaDataSource; //getDataSource("com.ibm.db2.jcc.DB2Driver","db2admin","Password_1","jdbc:db2:PLDALC");
		else if (dataSourceName.equals("sadbel"))
			//return getDataSource("com.ibm.db2.jcc.DB2Driver","db2admin","Password_1","jdbc:db2:SADBEL");
			return dataSource;
		else if (dataSourceName.equals("opera"))
			//return getDataSource("com.ibm.db2.jcc.DB2Driver","db2admin","Password_1","jdbc:db2:SADBEL");
			//return getOperaDataSource();
			return dataSource;
		return dataSource;
	}	
  
}