package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;

/*
 * Inspired from DDLUtils Reference
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

import org.apache.commons.lang.builder.EqualsBuilder;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;

/**
 * Represents a reference between a column in the local table and a column in another table.
 * 
 * @author Florian Adler
 */
public class ReferenceDDLUtils implements Reference
{
	private org.apache.ddlutils.model.Reference reference;
	
	private Column foreignColumn;
	private String foreignColumnName;
	private Table  foreignTable;
	private String foreignTableName;
	private Column localColumn;
	private Table  localTable;
	
    /**
     * Creates a new, empty reference.
     */
    public ReferenceDDLUtils(org.apache.ddlutils.model.Reference reference)
    {
    	this.reference = reference;
    }

    /**
     * Returns the sequence value within the owning key.
     *
     * @return The sequence value
     */
    public int getSequenceValue()
    {
        return reference.getSequenceValue();
    }

    /**
     * Sets the sequence value within the owning key. Please note
     * that you should not change the value once the reference has
     * been added to a key.
     *
     * @param sequenceValue The sequence value
     */
    public void setSequenceValue(int sequenceValue)
    {
    	reference.setSequenceValue(sequenceValue);
    }

    /**
     * Returns the local column.
     *
     * @return The local column
     */
    public Column getLocalColumn()
    {
    	// By default the reference is view as absolute
    	if (localColumn==null)
    		return new ColumnDDLUtils (reference.getLocalColumn(), getLocalTable());
    	else
    		// but this behavior can be overwrite especially for children of entities
    		// a reference can be relative to the entity
    		// the localColumn can be inserted and not taken from the default absolute
    		return localColumn;
        //return reference.getLocalColumn();//localColumn;//new ColumnDDLUtils (reference.getLocalColumn(), getLocalTable());
    	// for ibatis
    	//return new ColumnDDLUtils (reference.getLocalColumn(), getLocalTable());
    }

    /**
     * Returns the foreign column.
     *
     * @return The foreign column
     */
    public Column getForeignColumn()
    {
    	return foreignColumn;
        //return new ColumnDDLUtils (reference.getForeignColumn());
    }

    /**
     * Returns the name of the local column.
     * 
     * @return The column name
     */
    public String getLocalColumnName()
    {
    	return getLocalColumn().getName();
        //return reference.getLocalColumnName();
        // should be replaced by
        //return localColumnName();
    }

    /**
     * Sets the name of the local column. Note that you should not use this method when
     * manipulating the model manually. Rather use the {@link #setLocalColumn(Column)} method.
     * 
     * @param localColumnName The column name
     */
    public void setLocalColumnName(String localColumnName)
    {
    	reference.setLocalColumnName(localColumnName);
    }
    
    /**
     * Returns the name of the foreign column.
     * 
     * @return The column name
     */
    public String getForeignColumnName()
    {
        return reference.getForeignColumnName();
    }
    
    /**
     * Sets the name of the remote column. Note that you should not use this method when
     * manipulating the model manually. Rather use the {@link #setForeignColumn(Column)} method.
     * 
     * @param foreignColumnName The column name
     */
    public void setForeignColumnName(String foreignColumnName)
    {
    	this.foreignColumnName= foreignColumnName;
    }

    /**
     * {@inheritDoc}
     
    protected Object clone() throws CloneNotSupportedException
    {
        Reference result = (Reference)super.clone();

        result._localColumnName   = _localColumnName;
        result._foreignColumnName = _foreignColumnName;

        return result;
    }
*/
    /**
     * {@inheritDoc}
     
    public boolean equals(Object obj)
    {
    	return reference.equals(obj);
    }
    public boolean equals(Object obj)
    {
        if (obj instanceof ReferenceDDLUtils)
        {
        	ReferenceDDLUtils other = (ReferenceDDLUtils)obj;

            return new EqualsBuilder().append(getLocalColumnName(),   other.getLocalColumnName())
                                      .append(getForeignColumnName(), other.getForeignColumnName())
                                      .isEquals();
        }
        else
        {
            return false;
        }
    }*/
    /**
     * {@inheritDoc}
     */
    public int hashCode()
    {
        return reference.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return reference.toString();
    }
    
    // Added methods
    
    public void setForeignColumn(Column column) {
    	foreignColumn = column;
    }

    public void setForeignTable(Table table) {
    	foreignTable = table;
    }

    public Table getForeignTable () {
    	return foreignTable;
    }
    
    public void setForeignTableName(String foreignTableName) {
		this.foreignTableName = foreignTableName;
	}
	
    public String getForeignTableName () {
    	return foreignTableName;
    }

	public void setLocalColumn(Column localColumn) {
		this.localColumn = localColumn;
	}    
    
    
    public boolean equals (Object object) {
    	if (object instanceof ReferenceDDLUtils) {
    		ReferenceDDLUtils toCompare = (ReferenceDDLUtils) object;
    		if (toCompare.getForeignTableName().equals(getForeignTableName())
    		    && toCompare.getForeignColumnName().equals(getForeignColumnName())
    		    && toCompare.getLocalColumnName().equals(getLocalColumnName())
    		    && toCompare.getLocalTableName().equals(getLocalTableName())
    		    ) 
    			return true;
    		return false;
    	}
    	return false;
    }

	public String getLocalTableName() {
		// TODO Auto-generated method stub
		return localTable.getName();
	}

	public void setLocalTableName(String localTableName) {
		// TODO Auto-generated method stub
		
	}

	public Table getLocalTable() {
		return localTable;
	}

	public void setLocalTable(Table localTable) {
		this.localTable = localTable;
	}


}
