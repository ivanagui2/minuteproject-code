package net.sf.minuteProject.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.GeneratorConfig;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.presentation.Presentation;
import net.sf.minuteProject.utils.BslaLibraryUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.DBTemplateUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.URLUtils;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import sun.util.logging.resources.logging;

public class GeneratorConfigurator {
	
	private Logger log;
	
	GeneratorConfigurator () {
		log = Logger.getLogger(this.getClass()); 
	}
	
	public static void main(String[] args) throws Exception {
		GeneratorConfigurator generatorConfigurator = new GeneratorConfigurator();
		if (args.length<1){
			generatorConfigurator.log.info("set a configuration file");
			System.exit(0);
		}
		generatorConfigurator.processFirstConfiguration(args[0]);
	}

	private GeneratorConfig getGeneratorConfig() throws Exception {
		return getGeneratorConfig("generator-config.xml");
	}
	
	private GeneratorConfig getGeneratorConfig(String configFileName) throws Exception {
		GeneratorConfig generatorConfig = new GeneratorConfig();
        InputStream input = getClass().getClassLoader().getSystemResourceAsStream(configFileName);
        URL rules = getClass().getClassLoader().getResource("net/sf/minuteProject/configuration/generator-config-rules.xml");
        Digester digester = DigesterLoader.createDigester(rules);
        digester.push(generatorConfig);
        digester.parse(input);
        //completeConfig(configList);
        return generatorConfig;
    }
	
	private void loadPresentation(Configuration configuration) throws Exception {
	    loadPresentation(configuration, "generator-presentation-config.xml");
	}
	
    private void loadPresentation(Configuration configuration, String configFileName) throws Exception {
        InputStream input = getClass().getClassLoader().getSystemResourceAsStream(configFileName);
        URL rules = getClass().getClassLoader().getResource("net/sf/minuteProject/configuration/presentation-config-rules.xml");
        Digester digester = DigesterLoader.createDigester(rules);
        digester.push(configuration);
        digester.parse(input);
    }
    
    private void processFirstConfiguration(String configFile) throws Exception {
    	GeneratorConfig generatorConfig = getGeneratorConfig(configFile);
    	Configuration configuration= (Configuration)generatorConfig.getConfigurations().get(0);
    	loadPresentation (configuration);
    	loadModel(configuration);
    	
    	generateArtifacts(configuration);
    }
    
    private void loadModel(Configuration configuration) {
    	Model model = configuration.getModel();
    	model.getDataModel().loadDatabase();
    	model.getBusinessModel().complementDataModel();
    }
    
    private void generateArtifacts(Configuration configuration) {
    	for (Iterator iter= configuration.getTarget().getTemplateTargets().iterator(); iter.hasNext(); ) {
        	for (Iterator iter2= ((TemplateTarget)iter.next()).getTemplates().iterator(); iter2.hasNext(); ) {
        		generateArtifacts (configuration.getModel(),(Template)iter2.next());		
        	}     		
    	}    	
    }

    private void generateArtifacts (Model model, Template template) {
    	if (template.getEntitySpecific().equals("true"))
    		generateArtifactsByEntity(model, template);
    	else if (template.getPackageSpecific().equals("true"))
    		generateArtifactsByPackage (model, template);  	
    	else if (template.getModelSpecific().equals("true"))
    		generateArtifactsByModel (model, template);
    }

    private void generateArtifactsByPackage (Model model, Template template) {
    	generateForPackageAndTemplate(getMyVelocityContext(template), model, template);
    }

    private void generateArtifactsByModel (Model model, Template template) {
    	writeTemplateResultDB(getMyVelocityContext(template), model, template);
    }    
     
    private void generateArtifactsByEntity (Model model, Template template) {
    	generateFromDatabaseAndTemplate(getMyVelocityContext(template), model, template);
    }
    
    private VelocityContext getMyVelocityContext(Template template) {
		Properties p = new Properties();
		TemplateTarget templateTarget = template.getTemplateTarget();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,templateTarget.getDir());
		p.setProperty(Velocity.VM_LIBRARY,templateTarget.getLibdir());
		VelocityContext context = new VelocityContext();
		try {
			Velocity.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
    }    

    private void generateForPackageAndTemplate (VelocityContext context, Model model, Template template){
    	List packages = model.getBusinessModel().getBusinessPackage().getPackages();
    	for (int i = 0; i < packages.size(); i++) {
    		generateForPackageAndTemplate (context, model, (Package)packages.get(i), template);
    	}
    } 
    private void generateForPackageAndTemplate (VelocityContext context, Model model, Package pack, Template template){
    	writeTemplateResultPackage(context, model, pack, template);
    }     
    
    public void generateFromDatabaseAndTemplate (VelocityContext context, Model model, Template template){
    	Database db = model.getDataModel().getDatabase();
    	for (int i = 0; i < db.getTables().length; i++) {
    		generateFromTableAndTemplate (context, model, db.getTables()[i], template);
    	}
    }   
    public void generateFromTableAndTemplate (VelocityContext context, Model model, Table table, Template template){
    	if (ModelUtils.isToGenerate(model.getBusinessModel(), table)) {
    		writeTemplateResult(context, model, table, template);
    	}
    }
    
    private void writeTemplateResult (VelocityContext context, Model model, Table table, Template template) {
    	try {
    		String outputFilename = template.getGeneratorOutputFileName(table, template);
    		putCommonContextObject(context);
    		context.put("configuration", model.getConfiguration());
    		context.put("model", model);
    		context.put("template", template);
    		context.put("table", table);
	    	produce (context, template, outputFilename); 
    	}        
    	catch( Exception e )
        {
            System.out.println(e);
        }
    }

    private void writeTemplateResultPackage (VelocityContext context,  Model model, net.sf.minuteProject.configuration.bean.Package pack, Template template) {
    	try {
    		String outputFilename = template.getGenOutputFileName(pack, template);
    		putCommonContextObject(context);
    		context.put("configuration", model.getConfiguration());
    		context.put("model", model);
    		context.put("template", template);
    		context.put("package", pack);
	    	produce (context, template, outputFilename); 
    	}        
    	catch( Exception e )
        {
            System.out.println(e);
        }
    }
    
    private void writeTemplateResultDB (VelocityContext context, Model model, Template template) {
    	try {
    		String outputFilename = template.getGeneratorOutputFileNameForModel(template);
    		putCommonContextObject(context);
    		context.put("configuration", model.getConfiguration());
    		context.put("model", model);
    		context.put("template", template);
	    	produce (context, template, outputFilename); 
    	}        
    	catch( Exception e )
        {
            System.out.println(e);
        }
    }  
    
    private void putCommonContextObject (VelocityContext context) {
    	context.put("databaseUtils", new DatabaseUtils());
		context.put("formatUtils", new FormatUtils());
		context.put("bslaLibraryUtils", new BslaLibraryUtils());
		context.put("commonUtils", new BslaLibraryUtils());
    	context.put("URLUtils", new URLUtils());  
    	context.put("modelUtils", new ModelUtils());  
    }
    
    private void produce(VelocityContext context, Template template, String outputFilename) 
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

       if ( template != null)
    	   velocityTemplate.merge(context, writer);

       writer.flush();
       writer.close();    	
   }
    
}
