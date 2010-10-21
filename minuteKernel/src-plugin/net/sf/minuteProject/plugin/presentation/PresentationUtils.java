package net.sf.minuteProject.plugin.presentation;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessPackage;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.loader.presentation.node.Block;
import net.sf.minuteProject.loader.presentation.node.Window;

public class PresentationUtils {

	public static Boolean isForm(Template template, GeneratorBean generatorBean) {
		if (generatorBean instanceof Window)
			return isForm((Window)generatorBean);
		if (generatorBean instanceof Block)
			return isForm((Block)generatorBean);
		return false;
	}
	
	public static boolean isForm(Window window) {
		if (window.getIsform()==null)
			return false;
		return window.getIsform();
	}
	
	public static boolean isForm(Block block) {
		if (block.getIsform()==null)
			return false;
		return block.getIsform();
	}

	public static List<Table> getDisplayableEntityFromPackage (net.sf.minuteProject.configuration.bean.Package pack) {
		List<Table> list = new ArrayList<Table>();
		for (Table table : pack.getListOfTables()) {
			if (!table.isManyToMany() && !table.isLinkEntity()) 
				list.add(table);
		}
		for (Table table : pack.getListOfViews()) {
			if (!table.isManyToMany() && !table.isLinkEntity()) 
				list.add(table);
		}
		return list;
	}
}