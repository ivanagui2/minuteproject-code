package net.sf.minuteProject.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.AbstractConfigurationRoot;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.GeneratorConfig;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.model.data.DataModelFactory;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.utils.BslaLibraryUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.TemplateUtils;
import net.sf.minuteProject.utils.ViewUtils;
import net.sf.minuteProject.utils.io.FileUtils;
import net.sf.minuteProject.utils.property.PropertyUtils;

/**
 * @author Florian Adler
 *
 */
public abstract class AbstractGenerator implements Generator {
	
	protected static final String SCOPE_DATAMODEL_FUNCTION = "function";
	protected static final String SCOPE_TARGET_TEMPLATE = "target";
	
	private static Logger logger = Logger.getLogger(AbstractGenerator.class);
	private String configurationFile;
	private String templatePath;
	private String templateLibPath;
	
	/**
	 * The default constructor get the value of the configuration to which the generator is associated
	 * @param configurationFile
	 */
	public AbstractGenerator (String configurationFile) {
		this.configurationFile = configurationFile;
	}
	/**
	 * gets the configuration file that is to be loaded
	 * @return String
	 */
	public String getConfigurationFile() {
		return this.configurationFile;
	}
	
	/**
	 * gets the configuration rule file that is to be loaded
	 * @return
	 */
	public abstract String getConfigurationRulesFile();
	
	/**
	 * gets the configuration rule file that is to be loaded
	 * @return
	 */
	public abstract String getPropertyConfigurationRulesFile();
	
	/**
	 * gets the configuration root element 
	 * @return AbstractConfiguration
	 */
	public abstract AbstractConfiguration getConfigurationRoot();
	
	/* (non-Javadoc)
	 * @see net.sf.minuteProject.application.Generator#load(java.lang.String, java.lang.String)
	 */
	public final AbstractConfiguration load (String configuration, String rules) throws Exception{
		AbstractConfiguration abstractConfiguration = getConfigurationRoot();
		abstractConfiguration.setConfigurationFileInClassPath(configuration);
		loadConfiguration(abstractConfiguration, getConfigurationInputStream(configuration), rules);
        return abstractConfiguration;		
	}
	
	private InputStream getConfigurationInputStream (String configurationFileName) {
		return getClass().getClassLoader().getSystemResourceAsStream(configurationFileName);
	}
	
	public void loadTarget (AbstractConfigurationRoot abstractConfigurationRoot, Target target) {
		try {
			loadConfiguration(abstractConfigurationRoot, getTargetConfigurationInputStream(abstractConfigurationRoot, target), GENERATOR_TARGET_RULES);
			
		} catch (Exception e) {
			exit ("CANNOT LOAD FILE "+resolveFileAbsolutePath(abstractConfigurationRoot, target)+" - CHECK IT IS IN THE CLASSPATH");
		}
		complementWithTargetInfo(abstractConfigurationRoot, target);
	}

	public void copyAndComplementWithTargetInfo (AbstractConfigurationRoot abstractConfigurationRoot, Target target) throws Exception {
//		target = new Target();//(abstractConfigurationRoot, target);
		Target target2 = abstractConfigurationRoot.getTarget();
		target.setTemplateTargets(target2.getTemplateTargets());
		target.setAbstractConfigurationRoot(target2.getAbstractConfigurationRoot());
		target.setArchitectureTarget(target2.getArchitectureTarget());
		target.setPlugins(target2.getPlugins());
//		for (Target target2 : abstractConfigurationRoot.getTargets().getTargets()) {
//			if (target2==target) {
//				target2.setDir(target.getDir());
//				target2.setCanonicalDir(target.getCanonicalDir());
//				target2.setOutputdirRoot(target.getOutputdirRoot());
//				target2.setTemplatedirRoot(target.getTemplatedirRoot());				
//			}
//		}
	}
	
	public void complementWithTargetInfo (AbstractConfigurationRoot abstractConfigurationRoot, Target target) {
		Target target2 = abstractConfigurationRoot.getTarget();
		target2.setDir(target.getDir());
		target2.setCanonicalDir(target.getCanonicalDir());
		target2.setOutputdirRoot(target.getOutputdirRoot());
		target2.setTemplatedirRoot(target.getTemplatedirRoot());
	}
	
