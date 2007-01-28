package net.sf.minuteProject.utils;

import javax.sql.DataSource;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;


public class SchemaReader {

	public static Database readDatabase(String name, String toFile)
	{
		//DataSource dataSource = Pool.getDataSource("sadbel");
		DataSource dataSource = Pool.getDataSource(name);
	    Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
	    Database database = platform.readModelFromDatabase(null);
	    database.setName(name);
	    new DatabaseIO().write(database, toFile);
	    return database;
	}
	
	public static void main(String[] args) {
		readDatabase("plda","C://temp/ddlutils//plda-schema.ddl");
	}
	
	public static Database readDatabaseFromFile(String toFile)
	{
	    return new DatabaseIO().read(toFile);

	}

}
