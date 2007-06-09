package net.sf.minuteProject.configuration.bean.model.data;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Index;

public interface TableInt {

    /**
     * Returns the catalog of this table as read from the database.
     * 
     * @return The catalog
     */
    public String getCatalog();

    /**
     * Returns the schema of this table as read from the database.
     * 
     * @return The schema
     */
    public String getSchema();

    public String getName();
    /**
     * Sets the name of the table.
     * 
     * @param name The name
     */
    public void setName(String name);
    
    /**
     * Returns the description of the table.
     *
     * @return The description
     */
    public void setDescription(String description);
    
    /**
     * Returns the number of columns in this table.
     * 
     * @return The number of columns
     */
    public int getColumnCount();

    /**
     * Returns the columns in this table.
     * 
     * @return The columns
     */
    public Column[] getColumns();
    
    /**
     * Returns the number of foreign keys.
     * 
     * @return The number of foreign keys
     */
    public int getForeignKeyCount();
    
    /**
     * Returns the foreign key at the given position.
     * 
     * @param idx The foreign key index
     * @return The foreign key
     */
    public ForeignKey getForeignKey(int idx);
    
    /**
     * Returns the foreign keys of this table.
     * 
     * @return The foreign keys
     */
    public ForeignKey[] getForeignKeys();

    /**
     * Returns the number of indices.
     * 
     * @return The number of indices
     */
    public int getIndexCount();

    /**
     * Returns the index at the specified position.
     * 
     * @param idx The position
     * @return The index
     */
    public Index getIndex(int idx);
    
    /**
     * Returns the indices of this table.
     * 
     * @return The indices
     */
    public Index[] getIndices();

    /**
     * Gets a list of non-unique indices on this table.
     * 
     * @return The unique indices
     */
    public Index[] getNonUniqueIndices();
    
    /**
     * Gets a list of unique indices on this table.
     * 
     * @return The unique indices
     */
    public Index[] getUniqueIndices();

    // Helper methods
    //-------------------------------------------------------------------------

    /**
     * Determines whether there is at least one primary key column on this table.
     * 
     * @return <code>true</code> if there are one or more primary key columns
     */
    public boolean hasPrimaryKey();
    
    /**
     * Finds the column with the specified name, using case insensitive matching.
     * Note that this method is not called getColumn(String) to avoid introspection
     * problems.
     * 
     * @param name The name of the column
     * @return The column or <code>null</code> if there is no such column
     */
    public Column findColumn(String name);

    /**
     * Finds the column with the specified name, using case insensitive matching.
     * Note that this method is not called getColumn(String) to avoid introspection
     * problems.
     * 
     * @param name          The name of the column
     * @param caseSensitive Whether case matters for the names
     * @return The column or <code>null</code> if there is no such column
     */
    public Column findColumn(String name, boolean caseSensitive);
    
    /**
     * Finds the index with the specified name, using case insensitive matching.
     * Note that this method is not called getIndex to avoid introspection
     * problems.
     * 
     * @param name The name of the index
     * @return The index or <code>null</code> if there is no such index
     */
    public Index findIndex(String name);

    /**
     * Finds the index with the specified name, using case insensitive matching.
     * Note that this method is not called getIndex to avoid introspection
     * problems.
     * 
     * @param name          The name of the index
     * @param caseSensitive Whether case matters for the names
     * @return The index or <code>null</code> if there is no such index
     */
    public Index findIndex(String name, boolean caseSensitive);
    
    /**
     * Finds the foreign key in this table that is equal to the supplied foreign key.
     * 
     * @param key The foreign key to search for
     * @return The found foreign key
     */
    public ForeignKey findForeignKey(ForeignKey key);
    
    /**
     * Finds the foreign key in this table that is equal to the supplied foreign key.
     * 
     * @param key           The foreign key to search for
     * @param caseSensitive Whether case matters for the names
     * @return The found foreign key
     */
    public ForeignKey findForeignKey(ForeignKey key, boolean caseSensitive);
    
    /**
     * Returns the primary key columns of this table.
     * 
     * @return The primary key columns
     */
    public Column[] getPrimaryKeyColumns();
    
}
