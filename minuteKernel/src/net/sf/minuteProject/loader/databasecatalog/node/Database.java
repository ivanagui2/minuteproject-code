package net.sf.minuteProject.loader.databasecatalog.node; //schema database

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationLoader;
import net.sf.minuteProject.configuration.bean.Template;

public class Database extends AbstractConfigurationLoader{

   private String _name;
   private PrimaryKeyPolicy _primaryKeyPolicy;
   private String _version;
   private String _hibernateDialect;
   private Boolean _useSchema;
   private String _driverclassname;

   public Database() {
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
   
   
   public PrimaryKeyPolicy getPrimaryKeyPolicy() {
	  if (_primaryKeyPolicy == null)
	     _primaryKeyPolicy = new PrimaryKeyPolicy();
	      return _primaryKeyPolicy;
   }
	
   public void setPrimaryKeyPolicy (PrimaryKeyPolicy _primaryKeyPolicy) {
      this._primaryKeyPolicy = _primaryKeyPolicy;
   }
   
   
   public String getVersion() {
	  if (_version == null)
	     _version = new String();
	      return _version;
   }
	
   public void setVersion (String _version) {
      this._version = _version;
   }
   
   
   public String getHibernateDialect() {
	  if (_hibernateDialect == null)
	     _hibernateDialect = new String();
	      return _hibernateDialect;
   }
	
   public void setHibernateDialect (String _hibernateDialect) {
      this._hibernateDialect = _hibernateDialect;
   }
   
   
   public Boolean useSchema() {
      return getUseSchema();
   }
   
   public Boolean getUseSchema() {
	  if (_useSchema == null)
	     _useSchema = new Boolean("false");
	      return _useSchema;
   }
	
   public void setUseSchema (Boolean _useSchema) {
      this._useSchema = _useSchema;
   }
   
   
   public String getDriverclassname() {
	  if (_driverclassname == null)
	     _driverclassname = new String();
	      return _driverclassname;
   }
	
   public void setDriverclassname (String _driverclassname) {
      this._driverclassname = _driverclassname;
   }
   
   

}

