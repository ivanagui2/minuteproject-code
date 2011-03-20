package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

import org.apache.commons.lang.StringUtils;

public class ColumnNamingConvention extends Convention {

	public final String APPLY_STRIP_COLUMN_NAME_SUFFIX="apply-strip-column-name-suffix";
	public final String APPLY_STRIP_COLUMN_NAME_PREFIX="apply-strip-column-name-prefix";
	
	@Override
	public void apply(BusinessModel model) {
		if (APPLY_STRIP_COLUMN_NAME_SUFFIX.equals(type) || APPLY_STRIP_COLUMN_NAME_PREFIX.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		}
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
			column.setAlias(newName);
			setReferenceColumnAlias(column, name, newName);
			return true;
		}
		return false;
	}

	private boolean applyStripSuffix(Column column, String s) {
		String name = column.getName();
		if (name.endsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeEnd(column.getName(), s);
			column.setAlias(newName);
			setReferenceColumnAlias(column, name, newName);
			return true;
		}
		return false;
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
