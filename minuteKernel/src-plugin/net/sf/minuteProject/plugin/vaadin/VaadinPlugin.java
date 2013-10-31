package net.sf.minuteProject.plugin.vaadin;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ConvertUtils;

public class VaadinPlugin {

	public List<VaadinApplication> getVaadinApplications (Model model) {
		List<VaadinApplication> list = new ArrayList<VaadinApplication>();
		Table[] tables = model.getDataModel().getDatabase().getEntities();
		for (Table table : tables) {
			if (isVaadinApplication(table)) 
				list.add(new VaadinApplication(table));
		}
		return list;
	}
	
	public static String getConverter(Column column) {
		String javaType = ConvertUtils.getJavaTypeClassFromDBType(column);
		if (ConvertUtils.JAVA_BIGINTEGER_TYPE.equals(javaType)) {
			return "getBigInteger";
		}
		
		if (ConvertUtils.JAVA_DATE_TYPE.equals(javaType)) {
			return "getDate";
		}
		if (ConvertUtils.JAVA_TIME_TYPE.equals(javaType)) {
			return "getDate";
		}
		if (ConvertUtils.JAVA_TIMESTAMP_TYPE.equals(javaType)) {
			return "getDate";
		}
		return "get"+ConvertUtils.getJavaTypeFromDBType(column);
	}
	
	public boolean isVaadinApplication (Table table) {
		return (!table.isManyToMany())?true:false;
	}
}
