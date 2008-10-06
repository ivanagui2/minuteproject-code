package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;


/*
 * 
 * Wraps the DDLUtils Foreign Key class
 * 
 * Copyright 1999-2006 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;

import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;

/**
 * Represents a database foreign key.
 * 
 * @author Florian Adler
 */
public class ForeignKeyDDLUtils implements ForeignKey
{
	
	private org.apache.ddlutils.model.ForeignKey foreignKey;
	
	private ArrayList<Reference> references;
	/**
      * Creates a new foreign key object that has no name.
     */
    public ForeignKeyDDLUtils(org.apache.ddlutils.model.ForeignKey foreignKey)
    {
        this.foreignKey = foreignKey;
    }

    /**
     * Returns the name of this foreign key.
     * 
     * @return The name
     */
    public String getName()
    {
        return foreignKey.getName();
    }

    /**
     * Sets the name of this foreign key.
     * 
     * @param name The name
     */
    public void setName(String name)
    {
    	foreignKey.setName(name);
    }

    /**
     * Returns the foreign table.
     *
     * @return The foreign table
     */
    public Table getForeignTable()
    {
        return new TableDDLUtils (foreignKey.getForeignTable());
    }


    /**
     * Returns the name of the foreign table.
     * 
     * @return The table name
     */
    public String getForeignTableName()
    {
        return foreignKey.getForeignTableName();
    }
    
    /**
     * Sets the name of the foreign table. Please note that you should not use this method
     * when manually constructing or manipulating the database model. Rather utilize the
     * {@link #setForeignTable(Table)} method.
     * 
     * @param foreignTableName The table name
     */
    public void setForeignTableName(String foreignTableName)
    {
    	foreignKey.setForeignTableName(foreignTableName);
    }

    /**
     * Returns the number of references.
     * 
     * @return The number of references
     */
    public int getReferenceCount()
    {
        return foreignKey.getReferenceCount();
    }

    /**
     * Returns the indicated reference.
     * 
     * @param idx The index
     * @return The reference
     */
    public Reference getReference(int idx)
    {
        return new ReferenceDDLUtils (foreignKey.getReference(idx));
    }

    /**
     * Returns the references.
     * 
     * @return The references
     */
    public Reference[] getReferences()
    {
    	if (references == null) {
    		references = new ArrayList<Reference>();
    		for (int i = 0; i < foreignKey.getReferenceCount(); i++) {
    			Reference reference = new ReferenceDDLUtils (foreignKey.getReference(i));
    			//reference.setLocalColumn(reference.getLocalColumn());
    			references.add(reference);
    		}
    	}
    	return (Reference[])references.toArray(new Reference[references.size()]);		    	
    }

    /**
     * Returns the first reference if it exists.
     * 
     * @return The first reference
     */
    public Reference getFirstReference()
    {
        return new ReferenceDDLUtils (foreignKey.getFirstReference());
    }

    /**
     * Removes the indicated reference.
     * 
     * @param idx The index of the reference to remove
     */
    public void removeReference(int idx)
    {
    	foreignKey.removeReference(idx);
    }

    /**
     * {@inheritDoc}
     
    protected Object clone() throws CloneNotSupportedException
    {
        ForeignKey result = (ForeignKey)super.clone();

        result._name             = _name;
        result._foreignTableName = _foreignTableName;
        result._references       = new ListOrderedSet();

        for (Iterator it = _references.iterator(); it.hasNext();)
        {
            result._references.add(it.next());
        }

        return result;
    }
*/
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj)
    {
    	return foreignKey.equals(obj);
    }


    /**
     * {@inheritDoc}
     */
    public int hashCode()
    {
    	return foreignKey.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return foreignKey.toString();
    }

    /**
     * Returns a verbose string representation of this foreign key.
     * 
     * @return The string representation
     */
    public String toVerboseString()
    {
        return foreignKey.toVerboseString();
    }
}
