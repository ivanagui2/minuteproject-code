package net.sf.minuteProject.loader.mapping.node; //schema bean-map

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.mapping.MappingHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;

public class Bean extends AbstractConfiguration{

    private String _packageName;
   private BeanMap _beanMap;
   private String _type;
   private String _name;
   private String _alias;
   private String _id;
   private List<BeanAttribute> _attributes;

   public Bean() {
   }

   public String getTechnicalPackage(Template template) {
      return getPackageName();
   }

   public String getPackageName() {
      if (_packageName == null)
         _packageName = new String();
      return _packageName;
   }

   public void setPackageName(String _packageName) {
      this._packageName = _packageName;
   }
   
   public BeanMap getBeanMap() {
      return MappingHolder.getBeanMap(); 
   }
	
   public String getType() {
	  if (_type == null)
	     _type = new String();
      return _type;
   }
	
   public void setType (String _type) {
      this._type = _type;
   }
   
   
   public String getName() {
	  if (_name == null)
	     _name = new String();
      return _name;
   }
	
   public void setName (String _name) {
      this._name = _name;
   }
   
   
   public String getAlias() {
	  if (_alias == null)
	     _alias = new String();
      return _alias;
   }
	
   public void setAlias (String _alias) {
      this._alias = _alias;
   }
   
   
   public String getId() {
	  if (_id == null)
	     _id = new String();
      return _id;
   }
	
   public void setId (String _id) {
      this._id = _id;
   }
   
   
   public List<BeanAttribute> getAttributes() {
      if (_attributes == null){
         _attributes = new ArrayList<BeanAttribute>();
      }
      return _attributes;
   }
   
   public BeanAttribute[] getAttributesArray() {
      return (BeanAttribute[])getAttributes().toArray(new BeanAttribute[getAttributes().size()]);
   }
      
   public void setAttributes (List<BeanAttribute> _attributes) {
      this._attributes = _attributes;
   }
 
   public void setAttribute (BeanAttribute _attribute) {
      addAttribute(_attribute);
   }

   public void addAttribute (BeanAttribute _attribute) {
      getAttributes().add(_attribute);
   }


}

