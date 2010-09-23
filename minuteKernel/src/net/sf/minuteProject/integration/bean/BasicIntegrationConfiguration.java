package net.sf.minuteProject.integration.bean;

import org.apache.commons.dbcp.BasicDataSource;

import net.sf.minuteProject.configuration.bean.BeanCommon;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicy;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicyPattern;

public class BasicIntegrationConfiguration extends BeanCommon{

	private String schema, 
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
	targetDir;

	public Configuration getConfiguration () {
		Configuration configuration = new Configuration();
		configuration.setModel(getModel());
		configuration.setTargets(getTargets());
		return configuration;
	}
	
	private Targets getTargets() {
		Targets targets = new Targets();
		if (targetTechnology.equals("openxava")) {
			targets.addTarget(getTargetTechnology());
			targets.addTarget(getLibTarget());
		}
		return targets;
	}

	private Target getLibTarget() {
		Target target = new Target();
		target.setFileName("catalog/mp-template-config-bsla-LIB-features.xml");
		target.setTemplatedirRoot("../../minuteTemplate/template-bsla");		
		return target;
	}

	private Target getTargetTechnology() {
		//TODO redo it entirely it's just for test
		Target target = new Target();
		if (targetTechnology.equals("openxava")) {
			target.setFileName("catalog/mp-template-config-openxava-last-features.xml");
			target.setTemplatedirRoot("../../minuteTemplate/template/framework/openxava");
		}
			
		target.setOutputdirRoot(targetDir);
		return target;
	}

	private Model getModel() {
		Model model = new Model();
		model.setDataModel(getDataModel());
		model.setName(modelName);
		model.setPackageRoot(rootpackage);
		return model;
	}

	private DataModel getDataModel() {
		DataModel dataModel = new DataModel();
		dataModel.setBasicDataSource(getBasicDataSource());
		dataModel.setSchema(schema);
		dataModel.setPrimaryKeyPolicy(getPrimaryKeyPolicyConfig());
		return dataModel;
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

	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}
	
	
}
