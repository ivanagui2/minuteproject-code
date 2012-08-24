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

public class ForeignKeyConvention extends ModelConvention {

	public static final String APPLY_DEFAULT_FOREIGN_KEY_BASED_ON_TARGET_PRIMARY_KEY_NAME_WHEN_NO_AMBIGUITY = "apply-default-foreign-key-based-on-target-primary-key-name-when-no-ambiguity";
	public static final String APPLY_DEFAULT_FK_BY_ENTITY_NAME_AND_SUFFIX = "apply-default-foreign-key-by-entity-name-and-suffix";
	public static final String AUTODETECT_FOREIGN_KEY_BASED_ON_SIMILARITY_AND_MAP = "autodetect-foreign-key-based-on-similarity-and-map";

	public String defaultSuffix, columnEnding, columnStarting;

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

	@Override
	public void apply(BusinessModel model) {
		if (AUTODETECT_FOREIGN_KEY_BASED_ON_SIMILARITY_AND_MAP.equals(type)) {
			if (model.getBusinessPackage() != null) {
				for (Table table : model.getBusinessPackage().getTables()) {
					applyEntitySimilarity(table);
				}
			}
		}
		if (APPLY_DEFAULT_FOREIGN_KEY_BASED_ON_TARGET_PRIMARY_KEY_NAME_WHEN_NO_AMBIGUITY.equals(type)) {
			if (model.getBusinessPackage() != null) {
				for (Table table : model.getBusinessPackage().getTables()) {
					applyFieldSimilarity(table);
				}
			}
		}
	}

	private void applyEntitySimilarity(Table table) {
		for (Field field : getForeignKeyFieldsNotInSelfReferencedPrimaryKey(table)){
			ForeignKeyUtils.setForeignKey(table, field);
		}
	}
	private void applyFieldSimilarity(Table table) {
		for (Field field : getForeignKeyFieldsBasedOnTargetPk(table)){
			ForeignKeyUtils.setForeignKey(table, field);
		}
	}
	
	private List<Field> getForeignKeyFieldsBasedOnTargetPk(Table table) {
		List<Field> list = new ArrayList<Field>();
		for (Column column : table.getColumns()) {
			if (isConventionToApply(column)) { // 
				Field f = getForeignKeyField(column, table);
				if (f != null) {
					list.add(f);
				}
			}
		}
		return list;
	}

	private List<Field> getForeignKeyFieldsNotInSelfReferencedPrimaryKey(Table table) {
		List<Field> list = new ArrayList<Field>();
		for (Column column : table.getColumns()) {
			if (isConventionToApply(column)) { // 
				Field f = getForeignKeyField(column, table);
				if (f != null) {
					list.add(f);
				}
			}
		}
		return list;
	}

	private boolean isSelfReferencePrimaryKey(Column column) {
		if (column.isPrimaryKey()) {
			Table target = getTarget(column);
			if (target!=null && 
				 target.getName().toLowerCase().equals(column.getTable().getName().toLowerCase()))
				return true;
		}
		return false;
	}

	
	private Table getTarget(Column column) {
		Table table = column.getTable();
		String tablename = getTargetEntityNameLowerCase(column);
		Table target = TableUtils.getTable(table.getDatabase(), tablename);
		if (target == null) {
			target = TableUtils.getTableFromAlias(table.getDatabase(), tablename);
		}
		return target;
	}

	private Field getForeignKeyField(Column column, Table table) {
		// if (column.isPrimaryKey() && !table.isManyToMany()) return null;
//		String tablename = getTargetEntityName(column);
//		Table target = TableUtils.getTable(table.getDatabase(), tablename);
//		if (target == null)
//			target = TableUtils.getTableFromAlias(table.getDatabase(), tablename);
		Table target = getTarget(column);
		if (target != null) {
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

	private String getTargetEntityNameLowerCase(Column column) {
		String key = column.getName().toLowerCase();
		if (columnEnding != null && !"".equals(columnEnding))
			key = StringUtils.stripEnd(key, columnEnding);
		if (columnStarting != null && !"".equals(columnStarting))
			key = StringUtils.stripStart(key, columnStarting);
		return key;
	}

	private boolean isConventionToApply(Column column) {
		if (isSelfReferencePrimaryKey(column)) return false;
		return isConventionToApplyOnPatternRelevance(column);
	}
	
	private boolean isConventionToApplyOnPatternRelevance(Column column) {
		if ((columnEnding == null || "".equals(columnEnding)) &&
			(columnStarting == null || "".equals(columnStarting))) return false;
		String key = getTargetEntityNameLowerCase(column);
		if (key.equals(column.getName().toLowerCase())) {
			return false;
		}
		Table target = getTarget(column);
		if (target==null) 
			return false;
		String targetType = TableUtils.getPrimaryKeyType(target);
		if (!targetType.equals(column.getType()))
			return false;
		return true;
	}
}
