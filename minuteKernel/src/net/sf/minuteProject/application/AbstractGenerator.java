package net.sf.minuteProject.application;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
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

	private String configurationFile;
	
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
		loadConfiguration(abstractConfiguration, configuration, rules);
        return abstractConfiguration;		
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.minuteProject.application.Generator#loadTarget(java.lang.String)
	 */
	public void loadTarget (AbstractConfigurationRoot abstractConfigurationRoot, String refname) throws Exception {
		loadConfiguration(abstractConfigurationRoot, getTargetConfiguration(refname), GENERATOR_TARGET_RULES);
	}

	private String getTargetConfiguration (String refname) {
		//TODO now hardcoded to change when bean solutionPortfolio in place
		if (refname.equals("ViewOnBsla"))
			return "templateSet-ViewOnBsla.xml";
		else if (refname.equals("BackendOnBsla"))
			return "templateSet-BackendOnBsla.xml";//return "templateSet-BackendOnBsla.xml";
		else 
			return "templateSet-ViewOnBsla.xml";
	}
	
	private void loadConfiguration (Object object, String configuration, String rules) throws Exception {
        InputStream input = getClass().getClassLoader().getSystemResourceAsStream(configuration);
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
        	for (Iterator iter2= ((TemplateTarget)iter.next()).getTemplates().iterator(); iter2.hasNext(); ) {
        		this.generate((Template)iter2.next());    		
        		//generateArtifacts (configuration.getModel(),(Template)iter2.next());		
        	}     		
    	}  		
	}
	
	public void getSolutionPortfolio (String solutionPortfolioFileName) {
		
	}
	
    protected VelocityContext getVelocityContext(Template template) {
		Properties p = new Properties();
		TemplateTarget templateTarget = template.getTemplateTarget();
		Velocity.clearProperty(Velocity.FILE_RESOURCE_LOADER_PATH);
		Velocity.clearProperty(Velocity.VM_LIBRARY);
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,templateTarget.getDir());
		p.setProperty(Velocity.VM_LIBRARY,templateTarget.getLibdir());
		VelocityContext context = new VelocityContext();
		try {
			Velocity.init(p);
			Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,templateTarget.getDir());
			Velocity.setProperty(Velocity.VM_LIBRARY,templateTarget.getLibdir());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
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
