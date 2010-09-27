package net.sf.minuteProject.utils.catalog;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;

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
			technologies = getPublishedTechnologyCatalogHolder().getTechnologyCatalog().getTechnologiess();
		return technologies;
	}
	
	public static String[] getPublishedTechnologyNames() {
		List<String> list = new ArrayList<String>();
		for (Technology technology : getPublishedTechnologies()) {
			list.add(technology.getName());
		}
		return (String[])list.toArray(new String[list.size()]);
	}
}
