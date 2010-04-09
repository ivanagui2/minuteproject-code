package net.sf.minuteProject.configuration.bean;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.minuteProject.application.ModelGenerator;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.view.Function;
import net.sf.minuteProject.configuration.bean.view.View;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

public class Template extends TemplateTarget {

	public static final String FORMAT_JAVA_CLASS = "FORMAT_JAVA_CLASS";
	public static final String FORMAT_DB_OBJECT = "FORMAT_DB_OBJECT";
	public static final String FORMAT_LOWER_CASE_JAVA_CLASS = "FORMAT_LOWER_CASE_JAVA_CLASS";
	public static final String FORMAT_UPPER_CASE_FIRST_LETTER_ONLY_JAVA_CLASS = "FORMAT_UPPER_CASE_FIRST_LETTER_ONLY_JAVA_CLASS";
	public static final String FORMAT_UPPER_CASE_FIRST_LETTER = "FORMAT_UPPER_CASE_FIRST_LETTER";
	public static final String FORMAT_LOWER_CASE_FIRST_LETTER = "FORMAT_LOWER_CASE_FIRST_LETTER";
		
	private String templateFileName;
	private String subdir;
	private String outputsubdir; 
	private String technicalPackage;
	private String fileExtension;
	private String filePrefix; 
	private String fileSuffix; 
	private String fileNameFormat;
	private String entitySpecific;
	private String packageSpecific;
	private String modelSpecific;
	private String viewSpecific;
	private String serviceSpecific;
	private String functionSpecific;
	private String fieldSpecific;
	private String nodeAttributeNameSpecific;
	private String nodeNameValue;
	private String nodeAttributeNameValue;
	private String addModelName;
	private String addModelDirName;
	private String addTechnicalDirName;
	private String addBusinessPackageDirName;
	private String addEntityDirName;
	private String entityDirNameFormat;
	private String addScopeName;
	private String applicationSpecific;
	private String componentSpecific;
	private TemplateTarget templateTarget;
	private String fileNameBuilderPlugin;
	private String fileNameBuilderMethod;
	private String isTemplateToGenerateMethod, checkTemplateToGenerate;
	private String scopeSpecificValue;
	private String entityDirNameSuffix;
	private String entityDirNamePrefix;
	private String appendEndPackageDir;
	
	private static Logger logger = Logger.getLogger(Template.class);
	
	public Template () {}
	
	public Template (TemplateTarget templateTarget) {
		this.setOutputdir(templateTarget.getOutputdir());
		this.setDir(templateTarget.getDir());
		this.setTemplateTarget(templateTarget);
		this.setRootdir(templateTarget.getRootdir());
	}
	
