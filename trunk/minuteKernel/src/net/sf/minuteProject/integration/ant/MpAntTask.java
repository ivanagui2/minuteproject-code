package net.sf.minuteProject.integration.ant;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class MpAntTask extends Task {

	private String schema, driver, url, username, password, primaryKeyPolicy, target, database;
	
    public void execute() {
	   log("MinuteProject generator", Project.MSG_INFO);
	   
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
