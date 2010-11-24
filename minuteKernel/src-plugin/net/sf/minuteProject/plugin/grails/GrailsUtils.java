package net.sf.minuteProject.plugin.grails;

import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.TableUtils;

public class GrailsUtils {

	public static String getToString (Table table) {
		String semanticRef = getToStringFromSemanticReferenceTable(table);
		return (semanticRef!=null)?semanticRef:getGrailsToString (table);
	}

	private static String getGrailsToString(Table table) {
		for (Column column: table.getAttributes()) {
			return FormatUtils.getJavaNameVariable(column.getName());
		}
		return FormatUtils.getJavaNameVariable(table.getName());
	}

	private static String getToStringFromSemanticReferenceTable(Table table) {
		if (TableUtils.hasSemanticReference(table)) {
			return getToStringFromSemanticReference(table);
		} 
		return null;
	}

	private static String getToStringFromSemanticReference(Table table) {
		StringBuffer sb = new StringBuffer();
		List<Column> columns = TableUtils.getSemanticReferenceColumns(table);
		for (int i = 0; i < columns.size(); i++) {
			sb.append(FormatUtils.getJavaNameVariable(columns.get(i).getName()));
			if (i+1!=columns.size())
				sb.append(" - ");
		}
		return sb.toString();
	}
}
