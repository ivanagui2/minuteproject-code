package net.sf.minuteProject.loader.databasecatalog;

import net.sf.minuteProject.loader.databasecatalog.node.Database;

public class DatabasecatalogHolder {

   private static Database _database;

   public DatabasecatalogHolder() {
   }

   public static Database getDatabase() {
      return _database;
   }
	
   public static void setDatabase (Database _database2) {
      _database = _database2;
   }
   
}

