package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ddlutils.model.Index;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.TableAbstract;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.TableUtils;

/**
 * @author Florian Adler
 *
 */
public class TableDDLUtils extends TableAbstract {
	
	private org.apache.ddlutils.model.Table table;
	private Database database;
	private ArrayList<Column> columns;
	private ArrayList<ForeignKey> foreignKeys;
	private ArrayList<Column> primaryKeys;
	private ArrayList<Reference> parents;
	private ArrayList<Reference> children;
	
	private net.sf.minuteProject.configuration.bean.Package pack;
	private Column [] noPrimaryKeyNoForeignKeyColumns;
	
	
	public TableDDLUtils (org.apache.ddlutils.model.Table table) {
		super();
		if (this.table==null)
			setTable (table);
		if (noPrimaryKeyNoForeignKeyColumns==null)
			setNoPrimaryKeyNoForeignKeyColumns();
	}
	
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

	public Table getTable() {
		return new TableDDLUtils (table);
	}

	public void setTable(org.apache.ddlutils.model.Table table) {
		this.table = table;
	}

	public Column findColumn(String name) {
		// TODO Auto-generated method stub
		return new ColumnDDLUtils(table.findColumn(name), this);
	}

	public Column findColumn(String name, boolean caseSensitive) {
		// TODO Auto-generated method stub
		return new ColumnDDLUtils(table.findColumn(name, caseSensitive), this);
	}

	private ForeignKey findForeignKey(org.apache.ddlutils.model.ForeignKey key) {
		// TODO Auto-generated method stub
		return new ForeignKeyDDLUtils (table.findForeignKey(key));
	}

	private ForeignKey findForeignKey(org.apache.ddlutils.model.ForeignKey key, boolean caseSensitive) {
		// TODO Auto-generated method stub
		return new ForeignKeyDDLUtils (table.findForeignKey(key, caseSensitive));
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
    	if (columns == null) {
    		columns = new ArrayList<Column>();
    		for (int i = 0; i < table.getColumnCount(); i++) {
    			Column column = new ColumnDDLUtils (table.getColumn(i), this);
    			columns.add(column);
    		}
    	}
    	return (Column[])columns.toArray(new Column[columns.size()]);		
	}

    /**
     * Returns the column at the specified position.
     * 
     * @param idx The column index
     * @return The column at this position
     */
    public Column getColumn(int idx) {
    	return new ColumnDDLUtils (table.getColumn(idx), this);
    }
    
	public ForeignKey getForeignKey(int idx) {
		// TODO Auto-generated method stub
		return new ForeignKeyDDLUtils (table.getForeignKey(idx));
	}

	public int getForeignKeyCount() {
		// TODO Auto-generated method stub
		return table.getForeignKeyCount();
	}

