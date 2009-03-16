package net.sf.minuteProject.plugin.presentation;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
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

}
