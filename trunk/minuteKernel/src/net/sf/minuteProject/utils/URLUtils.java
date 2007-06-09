package net.sf.minuteProject.utils;

import org.apache.ddlutils.model.Table;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.Entity;
import net.sf.minuteProject.configuration.bean.model.Field;
import net.sf.minuteProject.configuration.bean.parameter.LogicalLink;

public class URLUtils {

	public String getURLSearchEntity(Model model, Table table) {
		String packageName = CommonUtils.getBusinessPackageName(model, table);
		String searchFileName = CommonUtils.getTableClassName(table)+"SearchScreen.html";
		String projectName = model.getConfiguration().getProjectname();
		return getRelativeURLApplicationRoot()+"/html/"+projectName+"/"+packageName+"/"+searchFileName;
	}
	
	public String getURLManageEntity(Model model, Table table) {
		return "crud.do?table="+table.getName()+"&action=manage";
		//return "crud.do?service=routingService+&inputObject="+BslaLibraryUtils.getDomainObjectImport(model, table, new Template())+"&name="+table.getName()+"&method=manage";
	}

	public String getURLAddReferencedEntity(String tableName, String linkField, String entityInSession, String referenceTablePK ) {
		return "crud.do?table="+tableName+"&action=add&"+linkField+"=<c:out value=\"${"+entityInSession+"."+DBTemplateUtils.getJavaNameVariable(referenceTablePK)+"}\"/>";
	}	
	
	public String getEditURL (String tableName, String field) {
		return getEditURL(tableName, field, "entity");
	}
	
	public String getEditURL (String tableName, String field, String entity) {
		return "crud.do?table="+tableName+"&action=edit&pk=<c:out value=\"${"+entity+"."+DBTemplateUtils.getJavaNameVariable(field)+"}\"/>";	
	}	

	public String getSearchByIdURL (LogicalLink link) {
		Entity linkEntity= link.getEntity();
		return getSearchByIdURL(linkEntity.getRefname(),((Field) linkEntity.getFields().get(0)).getRefname());
	}
	
	public String getSearchByIdURL (String tableName, String field) {
		return getSearchByIdURL(tableName, field, "entity");
	}
	public String getSearchByIdURL (String tableName, String field, String entity) {
		String url = "crud.do?table="+tableName+"&action=searchOnPk&pk=<c:out value=\"${"+entity+"."+DBTemplateUtils.getJavaNameVariable(field)+"}\"/>";
		return url;
	}	
	
	public String getRelativeURLApplicationRoot(Model model) {
		return "/${ctx}";
	}

	public String getRelativeURLApplicationRoot() {
		return "/${ctx}";
	}
	
	public String getRelativeURLPackageRoot(Model model, Table table) {
		return "/jsp/myProject";
	}	
	
	public String getManageEntity(Model model, Table table) {
		String packageName = CommonUtils.getBusinessPackageName(model, table);
		String manageFileName = CommonUtils.getTableClassName(table)+"ManageScreen.jsp";
		return packageName+"/"+manageFileName;
	}	
	
}
