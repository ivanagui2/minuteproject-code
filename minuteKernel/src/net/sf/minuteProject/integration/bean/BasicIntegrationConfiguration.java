package net.sf.minuteProject.integration.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BeanCommon;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.connection.Driver;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicy;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicyPattern;
import net.sf.minuteProject.loader.catalog.databasecatalog.node.Database;
import net.sf.minuteProject.loader.catalog.databasecatalog.node.MavenArtifact;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;
import net.sf.minuteProject.utils.catalog.CatalogUtils;
import net.sf.minuteProject.utils.catalog.DatabaseCatalogUtils;
import net.sf.minuteProject.utils.catalog.TechnologyCatalogUtils;
import net.sf.minuteProject.utils.io.FileUtils;

public class BasicIntegrationConfiguration extends BeanCommon{

	private String 
	    schema, 
		driver, 
		url, 
		username, 
		password, 
		primaryKeyPolicy, 
		targetTechnology, 
		database, 
		rootpackage,
		businesspackage,
		version,
		modelName,
		outputDir,
		catalogDir;
	private Technology choosenTechnology;
	private Database choosenDatabase;
	
	public Configuration getConfiguration () {
		Configuration configuration = new Configuration();
		configuration.setModel(getModel());
		configuration.setTargets(getTargets());
		return configuration;
	}
	
	private Targets getTargets() {
		Targets targets = new Targets();
//		Target target = getChoosenTarget();
//		targets.addTarget(target);
		for (Target target2 : getAllRelatedTechnologies()) {
			targets.addTarget(target2);
		}
		return targets;
	}

	private List<Target> getDependentTargetTechnologies() {
		List<Target> list = new ArrayList<Target>();
		List<Technology> technologies = TechnologyCatalogUtils.getDependentTechnologies(getChoosenTechnology(), getCatalogDir());
		for (Technology technology : technologies) {
			list.add(getTarget(technology, false));
		}
		return list;
	}
	
	private List<Target> getAllRelatedTechnologies() {
		boolean isDefaultOutputToAppend = false;
		List<Target> list = new ArrayList<Target>();
		List<Technology> technologies = TechnologyCatalogUtils.getAllRelatedTechnologies(getChoosenTechnology(), getCatalogDir());
		if (isDefaultOutputToAppend(technologies)) 
			isDefaultOutputToAppend = true;
		for (Technology technology : technologies) {
			list.add(getTarget(technology, isDefaultOutputToAppend));
		}
		return list;
	}

	private boolean isDefaultOutputToAppend(List<Technology> technologies) {
		int i = 0;
		for (Technology technology : technologies) {
			if (technology.isGenerable())
				i++;
			if (i>1)
				return true;
		}
		return false;
	}

	private Target getTarget(Technology technology, boolean isDefaultOutputToAppend) {
		Target target = new Target();
		target.setName(technology.getName());
		target.setFileName(technology.getTemplateConfigFileName());
		target.setDir(getTemplateSetFullPath(technology.getTemplateConfigFileName()));
		target.setTemplatedirRoot(technology.getTemplateDir());		
		target.setOutputdirRoot(getOutputDir(technology, isDefaultOutputToAppend));
		target.setIsGenerable(technology.isGenerable());
		return target;
	}

	private String getTemplateSetFullPathAndFileName(String fileName) {
		return FileUtils.getFileFullPathFromFileInRootClassPath(getCatalogDir()+"/"+ fileName);
	}
	
	private String getTemplateSetFullPath(String fileName) {
		String canonicalFileName = getTemplateSetFullPathAndFileName(fileName);
		return StringUtils.removeEnd(canonicalFileName, fileName);
	}

	private Database getChoosenDatabase() {
		if (choosenDatabase==null)
			choosenDatabase = DatabaseCatalogUtils.getPublishedDatabase(getDatabase(), getCatalogDir());
		return choosenDatabase;		
	}

//	private Target getChoosenTarget() {
//		Technology technology = getChoosenTechnology(); 	
//		Target target = getTarget(technology);
//		return target;
//	}

	private Technology getChoosenTechnology() {
		if (choosenTechnology==null)
			choosenTechnology = TechnologyCatalogUtils.getPublishedTechnology(targetTechnology, getCatalogDir());
		return choosenTechnology;
	}

	private Model getModel() {
		Model model = new Model();
		model.setDataModel(getDataModel());
		model.setName(modelName);
		model.setPackageRoot(rootpackage);
		model.setVersion(getVersion());
		return model;
	}

	private DataModel getDataModel() {
		DataModel dataModel = new DataModel();
		dataModel.setBasicDataSource(getBasicDataSource());
		if (getChoosenDatabase().useSchema())
			dataModel.setSchema(schema);
		dataModel.setPrimaryKeyPolicy(getPrimaryKeyPolicyConfig());
		dataModel.setDriver(getDriverMaven());
		return dataModel;
	}

	private Driver getDriverMaven() {
		Driver driver = new Driver();
		MavenArtifact mavenArtifact = getChoosenDatabase().getMavenArtifact();
		driver.setArtifactId(mavenArtifact.getMvnArtifactId());
		driver.setGroupId(mavenArtifact.getMvnGroupId());
		driver.setVersion(mavenArtifact.getMvnVersion());
		return driver;
	}

	private PrimaryKeyPolicy getPrimaryKeyPolicyConfig() {
		PrimaryKeyPolicy primaryKeyPolicy = new PrimaryKeyPolicy();
		primaryKeyPolicy.setOneForEachTable(true);
		primaryKeyPolicy.addPrimaryKeyPolicyPattern(getPrimaryKeyPolicyPattern());
		return primaryKeyPolicy;
	}

	private PrimaryKeyPolicyPattern getPrimaryKeyPolicyPattern() {
		PrimaryKeyPolicyPattern primaryKeyPolicyPattern = new PrimaryKeyPolicyPattern();
		if (primaryKeyPolicy.equals("sequence")) {
			primaryKeyPolicyPattern.setSuffix("_SEQ");
			primaryKeyPolicyPattern.setName("sequencePattern");
		}
		else if (primaryKeyPolicy.equals("autoincrement"))
			primaryKeyPolicyPattern.setName("autoincrementPattern");
		return primaryKeyPolicyPattern;
	}

	private BasicDataSource getBasicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		basicDataSource.setDriverClassName(driver);
		return basicDataSource;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrimaryKeyPolicy() {
		return primaryKeyPolicy;
	}

	public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}

	public void setTargetTechnology(String targetTechnology) {
		this.targetTechnology = targetTechnology;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getRootpackage() {
		return rootpackage;
	}

	public void setRootpackage(String rootpackage) {
		this.rootpackage = rootpackage;
	}

	public String getBusinesspackage() {
		return businesspackage;
	}

	public void setBusinesspackage(String businesspackage) {
		this.businesspackage = businesspackage;
	}

	public String getVersion() {
		if (version==null)
			version = "1.0";
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getOutputDir(Technology technology, boolean isDefaultOutputToAppend) {
		if (isDefaultOutputToAppend)
			return outputDir+"/"+technology.getDefaultOutputdir();
		return outputDir;
	}
	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getCatalogDir() {
		if (catalogDir==null)
			catalogDir=CatalogUtils.getDefaultCatalogDir();
		return catalogDir;
	}

	public void setCatalogDir(String catalogDir) {
		this.catalogDir = catalogDir;
	}
	
}
