package net.sf.minuteProject.plugin.openxava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
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
		list.addAll(getParentSemanticReference(table));
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
		
	public static List<String> getParentSemanticReference (Table table) {
		return getParentSemanticReference(table, null, true);
	}
	
	public static List<String> getParentSemanticReferenceEntityVariable (Table table) {
		return getParentSemanticReference(table, null, false);
	}
	
	public static List<String> getParentSemanticReference (Table table, Reference removeReference, boolean addChunk) {
		List<String> list = new ArrayList<String>();
		for (Reference reference : table.getParents()) {
			if (!ReferenceUtils.isSimilarReference(reference, removeReference)) {
				Table parent = reference.getForeignTable();
				if (TableUtils.hasSemanticReference(parent)) {
					SemanticReference sr = reference.getForeignTable().getSemanticReference();
					for (String chunk : sr.getSemanticReferenceBeanPath()) {
						String c = FormatUtils.getJavaNameVariable(reference.getLocalColumnName());
						if (addChunk) c = c+"."+chunk;
						list.add(c);
					}
				}
			}
		}
		return list;
	}
	
	public static boolean hasDescriptionList (Table table) {
		if (TableUtils.hasSemanticReference(table) && 
			(TableUtils.isReferenceDataContentType(table) || TableUtils.isPseudoStaticDataContentType(table)))
			return true;
		return false;
	}
	
	public static String getPackageName(Package pack) {
		//formatUtils.getJavaName(${package.name})
		return FormatUtils.getJavaName(pack.getAlias());
	}
	
	public static List<String> getListProperties (Reference reference) {
		List<String> list = new ArrayList<String>();
		Table linkTable = reference.getForeignTable();
		list.addAll(getTabDefaultProperties(linkTable));
		list.addAll(getParentChildRelativeSemanticReference(reference, linkTable));
		return list;
	}

	private static Collection<? extends String> getParentChildRelativeSemanticReference(
			Reference reference, Table linkTable) {
		return getParentSemanticReference (linkTable, reference, true);
	}

}
