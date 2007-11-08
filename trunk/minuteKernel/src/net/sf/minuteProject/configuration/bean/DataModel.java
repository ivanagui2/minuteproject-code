package net.sf.minuteProject.configuration.bean;

import java.io.File;

import javax.sql.DataSource;

import net.sf.minuteProject.configuration.bean.model.data.DataModelFactory;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicy;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;

//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.apache.commons.dbcp.BasicDataSource;

public class DataModel {
	
	private Model model;
	private Database database;
	private DataSource datasource;
	private FileSource fileSource;
	private String schema;
	private PrimaryKeyPolicy primaryKeyPolicy;
	
	private BasicDataSource basicDataSource;
	
	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}

	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public void loadDatabase () {
		database = DataModelFactory.getInstance().getDatabase(this);
		//database.loadDatabase(this);
		/*if (isDatabaseOnFile())
			database = new DatabaseIO().read(getFileSourceName());		
		else 
		if (basicDataSource!=null) {
			//basicDataSource.set
			DataSource dataSource = basicDataSource;
			//basicDataSource.set
		    Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
		    platform.getModelReader().setDefaultSchemaPattern(getSchema());
		    database = platform.readModelFromDatabase(null);
		    writeDatabase(database);
		}*/
	}
	/*
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
	    String filedir = null;
	    if (fileSource!=null) {
	    	filename = fileSource.getName();
	    	filedir = fileSource.getDir();
	    	if (filename!=null)
	    		filename = filedir+"/"+filename+".xml";
	    	else 
	    		filename = filedir+"/"+model.getName()+".xml";
	    }
	    return filename;
	}*/
	

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

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public PrimaryKeyPolicy getPrimaryKeyPolicy() {
		return primaryKeyPolicy;
	}

	public void setPrimaryKeyPolicy(PrimaryKeyPolicy primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}
	
}