	public TemplateTarget getTemplateTarget() {
		return templateTarget;
	}
	public void setTemplateTarget(TemplateTarget templateTarget) {
		this.templateTarget = templateTarget;
	}
	public String getEntitySpecific() {
		if (entitySpecific==null)
			entitySpecific="false";
		return entitySpecific;
	}
	public String getNodeAttributeNameSpecific() {
		if (nodeAttributeNameSpecific==null)
			nodeAttributeNameSpecific="false";
		return nodeAttributeNameSpecific;
	}
	public void setNodeAttributeNameSpecific(String nodeAttributeNameSpecific) {
		this.nodeAttributeNameSpecific = nodeAttributeNameSpecific;
	}
	public String getNodeAttributeNameValue() {
		return nodeAttributeNameValue;
	}
	public void setNodeAttributeNameValue(String nodeAttributeNameValue) {
		this.nodeAttributeNameValue = nodeAttributeNameValue;
	}
	public String getNodeNameValue() {
		return nodeNameValue;
	}
	public void setNodeNameValue(String nodeNameValue) {
		this.nodeNameValue = nodeNameValue;
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
		if (modelSpecific==null)
			modelSpecific="false";
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
		if (packageSpecific==null)
			return "false";
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
	public String getOutputFileName (GeneratorBean bean) {
		return getOutputFileMain(bean)+"."+fileExtension;
	}	

	public String getAppendEndPackageDir() {
		return appendEndPackageDir;
	}

	public void setAppendEndPackageDir(String appendEndPackageDir) {
		this.appendEndPackageDir = appendEndPackageDir;
	}

	/**
	 * Returns the name of the file without the extention
	 * @param input
	 * @return
	 */
	public String getOutputFileMain (GeneratorBean bean) {
		return getFormatFileName(getOutputFileNameMain(bean));
	}
	
	public String getFormatFileName (String fileName) {
		if (fileNameFormat!=null && !fileNameFormat.equals("")) {
			if (fileNameFormat.equals(FORMAT_UPPER_CASE_FIRST_LETTER_ONLY_JAVA_CLASS))
				return FormatUtils.firstUpperCaseOnly(fileName);
			if (fileNameFormat.equals(FORMAT_UPPER_CASE_FIRST_LETTER))
				return FormatUtils.firstUpperCase(fileName);	
			if (fileNameFormat.equals(FORMAT_LOWER_CASE_FIRST_LETTER))
				return FormatUtils.firstLowerCase(fileName);			
		}
		return fileName;
	}
	
	public String getOutputFileNameMain (GeneratorBean bean) {
		String pluginResult = getPluginFileMain(bean);
		if (pluginResult!=null)
			return pluginResult;
		
		//return getNonPluginFileMain(FormatUtils.getJavaName(bean.getName()));
		return getNonPluginFileMain(bean.getGeneratedBeanName());
	}
	
	public String getNonPluginFileMain (String input) {
		if ((addModelName!=null && addModelName.equals("false")) ||
			(addScopeName!=null && addScopeName.equals("false")) )
			return filePrefix+fileSuffix;
		return filePrefix+input+fileSuffix;
	}
	
	private String getPluginFileMain (GeneratorBean bean) {
		if (fileNameBuilderPlugin!=null && fileNameBuilderMethod!=null) {
			// lookup builder in the plugin
			Plugin plugin = getFileBuilderPlugin(fileNameBuilderPlugin);
			if (plugin!=null) {
				String result = getPluginBuildFileName (plugin, fileNameBuilderMethod, bean);
				if (result != null)
					return result;
			}
		}	
		return null;
	}
	
	private Plugin getFileBuilderPlugin (String fileNameBuilderPlugin) {
		List<Plugin> plugins = this.getTemplateTarget().getTarget().getPlugins();
		for (Plugin plugin : plugins) {
			if (plugin.getName().equals(fileNameBuilderPlugin))
				return plugin;
		}		
		return null;
	}
	
	public boolean isToGenerate(GeneratorBean bean) {
		boolean isToGenerate = 
			getPluginIsToGenerate(
				getFileBuilderPlugin(getIsTemplateToGenerateMethodPluginName()), 
				getIsTemplateToGenerateMethodFunctionName(),
				bean);
		if (!isToGenerate)
			return false;
		return true;
	}
	
	private String getIsTemplateToGenerateMethodPluginName () {
		return StringUtils.substringBefore(getIsTemplateToGenerateMethod(), ".");
	}
	
	private String getIsTemplateToGenerateMethodFunctionName () {
		return StringUtils.substringAfterLast(getIsTemplateToGenerateMethod(), ".");
	}
	
	private boolean getPluginIsToGenerate (Plugin plugin, String function, GeneratorBean bean) {
		if (plugin==null || function==null)
			return false;
		if (plugin.equals(""))
			return true;
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		try {
			Class clazz = cl.loadClass(plugin.getClassName());
			Object pluginObject = clazz.newInstance();
			Class arg [] = new Class [2];
			arg [0] = Template.class;
			arg [1] = GeneratorBean.class;
			Object obj [] = new Object [2];
			obj [0] = this;
			obj [1] = bean;
			Method method = clazz.getMethod(function, arg);
			Boolean result = (Boolean) method.invoke(pluginObject, obj);
			return result;
		} catch (ClassNotFoundException e) {
			logger.info("cannot find plugin "+plugin.getName()+" via class "+plugin.getClassName());
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.info("cannot instantiate plugin "+plugin.getName()+" via class "+plugin.getClassName());
		} catch (IllegalAccessException e) {
			logger.info("cannot access plugin "+plugin.getName()+" via class "+plugin.getClassName());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin "+plugin.getName()+" via method "+function);
//			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin "+plugin.getName()+" via method "+function);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin "+plugin.getName()+" via method "+function);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin "+plugin.getName()+" via method "+function);
		}
		return false;
	}

	private String getPluginBuildFileName (Plugin plugin, String fileNameBuilderMethod, GeneratorBean bean) {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		try {
			Class clazz = cl.loadClass(plugin.getClassName());
			Object pluginObject = clazz.newInstance();
			Class arg [] = new Class [2];
			arg [0] = Template.class;
			arg [1] = GeneratorBean.class;
			Object obj [] = new Object [2];
			obj [0] = this;
			obj [1] = bean;
			Method method = clazz.getMethod(fileNameBuilderMethod, arg);
			String result = (String) method.invoke(pluginObject, obj);
			return result;
		} catch (ClassNotFoundException e) {
			logger.info("cannot find plugin "+plugin.getName()+" via class "+plugin.getClassName());
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.info("cannot instantiate plugin "+plugin.getName()+" via class "+plugin.getClassName());
		} catch (IllegalAccessException e) {
			logger.info("cannot access plugin "+plugin.getName()+" via class "+plugin.getClassName());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod);
//			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod);
		}
		return null;
	}
	
