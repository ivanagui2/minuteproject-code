package net.sf.minuteProject.loader.targetcatalog.node; //schema target-technology

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.targetcatalog.TargetcatalogHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationLoader;
import net.sf.minuteProject.configuration.bean.Template;

public class TargetTechnology extends AbstractConfigurationLoader{

   private String _name;
   private String _version;
   private String _templateConfigFileName;
   private String _templateDir;
   private String _dependsOnTargets;

   public TargetTechnology() {
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
   
   

}

