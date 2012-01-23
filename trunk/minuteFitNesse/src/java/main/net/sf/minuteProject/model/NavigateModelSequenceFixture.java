package net.sf.minuteProject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import fitlibrary.SequenceFixture;
import net.sf.minuteProject.application.ModelViewGenerator;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.MpObjectBuilder;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.TableUtils;

public class NavigateModelSequenceFixture extends SequenceFixture{

	private enum ObjectAttribute {NAME, ALIAS};
	private enum ColumnType {PK, FK, SIMPLE};
	private enum ReferenceAttribute {LOCAL_COLUMN, LINKED_COLUMN, LOCAL_TABLE, LINKED_TABLE}
	private ModelViewGenerator modelViewGenerator;
	private Configuration configuration;
	private Model model;
	private Database database;
	
	public String load (String config) {
		try {
			modelViewGenerator = new ModelViewGenerator(config);
			configuration = (Configuration) modelViewGenerator.load();
			model = modelViewGenerator.getEnrichedModel(configuration);
			modelViewGenerator.load();
			database = model.getDataModel().getDatabase();
		} catch (MinuteProjectException e) {
			// TODO Auto-generated catch block
			return e.getError();
		}
		return "SUCCESSFUL";
	}

	public int numberOfEntity (String name) {
		if (model!=null) {
			return model.getBusinessModel().getBusinessPackage().getEntities().size();
		}
		return -1;
	}
	
	public Reference getParentReference(String child, String parent) {
		for (Reference ref : parentsReferenceForEntity(child)) {
			if (ref.getForeignTable().getName().toLowerCase().equals(parent.toLowerCase()))
				return ref;
		}
		return null;
	}
	public Table getEntity (String name) {
		Database db = model.getDataModel().getDatabase();
		return TableUtils.getEntity(db, name);
	}
	public List<Reference> parentsReferenceForEntity (String name) {
		Database db = model.getDataModel().getDatabase();
		return Arrays.asList(TableUtils.getEntity(db, name).getParents());
	}
	
	public int numberOfParentsForEntity (String name) {
		if (model!=null) {
			return parentsReferenceForEntity(name).size();
		}
		return -1;
	}

	
//	public List<String> getNamesOfParentsEntitiesForEntity (String name) {
//		List<String> list = new ArrayList<String>();
//		List<Reference> l =  parentsReferenceForEntity(name);
//		for (Reference ref : l) {
//			list.add(ref.getTable()Name());
//		}
//		return list;
//	}
	
