package net.sf.minuteProject.plugin.oracle;

import org.codehaus.groovy.runtime.ConvertedClosure;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.FormatUtils;

public class AdfUtils {

	private static final int MAX_COLUMNS_DISPLAY_SIZE = 40;
	private static final String MOCK = "mock";
	private static final String DB_DIRECT = "db-direct";
	private static final String BINDING_JAVA = "java";
	private static final String WS_SDO = "ws-sdo";
	private static final String ADF_FACES_BACKEND_BINDING = "adf-faces-backend-binding";

	public boolean isBindingWsSDO(Template template) {
		return WS_SDO.equals(getBinding(template));
	}
	public boolean isBindingJava(Template template) {
		return BINDING_JAVA.equals(getBinding(template));
	}
	public boolean isBindingDBDirect(Template template) {
		return DB_DIRECT.equals(getBinding(template));
	}
	public boolean isBindingMock(Template template) {
		return MOCK.equals(getBinding(template));
	}
	
	private String getBinding(Template template) {
		return template.getPropertyValue(ADF_FACES_BACKEND_BINDING);
	}

	public String getAdfType(Column column) {
		if (ColumnUtils.isTimeStampColumn(column))
			return "oracle.jbo.domain.Timestamp";
		if (ColumnUtils.isTimeColumn(column))
			return "oracle.jbo.domain.Date";
		if (ColumnUtils.isNumeric(column))
			return "oracle.jbo.domain.Number";
		if ("CLOB".equals(column.getType()))
			return "oracle.jbo.domain.ClobDomain";
		if ("BLOB".equals(column.getType()))
			return "oracle.jbo.domain.BlobDomain";
		return "java.lang.String";
	}
	
	public String getAdfSQLType(Column column) {
		if (ColumnUtils.isTimeStampColumn(column))
			return "TIMESTAMP";
		if (ColumnUtils.isTimeColumn(column))
			return "DATE";
		if (ColumnUtils.isNumeric(column))
			return "NUMERIC";
		if ("CLOB".equals(column.getType()))
			return "CLOB";		
		if ("BLOB".equals(column.getType()))
			return "BLOB";		
		return "VARCHAR";
	}
	
	public String getColumnType(Column column) {
		if (isSDOTypeNumeric(column))
			return "NUMERIC";
		return column.getType();
	}
	
	public String getSDOColumnTypeBegin(Column column) {
		if (isSDOTypeNumeric(column))
			return "new Integer(getInt";
		if (ColumnUtils.isTimeColumn(column)) 
			return "(java.sql.Timestamp)get";
		if (column.isLob())
			return "(javax.activation.DataHandler)get(";
		return "get"+CommonUtils.getJavaType(column);
	}	
	
	public String getSDOColumnFullType(Column column) {
		if ("DATE".equals(column.getType()))
			return "java.sql.Timestamp";
		if (ColumnUtils.isTimeStampColumn(column)) 
			return "java.sql.Timestamp";
		if (column.isLob())//"CLOB".equals(column.getType()) )
			return "javax.activation.DataHandler";
		return CommonUtils.getFullType2(column);
	}
	
	public String getSDOColumnTypeEnd(Column column) {
		if (isSDOTypeNumeric(column) ||
			column.isLob())
			return ")";
		return "";
	}

	private boolean isSDOTypeNumeric(Column column) {
		return "INTEGER".equals(column.getType()) || 
			"SMALLINT".equals(column.getType())	||
			"NUMERIC".equals(column.getType());
	}
	
	public String getSdoXsdType(Column column) {
		if (ColumnUtils.isTimeColumn(column)) {
			return "ns0:dateTime-Timestamp";
		}
		if ("CLOB".equals(column.getType())) {
			return "ns0:base64Binary-DataHandler";
		}
		return "xsd:"+getSdoXsdTypeValue(column);
	}

	private String getSdoXsdTypeValue(Column column) {
		String uml = ConvertUtils.getUMLTypeFromDBFullType(column.getType());
		if ("integer".equals(uml))
			return "int";
		return uml;
	}
	
	public boolean hasSdoXmlDataType(Column column) {
		return (getSdoXmlDataType(column)!=null);
	}
	
	public String getSdoXmlDataType(Column column) {
		if (ColumnUtils.isNumeric(column)) 
			return "sdoJava:IntObject";
		return null;
	}
	
	public static String getNamespacePackage(Model model, Template template, String targetTemplate) {
		String packageName = CommonUtils.getPackageName(model, CommonUtils.getTemplate(model.getConfiguration(), targetTemplate));
		return FormatUtils.getDirFromPackage(packageName);
	}
	
	public static String getPrecision(Column column) {
		Integer i = column.getSizeAsInt();
		return i.intValue()+"";
	}
	
	public int getDisplayColumns(Column column) {
		return Math.min(column.getSizeAsInt(),MAX_COLUMNS_DISPLAY_SIZE);
	}
	
	public int getDisplayRows(Column column) {
		int sizeAsInt = column.getSizeAsInt();
		int i = sizeAsInt%MAX_COLUMNS_DISPLAY_SIZE;
		int j = sizeAsInt/MAX_COLUMNS_DISPLAY_SIZE;
		return (i % MAX_COLUMNS_DISPLAY_SIZE == 0)? j : j+1;
	}
	
}
