package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.sql.DataSource;

import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.FileSource;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;

/**
 * Inspired by DDLUtils Database class
 * Represents the database model, ie. the tables in the database. It also
 * contains the corresponding dyna classes for creating dyna beans for the
 * objects stored in the tables.
 *
 * @author Florian Adler
 */
public class DatabaseDDLUtils implements Database
{
	private org.apache.ddlutils.model.Database database;
	private ArrayList tables;
	private DataModel dataModel;
	
	public DatabaseDDLUtils(org.apache.ddlutils.model.Database database) {
		this.database = database;
	}

	public DatabaseDDLUtils(DataModel dataModel) {
		this.dataModel = dataModel;
		loadDatabase(dataModel);
	}
	
	public Database loadDatabase(DataModel dataModel) {
	    Platform platform = PlatformFactory.createNewPlatformInstance(dataModel.getBasicDataSource());
	    platform.getModelReader().setDefaultSchemaPattern(dataModel.getSchema());
	    database = platform.readModelFromDatabase(null);
	    writeDatabase(database, dataModel);
	    return this;
	}
	
	private void writeDatabase (org.apache.ddlutils.model.Database database, DataModel dataModel) {
	    String filename = getFileSourceName(dataModel);
	    if (filename!= null)
	    	new DatabaseIO().write(database, filename);
	}
	
	private String getFileSourceName(DataModel dataModel) {
		FileSource fileSource = dataModel.getFileSource();
		Model model = dataModel.getModel();
	    String filename = null;
	    String filedir = null;
	    if (fileSource!=null) {
	    	filename = fileSource.getName();
	    	filedir = fileSource.getDir();
	    	if (filename!=null)
	    		filename = filedir+"/"+filename+".xml";
	    	else 
	    		filename = filedir+"/"+model.getName()+".xml";
	    }
	    return filename;
	}	
	
	
	
    /**
     * Returns the name of this database model.
     * 
     * @return The name
     */
    public String getName()
    {
        return database.getName();
    }

    /**
     * Sets the name of this database model.
     * 
     * @param name The name
     */
    public void setName(String name)
    {
    	database.setName(name);
    }

    /**
     * Returns the version of this database model.
     * 
     * @return The version
     */
    public String getVersion()
    {
        return database.getVersion();
    }

    /**
     * Sets the version of this database model.
     * 
     * @param version The version
     */
    public void setVersion(String version)
    {
    	database.setVersion(version);
    }

    /**
     * Returns the method for generating primary key values.
     * 
     * @return The method
     */
    public String getIdMethod()
    {
        return database.getIdMethod();
    }

    /**
     * Sets the method for generating primary key values. Note that this
     * value is ignored by DdlUtils and only for compatibility with Torque.
     * 
     * @param idMethod The method
     */
    public void setIdMethod(String idMethod)
    {
    	database.setIdMethod(idMethod);
    }

    /**
     * Returns the number of tables in this model.
     * 
     * @return The number of tables
     */
    public int getTableCount()
    {
        return database.getTableCount();
    }

    /**
     * Returns the tables in this model.
     * 
     * @return The tables
     */
    public Table[] getTables()
    {
    	if (tables == null) {
    		tables = new ArrayList<Table>();
    		for (int i = 0; i < database.getTableCount(); i++) {
    			Table table = new TableDDLUtils (database.getTable(i));
    			tables.add(table);
    		}
    	}
    	return (Table[])tables.toArray(new Table[tables.size()]);
    }

    /**
     * Returns the table at the specified position.
     * 
     * @param idx The index of the table
     * @return The table
     */
    public Table getTable(int idx)
    {
        return (Table)tables.get(idx);
    }

    /**
     * Adds a table.
     * 
     * @param table The table to add
     */
    public void addTable(Table table)
    {
        if (table != null)
        {
            tables.add(table);
        }
    }

    /**
     * Adds a table at the specified position.
     * 
     * @param idx   The index where to insert the table
     * @param table The table to add
     */
    public void addTable(int idx, Table table)
    {
        if (table != null)
        {
            tables.add(idx, table);
        }
    }

    /**
     * Adds the given tables.
     * 
     * @param tables The tables to add
     */
    public void addTables(Collection tables)
    {
    	database.addTables(tables);
    }

    /**
     * Removes the given table.
     * 
     * @param table The table to remove
     */
    public void removeTable(Table table)
    {
        if (table != null)
        {
            tables.remove(table);
        }
    }

    /**
     * Removes the indicated table.
     * 
     * @param idx The index of the table to remove
     */
    public void removeTable(int idx)
    {
        tables.remove(idx);
    }

    /**
     * Finds the table with the specified name, using case insensitive matching.
     * Note that this method is not called getTable to avoid introspection
     * problems.
     * 
     * @param name The name of the table to find
     * @return The table or <code>null</code> if there is no such table
     */
    public Table findTable(String name)
    {
        return findTable(name, false);
    }

    /**
     * Finds the table with the specified name, using case insensitive matching.
     * Note that this method is not called getTable) to avoid introspection
     * problems.
     * 
     * @param name          The name of the table to find
     * @param caseSensitive Whether case matters for the names
     * @return The table or <code>null</code> if there is no such table
     */
    public Table findTable(String name, boolean caseSensitive)
    {
    	return new TableDDLUtils (database.findTable(name, caseSensitive));
    }


    /**
     * {@inheritDoc}
     
    public Object clone() throws CloneNotSupportedException
    {
    	return database.clone();
    }*/

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj)
    {
    	return database.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode()
    {
        return database.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
    	return database.toString();
    }

    /**
     * Returns a verbose string representation of this database.
     * 
     * @return The string representation
     */
    public String toVerboseString()
    {
    	return database.toVerboseString();
    }

	public DataModel getDataModel() {
		return dataModel;
	}


}

