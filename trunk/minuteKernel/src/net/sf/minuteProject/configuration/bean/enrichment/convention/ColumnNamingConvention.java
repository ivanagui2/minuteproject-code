package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
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

	private void apply(Table table) {
		for (Column column : table.getColumns()) {
			apply (column);
		}
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
			column.setName(newName);
			performReferenceUpdate(column, name, newName);
			return true;
		}
		return false;
	}

	private boolean applyStripSuffix(Column column, String s) {
		String name = column.getName();
		if (name.endsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeEnd(column.getName(), s);
			column.setName(newName);
			performReferenceUpdate(column, name, newName);
			return true;
		}
		return false;
	}

	private void performReferenceUpdate(Column column, String name, String newName) {
		for (ForeignKey fk : column.getTable().getForeignKeys()) {
			for (Reference ref : fk.getReferences()) {
				if (newName.equals(ref.getLocalColumn().getName())) 
					ref.setLocalColumnName(newName);
				//break;
			}
		}
		
	}

}
