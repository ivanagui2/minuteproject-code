package net.sf.minuteProject.configuration.bean;

import java.io.File;

import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;

import org.apache.ddlutils.model.Table;
import org.apache.velocity.VelocityContext;

public class Template extends TemplateTarget {

	private String templateFileName;
	private String subdir;
	private String outputsubdir; 
	private String technicalPackage;
	private String 	fileExtension;
	private String filePrefix; 
	private String fileSuffix; 
	private String entitySpecific;
	private String packageSpecific;
	private String modelSpecific;
	private String addModelName;
	private TemplateTarget templateTarget;
	
	public Template () {}
	
	public Template (TemplateTarget templateTarget) {
		this.setOutputdir(templateTarget.getOutputdir());
		this.setDir(templateTarget.getDir());
		this.setTemplateTarget(templateTarget);
	}
	public TemplateTarget getTemplateTarget() {
		return templateTarget;
	}
	public void setTemplateTarget(TemplateTarget templateTarget) {
		this.templateTarget = templateTarget;
	}
	public String getEntitySpecific() {
		return entitySpecific;
	}
	public void setEntitySpecific(String entitySpecific) {
		this.entitySpecific = entitySpecific;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFilePrefix() {
		return filePrefix;
	}
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	public String getModelSpecific() {
		return modelSpecific;
	}
	public void setModelSpecific(String modelSpecific) {
		this.modelSpecific = modelSpecific;
	}
	public String getOutputsubdir() {
		return outputsubdir;
	}
	public void setOutputsubdir(String outputsubdir) {
		this.outputsubdir = outputsubdir;
	}
	public String getPackageSpecific() {
		return packageSpecific;
	}
	public void setPackageSpecific(String packageSpecific) {
		this.packageSpecific = packageSpecific;
	}
	public String getSubdir() {
		return subdir;
	}
	public void setSubdir(String subdir) {
		this.subdir = subdir;
	}
	public String getTechnicalPackage() {
		return technicalPackage;
	}
	public void setTechnicalPackage(String technicalPackage) {
		this.technicalPackage = technicalPackage;
	}
	public String getTemplateFileName() {
		return templateFileName;
	}
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}
	public String getOutputFileName (String input) {
		return getOutputFileMain(input)+"."+fileExtension;
	}	
	public String getOutputFileMain (String input) {
		return filePrefix+input+fileSuffix;
	}
	public void setPackageRoot(String packageRoot) {
		super.setPackageRoot(packageRoot);
	}
	
    public String getGeneratorOutputFileName (Table table, Template template) {
    	Model model = template.getTemplateTarget().getTarget().getConfiguration().getModel();
    	/*ModelUtils.getPackageDir(model, template,table);
		StringBuffer sb = new StringBuffer(template.getPackageRoot());	
		if (getAddModelName()==null || !getAddModelName().equals("false"))
			sb.append("."+model.getName());
		sb.append("."+template.getTechnicalPackage());
		if (businessPackage!=null)
			sb.append("."+model.getBusinessModel().getBusinessPackage().getPackage(businessPackage));
		String packageSt = FormatUtils.getDirFromPackage(sb.toString());
		*/
    	StringBuffer sb = new StringBuffer(template.getOutputdir());
    	sb.append("//"+ModelUtils.getPackageDir(model, template,table));
		String outputFileDir = FormatUtils.getDirFromPackage(sb.toString());
		new File (outputFileDir.toString()).mkdirs();
		String TemplateFileName = CommonUtils.getFileName(template,table.getName());
		String outputFilename = outputFileDir+"//"+TemplateFileName;
		return outputFilename;
	}

    public String getGenOutputFileName (Package pack, Template template) {
    	Model model = template.getTemplateTarget().getTarget().getConfiguration().getModel();
    	StringBuffer sb = new StringBuffer(template.getOutputdir());
    	sb.append("//"+ModelUtils.getPackageDir(model, template,pack));
		String outputFileDir = FormatUtils.getDirFromPackage(sb.toString());
    	//String outputFileDir = ModelUtils.getPackageDir(model, template, pack);
		new File (outputFileDir.toString()).mkdirs();
		String TemplateFileName = CommonUtils.getFileName(template,pack.getName());
		String outputFilename = outputFileDir+"//"+TemplateFileName;
		return outputFilename;
	}
    
    public String getGeneratorOutputFileNameForModel (Template template) {
    	Model model = template.getTemplateTarget().getTarget().getConfiguration().getModel();
		/*ModelUtils.getTechnicalPackage(model, template);
		StringBuffer sb = new StringBuffer(template.getPackageRoot());	
		if (getAddModelName()==null || !getAddModelName().equals("false"))
			sb.append("."+model.getName());
		sb.append("."+template.getTechnicalPackage());
		String packageSt = FormatUtils.getDirFromPackage(sb.toString());
		*/
    	StringBuffer sb = new StringBuffer(template.getOutputdir());
    	sb.append("//"+ModelUtils.getTechnicalPackage(model, template));
		String outputFileDir = FormatUtils.getDirFromPackage(sb.toString());
    	
		//String outputFileDir = ModelUtils.getTechnicalPackage(model, template);//\\template.getTemplateTarget().getOutputdir()+"//"+packageSt;
		new File (outputFileDir.toString()).mkdirs();
		// TODO String TemplateFileName = template.getOutputFileName(FormatUtils.getJavaName(model.getName())); is redundant with the template
		String TemplateFileName = CommonUtils.getFileName(template,model.getName());
		String outputFilename = outputFileDir+"//"+TemplateFileName;
		return outputFilename;
	}
    
	public String getAddModelName() {
		return addModelName;
	}
	public void setAddModelName(String addModelName) {
		this.addModelName = addModelName;
	}
}