	public ForeignKey[] getForeignKeys() {
    	if (foreignKeys == null) {
    		foreignKeys = new ArrayList<ForeignKey>();
    		for (int i = 0; i < table.getForeignKeyCount(); i++) {
    			ForeignKey foreignKey = new ForeignKeyDDLUtils (table.getForeignKey(i));
    			foreignKeys.add(foreignKey);
    		}
    	}
    	return (ForeignKey[])foreignKeys.toArray(new ForeignKey[foreignKeys.size()]);		
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
    	if (primaryKeys == null) {
    		primaryKeys = new ArrayList<Column>();
    		org.apache.ddlutils.model.Column [] primaryKeyColumns = table.getPrimaryKeyColumns();
    		for (int i = 0; i < primaryKeyColumns.length; i++) {
    			Column primaryKey = new ColumnDDLUtils (primaryKeyColumns[i], this);
    			primaryKeys.add(primaryKey);
    		}
    	}
    	return (Column[])primaryKeys.toArray(new Column[primaryKeys.size()]);			

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

	private void setNoPrimaryKeyNoForeignKeyColumns() {
		List<String> primaryKeyAndForeignKeyColumnsName = new ArrayList<String>();
		List<Column> noPrimaryKeyNoForeignKeyColumnsName = new ArrayList<Column>();
		if (noPrimaryKeyNoForeignKeyColumns==null) {
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
			noPrimaryKeyNoForeignKeyColumns = new Column [noPrimaryKeyNoForeignKeyColumnsName.size()];
			int posix = 0;
			for (Iterator<Column> iter = noPrimaryKeyNoForeignKeyColumnsName.iterator(); iter.hasNext();) {
				noPrimaryKeyNoForeignKeyColumns [posix] = (Column)iter.next();
				posix++;
			}
		}

	}

	public Column[] getNoPrimaryKeyNoForeignKeyColumns() {
		return noPrimaryKeyNoForeignKeyColumns;
	}

	public String toVerboseString() {
		return table.toVerboseString();
	}

	public String getType() {
		return table.getType();
	}

    /**
     * Get the array of parents 
     * @return Reference
     */
    public Reference [] getParents() {
    	if (parents == null) {
    		parents = new ArrayList<Reference>();
    		boolean error = false;
    		for (int i = 0; i < table.getForeignKeys().length; i++) {
    			error = false;
    			org.apache.ddlutils.model.ForeignKey foreignKeyddlutils = table.getForeignKeys()[i];
    			org.apache.ddlutils.model.Reference referenceddlutils = foreignKeyddlutils.getFirstReference();
				Reference reference = new ReferenceDDLUtils (referenceddlutils);

				Table foreignTable = TableUtils.getTable(database,foreignKeyddlutils.getForeignTableName());
				reference.setForeignColumn(new ColumnDDLUtils(referenceddlutils.getForeignColumn(),foreignTable));
				reference.setForeignColumnName(referenceddlutils.getForeignColumnName());
				reference.setForeignTable(foreignTable);
				reference.setForeignTableName(foreignKeyddlutils.getForeignTableName());
				reference.setLocalColumn(new ColumnDDLUtils(referenceddlutils.getLocalColumn(), this));
				reference.setLocalTable(new TableDDLUtils(table));
				if (reference.getForeignColumnName()==null) {
					System.out.println ("error in ref : no column on "+table.getName()+" - "+reference.getLocalColumnName());
					error = true;
				}
					
				if (!error)
					//parents.add(reference);
					addReference(parents, reference);
			}
    	}
    	return (Reference[])parents.toArray(new Reference[parents.size()]);	
    }
    
    /**
     * Get the associated children
     * @return Reference
     */
    public Reference [] getChildren() {
    	if (children == null) {
    		children = new ArrayList<Reference>();
			String columnRef;
			Reference ref;
			Reference reference;
			Table [] tables = database.getTables();
	    	for (int i = 0; i < tables.length; i++) {
	    		ForeignKey [] fk = tables[i].getForeignKeys();
	        	for (int j = 0; j < fk.length; j++) {
	        		String tableName = fk[j].getForeignTableName();
	        		if (tableName!=null) {
		        		if (tableName.equals(table.getName())) {
		        			ref = fk[j].getReference(0);
		        			columnRef = ref.getLocalColumnName();
		        			Column column = ColumnUtils.getColumn (tables[i], ref.getLocalColumnName());
		        			//reference = new Reference(tables[i], column2, tables[i].getName(), ref.getLocalColumnName());

		        			//org.apache.ddlutils.model.ForeignKey foreignKeyddlutils = tables[i].getForeignKeys()[j].getFirstReference();
		        			//org.apache.ddlutils.model.Reference referenceddlutils = foreignKeyddlutils.getFirstReference();
		    				reference = tables[i].getForeignKeys()[j].getFirstReference();
		    				
		        			//reference = new ReferenceDDLUtils (new org.apache.ddlutils.model.Reference ());
		    				reference.setForeignColumn(column);
		    				reference.setForeignColumnName(column.getName());
		    				reference.setForeignTable(tables[i]);
		    				reference.setForeignTableName(tables[i].getName());
		    				reference.setLocalColumn(TableUtils.getPrimaryFirstColumn(new TableDDLUtils(table)));
		    				reference.setLocalTable(new TableDDLUtils(table));
		    				addReference(children, reference);
		        		}
	        		}
	        	}    	
	        }		
    	}
    	return (Reference[])children.toArray(new Reference[children.size()]);	  	
    }
    
    private void addReference (List list, Reference reference) {
    	if (list==null) return;
    	boolean isAlreadyPresent = false;
    	for (Iterator<Reference> iter = list.iterator(); iter.hasNext();) {
    		if (((Reference)iter.next()).equals(reference)) {
    		    isAlreadyPresent = true;
    		    break;
    		}
    	}
    	if (!isAlreadyPresent)
    		list.add(reference);
    }
}
