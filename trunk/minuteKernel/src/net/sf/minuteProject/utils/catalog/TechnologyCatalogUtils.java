package net.sf.minuteProject.utils.catalog;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class TechnologyCatalogUtils extends CatalogUtils{

	private static List<Technology> technologies;
	
	public static Technology getPublishedTechnology(String name, String catalogDir) {
		for (Technology technology : getPublishedTechnologies (catalogDir)) {
			if (technology.getName().equals(name))
				return technology;
		}		
		return null;
	}
	
	public static List<Technology> getPublishedTechnologies (String catalogDir) {
		if (technologies==null)
			technologies = getPublishedTechnologyCatalogHolder(catalogDir).getTechnologyCatalog().getTechnologies().getTechnologys();
		return technologies;
	}
	
	public static String[] getPublishedTechnologyNames(String catalogDir) {
		List<String> list = new ArrayList<String>();
		for (Technology technology : getPublishedTechnologies(catalogDir)) {
			if (technology.isGenerable())
				list.add(technology.getName());
		}
		return (String[])list.toArray(new String[list.size()]);
	}
	
	public static List<Technology> getDependentTechnologies (Technology technology, String catalogDir) {
		List<Technology> list = new ArrayList<Technology>();
		List<String> targets = ParserUtils.getList(technology.getDependsOnTargets());
		for (String targetName : targets) {
			Technology tech = getTechnologyByTargetName(targetName, catalogDir);
			if (tech!=null)
				list.add(tech);
		}
		return list;
	}

	public static Technology getTechnologyByTargetName(String name, String catalogDir) {
		for (Technology technology : getPublishedTechnologies (catalogDir)) {
			if (technology.getTargetName().equals(name))
				return technology;
		}		
		return null;
	}
}