	protected InputStream getTargetConfigurationInputStream (
			AbstractConfigurationRoot abstractConfigurationRoot, 
			Target target) throws FileNotFoundException {
		String filePath = resolveFileAbsolutePath(abstractConfigurationRoot, target);
		String dirPath = FileUtils.stripFileName(filePath);
		target.setCanonicalDir(dirPath);
		return new FileInputStream (new File (filePath));
	}

//	private String resolvePathAbsolutePath(
//			AbstractConfigurationRoot abstractConfigurationRoot, 
//			Target target) {
//		return FileUtils.stripFileName(resolveFileAbsolutePath(abstractConfigurationRoot, target));
//	}
	private String resolveFileAbsolutePath(
			AbstractConfigurationRoot abstractConfigurationRoot, 
			Target target) {
		String dir = target.getDir();
		if (dir!=null) { // absolute path provided
			return dir+"/"+target.getFileName();
		}
		else {//relative path
			String result = FileUtils.getFileFullPath(
					abstractConfigurationRoot.getConfigurationFileInClassPath(), dir, target.getFileName());
			return result;
		}
	}

	protected void loadConfiguration (Object object, InputStream input, String rules) throws Exception {
        URL rulesURL = getClass().getClassLoader().getResource(rules);
        Digester digester = DigesterLoader.createDigester(rulesURL);
        digester.push(object);
        digester.parse(input);
	}
	
	/**
	 * load the configuration root element
	 * @return AbstractConfiguration
	 * @throws Exception
	 */
	public final AbstractConfiguration load() {
		AbstractConfiguration abstractConfiguration = null;
		try {
			abstractConfiguration = load(getConfigurationFile(), getConfigurationRulesFile());
		} catch (Exception e) {
			exit("CANNOT LOAD FILE "+getConfigurationFile()+" - CHECK IT IS IN THE CLASSPATH");
		}
		return abstractConfiguration;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject.configuration.bean.AbstractConfiguration, net.sf.minuteProject.configuration.bean.Target)
	 */
	public void generate (Target target) throws Exception {
    	for (Iterator iter= target.getTemplateTargets().iterator(); iter.hasNext(); ) {
    		TemplateTarget templateTarget = (TemplateTarget)iter.next();
    		logger.info("> generate template: "+templateTarget.getName()+" in "+templateTarget.getOutputdir());
    		if (templateTarget!=null && templateTarget.getTemplates()!=null) {
	    		for (Iterator iter2= templateTarget.getTemplates().iterator(); iter2.hasNext(); ) {
	        		Template template= (Template)iter2.next();
	        		logger.info(">> generate template: "+template.getName()+" in "+template.getOutputdir());
	        		this.generate(template);    		
	        		//generateArtifacts (configuration.getModel(),(Template)iter2.next());		
	        	} 
    		}
    	}  		
	}
	
	public void getSolutionPortfolio (String solutionPortfolioFileName) {
		
	}
	
    protected VelocityContext getVelocityContext(Template template) {
		Properties p = new Properties();
		
		Velocity.clearProperty(Velocity.FILE_RESOURCE_LOADER_PATH);
		Velocity.clearProperty(Velocity.VM_LIBRARY);
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,getTemplatePath(template));
		p.setProperty(Velocity.VM_LIBRARY,getTemplateRelativeLibPath(template));
		VelocityContext context = new VelocityContext();
		try {
			Velocity.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
    }  
    
	protected void putPluginContextObject (VelocityContext context, Template template) {
		List <Plugin> plugins = template.getTemplateTarget().getTarget().getPlugins();
		for (Plugin plugin : plugins) {
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			try {
				Class clazz = cl.loadClass(plugin.getClassName());
				Object velocityObject = clazz.newInstance();
				context.put(plugin.getName(), velocityObject);
			} catch (ClassNotFoundException e) {
				logger.info("cannot find plugin "+plugin.getName()+" via class "+plugin.getClassName());
				e.printStackTrace();
			} catch (InstantiationException e) {
				logger.info("cannot instantiate plugin "+plugin.getName()+" via class "+plugin.getClassName());
			} catch (IllegalAccessException e) {
				logger.info("cannot access plugin "+plugin.getName()+" via class "+plugin.getClassName());
			}
		}
	}
	
