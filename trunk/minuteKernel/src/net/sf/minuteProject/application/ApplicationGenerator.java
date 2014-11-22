package net.sf.minuteProject.application;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.FunctionModel;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Component;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.configuration.bean.model.statement.Composite;
import net.sf.minuteProject.configuration.bean.model.statement.CompositeQueryElement;
import net.sf.minuteProject.configuration.bean.model.statement.Queries;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.service.Scope;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.utils.BslaLibraryUtils;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.EnumUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.MinuteProjectUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.RoutineUtils;
import net.sf.minuteProject.utils.ServiceUtils;
import net.sf.minuteProject.utils.SqlUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.TemplateUtils;
import net.sf.minuteProject.utils.TestUtils;
import net.sf.minuteProject.utils.TriggerUtils;
import net.sf.minuteProject.utils.URLUtils;
import net.sf.minuteProject.utils.ViewUtils;
import net.sf.minuteProject.utils.WebUtils;
import net.sf.minuteProject.utils.criteria.OrderingUtils;
import net.sf.minuteProject.utils.enrichment.EnrichmentUtils;
import net.sf.minuteProject.utils.enrichment.SemanticReferenceUtils;
import net.sf.minuteProject.utils.io.FileUtils;
import net.sf.minuteProject.utils.io.UpdatedAreaUtils;
import net.sf.minuteProject.utils.java.JavaUtils;
import net.sf.minuteProject.utils.sql.QueryUtils;
import net.sf.minuteProject.utils.sql.StatementUtils;
import net.sf.minuteProject.utils.velocity.VelocityUtils;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

/**
 * @author Florian Adler
 * 
 */
public class ApplicationGenerator extends AbstractGenerator {

	private static Logger logger = Logger.getLogger(ModelGenerator.class);
	public static final String GENERATOR_MODEL_RULES = "net/sf/minuteProject/configuration/model-config-rules.xml";
	public static final String GENERATOR_MODEL_PROPERTY_RULES = "net/sf/minuteProject/configuration/model-property-config-rules.xml";

	private Application application;

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
	public ApplicationGenerator(String configurationFile) {
		super(configurationFile);
	}

	public ApplicationGenerator(BasicIntegrationConfiguration bic) {
		super(bic);
	}

	@Override
	public Configuration getConfigurationRoot() {
		return new Configuration();
	}

	@Override
	public String getConfigurationRulesFile() {
		return GENERATOR_MODEL_RULES;
	}

	@Override
	public String getPropertyConfigurationRulesFile() {
		return GENERATOR_MODEL_PROPERTY_RULES;
	}
	
	public static void main(String args[]) {
		String config;
		if (args.length < 1) {
			System.exit(1);
		}
		config = args[0];
		Date startDate = new Date();
	    logger.info("start time = "+startDate);
	    ApplicationGenerator generator = new ApplicationGenerator(config);
		try {
			generator.generate();
		} catch (MinuteProjectException e) {
			generator.exit ("");
		}
		Date endDate = new Date();
		logger.info("end time = "+endDate);
		logger.info("time taken : "+(endDate.getTime()-startDate.getTime())/1000+ "s.");
	}


	protected void generate (Configuration configuration) throws MinuteProjectException {
		application = configuration.getApplication();
		boolean targetLoaded = false;
		for (Model model : application.getModels()) {
			ModelViewGenerator mvg = new ModelViewGenerator(model);
			mvg.getEnrichedModel(model);
			model.setConfiguration(application.getConfiguration());
			mvg.generate(model, targetLoaded);
			targetLoaded = true;
		}
		//TODO add application centric artifacts
		applyTargetConventionAndGenerate(configuration.getTarget());
		
		Targets targets = configuration.getTargets();
		if (hasPostGenerationAction(targets)) {
			executePostGenerationAction(targets);
		}
	}
	
	private void executePostGenerationAction(Targets targets) {
		targets.getPostGenerationAction().run();
	}

	private boolean hasPostGenerationAction(Targets targets) {
		return targets!=null && targets.getPostGenerationAction()!=null;
	}

	protected boolean hasTarget () {
		return application.getConfiguration().hasTarget();
	}
	
	protected boolean hasTargets () {
		return application.getConfiguration().hasTargets();
	}
	
	protected void loadAndGenerate (Target target) throws MinuteProjectException {
		loadTarget(application.getConfiguration(), target);
		applyTargetConventionAndGenerate(application.getConfiguration().getTarget());		
	}
/*
	protected void loadAndGenerate (Targets targets) throws MinuteProjectException {
		Target targetFinal = new Target();
		Configuration configuration = application.getConfiguration();
		for (Target target : targets.getTargets()) {
			loadTarget(configuration, target);
			configuration.getTarget().setIsGenerable(target.isGenerable());
			targetFinal.complement(configuration.getTarget());
			targetFinal.complementAdditional (target);
			configuration.setTarget(new Target()); //TODO remove
		}	
		configuration.setTarget(targetFinal);
		applyTargetConventionAndGenerate(configuration.getTarget());
	}
*/	
	private void applyTargetConventionAndGenerate (Target target) throws MinuteProjectException {
		applyTargetConvention(target);
		generate(target);
	}
	
	private void applyTargetConvention(Target target) {
		//TODO
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject.configuration.bean.Template)
	 */
	public void generate(Template template) throws MinuteProjectException {
		if (template.isApplicationScope())
			generateArtifactsByApplication(template);
	}

	private void generateArtifactsByApplication(Template template) throws MinuteProjectException {	
		if (isToGenerate(application, template))
			template.setAddModelDirName("false");
			writeTemplateResult(application, template);
	}
	
	protected void writeTemplateResult(GeneratorBean bean, Template template) throws MinuteProjectException {
		// enable cache
		bean.enableCache();
		//velocity bean manipulation
		String outputFilename = template.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		//context
		VelocityContext context = getVelocityContext(template);
		Map<String,String> updatedAreas = TemplateUtils.getUpdatedAreas(template, bean);
		if (updatedAreas!=null) {
			if (updatedAreas.containsKey(UpdatedAreaUtils.MP_MANAGED_STOP_GENERATING))
				return; //stop generating directive
			context.put("updatedAreas", updatedAreas);
		}
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		if (bean instanceof Component) {
			Component component = (Component) bean;
			Table table = component.getTable();
			context.put("table", table);
		}		
		if (bean instanceof Function) {
			context.put("function", bean);
			context.put("table", ((Function)bean).getEntity(Direction.ANY)); //to give access to model
//			context.put("table", bean);
		}		
		if (beanName.equals("view"))
			context.put("table", bean);		
		context.put("template", template);
		putCommonContextObject(context, template);
        //
		try {
			produce(context, template, outputFilename);
		} catch (Exception ex) {
			logger.error("ERROR on template "+template.getName()+" - on bean "+bean.getName());
			ex.printStackTrace();
			throwException(ex, "ERROR : "+ex.getMessage());		
//			logger.error("ERROR : "+ex.getMessage());
//			throw ex;
		}
	}


	protected void putCommonContextObject(VelocityContext context, Template template) {
		putStandardContextObject(context);
		putPluginContextObject(context, template);
		context.put("application", application);
		context.put("configuration", application.getConfiguration());
	}
	
	protected void putStandardContextObject (VelocityContext context) {
		super.putStandardContextObject(context);
	}

}
