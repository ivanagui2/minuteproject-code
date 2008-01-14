package net.sf.minuteProject.application;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.ddlutils.model.Database;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.view.Function;
import net.sf.minuteProject.configuration.bean.view.Service;
import net.sf.minuteProject.configuration.bean.view.View;
import net.sf.minuteProject.utils.BslaLibraryUtils;
import net.sf.minuteProject.utils.BslaViewLibraryUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.TestUtils;
import net.sf.minuteProject.utils.URLUtils;
import net.sf.minuteProject.utils.ViewUtils;
import net.sf.minuteProject.utils.WebUtils;

/**
 * @author Florian Adler
 * 
 */
public class ModelGenerator extends AbstractGenerator {

	private static Logger logger = Logger.getLogger(ModelGenerator.class);
	public static final String GENERATOR_MODEL_RULES = "net/sf/minuteProject/configuration/model-config-rules.xml";

	private Model model;

	private String modelConfig;

	public String getModelConfig() {
		return modelConfig;
	}

	public void setModelConfig(String modelConfig) {
		this.modelConfig = modelConfig;
	}

	/**
	 * Constructs the generator with its configuration
	 * 
	 * @param configurationFile
	 */
	public ModelGenerator(String configurationFile) {
		super(configurationFile);
	}

	@Override
	public AbstractConfiguration getConfigurationRoot() {
		return new Configuration();
	}

	@Override
	public String getConfigurationRulesFile() {
		return GENERATOR_MODEL_RULES;
	}

	public static void main(String args[]) throws Exception {
		String config;
		if (args.length < 1) {
			System.exit(1);
		}
		config = args[0];
		Date startDate = new Date();
	    logger.info("start time = "+new Date());
		ModelGenerator generator = new ModelGenerator(config);
		// Model model = (Model) generator.load();
		Configuration configuration = (Configuration) generator.load();
		Model model = configuration.getModel();
		generator.setModel(model);
		generator.loadModel(model);
		generator.loadTarget(model.getConfiguration(), model.getConfiguration()
				.getTarget());
		generator.generate(model.getConfiguration().getTarget());
		Date endDate = new Date();
		//logger.info("start date = "+startDate.getTime());
		//logger.info("end date = "+endDate.getTime());
		logger.info("time taken : "+(endDate.getTime()-startDate.getTime())/1000+ "s.");
	}

	private void loadModel(Model model) {
		model.getDataModel().loadDatabase();
		model.getBusinessModel().complementDataModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject.configuration.bean.Template)
	 */
	public void generate(Template template) throws Exception {
		// TODO Auto-generated method stub
		// getView();
		if (template.getEntitySpecific().equals("true"))
			generateArtifactsByEntity(template);
		else if (template.getPackageSpecific().equals("true"))
			generateArtifactsByPackage(template);
		else if (template.getModelSpecific().equals("true"))
			generateArtifactsByModel(template);
		else if (template.getApplicationSpecific().equals("true"))
			generateArtifactsByApplication(template);
	}

	public Model getModel() throws Exception {
		if (model == null) {
			ModelGenerator modelGenerator = new ModelGenerator(getModelConfig());
			setModel((Model) modelGenerator.load());
		}
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	/*private void writeTemplateResultView(VelocityContext context,
			Template template) {
		try {
			String outputFilename = template
					.getGeneratorOutputFileNameForConfigurationBean(getModel(),
							template);
			// String outputFilename =
			// template.getGeneratorOutputFileNameForModel(template);
			// TODO set as method in Generator
			context.put("model", getModel());
			context.put("template", template);
			putCommonContextObject(context);
			produce(context, template, outputFilename);
		} catch (Exception e) {
			System.out.println(e);
		}
	}*/

	private void generateArtifactsByModel(Template template) throws Exception {
		writeTemplateResult(getModel(), template);
	}

	private void generateArtifactsByPackage(Template template) throws Exception {
		List packages = model.getBusinessModel().getBusinessPackage()
				.getPackages();
		for (Iterator<Package> iter = packages.iterator(); iter.hasNext();) {
			// Package pack = new Package();
			// pack.getBusinessPackage().getBusinessModel().getModel()
			writeTemplateResult((Package) iter.next(), template);
		}
	}

	private void generateArtifactsByEntity(Template template) throws Exception {	
		for (Iterator iter =  model.getBusinessModel().getBusinessPackage().getTables().iterator(); iter.hasNext(); ) {
			Table table = getDecoratedTable((Table) iter.next());
			//table.getParents();
			writeTemplateResult(table, template);
		}
	}

	private void generateArtifactsByApplication(Template template) throws Exception {	
		writeTemplateResult(getModel().getConfiguration(), template);
	}
	
	private void writeTemplateResult(GeneratorBean bean,
			Template template) throws Exception {
		String outputFilename = template
				.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		VelocityContext context = getVelocityContext(template);
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		context.put("template", template);
		putCommonContextObject(context);
		try {
			produce(context, template, outputFilename);
		} catch (Exception ex) {
			logger.error("ERROR on template "+template.getName()+" - on bean "+bean.getName());
			throw ex;
		}
	}

	private void putCommonContextObject(VelocityContext context) {
		context.put("convertUtils", new ConvertUtils());
		context.put("commonUtils", new CommonUtils());
		context.put("viewUtils", new ViewUtils());
		context.put("formatUtils", new FormatUtils());
		context.put("bslaLibraryUtils", new BslaLibraryUtils());
		context.put("databaseUtils", new DatabaseUtils());
		context.put("modelUtils", new ModelUtils());
		context.put("URLUtils", new URLUtils());
		context.put("TestUtils", new TestUtils());
		context.put("WebUtils", new WebUtils());
		context.put("model", model);
	}

}
