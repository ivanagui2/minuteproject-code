package net.sf.minuteProject.plugin.format;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.FormatUtils;

import org.apache.commons.lang.StringUtils;

public class I18nUtils {
	public static final String BLANK = "";
	public static final String ONE_SPACE = " ";
	public static final String UNDERSCORE = "_";

	public static String getI18nFromDBName (String input) {
		input = StringUtils.replace(input, UNDERSCORE, ONE_SPACE);
//		input = StringUtils.lowerCase(input);
		input = FormatUtils.firstUpperCaseOnly(input);
		return input;
	}
	
	public static String getI18nFromDBNameStripPrefix (String input) {
		if (input==null) return null;
		String prefix = StringUtils.substringBefore(input, UNDERSCORE);
		if (prefix.equals(input))
			return input;
		input = StringUtils.removeStart(input, prefix);
		input = StringUtils.stripStart(input, UNDERSCORE);
		return getI18nFromDBName(input);
	}

	public static String getI18nFromDBNameStripSufix (String input) {
		String sufix = StringUtils.substringBeforeLast(input, UNDERSCORE);
		if (!sufix.equals(input))
			input = StringUtils.removeEnd(input, sufix);
		input = StringUtils.stripStart(input, UNDERSCORE);
		return getI18nFromDBName(input);
	}
	
	public static String getI18nFromDBObject (Table table) {
		return getI18nFromDBNameStripPrefix(table.getAlias());
	}
	
	public static String getI18nFromDBObject (Column column) {
		if (ColumnUtils.isForeignKey(column)){
			return getI18nFromDBNameStripSufix(column.getName());
		}
		return getI18nFromDBName(column.getName());
	}
}
