package net.sf.minuteProject.configuration.bean.model.data.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.Index;

/**
 * @author Florian Adler
 *
 */
public abstract class TableAbstract extends AbstractConfiguration implements Table{
	
	private Table table;
	private String alias;
	private Reference [] distinctChildrenRef;
	private SemanticReference semanticReference;
	private String contentType;
	
	public TableAbstract () {
	}
	
	public TableAbstract (Table table) {
		setTable (table);
		this.setAlias(table.getAlias());
		this.setProperties(table.getProperties());
		this.setContentType(table.getContentType());
		this.setSemanticReference(table.getSemanticReference());
	}
	
	public String getName () {
		return table.getName();
	}
	
	public net.sf.minuteProject.configuration.bean.Package getPackage() {
		return table.getPackage();
	}

	public void setPackage(net.sf.minuteProject.configuration.bean.Package pack) {
		this.table.setPackage(pack);
	}

	public Database getDatabase() {
		return table.getDatabase();
	}

	public void setDatabase(Database database) {
		this.table.setDatabase(database);
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
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
		return table.getColumns();	
	}

    /**
     * Returns the column at the specified position.
     * 
     * @param idx The column index
     * @return The column at this position
     */
    public Column getColumn(int idx) {
    	return table.getColumn(idx);
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
		return getPackage().getTechnicalPackage(template);
	}

	/**
	private void setNoPrimaryKeyNoForeignKeyColumns() {
		List<String> primaryKeyAndForeignKeyColumnsName = new ArrayList<String>();
		List<Column> noPrimaryKeyNoForeignKeyColumnsName = new ArrayList<Column>();
		if (table.getNoPrimaryKeyNoForeignKeyColumns()==null) {
			// populate with foreign key columns
			ForeignKey [] foreignKeys =  getForeignKeys();
			for (int i=0; i < foreignKeys.length; i++) {
				Reference references [] = foreignKeys[i].getReferences();
				for (int j=0; j < references.length; j++) {
					primaryKeyAndForeignKeyColumnsName.add (references[j].getLocalColumnName());
				}
			}
			// add pk columns
			Column [] primaryKeys =  getPrimaryKeyColumns();
			for (int i=0; i < primaryKeys.length; i++) {
				primaryKeyAndForeignKeyColumnsName.add (primaryKeys[i].getName());
			}			
			// compute the remaining column
			Column columns [] = getColumns();
			boolean isMatching;
			for (int i=0; i < columns.length; i++) {
				isMatching = false;
				for (Iterator<String> iter = primaryKeyAndForeignKeyColumnsName.iterator(); iter.hasNext();) {
					String name = iter.next();
					if (name.equals(columns [i].getName())) {
						isMatching = true;
						break;
					}
				}
				if (isMatching == false)
					noPrimaryKeyNoForeignKeyColumnsName.add (columns [i]);
			}
			//noPrimaryKeyNoForeignKeyColumns = (Column []) noPrimaryKeyNoForeignKeyColumnsName.toArray();
			table.getNoPrimaryKeyNoForeignKeyColumns() = new Column [noPrimaryKeyNoForeignKeyColumnsName.size()];
			int posix = 0;
			for (Iterator<Column> iter = noPrimaryKeyNoForeignKeyColumnsName.iterator(); iter.hasNext();) {
				noPrimaryKeyNoForeignKeyColumns [posix] = (Column)iter.next();
				posix++;
			}
		}

	}
*/
	public Column[] getNoPrimaryKeyNoForeignKeyColumns() {
		return table.getNoPrimaryKeyNoForeignKeyColumns();
	}

	public String toVerboseString() {
		return table.toVerboseString();
	}

	public String getType() {
		return table.getType();
	}

	public Column [] getAttributes () {
		return getColumns();
	}
	
    /**
     * Get the array of parents 
     * @return Reference
     */
    public Reference [] getParents() {
    	return table.getParents();
    }
    
    /**
     * Get the associated children
     * @return Reference
     */
    public Reference [] getChildren() {
    	return table.getChildren();
    }
    
    /**
     * Indicates if it is a many to many table
     * A table is many to many if it contains 2 columns only and those colums are Foreign keys.
     * @return boolean
     */
    public boolean isManyToMany() {
    	return (getColumnCount() == 2) 
    	        && (getParents().length == 2);
    }
    
    public boolean equals (Object object) {
    	if (!(object instanceof Table))
    		return false;
    	return this.getName().equals(((Table)object).getName());
    }
    
	public String getAlias() {
		if (alias!=null && !alias.equals(""))
			return alias;
		else
			return getName();
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public boolean isManyToManyRecursive() {
		if (isManyToMany()) {
		   Reference [] parents = getParents();
		   return parents[0].getForeignTableName().equals(parents[1].getForeignTableName());
		}
		return false;
	}
	
	public Reference [] getDistinctChildrenType() {
		if (distinctChildrenRef==null)
			distinctChildrenRef = getDistinctChildrenTypeArray();
		return distinctChildrenRef;
	}
	
	public Reference [] getDistinctChildrenTypeArray() {
		List<Reference> distinctTypes = new ArrayList<Reference>();

		Reference[] references = getChildren();
		for (int i = 0; i < references.length; i++) {
			boolean toAdd = true;
			for (Reference reference : distinctTypes) {
				if (   reference.getForeignTableName().equals(references[i].getForeignTableName())  
					&& reference.getLocalTableName().equals(references[i].getLocalTableName())	
					) {
					toAdd = false;
					break;
				}
			}	
			if (toAdd)
				distinctTypes.add(references[i]);
		}
		return (Reference []) distinctTypes.toArray(new Reference[distinctTypes.size()]);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public SemanticReference getSemanticReference() {		
		return semanticReference;
	}

	public void setSemanticReference(SemanticReference semanticReference) {
		this.semanticReference = semanticReference;
	}
	
//	public String getContentType() {
//		return table.getContentType();
//	}
//
//	public void setContentType(String contentType) {
//		table.setContentType(contentType);
//	}
//
//	public SemanticReference getSemanticReference() {
//		return table.getSemanticReference();
//	}
//
//	public void setSemanticReference(SemanticReference semanticReference) {
//		table.setSemanticReference(semanticReference);
//	}
//	
	public boolean hasVersion() {
		return false;
	}
	
}
