package net.sf.minuteProject.configuration.bean;

import java.io.File;

import javax.sql.DataSource;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataModel {
	
	private Model model;
	private Database database;
	private DataSource datasource;
	private FileSource fileSource;

	private DriverManagerDataSource driverManagerDataSource;
	
	public DriverManagerDataSource getDriverManagerDataSource() {
		return driverManagerDataSource;
	}

	public void setDriverManagerDataSource(DriverManagerDataSource driverManagerDataSource) {
		this.driverManagerDataSource = driverManagerDataSource;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public void loadDatabase () {
		/*if (isDatabaseOnFile())
			database = new DatabaseIO().read(getFileSourceName());		
		else */
		if (driverManagerDataSource!=null) {
			DataSource dataSource = driverManagerDataSource;
		    Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
		    database = platform.readModelFromDatabase(null);
		    writeDatabase(database);
		}
	}
	
	private void writeDatabase (Database database) {
	    String filename = getFileSourceName();
	    if (filename!= null)
	    	new DatabaseIO().write(database, filename);
	}
	
	
	private boolean isDatabaseOnFile() {
	    String filename = getFileSourceName();
	    if (filename!= null) {
	    	File file = new File (filename);
	    	if (file.exists())
	    		return true;
	    }
	    return false;
	}
	
	private String getFileSourceName() {
	    String filename = null;
	    if (fileSource!=null) {
	    	filename = fileSource.getName();
	    	if (fileSource.getDir()!=null)
	    		filename = filename+"/"+fileSource.getDir()+".xml";
	    	else 
	    		filename = filename+"/"+model.getName()+".xml";
	    }
	    return filename;
	}
	

	public Database getDatabase() {
		return database;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public FileSource getFileSource() {
		return fileSource;
	}

	public void setFileSource(FileSource filesource) {
		this.fileSource = filesource;
	}
	
}
