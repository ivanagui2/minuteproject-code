package net.sf.minuteProject.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
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

/**
 * @author Florian Adler
 *
 */
public abstract class AbstractGenerator implements Generator {
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
	 * gets the configuration root element 
	 * @return AbstractConfiguration
	 */
	public abstract AbstractConfiguration getConfigurationRoot();
	
	/* (non-Javadoc)
	 * @see net.sf.minuteProject.application.Generator#load(java.lang.String, java.lang.String)
	 */
	public final AbstractConfiguration load (String configuration, String rules) throws Exception{
		AbstractConfiguration abstractConfiguration = getConfigurationRoot();
		loadConfiguration(abstractConfiguration, getConfigurationInputStream(configuration), rules);
        return abstractConfiguration;		
	}
	
	private InputStream getConfigurationInputStream (String configurationFileName) {
		return getClass().getClassLoader().getSystemResourceAsStream(configurationFileName);
	}
	
	public void loadTarget (AbstractConfigurationRoot abstractConfigurationRoot, Target target) throws Exception {
		loadConfiguration(abstractConfigurationRoot, getTargetConfigurationInputStream(target), GENERATOR_TARGET_RULES);
	}

	private InputStream getTargetConfigurationInputStream (Target target) throws Exception{
		//TODO now hardcoded to change when bean solutionPortfolio in place
		return new FileInputStream (new File (target.getDir()+"/"+target.getFileName()));
		
		/*
		if (refname.equals("ViewOnBsla"))
			return "templateSet-ViewOnBsla.xml";
		else if (refname.equals("BackendOnBsla"))
			return "templateSet-BackendOnBsla.xml";//return "templateSet-BackendOnBsla.xml";
		else 
			return "templateSet-ViewOnBsla.xml";
		//*/
	}
	
	private void loadConfiguration (Object object, InputStream input, String rules) throws Exception {
		//InputStream input = new FileInputStream (new File (configuration));
		//InputStream input = getClass().getClassLoader().getSystemResourceAsStream(configuration);
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
	public final AbstractConfiguration load() throws Exception{
		return load(getConfigurationFile(), getConfigurationRulesFile());
	}
	
	/* (non-Javadoc)
	 * @see net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject.configuration.bean.AbstractConfiguration, net.sf.minuteProject.configuration.bean.Target)
	 */
	public void generate (Target target) throws Exception {
    	for (Iterator iter= target.getTemplateTargets().iterator(); iter.hasNext(); ) {
    		TemplateTarget templateTarget = (TemplateTarget)iter.next();
    		logger.info("> generate template: "+templateTarget.getName());
        	for (Iterator iter2= templateTarget.getTemplates().iterator(); iter2.hasNext(); ) {
        		Template template= (Template)iter2.next();
        		logger.info(">> generate template: "+template.getName());
        		this.generate(template);    		
        		//generateArtifacts (configuration.getModel(),(Template)iter2.next());		
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
    
    private String getTemplatePath (Template template) {
    	TemplateTarget templateTarget = template.getTemplateTarget();
    	Target target = templateTarget.getTarget();
    	if (templatePath==null) {
    		StringBuffer sb = new StringBuffer();
    		for (Iterator iterator = target.getTemplateTargets().iterator(); iterator.hasNext();) {
    			TemplateTarget templateTarget2 = (TemplateTarget)iterator.next();
    			sb.append(templateTarget.getRootdir());
    			sb.append(",");
    			sb.append(templateTarget.getTemplateFullDir());
    			sb.append(",");
    		}
    		
    		templatePath = sb.toString();
    	}
    	return templatePath;
    }
    
    private String getTemplateRelativeLibPath (Template template) {
    	TemplateTarget templateTarget = template.getTemplateTarget();
    	Target target = templateTarget.getTarget();
    	if (templateLibPath==null) {
    		StringBuffer sb = new StringBuffer();
    		for (Iterator iterator = target.getTemplateTargets().iterator(); iterator.hasNext();) {
    			TemplateTarget templateTarget2 = (TemplateTarget)iterator.next();
    			sb.append(templateTarget.getLibdir());
    			sb.append(",");
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
		if (beanName.equals("columnddltils"))
			return "column";		
		
		return beanName;
   }
	
    protected Table getDecoratedTable (Table table) {
    	return DataModelFactory.getTable(table);
    }
}
