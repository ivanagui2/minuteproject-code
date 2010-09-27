package net.sf.minuteProject.utils.catalog;

import net.sf.minuteProject.loader.catalog.databasecatalog.Databasecatalog;
import net.sf.minuteProject.loader.catalog.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.loader.catalog.technologycatalog.Technologycatalog;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;

public class CatalogUtils {

	private static TechnologycatalogHolder technologycatalogHolder;
	private static DatabasecatalogHolder databasecatalogHolder;

	
	public static TechnologycatalogHolder getPublishedTechnologyCatalogHolder() {
		if (technologycatalogHolder==null)
			technologycatalogHolder = loadTechnologyCatalogHolder("catalog/technology-catalog.xml");
		return technologycatalogHolder;
	}
	
	public static TechnologycatalogHolder loadTechnologyCatalogHolder(String name) {
		try {
			return getTechnologyCatalogLoader(name).load();
		} catch (Exception e) {
			return new TechnologycatalogHolder();
		}
	}

	private static Technologycatalog getTechnologyCatalogLoader(String name) {
		return new Technologycatalog(name);
	}
	
	public static DatabasecatalogHolder getPublishedDatabaseCatalogHolder() {
		if (databasecatalogHolder==null)
			databasecatalogHolder = loadDatabaseCatalogHolder("catalog/database-catalog.xml");
		return databasecatalogHolder;
	}
	
	public static DatabasecatalogHolder loadDatabaseCatalogHolder(String name) {
		try {
			return getDatabaseCatalogLoader(name).load();
		} catch (Exception e) {
			return new DatabasecatalogHolder();
		}
	}

	private static Databasecatalog getDatabaseCatalogLoader(String name) {
		return new Databasecatalog(name);
	}


}
