package net.sf.minuteProject.loader.mapping.node; //schema bean-map

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.mapping.MappingHolder;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationLoader;
import net.sf.minuteProject.configuration.bean.Template;

public class Call extends AbstractConfigurationLoader{

    private String _packageName;
   private BeanMap _beanMap;
   private String _type;
   private String _name;
   private String _alias;
   private String _id;
   private String _method;
   private CallEntryPoint _callEntryPoint;
   private String _transactional;
   private CallSequence _sequence;

   public Call() {
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
   
   
   public String getMethod() {
	  if (_method == null)
	     _method = new String();
	      return _method;
   }
	
   public void setMethod (String _method) {
      this._method = _method;
   }
   
   
   public CallEntryPoint getCallEntryPoint() {
	  if (_callEntryPoint == null)
	     _callEntryPoint = new CallEntryPoint();
	      return _callEntryPoint;
   }
	
   public void setCallEntryPoint (CallEntryPoint _callEntryPoint) {
      this._callEntryPoint = _callEntryPoint;
   }
   
   
   public String getTransactional() {
	  if (_transactional == null)
	     _transactional = new String();
	      return _transactional;
   }
	
   public void setTransactional (String _transactional) {
      this._transactional = _transactional;
   }
   
   
   public CallSequence getSequence() {
	  if (_sequence == null)
	     _sequence = new CallSequence();
	      return _sequence;
   }
	
   public void setSequence (CallSequence _sequence) {
      this._sequence = _sequence;
   }
   
   

}

