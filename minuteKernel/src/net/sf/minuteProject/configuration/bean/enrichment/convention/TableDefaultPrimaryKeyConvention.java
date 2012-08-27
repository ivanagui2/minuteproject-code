package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

@SuppressWarnings("serial")
public class TableDefaultPrimaryKeyConvention extends ModelConvention {

	public static final String APPLY_DEFAULT_PK_OTHERWISE_FIRST_FIELD_IS_PK = "apply-default-primary-key-otherwise-first-one";
	public String defaultPrimaryKeyNames, excludePrimaryKeyNames, fieldPatternType="equals";
	public boolean allowCompositePk=true;
	
	public String getDefaultPrimaryKeyNames() {
		return defaultPrimaryKeyNames;
	}
	public void setDefaultPrimaryKeyNames(String defaultPrimaryKeyNames) {
		this.defaultPrimaryKeyNames = defaultPrimaryKeyNames;
	}
	public String getExcludePrimaryKeyNames() {
		return excludePrimaryKeyNames;
	}
	public void setExcludePrimaryKeyNames(String excludePrimaryKeyNames) {
		this.excludePrimaryKeyNames = excludePrimaryKeyNames;
	}	
	public boolean isAllowCompositePk() {
		return allowCompositePk;
	}
	public void setAllowCompositePk(boolean allowCompositePk) {
		this.allowCompositePk = allowCompositePk;
	}
	public String getFieldPatternType() {
		return fieldPatternType;
	}
	public void setFieldPatternType(String fieldPatternType) {
		this.fieldPatternType = fieldPatternType;
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
		Column column;
		if (table.getColumnCount()>0) {
			column = table.getColumn(0);
			if (column!=null)
				pks.add(column);
		}
		return pks;
	}
	
	protected List<Column> getPksByDefaultPrimaryKeyNames(Table table) {
		List<Column> pks = new ArrayList<Column>();
		if (defaultPrimaryKeyNames!=null && !defaultPrimaryKeyNames.isEmpty()) {
			for (String pattern: ParserUtils.getList(defaultPrimaryKeyNames)) {
				for (Column column : table.getColumns()) {
					String columnName=column.getName();
					if (net.sf.minuteProject.utils.StringUtils.checkExpression(columnName, fieldPatternType, pattern)
						&&	!ParserUtils.isInList(columnName, getExcludePrimaryKeyNames())) {
						//pks.add(column);
						pks.add(ColumnUtils.getColumn(table, columnName));
						if (!allowCompositePk)
							return pks;
					}		
				}
			}
		}
//		if (pk!=null) {
//			pk = StringUtils.remove(pk, " ");
//			for (String columnName : getDefaultPrimaryKeyNames().split(",")) {
//				if (!ParserUtils.isInList(columnName, getExcludePrimaryKeyNames())) {
//					Column column = ColumnUtils.getColumn(table, columnName);
//					if (column!=null)
//						pks.add(column);
//				}
//			}
//		}
		return pks;
	}
	
}
