package net.sf.minuteProject.loader.catalog.technologycatalog.node; //schema technology-catalog

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationLoader;
import net.sf.minuteProject.configuration.bean.Template;

public class TechnologyCatalog extends AbstractConfigurationLoader{

   private String _name;
   private List<Technology> _technologiess;

   public TechnologyCatalog() {
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
   
   
   public List<Technology> getTechnologiess() {
      if (_technologiess == null){
         _technologiess = new ArrayList<Technology>();
      }
      return _technologiess;
   }
   
   public Technology[] getTechnologiessArray() {
      return (Technology[])getTechnologiess().toArray(new Technology[getTechnologiess().size()]);
   }
      
   public void setTechnologiess (List<Technology> _technologiess) {
      this._technologiess = _technologiess;
   }
 
   public void setTechnologies (Technology _technologies) {
      addTechnologies(_technologies);
   }

   public void addTechnologies (Technology _technologies) {
      getTechnologiess().add(_technologies);
   }
   
   public Technology getFirstTechnologyFromTechnologiesByName (String name) {
      if (name==null)
         return null;
      for (Technology _technologies : getTechnologiess()) {
         if (_technologies.getName().equals(name))
            return _technologies;
      }
      return null;
   } 


}

