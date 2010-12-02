package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.utils.ColumnUtils;

@SuppressWarnings("serial")
public class TableDefaultPrimaryKeyConvention extends Convention {

	public static final String APPLY_DEFAULT_PK_OTHERWISE_FIRST_FIELD_IS_PK = "apply-default-primary-key-otherwise-first-one";
	public String defaultPrimaryKeyNames;
	
	public String getDefaultPrimaryKeyNames() {
		return defaultPrimaryKeyNames;
	}
	public void setDefaultPrimaryKeyNames(String defaultPrimaryKeyNames) {
		this.defaultPrimaryKeyNames = defaultPrimaryKeyNames;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultPrimaryKeyNames = defaultValue;
	}
	
	@Override
	public void apply(BusinessModel model) {
		if (model.getBusinessPackage()!=null) {
			for (Table table : model.getBusinessPackage().getTables()) {
				if (table.getPrimaryKeyColumns().length==0)
					apply (table);
			}
		}
	}
	
	protected void apply(Table table) {
		table.setPrimaryKeys(getVirtualPrimaryKey(table));
	}
	
	protected Column[] getVirtualPrimaryKey(Table table) {
		List<Column> pks = getPksByDefaultPrimaryKeyNames(table);
		if (pks==null || pks.isEmpty())
			pks = getPksByFirstColumn(table);
		return (Column[])pks.toArray(new Column[pks.size()]);
	}
	
	protected List<Column> getPksByFirstColumn(Table table) {
		List<Column> pks = new ArrayList<Column>();
		Column column = table.getColumn(0);
		if (column!=null)
			pks.add(column);		
		return pks;
	}
	
	protected List<Column> getPksByDefaultPrimaryKeyNames(Table table) {
		List<Column> pks = new ArrayList<Column>();
		String pk = getDefaultPrimaryKeyNames();
		if (pk!=null) {
			pk = StringUtils.remove(pk, " ");
			for (String columnName : getDefaultPrimaryKeyNames().split(",")) {
				Column column = ColumnUtils.getColumn(table, columnName);
				if (column!=null)
					pks.add(column);
			}
		}
		return pks;
	}
	
}
