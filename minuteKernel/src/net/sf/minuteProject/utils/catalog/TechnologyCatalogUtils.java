package net.sf.minuteProject.utils.catalog;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class TechnologyCatalogUtils extends CatalogUtils{

	private static List<Technology> technologies;
	
	public static Technology getPublishedTechnology(String name) {
		for (Technology technology : getPublishedTechnologies ()) {
			if (technology.getName().equals(name))
				return technology;
		}		
		return null;
	}
	
	public static List<Technology> getPublishedTechnologies () {
		if (technologies==null)
			technologies = getPublishedTechnologyCatalogHolder().getTechnologyCatalog().getTechnologies().getTechnologys();
		return technologies;
	}
	
	public static String[] getPublishedTechnologyNames() {
		List<String> list = new ArrayList<String>();
		for (Technology technology : getPublishedTechnologies()) {
			if (technology.isGenerable())
				list.add(technology.getName());
		}
		return (String[])list.toArray(new String[list.size()]);
	}
	
	public static List<Technology> getDependentTechnologies (Technology technology) {
		List<Technology> list = new ArrayList<Technology>();
		List<String> targets = ParserUtils.getList(technology.getDependsOnTargets());
		for (String targetName : targets) {
			Technology tech = getTechnologyByTargetName(targetName);
			if (tech!=null)
				list.add(tech);
		}
		return list;
	}

	public static Technology getTechnologyByTargetName(String name) {
		for (Technology technology : getPublishedTechnologies ()) {
			if (technology.getTargetName().equals(name))
				return technology;
		}		
		return null;
	}
}
