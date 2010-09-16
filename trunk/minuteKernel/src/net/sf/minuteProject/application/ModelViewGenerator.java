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
import net.sf.minuteProject.configuration.bean.model.data.Component;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.view.Function;
import net.sf.minuteProject.configuration.bean.view.Service;
import net.sf.minuteProject.utils.BslaLibraryUtils;
import net.sf.minuteProject.utils.BslaViewLibraryUtils;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.SqlUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.TestUtils;
import net.sf.minuteProject.utils.URLUtils;
import net.sf.minuteProject.utils.ViewUtils;
import net.sf.minuteProject.utils.WebUtils;

/**
 * @author Florian Adler
 * 
 */
public class ModelViewGenerator extends ModelGenerator {

	private static Logger logger = Logger.getLogger(ModelGenerator.class);

	// public static final String GENERATOR_MODEL_RULES =
	// "net/sf/minuteProject/configuration/model-config-rules.xml";

	/*
	 * context object
	 */
	// private CommonUtils commonUtils;
	// private ConvertUtils convertUtils;
	// private ColumnUtils columnUtils;
	// private ViewUtils viewUtils;
	// private FormatUtils formatUtils;
	// private BslaLibraryUtils bslaLibraryUtils;
	// private DatabaseUtils databaseUtils;
	// private ModelUtils modelUtils;
	// private URLUtils urlUtils;
	// private TestUtils testUtils;
	// private WebUtils webUtils;
	// private SqlUtils sqlUtils;
	// private TableUtils tableUtils;

	// private Model model;

	// private String modelConfig;
	//
	// public String getModelConfig() {
	// return modelConfig;
	// }
	//
	// public void setModelConfig(String modelConfig) {
	// this.modelConfig = modelConfig;
	// }

	/**
	 * Constructs the generator with its configuration
	 * 
	 * @param configurationFile
	 */
	public ModelViewGenerator(String configurationFile) {
		super(configurationFile);
	}

	// @Override
	// public AbstractConfiguration getConfigurationRoot() {
	// return new Configuration();
	// }
	//
	// @Override
	// public String getConfigurationRulesFile() {
	// return GENERATOR_MODEL_RULES;
	// }

	public static void main(String args[]) throws Exception {
		String config;
		if (args.length < 1) {
			System.exit(1);
		}
		config = args[0];
		Date startDate = new Date();
		logger.info("start time = " + new Date());
		ModelViewGenerator generator = new ModelViewGenerator(config);
		Configuration configuration = (Configuration) generator.load();

		generator.generate(configuration);
		// Model model = configuration.getModel();
		// generator.setModel(model);
		// generator.loadModel(model);
		// if (generator.hasTarget())
		// generator.loadAndGenerate(model.getConfiguration().getTarget());
		// if (generator.hasTargets())
		// generator.loadAndGenerate(model.getConfiguration().getTargets());

		// generator.loadTarget(model.getConfiguration(),
		// model.getConfiguration().getTarget());
		// generator.generate(model.getConfiguration().getTarget());
		Date endDate = new Date();
		logger.info("time taken : " + (endDate.getTime() - startDate.getTime())
				/ 1000 + "s.");
	}

	protected void loadModel(Model model) {
		super.loadModel(model);
		// model.getBusinessModel().complementDataModelWithTables();
		model.getBusinessModel().complementDataModelWithViews();
		model.getBusinessModel().complementService();
	}

	public Model getModel() {
		return super.getModel();
	}

	public void setModel(Model model) {
		super.setModel(model);
	}

	protected void writeTemplateResult(GeneratorBean bean, Template template)
			throws Exception {
		String outputFilename = template
				.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		VelocityContext context = getVelocityContext(template);
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		if (bean instanceof Component) {
			Component component = (Component) bean;
			Table table = component.getTable();
			context.put("table", table);
		}
		if (beanName.equals("view"))
			context.put("table", bean);
		context.put("template", template);
		putCommonContextObject(context, template);
		try {
			produce(context, template, outputFilename);
		} catch (Exception ex) {
			logger.error("ERROR on template " + template.getName()
					+ " - on bean " + bean.getName());
			ex.printStackTrace();
			throw ex;
		}
	}

	protected void generateArtifactsByEntity(Template template) throws Exception {	
		super.generateArtifactsByEntity(template);
		for (Iterator iter =  getModel().getBusinessModel().getBusinessPackage().getViews().iterator(); iter.hasNext(); ) {
			View view = (View) iter.next();
			writeTemplateResult(view, template);
		}
	}
	
	protected void putCommonContextObject(VelocityContext context,
			Template template) {
		putStandardContextObject(context);
		putPluginContextObject(context, template);
		context.put("model", getModel());
	}

}
