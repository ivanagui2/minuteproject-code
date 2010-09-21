package net.sf.minuteProject.loader.targetcatalog;

import net.sf.minuteProject.loader.targetcatalog.node.TargetTechnology;

public class TargetcatalogHolder {

   private static TargetTechnology _targetTechnology;

   public TargetcatalogHolder() {
   }

   public static TargetTechnology getTargetTechnology() {
      return _targetTechnology;
   }
	
   public static void setTargetTechnology (TargetTechnology _targetTechnology2) {
      _targetTechnology = _targetTechnology2;
   }
   
}

