package net.sf.minuteProject.loader.catalog.databasecatalog.node; //schema database-catalog

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.catalog.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationLoader;
import net.sf.minuteProject.configuration.bean.Template;

public class DatabaseCatalog extends AbstractConfigurationLoader{

   private String _name;
   private List<Database> _databasess;

   public DatabaseCatalog() {
   }

   public String getTechnicalPackage(Template template) {
      return template.getTechnicalPackage();
   }
   
   public String getName() {
	  if (_name == null)
	     _name = new String();
	      return _name;
   }
	
   public void setName (String _name) {
      this._name = _name;
   }
   
   
   public List<Database> getDatabasess() {
      if (_databasess == null){
         _databasess = new ArrayList<Database>();
      }
      return _databasess;
   }
   
   public Database[] getDatabasessArray() {
      return (Database[])getDatabasess().toArray(new Database[getDatabasess().size()]);
   }
      
   public void setDatabasess (List<Database> _databasess) {
      this._databasess = _databasess;
   }
 
   public void setDatabases (Database _databases) {
      addDatabases(_databases);
   }

   public void addDatabases (Database _databases) {
      getDatabasess().add(_databases);
   }
   
   public Database getFirstDatabaseFromDatabasesByName (String name) {
      if (name==null)
         return null;
      for (Database _databases : getDatabasess()) {
         if (_databases.getName().equals(name))
            return _databases;
      }
      return null;
   } 


}

