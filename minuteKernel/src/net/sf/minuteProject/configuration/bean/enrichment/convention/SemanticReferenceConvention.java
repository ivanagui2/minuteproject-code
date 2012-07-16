package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.experimental.max.MaxCore;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.enrichment.path.SqlPath;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class SemanticReferenceConvention extends ModelConvention {

	private static final int DEFAULT_MAX_FIELD = 3;
	private Logger logger = Logger.getLogger(SemanticReference.class);
	private String entityPattern, patternType;
	private String fieldPattern, fieldPatternType;
	private int maxNumberOfFields, maxColumn=0;
	private boolean toOverride=false;
	private String pack, contentType;

	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else
			logger.error("convention not valid");
	}

	private boolean isValid() {
		return (hasEntityPattern() || hasPackage() || hasContentType() || matchAllEntities()) && hasFieldPattern();
	}
	
	private boolean matchAllEntities() {
		return !hasEntityPattern() && !hasPackage() && !hasContentType();
	}

	private boolean isValid(Table table) {
		return (hasEntityPattern(table) || hasPackage(table) || hasContentType(table) || matchAllEntities());
	}

	private void apply(Table table) {
		if (isValid (table))
			applySemanticReference(table);
	}
	
	private void applySemanticReference(Table table) {
		if (!toOverride && TableUtils.hasSemanticReference(table))
			return;
		int maxColumn = getMaxColumns();
		int cpt=0;
		SemanticReference semanticReference = new SemanticReference();
		List<String> columnNames = ColumnUtils.getColumnNames(table);
		for (String pattern : getFieldPatterns()) {
			for (Column column : table.getColumns()) {
				String colName = column.getName();
				if (columnNames.contains(colName) && cpt <= maxColumn) {
					columnNames.remove(colName);
					if (net.sf.minuteProject.utils.StringUtils.checkExpression(colName, fieldPatternType, pattern)) {
						semanticReference.addSqlPath(getSqlPath(column));
						cpt++;
					}
				}
			}
		}
		table.setSemanticReference(semanticReference);
	}

	private int getMaxColumns() {
		if (maxColumn==0)
			maxColumn = (maxNumberOfFields>0)?maxNumberOfFields:DEFAULT_MAX_FIELD;
		return maxColumn;
	}

	private List<String> getFieldPatterns() {
		return ParserUtils.getList(fieldPattern);
	}
//
//	private SemanticReference getSemanticReference(Column column) {
//		SemanticReference semanticReference = new SemanticReference();
//		semanticReference.addSqlPath(getSqlPath(column));
//		return semanticReference;
//	}

	private SqlPath getSqlPath(Column column) {
		SqlPath sqlPath = new SqlPath();
		sqlPath.setPath(column.getName());
		return sqlPath;
	}
//
//	private boolean isMatch(Table table) {
//		boolean hasEntityPattern, hasContentType, hasPackage;
//		hasEntityPattern=hasEntityPattern();
//		if (hasEntityPattern()) {
//			r
//		}
//		return hasCorrectContentType(table);
//		return false;
//	}

	private boolean hasContentType(Table table) {
		if (table.getContentType()!=null && table.getContentType().equals(contentType))
			return true;
		return false;
	}

	private boolean hasEntityPattern() {
		return ! (StringUtils.isEmpty(entityPattern) && StringUtils.isEmpty(patternType));
	}
	
	private boolean hasEntityPattern(Table table) {
		if (hasEntityPattern()) {
			return net.sf.minuteProject.utils.StringUtils.checkExpression(table.getName(), patternType, entityPattern);
		}
		return false;
	}
	
	private boolean hasPackage(Table table) {
		if (hasPackage()) {
			return pack.toLowerCase().equals(table.getPackage().getName().toLowerCase());
		}
		return false;
	}
	
	private boolean hasFieldPattern() {
		return ! (StringUtils.isEmpty(fieldPattern) && StringUtils.isEmpty(fieldPatternType));
	}
	
	private boolean hasPackage() {
		return ! StringUtils.isEmpty(pack);
	}
	
	private boolean hasContentType() {
		return ! StringUtils.isEmpty(contentType);
	}

	public String getEntityPattern() {
		return entityPattern;
	}

	public void setEntityPattern(String entityPattern) {
		this.entityPattern = entityPattern;
	}

	public String getPatternType() {
		return patternType;
	}

	public void setPatternType(String patternType) {
		this.patternType = patternType;
	}

	public String getFieldPattern() {
		return fieldPattern;
	}

	public void setFieldPattern(String fieldPattern) {
		this.fieldPattern = fieldPattern;
	}

	public String getFieldPatternType() {
		return fieldPatternType;
	}

	public void setFieldPatternType(String fieldPatternType) {
		this.fieldPatternType = fieldPatternType;
	}

	public int getMaxNumberOfFields() {
		return maxNumberOfFields;
	}

	public void setMaxNumberOfFields(int maxNumberOfFields) {
		this.maxNumberOfFields = maxNumberOfFields;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	
}