	public void setPackageRoot(String packageRoot) {
		super.setPackageRoot(packageRoot);
	}
	
	private Model getModel (Template template) {
		return ((Configuration)(template.getTemplateTarget().getTarget().getAbstractConfigurationRoot())).getModel();
	}
	
    public String getGeneratorOutputFileNameForView (View view, Template template) {
    	StringBuffer sb = new StringBuffer(template.getOutputdir());
    	sb.append("//"+ModelUtils.getTechnicalPackage(view, template));
		//String outputFileDir = FormatUtils.getDirFromPackage(sb.toString());
    	String outputFileDir = sb.toString();
		new File (outputFileDir.toString()).mkdirs();
		String TemplateFileName = CommonUtils.getFileName(template,view);
		String outputFilename = outputFileDir+"//"+TemplateFileName;
		return outputFilename;
	}

    public String getGeneratorOutputFileNameForFunction (Function function, Template template) {
    	StringBuffer sb = new StringBuffer(template.getOutputdir());
    	sb.append("//"+ModelUtils.getTechnicalPackage(function, template));
		String outputFileDir = FormatUtils.getDirFromPackage(sb.toString());
		new File (outputFileDir.toString()).mkdirs();
		String TemplateFileName = CommonUtils.getFileName(template,function);
		String outputFilename = outputFileDir+"//"+TemplateFileName;
		return outputFilename;
	}
    
    public String getGeneratorOutputFileNameForConfigurationBean (GeneratorBean bean, Template template) {
    	
    	StringBuffer sb = new StringBuffer(template.getOutputdir());
    	String sb1 = new String(CommonUtils.getPackageName(bean, template));
    	String dir = FormatUtils.getDirFromPackage(sb1);
    	sb.append("//");
    	sb.append(dir);
    	if (addEntityDirName!=null && addEntityDirName.equals("true")) {
    		sb.append("//");
    		sb.append(getEntityDirName(bean.getGeneratedBeanName()));
    	}
    	if (appendEndPackageDir!=null) {
    		sb.append("//"+appendEndPackageDir);
    	}
    	String outputFileDir = sb.toString();
		new File (outputFileDir.toString()).mkdirs();
		String TemplateFileName = CommonUtils.getFileName(template,bean);
		String outputFilename = outputFileDir+"//"+TemplateFileName;
		return outputFilename;    	
    }
    
    private String getEntityDirName (String input) {
    	StringBuffer output = new StringBuffer();
		if (entityDirNamePrefix!=null && !entityDirNamePrefix.equals("")) {
			output.append(entityDirNamePrefix);
		}
    	output.append(getEntityDirNameFormat(input));
		if (entityDirNameSuffix!=null && !entityDirNameSuffix.equals("")) {
			output.append(entityDirNameSuffix);
		}
		return output.toString();
    }
    
    private String getEntityDirNameFormat (String input) {
		if (entityDirNameFormat!=null && !entityDirNameFormat.equals("")) {
			if (entityDirNameFormat.equals(FORMAT_UPPER_CASE_FIRST_LETTER_ONLY_JAVA_CLASS))
				return FormatUtils.firstUpperCaseOnly(input);
		}
		return input;
    }
    
    
    public String getAddModelName() {
		return addModelName;
	}
	public void setAddModelName(String addModelName) {
		this.addModelName = addModelName;
	}

	public String getFunctionSpecific() {
		return functionSpecific;
	}

	public void setFunctionSpecific(String functionSpecific) {
		this.functionSpecific = functionSpecific;
	}

	public String getServiceSpecific() {
		if (serviceSpecific==null)
			serviceSpecific="false";
		return serviceSpecific;
	}

	public void setServiceSpecific(String serviceSpecific) {
		this.serviceSpecific = serviceSpecific;
	}

