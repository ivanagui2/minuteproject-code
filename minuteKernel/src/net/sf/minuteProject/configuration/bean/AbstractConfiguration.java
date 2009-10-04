package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.FormatUtils;

public abstract class AbstractConfiguration implements GeneratorBean{
	// TODO IDEALLY set the abstractConfiguration properties after every creation in the digester
	// Done by AOP
	protected String name;
	private String refname;
	private String description;
	private List<Property> properties;
	private AbstractConfiguration parent;
	private String configurationFileInClassPath;
	
	public AbstractConfiguration getParent() {
		return parent;
	}
	public void setParent(AbstractConfiguration parent) {
		this.parent = parent;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public List<Property> getProperties() {
		if (properties == null) 
			properties = new ArrayList<Property>();
		return properties;
	}
	public void addProperty (Property property) {
		getProperties().add(property);
	}
	public Property[] getPropertiesArray() {
		properties = getProperties();
		return (Property[]) properties.toArray(new Property[properties.size()]);
	}
	public String getPropertyValue (String name) {
		// TODO browse recursively via the parent to see the first matching propertiesif (getProperties().)
		// set AOP advice on each setter method of AbstractConfiguration with at string like param
		// parse that String, look up in property within ${}for the first matching one
		// if found => replace with the correct value;
		// else let the current reference
		//return null;
		for (Property property : getProperties()) {
			if (property.getName().equals(name))
				return property.getValue();
		}
		return null;
	}
	
	public boolean getIsTrueProperty (String name) {
		String value = getPropertyValue(name);
		if (value==null)
			return false;
		return value.equals("true");
	}	
	
	public boolean getIsFalseProperty (String name) {
		String value = getPropertyValue(name);
		if (value==null)
			return true;
		return value.equals("false");
	}	
	
	public String getTechnicalPackage(Template template)
	{
		return "TO OVERWRITE in herited classes";
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRefname() {
		return refname;
	}
	public void setRefname(String refname) {
		this.refname = refname;
	}
	public Package getPackage() {
		return null;
	}
	public void setPackage(Package pack) {

	}

	public String getGeneratedBeanName() {
		return FormatUtils.getJavaName(getName());
	}
	public String getConfigurationFileInClassPath() {
		return configurationFileInClassPath;
	}
	public void setConfigurationFileInClassPath(String configurationFileInClassPath) {
		this.configurationFileInClassPath = configurationFileInClassPath;
	}
	
	
}
