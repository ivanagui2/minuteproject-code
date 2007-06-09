package net.sf.minuteProject.configuration.bean.model.data;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Index;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;

/**
 * @author Florian Adler
 *
 */
public class Table extends AbstractConfiguration implements TableInt{
	
	private org.apache.ddlutils.model.Table table;
	private Database database;
	private net.sf.minuteProject.configuration.bean.Package pack;
	
	public String getName () {
		return table.getName();
	}
	
	public net.sf.minuteProject.configuration.bean.Package getPackage() {
		return pack;
	}

	public void setPackage(net.sf.minuteProject.configuration.bean.Package pack) {
		this.pack = pack;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Table (org.apache.ddlutils.model.Table table) {
		setTable (table);
	}

	public org.apache.ddlutils.model.Table getTable() {
		return table;
	}

	public void setTable(org.apache.ddlutils.model.Table table) {
		this.table = table;
	}

	public Column findColumn(String name) {
		// TODO Auto-generated method stub
		return table.findColumn(name);
	}

	public Column findColumn(String name, boolean caseSensitive) {
		// TODO Auto-generated method stub
		return table.findColumn(name, caseSensitive);
	}

	public ForeignKey findForeignKey(ForeignKey key) {
		// TODO Auto-generated method stub
		return table.findForeignKey(key);
	}

	public ForeignKey findForeignKey(ForeignKey key, boolean caseSensitive) {
		// TODO Auto-generated method stub
		return table.findForeignKey(key, caseSensitive);
	}

	public Index findIndex(String name) {
		// TODO Auto-generated method stub
		return table.findIndex(name);
	}

	public Index findIndex(String name, boolean caseSensitive) {
		// TODO Auto-generated method stub
		return table.findIndex(name, caseSensitive);
	}

	public String getCatalog() {
		// TODO Auto-generated method stub
		return table.getCatalog();
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return table.getColumnCount();
	}

	public Column[] getColumns() {
		// TODO Auto-generated method stub
		return table.getColumns();
	}

	public ForeignKey getForeignKey(int idx) {
		// TODO Auto-generated method stub
		return table.getForeignKey(idx);
	}

	public int getForeignKeyCount() {
		// TODO Auto-generated method stub
		return table.getForeignKeyCount();
	}

	public ForeignKey[] getForeignKeys() {
		// TODO Auto-generated method stub
		return table.getForeignKeys();
	}

	public Index getIndex(int idx) {
		// TODO Auto-generated method stub
		return table.getIndex(idx);
	}

	public int getIndexCount() {
		// TODO Auto-generated method stub
		return table.getIndexCount();
	}

	public Index[] getIndices() {
		// TODO Auto-generated method stub
		return table.getIndices();
	}

	public Index[] getNonUniqueIndices() {
		// TODO Auto-generated method stub
		return table.getNonUniqueIndices();
	}

	public Column[] getPrimaryKeyColumns() {
		// TODO Auto-generated method stub
		return table.getPrimaryKeyColumns();
	}

	public String getSchema() {
		// TODO Auto-generated method stub
		return table.getSchema();
	}

	public Index[] getUniqueIndices() {
		// TODO Auto-generated method stub
		return table.getNonUniqueIndices();
	}

	public boolean hasPrimaryKey() {
		// TODO Auto-generated method stub
		return table.hasPrimaryKey();
	}
	
	public String getTechnicalPackage(Template template) {
		//StringBuffer sb = new StringBuffer(getBusinessPackage().getBusinessModel().getModel().getTechnicalPackage(template));
		StringBuffer sb = new StringBuffer(getPackage().getTechnicalPackage(template));
		//sb.append("."+getName());
		return sb.toString();
	}
}
