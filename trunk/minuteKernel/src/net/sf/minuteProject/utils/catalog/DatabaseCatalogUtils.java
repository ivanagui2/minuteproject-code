package net.sf.minuteProject.utils.catalog;

import java.util.List;

import net.sf.minuteProject.loader.catalog.databasecatalog.node.Database;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;

public class DatabaseCatalogUtils extends CatalogUtils{

	private static List<Database> databases;
	
	public static List<Database> getPublishedDatabases() {
		if (databases==null)
			databases = getPublishedDatabaseCatalogHolder().getDatabaseCatalog().getDatabases().getDatabases();
		return databases;		
	}
	
	public static Database getPublishedDatabase(String name) {
		for (Database database : getPublishedDatabases ()) {
			if (database.getName().equals(name))
				return database;
		}		
		return null;
	}	
}
