package net.sf.minuteProject.configuration.bean.enrichment.convention;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class EntityNamingConvention extends Convention {

	public final String APPLY_STRIP_TABLE_NAME_SUFFIX="apply-strip-table-name-suffix";
	public final String APPLY_STRIP_TABLE_NAME_PREFIX="apply-strip-table-name-prefix";
	
	@Override
	public void apply(BusinessModel model) {
		if (APPLY_STRIP_TABLE_NAME_SUFFIX.equals(type) || APPLY_STRIP_TABLE_NAME_PREFIX.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					if (isConventionApplicable (table))
						apply (table);
				}
			}
		}
	}
	
	public void patternToStrip (String s) {
		setDefaultValue(s);
	}

	private boolean isConventionApplicable(Table table) {
		return (table.getAlias().equals(table.getName()));
	}

	private void apply(Table table) {
		for (String s : ParserUtils.getList(defaultValue)) {
			if (apply (table, s)) return;
		}		
	}

	private boolean apply(Table table, String s) {
		if (APPLY_STRIP_TABLE_NAME_SUFFIX.equals(type))
			return applyStripSuffix (table, s);
		if (APPLY_STRIP_TABLE_NAME_PREFIX.equals(type))
			return applyStripPrefix (table, s);
		return true;
	}

	private boolean applyStripPrefix(Table table, String s) {
		String name = table.getName();
		if (name.startsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeStart(table.getName(), s);
			table.setName(newName);
			performReferenceUpdate(table, name, newName);
			return true;
		}
		return false;
	}

	private boolean applyStripSuffix(Table table, String s) {
		String name = table.getName();
		if (name.endsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeEnd(table.getName(), s);
			table.setName(newName);
			performReferenceUpdate(table, name, newName);
			return true;
		}
		return false;
	}

	//TODO check + apply for replace-name-with-alias
	private void performReferenceUpdate(Table table, String name, String newName) {
		for (ForeignKey fk : table.getForeignKeys()) {
			for (Reference ref : fk.getReferences()) {
				if (newName.equals(ref.getLocalTable().getName())) 
					ref.setLocalTableName(newName);
				//break;
			}
		}
		
	}
}