    protected String getTemplatePath (Template template) {
    	if (templatePath==null) {
        	TemplateTarget templateTarget = template.getTemplateTarget();
        	Target target = templateTarget.getTarget();
    		StringBuffer sb = new StringBuffer();
    		for (Iterator iterator = target.getTemplateTargets().iterator(); iterator.hasNext();) {
    			TemplateTarget templateTarget2 = (TemplateTarget)iterator.next();
    			sb.append(templateTarget2.getAbsoluteRootDir());
    			sb.append(",");
    			sb.append(templateTarget2.getTemplateFullDir());
    			sb.append(",");
    		}
    		templatePath = sb.toString();
    	}
    	return templatePath;
    }
    
    private String getTemplateRelativeLibPath (Template template) {
    	if (templateLibPath==null) {
        	TemplateTarget templateTarget = template.getTemplateTarget();
        	Target target = templateTarget.getTarget();
    		StringBuffer sb = new StringBuffer();
    		for (Iterator iterator = target.getTemplateTargets().iterator(); iterator.hasNext();) {
    			TemplateTarget templateTarget2 = (TemplateTarget)iterator.next();
    			if (templateTarget2.getLibdir()!=null 
    				&& !templateTarget2.getLibdir().equals("")) {
	    			sb.append(templateTarget2.getLibdir());
	    			sb.append(","); //TODO change for last element
    			}
    		}
    		
    		templateLibPath = sb.toString();
    	}
    	return templateLibPath;    	
    }
    
	protected void produce(VelocityContext context, Template template, String outputFilename) 
    throws Exception{
	   	org.apache.velocity.Template velocityTemplate =  null;
	   	try 
	       {
	   			velocityTemplate = Velocity.getTemplate(template.getTemplateFileName());
	       }
       catch( ResourceNotFoundException rnfe )
       {
           System.out.println("Example : error : cannot find template " + template.getTemplateFileName() );
       }
       catch( ParseErrorException pee )
       {
           System.out.println("Example : Syntax error in template " + template.getTemplateFileName() + ":" + pee );
       } 

       BufferedWriter writer = new BufferedWriter(
           new OutputStreamWriter(new FileOutputStream(outputFilename)));

       if ( velocityTemplate != null)
    	   velocityTemplate.merge(context, writer);

       writer.flush();
       writer.close();    	
   }
	
   protected String getAbstractBeanName (GeneratorBean bean) {
		String beanName = StringUtils.lowerCase(bean.getClass().getName());
		beanName = StringUtils.substring(beanName,
				beanName.lastIndexOf(".") + 1);
		// TODO change
		if (beanName.equals("tableddlutils") || beanName.equals("tableumlnotation"))
			return "table";
		if (beanName.equals("columnddlutils"))
			return "column";		
		if (beanName.equals("viewddlutils"))
			return "view";	
		if (beanName.equals("componentddlutils"))
			return "component";			
		if (beanName.equals("functionddlutils"))
			return "function";				
		return beanName;
   }
	
    protected Table getDecoratedTable (Table table) {
    	return DataModelFactory.getTable(table);
    }
    
	protected void writeTemplateResult(GeneratorBean bean, 
			Template template) throws Exception {
		String outputFilename = template.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		VelocityContext context = getVelocityContext(template);
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		context.put("template", template);
		putCommonContextObject(context, template);
		produce(context, template, outputFilename);
	}
	
	protected void putCommonContextObject(VelocityContext context, Template template) {
		putStandardContextObject(context);
		putPluginContextObject(context, template);
	}
	
	protected void putStandardContextObject(VelocityContext context) {
		context.put("convertUtils", new ConvertUtils());
		context.put("commonUtils", new CommonUtils());
		context.put("viewUtils", new ViewUtils());
		context.put("formatUtils", new FormatUtils());
		context.put("bslaLibraryUtils", new BslaLibraryUtils());
		context.put("databaseUtils", new DatabaseUtils());
		context.put("modelUtils", new ModelUtils());
		context.put("templateUtils", new TemplateUtils());
		context.put("propertyUtils", new PropertyUtils());
	}

    protected void exit (String message) {
		logger.error(message);
		System.exit(-1);
    }
}