	public String namesOfParentsEntityForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.NAME, ReferenceAttribute.LINKED_TABLE);
	}
	
	public String aliasesOfParentsEntityForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.ALIAS, ReferenceAttribute.LINKED_TABLE);
	}
	
	public String namesOfLocalColumnsForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.NAME, ReferenceAttribute.LOCAL_COLUMN);
	}
	
	public String aliasesOfLocalColumnsForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.ALIAS, ReferenceAttribute.LOCAL_COLUMN);
	}
	
	
	public String compareAttributesOfParentsEntitiesForEntity (String name, 
			 String values,
			 ObjectAttribute attribute,
			 ReferenceAttribute refAttribute) {
		List<String> l1 = getAttributesOfParentsEntitiesForEntity (name, attribute, refAttribute);
		List<String> l2 = Arrays.asList(StringUtils.split(values, ","));
		if (isIdentical(l1, l2)) {
			return "true";
		}
		return l1.toString();
	}
	
	
	public List<String> getAttributesOfParentsEntitiesForEntity 
			(String name,
			 ObjectAttribute attribute,
			 ReferenceAttribute refAttribute) {
		List<String> list = new ArrayList<String>();
		List<Reference> l =  parentsReferenceForEntity(name);
		for (Reference ref : l) {
			String s = "";
			switch (refAttribute) {
				case LOCAL_COLUMN: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getLocalColumn().getName():ref.getLocalColumn().getAlias();
					break;
				case LINKED_COLUMN: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getForeignColumn().getName():ref.getForeignColumn().getAlias();
					break;
				case LOCAL_TABLE: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getLocalTable().getName():ref.getLocalTable().getAlias();
					break;
				case LINKED_TABLE: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getForeignTable().getName():ref.getForeignTable().getAlias();
					break;
			}
			list.add(s);
		}
		return list;
	}
	
	public boolean isIdentical (List<String> l1, List<String> l2) {
      Collection<String> compare = new HashSet<String>(l1);
      return (compare.retainAll(l2)&&compare.removeAll(l2));
	}
	
	public String aliasJavaVariableOfFirstRelationshipBetween (String child, String parent) {
//		Reference ref = getReference(child, parent);
		return CommonUtils.getColumnNameVariable(getEntity(child), getParentReference(child, parent));
	}
	
	public String aliasJavaVariableOfFirstRelationshipBetweenNew (String child, String parent) {
		Reference reference = getParentReference(child, parent);
		return FormatUtils.getJavaNameVariable(ReferenceUtils.getColumnAlias(reference.getForeignTable(), reference.getForeignColumn()));
	}
	
	public String columnClassNameOfFirstRelationshipBetween (String child, String parent) {
		Reference reference = getParentReference(child, parent);
		Database db = model.getDataModel().getDatabase();
		return ReferenceUtils.getColumnClassNameForLinkTable(db, reference.getForeignColumn());
		
		//FormatUtils.getJavaNameVariable(ReferenceUtils.getColumnAlias(reference.getForeignTable(), reference.getForeignColumn()));
	}
	
	public String columnClassNameOfFirstRelationshipBetweenNew (String child, String parent) {
		Reference reference = getParentReference(child, parent);
		Database db = model.getDataModel().getDatabase();
//		return ReferenceUtils.getColumnAlias(reference.getForeignTable(), reference.getForeignColumn());
		return ReferenceUtils.getColumnAlias(TableUtils.getEntity(db, reference.getForeignTable().getName()), reference.getForeignColumn());
		
		//FormatUtils.getJavaNameVariable(ReferenceUtils.getColumnAlias(reference.getForeignTable(), reference.getForeignColumn()));
	}

	public String aliasOfColumnOfTable (String columnName, String tableName) {
		Column column = ColumnUtils.getColumn(TableUtils.getEntity(database, tableName), columnName);
		return column.getAlias();
	}
	
	public Column getColumnOfTableByType (String columnName, String tableName, ColumnType type) throws MinuteProjectException {
		for (Table table : model.getBusinessModel().getBusinessPackage().getTables()) {
			if (table.getName().equals(tableName)) {
				if (type.equals(ColumnType.PK))
					for (Column column:table.getPrimaryKeyColumns()) {
						System.out.println("column "+column.getName());
						if (column.getName().equals(columnName)) {
							return column;
						}
					}
				if (type.equals(ColumnType.SIMPLE))
					for (Column column:table.getColumns()) {
						System.out.println("column "+column.getName());
						if (column.getName().equals(columnName)) {
							return column;
						}
					}
				throw new MinuteProjectException("column not found","error");
			}
		}
		throw new MinuteProjectException("table not found","error");
	}
	
	public String aliasOfColumnOfTableNew (String columnName, String tableName) {
		try {
			return getColumnOfTableByType(columnName, tableName, ColumnType.PK).getAlias();
		} catch (MinuteProjectException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}

	public String columnNameOfTableNameIsNotForeignKey (String columnName, String tableName) {
		String s = columnNameOfTableNameIsForeignKey(columnName, tableName);
		if ("true".equals(s)) return "false";
		if ("false".equals(s)) return "true";
		return s;
	}
	public String columnNameOfTableNameIsForeignKey (String columnName, String tableName) {
		try {
			Column column = getColumnOfTableByType(columnName, tableName, ColumnType.SIMPLE);
			return (ColumnUtils.isForeignKey(column))?"true":"false";
		} catch (MinuteProjectException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
}
