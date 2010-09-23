package net.sf.minuteProject.loader.catalog.technologycatalog.node; //schema technology-catalog

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationLoader;
import net.sf.minuteProject.configuration.bean.Template;

public class Technology extends AbstractConfigurationLoader{

   private TechnologyCatalog _technologyCatalog;
   private String _name;
   private String _version;
   private String _templateConfigFileName;
   private String _templateDir;
   private String _dependsOnTargets;
   private List<Limitation> _limitationss;
   private List<Convention> _conventionss;

   public Technology() {
   }

   public String getTechnicalPackage(Template template) {
      return template.getTechnicalPackage();
   }
   
   public TechnologyCatalog getTechnologyCatalog() {
      return TechnologycatalogHolder.getTechnologyCatalog(); 
   }
	
   public String getName() {
	  if (_name == null)
	     _name = new String();
	      return _name;
   }
	
   public void setName (String _name) {
      this._name = _name;
   }
   
   
   public String getVersion() {
	  if (_version == null)
	     _version = new String();
	      return _version;
   }
	
   public void setVersion (String _version) {
      this._version = _version;
   }
   
   
   public String getTemplateConfigFileName() {
	  if (_templateConfigFileName == null)
	     _templateConfigFileName = new String();
	      return _templateConfigFileName;
   }
	
   public void setTemplateConfigFileName (String _templateConfigFileName) {
      this._templateConfigFileName = _templateConfigFileName;
   }
   
   
   public String getTemplateDir() {
	  if (_templateDir == null)
	     _templateDir = new String();
	      return _templateDir;
   }
	
   public void setTemplateDir (String _templateDir) {
      this._templateDir = _templateDir;
   }
   
   
   public String getDependsOnTargets() {
	  if (_dependsOnTargets == null)
	     _dependsOnTargets = new String();
	      return _dependsOnTargets;
   }
	
   public void setDependsOnTargets (String _dependsOnTargets) {
      this._dependsOnTargets = _dependsOnTargets;
   }
   
   
   public List<Limitation> getLimitationss() {
      if (_limitationss == null){
         _limitationss = new ArrayList<Limitation>();
      }
      return _limitationss;
   }
   
   public Limitation[] getLimitationssArray() {
      return (Limitation[])getLimitationss().toArray(new Limitation[getLimitationss().size()]);
   }
      
   public void setLimitationss (List<Limitation> _limitationss) {
      this._limitationss = _limitationss;
   }
 
   public void setLimitations (Limitation _limitations) {
      addLimitations(_limitations);
   }

   public void addLimitations (Limitation _limitations) {
      getLimitationss().add(_limitations);
   }
   
   public Limitation getFirstLimitationFromLimitationsByName (String name) {
      if (name==null)
         return null;
      for (Limitation _limitations : getLimitationss()) {
         if (_limitations.getName().equals(name))
            return _limitations;
      }
      return null;
   } 

   public List<Convention> getConventionss() {
      if (_conventionss == null){
         _conventionss = new ArrayList<Convention>();
      }
      return _conventionss;
   }
   
   public Convention[] getConventionssArray() {
      return (Convention[])getConventionss().toArray(new Convention[getConventionss().size()]);
   }
      
   public void setConventionss (List<Convention> _conventionss) {
      this._conventionss = _conventionss;
   }
 
   public void setConventions (Convention _conventions) {
      addConventions(_conventions);
   }

   public void addConventions (Convention _conventions) {
      getConventionss().add(_conventions);
   }
   
   public Convention getFirstConventionFromConventionsByName (String name) {
      if (name==null)
         return null;
      for (Convention _conventions : getConventionss()) {
         if (_conventions.getName().equals(name))
            return _conventions;
      }
      return null;
   } 


}

