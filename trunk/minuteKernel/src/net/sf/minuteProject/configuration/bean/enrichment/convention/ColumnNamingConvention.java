package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

import org.apache.commons.lang.StringUtils;

public class ColumnNamingConvention extends ModelConvention {

	public final static String APPLY_STRIP_COLUMN_NAME_SUFFIX="apply-strip-column-name-suffix";
	public final static String APPLY_STRIP_COLUMN_NAME_PREFIX="apply-strip-column-name-prefix";
	public final static String APPLY_FIX_PRIMARY_KEY_COLUMN_NAME_WHEN_NO_AMBIGUITY="apply-fix-primary-key-column-name-when-no-ambiguity";
	public final static String APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_NAME  ="apply-strip-field-name-prefix-when-matching-entity-name";
	public final static String APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_ALIAS ="apply-strip-field-name-prefix-when-matching-entity-alias";


	
	@Override
	public void apply(BusinessModel model) {
		if (APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_NAME.equals(type) || APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_ALIAS.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyMatch (table);
				}
			}
		}		
		if (APPLY_STRIP_COLUMN_NAME_SUFFIX.equals(type) || APPLY_STRIP_COLUMN_NAME_PREFIX.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else if (APPLY_FIX_PRIMARY_KEY_COLUMN_NAME_WHEN_NO_AMBIGUITY.equals(type))  {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyFixPk (table);
				}
			}			
		}
		
	}

	private void applyMatch (Table table) {
		for (Column column : table.getColumns())
			apply (table, column);	
	}

	private void apply(Table table, Column column) {
		if (APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_NAME.equals(type))
			applyMatchEntityName(table, column);
		else
			applyMatchEntityAlias(table, column);
	}

	private void applyMatchEntityName(Table table, Column column) {
//		if (column.getAlias().toLowerCase().startsWith(table.getName().toLowerCase()))
		column.setAlias(StringUtils.removeStart(column.getAlias().toLowerCase(), table.getName().toLowerCase()).toUpperCase());
	}

	private void applyMatchEntityAlias(Table table, Column column) {
//		if (column.getAlias().toLowerCase().startsWith(table.getName().toLowerCase()))
		column.setAlias(StringUtils.removeStart(column.getAlias().toLowerCase(), table.getAlias().toLowerCase()).toUpperCase());
	}
	
	private void applyFixPk(Table table) {
		if (defaultValue==null) return;
		if (table.getPrimaryKeyColumns().length>1) return;
		for (Column column : table.getColumns()) {
			if (column.getAlias().toLowerCase().equals(defaultValue.toLowerCase()))
				return;
		}		
		String firstColumn = null;
		for (Column column : table.getPrimaryKeyColumns()) {
			applyFixPk (column);
			firstColumn=column.getName();
			continue; //only one
		}	
		// also of the column in attribute
		Column column = ColumnUtils.getColumn(table, firstColumn) ;
		column.setAlias(defaultValue);	
	}

	public void setPatternToStrip (String s) {
		setDefaultValue(s);
	}
	
	private void apply(Table table) {
		apply(table.getColumns());
		apply(table.getPrimaryKeyColumns());
		apply(table.getAttributes());
		apply(table.getNoPrimaryKeyNoForeignKeyColumns());
	}

	private void apply(Column[] columns) {
		for (Column column : columns) {
			if (isConventionApplicable(column))
				apply (column);
		}
	}

	private boolean isConventionApplicable(Column column) {
		return (column.getAlias().equals(column.getName()));
	}

	private void applyFixPk(Column column) {
		setNewColumnValue (column, column.getName(), defaultValue);
	}
	
	private void apply(Column column) {
		for (String s : ParserUtils.getList(defaultValue)) {
			if (apply (column, s)) return;
		}
	}

	private boolean apply(Column column, String s) {
		if (APPLY_STRIP_COLUMN_NAME_SUFFIX.equals(type))
			return applyStripSuffix (column, s);
		if (APPLY_STRIP_COLUMN_NAME_PREFIX.equals(type))
			return applyStripPrefix (column, s);
		return true;
	}

	private boolean applyStripPrefix(Column column, String s) {
		String name = column.getName();
		if (name.startsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeStart(column.getName(), s);
			setNewColumnValue(column, name, newName);
//			column.setAlias(newName);
//			setReferenceColumnAlias(column, name, newName);
			return true;
		}
		return false;
	}

	private boolean applyStripSuffix(Column column, String s) {
		String name = column.getName();
		if (name.endsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeEnd(column.getName(), s);
			setNewColumnValue(column, name, newName);
//			column.setAlias(newName);
//			setReferenceColumnAlias(column, name, newName);
			return true;
		}
		return false;
	}

	private void setNewColumnValue (Column column, String name, String newName) {
		column.setAlias(newName);
		setReferenceColumnAlias(column, name, newName);		
	}
	
	private void setReferenceColumnAlias(Column column, String name, String newName) {
		ReferenceUtils.setReferenceColumnAlias(column, name, newName);
////		for (ForeignKey fk : column.getTable().getForeignKeys()) {
//			for (Reference ref : column.getTable().getParents()) {
//				if (name.equals(ref.getLocalColumn().getName())) 
//					ref.getLocalColumn().setAlias(newName);
////					ref.setLocalColumnName(newName);
//				//break;
//			}
////		}
	}
	
	private void performReferenceUpdate2(Column column, String name, String newName) {
		for (ForeignKey fk : column.getTable().getForeignKeys()) {
			for (Reference ref : fk.getReferences()) {
				if (newName.equals(ref.getLocalColumn().getName())) 
					ref.setLocalColumnName(newName);
				//break;
			}
		}
	}

}