	public String getViewSpecific() {
		if (viewSpecific==null)
			viewSpecific="false";
		return viewSpecific;
	}

	public void setViewSpecific(String viewSpecific) {
		this.viewSpecific = viewSpecific;
	}

	public String getApplicationSpecific() {
		if (applicationSpecific==null)
			applicationSpecific="false";
		return applicationSpecific;
	}

	public void setApplicationSpecific(String applicationSpecific) {
		this.applicationSpecific = applicationSpecific;
	}

	public String getFileNameBuilderMethod() {
		return fileNameBuilderMethod;
	}

	public void setFileNameBuilderMethod(String fileNameBuilderMethod) {
		this.fileNameBuilderMethod = fileNameBuilderMethod;
	}

	public String getFileNameBuilderPlugin() {
		return fileNameBuilderPlugin;
	}

	public void setFileNameBuilderPlugin(String fileNameBuilderPlugin) {
		this.fileNameBuilderPlugin = fileNameBuilderPlugin;
	}

	public String getComponentSpecific() {
		if (componentSpecific==null)
			return "false";
		return componentSpecific;
	}

	public void setComponentSpecific(String componentSpecific) {
		this.componentSpecific = componentSpecific;
	}

	public String getIsTemplateToGenerateMethod() {
		return isTemplateToGenerateMethod;
	}

	public void setIsTemplateToGenerateMethod(String isTemplateToGenerateMethod) {
		this.isTemplateToGenerateMethod = isTemplateToGenerateMethod;
	}

	public String getScopeSpecificValue() {
		return scopeSpecificValue;
	}

	public void setScopeSpecificValue(String scopeSpecificValue) {
		this.scopeSpecificValue = scopeSpecificValue;
	}
	
    public String getAddModelDirName() {
		return addModelDirName;
	}

	public void setAddModelDirName(String addModelDirName) {
		this.addModelDirName = addModelDirName;
	}

	public String getAddTechnicalDirName() {
		return addTechnicalDirName;
	}

	public void setAddTechnicalDirName(String addTechnicalDirName) {
		this.addTechnicalDirName = addTechnicalDirName;
	}
	
	public String getAddEntityDirName() {
		return addEntityDirName;
	}

	public void setAddEntityDirName(String addEntityDirName) {
		this.addEntityDirName = addEntityDirName;
	}

	public String getAddScopeName() {
		return addScopeName;
	}

	public void setAddScopeName(String addScopeName) {
		this.addScopeName = addScopeName;
	}

	public String getAddBusinessPackageDirName() {
		return addBusinessPackageDirName;
	}

	public void setAddBusinessPackageDirName(String addBusinessPackageDirName) {
		this.addBusinessPackageDirName = addBusinessPackageDirName;
	}

	public String getFileNameFormat() {
		return fileNameFormat;
	}

	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}

	public String getEntityDirNameFormat() {
		return entityDirNameFormat;
	}

	public void setEntityDirNameFormat(String entityDirNameFormat) {
		this.entityDirNameFormat = entityDirNameFormat;
	}

	public String getEntityDirNamePrefix() {
		return entityDirNamePrefix;
	}

	public void setEntityDirNamePrefix(String entityDirNamePrefix) {
		this.entityDirNamePrefix = entityDirNamePrefix;
	}

	public String getEntityDirNameSuffix() {
		return entityDirNameSuffix;
	}

	public void setEntityDirNameSuffix(String entityDirNameSuffix) {
		this.entityDirNameSuffix = entityDirNameSuffix;
	}

	public String getCheckTemplateToGenerate() {
		return checkTemplateToGenerate;
	}

	public void setCheckTemplateToGenerate(String checkTemplateToGenerate) {
		this.checkTemplateToGenerate = checkTemplateToGenerate;
	}

	public String getFieldSpecific() {
		if (fieldSpecific==null)
			return "false";
		return fieldSpecific;
	}

	public void setFieldSpecific(String fieldSpecific) {
		this.fieldSpecific = fieldSpecific;
	}
	
	public String getPackageRoot() {
		if (packageRoot==null && getTemplateTarget().getTarget()!=null){
			Configuration configuration = (Configuration) getTemplateTarget().getTarget().getAbstractConfigurationRoot();
			setPackageRoot(configuration.getModel().getPackageRoot());
		}
		return packageRoot;
	}
	
	public Target getTarget() {
		return getTemplateTarget().getTarget();
	}
	
	public String getOutputdir() {
		return super.getOutputdir();
	}
}
