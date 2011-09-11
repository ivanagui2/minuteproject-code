package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ForeignKeyUtils;
import net.sf.minuteProject.utils.TableUtils;

public class ForeignKeyConvention extends Convention {

	public static final String APPLY_DEFAULT_FK_BY_ENTITY_NAME_AND_SUFFIX = "apply-default-foreign-key-by-entity-name-and-suffix";
	public static final String AUTODETECT_FOREIGN_KEY_BASED_ON_SIMILARITY_AND_MAP = "autodetect-foreign-key-based-on-similarity-and-map";
	
	public String defaultSuffix, columnEnding, columnStarting, columnBody;
	
	public String getDefaultSuffix() {
		return defaultSuffix;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultSuffix = defaultValue;
	}
	public void setDefaultSuffix(String defaultSuffix) {
		this.defaultSuffix = defaultSuffix;
	}
	
	public String getColumnEnding() {
		return columnEnding;
	}
	public void setColumnEnding(String columnEnding) {
		this.columnEnding = columnEnding;
	}
	public String getColumnStarting() {
		return columnStarting;
	}
	public void setColumnStarting(String columnStarting) {
		this.columnStarting = columnStarting;
	}
	public String getColumnBody() {
		return columnBody;
	}
	public void setColumnBody(String columnBody) {
		this.columnBody = columnBody;
	}
	@Override
	public void apply(BusinessModel model) {
		if (AUTODETECT_FOREIGN_KEY_BASED_ON_SIMILARITY_AND_MAP.equals(type)) {
			if (model.getBusinessPackage()!=null) {
//				Database database = model.getModel().getDataModel().getDatabase();
				for (Table table : model.getBusinessPackage().getTables()) {
//					Table t = TableUtils.getEntity(database, table.getName());
					apply (table);
				}
			}
		}		
	}
	
	private void apply (Table table) {
		for (Field field: getForeignKeyFields(table))
			ForeignKeyUtils.setForeignKey (table, field);
	}
	
	private List<Field> getForeignKeyFields (Table table) {
		List<Field> list = new ArrayList<Field>();
		for (Column column : table.getColumns()) {
			 Field f = getForeignKeyField(column, table);
			 if (f!=null) {
				 list.add(f);
//				 System.out.println("f = "+f);
			 }
		}
		return list;
	}
	
	private Field getForeignKeyField(Column column, Table table) {
		if (column.isPrimaryKey() && !table.isManyToMany()) return null;
		String tablename = getTargetEntityName (column);
		Table target = TableUtils.getTable(table.getDatabase(), tablename);
		if (target!=null) {
			Field f = new Field();
			f.setName(column.getName());
			f.setLinkToTargetEntity(target.getName());
			f.setLinkToTargetField(TableUtils.getPrimaryKey(target));
			Entity entity = new Entity();
			entity.setName(table.getName());
			f.setEntity(entity);
			f.setBidirectional("true");
			return f;
		}
		return null;
	}
	
	private String getTargetEntityName(Column column) {
		String key = column.getName().toLowerCase();
		if (columnEnding!=null && !"".equals(columnEnding)) 
			key = StringUtils.stripEnd(key, columnEnding);
		if (columnStarting!=null && !"".equals(columnStarting)) 
			key = StringUtils.stripStart(key, columnStarting);		
		return key;
	}  
	
}
