package net.sf.minuteProject.plugin.openxava;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.TableUtils;

public class OpenXavaUtils {

	public static String getHelpDirectory () {
		return "xava/help/";
	}
	
	public static String getHelpFileExtension () {
		return "jsp";
	}
	
	public static List<String> getTabAll (Table table) {
		List<String> list = new ArrayList<String>();
		list.addAll(getTabDefaultProperties(table));
		list.addAll(getTabParentSemanticReference(table));
		return list;
	}
	public static List<String> getTabSimple (Table table) {
		List<String> list = new ArrayList<String>();
		list.addAll(getTabDefaultProperties(table));
		return list;
	}
	public static List<String> getTabWithRef (Table table) {
		return getTabAll(table);
	}
	
	public static boolean hasTabAll (Table table) {
		return (getTabAll(table).isEmpty())?false:true;
	}
	public static boolean hasTabSimple (Table table) {
		return (getTabSimple(table).isEmpty())?false:true;
	}
	public static boolean hasTabWithRef (Table table) {
		return (getTabWithRef(table).isEmpty())?false:true;
	}
	
	private static List<String> getTabDefaultProperties (Table table) {
		List<String> list = new ArrayList<String>();
		for (Column column : table.getAttributes()) {
			if (!column.isLob())
				list.add(FormatUtils.getJavaNameVariable(column.getName()));
		}
		return list;
	}
		
	private static List<String> getTabParentSemanticReference (Table table) {
		List<String> list = new ArrayList<String>();
		for (Reference reference : table.getParents()) {
			Table parent = reference.getForeignTable();
			if (TableUtils.isReferenceDataContentType(parent)) {
				SemanticReference sr = reference.getForeignTable().getSemanticReference();
				for (String chunk : sr.getSemanticReferenceBeanPath()) {
					//String c = FormatUtils.getJavaNameVariable(parent.getName())+"."+chunk;
					String c = FormatUtils.getJavaNameVariable(reference.getLocalColumnName())+"."+chunk;
					list.add(c);
				}
			}
		}
		return list;
	}

}
