package net.sf.minuteProject.configuration.bean;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

public class Datasource {

	private BasicDataSource basicDataSource;
	private String dbType;

	public Datasource(BasicDataSource basicDataSource, String dbType) {
		this.basicDataSource = basicDataSource;
		this.dbType = dbType;
		convertDatasource();
	}

	private void convertDatasource() {
		String url = basicDataSource.getUrl();
		if (url!=null) {
			if ("mysql".equals(dbType)) {
				//jdbc:hsqldb:hsql://{server-name}:${port-number}/{database-name}
				String urlAfterProtocol = StringUtils.substringAfter(url, "//");
				String urlServerPort = StringUtils.substringBefore(urlAfterProtocol, "/");
				server = StringUtils.substringBefore(urlServerPort, ":");
				port = StringUtils.substringAfter(urlServerPort, ":");
				databaseInstance = StringUtils.substringAfter(urlAfterProtocol, "/");
			}
		}
	}

	private String server, port, databaseInstance;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabaseInstance() {
		return databaseInstance;
	}

	public void setDatabaseInstance(String databaseInstance) {
		this.databaseInstance = databaseInstance;
	}

}
