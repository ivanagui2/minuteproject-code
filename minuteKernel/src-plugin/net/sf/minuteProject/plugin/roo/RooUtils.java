package net.sf.minuteProject.plugin.roo;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.java.JavaUtils;

public class RooUtils {

	public static String getRooConsoleType (Column column){
		return StringUtils.lowerCase(ConvertUtils.getJavaTypeFromDBType(column));
	}
	
	
}
