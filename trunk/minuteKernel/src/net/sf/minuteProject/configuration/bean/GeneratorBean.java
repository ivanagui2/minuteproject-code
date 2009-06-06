package net.sf.minuteProject.configuration.bean;

public interface GeneratorBean {

	// Minute standard method
	/**
	 * In MinuteProject a table is associated with a package
	 * @return net.sf.minuteProject.configuration.bean.Package
	 */
	public net.sf.minuteProject.configuration.bean.Package getPackage() ;

	/**
	 * Get the package to which the table is associated to 
	 * @param pack
	 */
	public void setPackage(net.sf.minuteProject.configuration.bean.Package pack) ;
	
	/**
	 * Get the technical package
	 * @param template
	 * @return String
	 */
	public String getTechnicalPackage(Template template);
	
	public String getName();

	/**
	 * Get the name of the formatted to be output.
	 * @return String
	 */
	public String getGeneratedBeanName();
	
}
