package net.sf.minuteProject.loader.databasecatalog.node; //schema database

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationLoader;
import net.sf.minuteProject.configuration.bean.Template;

public class PrimaryKeyPolicy extends AbstractConfigurationLoader{

   private Database _database;
   private String _type;
   private String _suffix;
   private String _prefix;

   public PrimaryKeyPolicy() {
   }

   public String getTechnicalPackage(Template template) {
      return template.getTechnicalPackage();
   }
   
   public Database getDatabase() {
      return DatabasecatalogHolder.getDatabase(); 
   }
	
   public String getType() {
	  if (_type == null)
	     _type = new String();
	      return _type;
   }
	
   public void setType (String _type) {
      this._type = _type;
   }
   
   
   public String getSuffix() {
	  if (_suffix == null)
	     _suffix = new String();
	      return _suffix;
   }
	
   public void setSuffix (String _suffix) {
      this._suffix = _suffix;
   }
   
   
   public String getPrefix() {
	  if (_prefix == null)
	     _prefix = new String();
	      return _prefix;
   }
	
   public void setPrefix (String _prefix) {
      this._prefix = _prefix;
   }
   
   

}

