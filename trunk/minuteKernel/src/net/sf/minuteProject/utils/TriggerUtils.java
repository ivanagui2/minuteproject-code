package net.sf.minuteProject.utils;

import static net.sf.minuteProject.configuration.bean.enrichment.Trigger.INSERT;
import static net.sf.minuteProject.configuration.bean.enrichment.Trigger.UPDATE;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import net.sf.minuteProject.configuration.bean.enrichment.Trigger;
import net.sf.minuteProject.configuration.bean.enumeration.CRUDEnum;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.property.PropertyUtils;
import static net.sf.minuteProject.utils.property.PropertyUtils.*;

public class TriggerUtils {

	private static final String TRIGGER = "TRIGGER";
	
	public static Trigger getTriggerFromProperty(Property property, Column column) {
		if (isTriggerTag(property)) {
			Trigger trigger = new Trigger();
			trigger.setColumnName(column.getName());
			trigger.setCruds(getCruds(property));
			trigger.setValue(getValue(property));
			return trigger;
		}
		return null;
	}

	private static String getValue(Property property) {
		return "with-current-date";
	}

	private static List<CRUDEnum> getCruds(Property property) {
		List<CRUDEnum> list = new ArrayList<CRUDEnum>();
		for (CRUDEnum c : CRUDEnum.values()) {
			if (isPropertyTagContain(property, c.toString()))
				list.add(c);
		}
		return list;
	}

	public static boolean isTriggerTag(Property property) {
		return isPropertyTagStartWith(property, TRIGGER);
	}

	public static boolean isInsertTrigger(Property property) {
		return isTriggerTag(property) && isInsertTag(property);
	}

	public static boolean isUpdateTrigger(Property property) {
		return isTriggerTag(property) && isUpdateTag(property);
	}
	
	private static boolean isInsertTag(Property property) {
		return isPropertyTagContain(property, INSERT);
	}

	private static boolean isUpdateTag(Property property) {
		return isPropertyTagContain(property, UPDATE);
	}

}
