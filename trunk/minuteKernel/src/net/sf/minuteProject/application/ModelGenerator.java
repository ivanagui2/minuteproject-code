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
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.FunctionModel;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.configuration.bean.service.Scope;
import net.sf.minuteProject.configuration.bean.system.Plugin;

import net.sf.minuteProject.configuration.bean.view.Service;
import net.sf.minuteProject.configuration.bean.view.View;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.target.TargetHolder;
import net.sf.minuteProject.utils.BslaLibraryUtils;
import net.sf.minuteProject.utils.BslaViewLibraryUtils;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.ServiceUtils;
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
public class ModelGenerator extends AbstractGenerator {

	private static Logger logger = Logger.getLogger(ModelGenerator.class);
	public static final String GENERATOR_MODEL_RULES = "net/sf/minuteProject/configuration/model-config-rules.xml";
	public static final String GENERATOR_MODEL_PROPERTY_RULES = "net/sf/minuteProject/configuration/model-property-config-rules.xml";

	/*
	 * context object 
	 */
	private CommonUtils commonUtils = new CommonUtils();
	private ConvertUtils convertUtils = new ConvertUtils();
	private ColumnUtils columnUtils = new ColumnUtils();
	private ViewUtils viewUtils = new ViewUtils();
	private FormatUtils formatUtils = new FormatUtils();
	private BslaLibraryUtils bslaLibraryUtils = new BslaLibraryUtils();
	private DatabaseUtils databaseUtils = new DatabaseUtils();
	private ModelUtils modelUtils = new ModelUtils();
	private URLUtils urlUtils = new URLUtils();
	private TestUtils testUtils = new TestUtils();
	private WebUtils webUtils = new WebUtils();
	private SqlUtils sqlUtils = new SqlUtils();
	private TableUtils tableUtils = new TableUtils();
	private ReferenceUtils referenceUtils = new ReferenceUtils();

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

	public ModelGenerator(BasicIntegrationConfiguration bic) {
		super(bic);
	}

	@Override
	public AbstractConfiguration getConfigurationRoot() {
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
	    logger.info("start time = "+new Date());
		ModelGenerator generator = new ModelGenerator(config);
//		Configuration configuration;
		// Model model = (Model) generator.load();
//		Configuration configuration = (Configuration) generator.load();
		try {
			generator.generate();
		} catch (MinuteProjectException e) {
			generator.exit ("");
		}
//		Model model = configuration.getModel();
//		generator.setModel(model);
//		generator.loadModel(model);
////		generator.loadTarget(model.getConfiguration(), model.getConfiguration()
////				.getTarget());
////		generator.generate(model.getConfiguration().getTarget());
//		if (generator.hasTarget())
//			generator.loadAndGenerate(model.getConfiguration().getTarget());
//		if (generator.hasTargets())
//			generator.loadAndGenerate(model.getConfiguration().getTargets());
		Date endDate = new Date();
		//logger.info("start date = "+startDate.getTime());
		//logger.info("end date = "+endDate.getTime());
		logger.info("time taken : "+(endDate.getTime()-startDate.getTime())/1000+ "s.");
	}


	public void generate() throws MinuteProjectException {
		Configuration configuration = (Configuration) load();
		generate(configuration);		
	}
	
	protected void generate (Configuration configuration) throws MinuteProjectException {
		Model model = configuration.getModel();
		setModel(model);
		loadModel(model);
		applyConventions(model);
		applyLimitations(model);
		if (hasTarget())
			loadAndGenerate(model.getConfiguration().getTarget());
		if (hasTargets())
			loadAndGenerate(model.getConfiguration().getTargets());
	}
	
	private void applyLimitations(Model model) {
		model.getBusinessModel().applyLimitations();
	}

	protected void applyConventions (Model model) {
		model.getBusinessModel().applyConventions();
	}
	
	protected boolean hasTarget () {
		return model.getConfiguration().hasTarget();
	}
	
	protected boolean hasTargets () {
		return model.getConfiguration().hasTargets();
	}
	
	protected void loadAndGenerate (Target target) throws MinuteProjectException {
		loadTarget(model.getConfiguration(), target);
		generate(model.getConfiguration().getTarget());		
	}

	protected void loadAndGenerate (Targets targets) throws MinuteProjectException {
		Target targetFinal = new Target();
		Configuration configuration = model.getConfiguration();
		for (Target target : targets.getTargets()) {
//			TargetHolder targetHolder = new TargetHolder();
			loadTarget(configuration, target);
			configuration.getTarget().setIsGenerable(target.isGenerable());
//			generate(configuration.getTarget());
//			targetHolder.setTarget(model.getConfiguration().getTarget());
			
			targetFinal.complement(configuration.getTarget());
			configuration.setTarget(new Target());
		}	
		configuration.setTarget(targetFinal);
		generate(configuration.getTarget());
//		for (Target target : targets.getTargets()) {
//			complementWithTargetInfo(configuration, target);
//			System.out.println("---");
//			generate(configuration.getTarget());
//		}
//		targets.getAbstractConfigurationRoot().setTarget(targetHolder);
			
	}
	
	protected void loadModel(Model model) {
		model.getDataModel().loadDatabase();
		BusinessModel businessModel = model.getBusinessModel();
		businessModel.secureEntityType();
		businessModel.complementDataModelWithTables();
		businessModel.complementDataModelWithTransferEntitiesEnrichment();
		if (model.hasFunctionModel()) {
			FunctionModel functionModel = model.getFunctionModel();
			functionModel.complementFunctionWithFunctionEntity();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject.configuration.bean.Template)
	 */
	public void generate(Template template) throws MinuteProjectException {
		// TODO Auto-generated method stub
		// getView();
		if (template.getFieldSpecific().equals("true"))
			generateArtifactsByField(template);		
		else if (template.getEntitySpecific().equals("true"))
			generateArtifactsByEntity(template);
		else if (template.getPackageSpecific().equals("true"))
			generateArtifactsByPackage(template);
		else if (template.getModelSpecific().equals("true"))
			generateArtifactsByModel(template);
		else if (template.getServiceSpecific().equals("true"))
			generateArtifactsByService(template);
		else if (template.getApplicationSpecific().equals("true"))
			generateArtifactsByApplication(template);
		else if (template.getComponentSpecific().equals("true"))
			generateArtifactsByComponent(template);
		else if (template.getScopeSpecificValue().equals(SCOPE_DATAMODEL_FUNCTION_INPUT))
			generateArtifactsByFunction(template, Direction.IN, Direction.INOUT);
		else if (template.getScopeSpecificValue().equals(SCOPE_DATAMODEL_FUNCTION_OUTPUT))
			generateArtifactsByFunction(template, Direction.OUT, Direction.INOUT);		
		else if (template.getScopeSpecificValue().equals(SCOPE_DATAMODEL_FUNCTION))
			generateArtifactsByFunction(template);
		else if (template.getScopeSpecificValue().equals(SCOPE_TARGET_TEMPLATE))
			generateArtifactsByTargetTemplate(template);	
		else if (template.getScopeSpecificValue().equals(SCOPE_TRANSFER_ENTITY_TEMPLATE))
			generateArtifactsByTransferEntity(template);	
		else if (template.getScopeSpecificValue().equals(SCOPE_ACTION_TEMPLATE))
			generateArtifactsByAction(template);			
	}

	private void generateArtifactsByAction(Template template) throws MinuteProjectException {
		for (Table table : getModel().getBusinessModel().getBusinessPackage().getTransferEntities()) {
			for (Action action : table.getActions()) {
				writeTemplateResult(action, template);
			}
		}
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	protected void generateArtifactsByTargetTemplate(Template template) throws MinuteProjectException {
		writeTemplateResult(getModel().getConfiguration(), template);
	}
	
	protected void generateArtifactsByModel(Template template) throws MinuteProjectException {
		if (isToGenerate(getModel(), template))
			writeTemplateResult(getModel(), template);
	}

	protected void generateArtifactsByPackage(Template template) throws MinuteProjectException {
		List<Package> packages = getModel().getBusinessModel().getBusinessPackage().getPackages();
		for (Package pack : packages) {
			writeTemplateResult(pack, template);
		}
	}

	protected void generateArtifactsByField(Template template) throws MinuteProjectException {	
		for (Table table : getModel().getBusinessModel().getBusinessPackage().getTables()) {
			generateArtifactsByField(template, table);
		}
	}
	

	protected void generateArtifactsByField(Template template, Table table) throws MinuteProjectException{
		table = getDecoratedTable(table);
		for (Column column : table.getColumns()) {
//			boolean isToGenerate = true;
//    		if (template.getCheckTemplateToGenerate()!=null && template.getCheckTemplateToGenerate().equals("true")) {
//    			if (!template.isToGenerate(column)) {
//    				isToGenerate =false;
//    			}
//    		} 
    		if (isToGenerate(table, template))
			   writeTemplateResult(column, template);
		}
	}

	protected void generateArtifactsByTransferEntity(Template template) throws MinuteProjectException {	
		for (Table table : getModel().getBusinessModel().getBusinessPackage().getTransferEntities()) {
			generateArtifactsByEntity (table, template);
		}
	}
	
	protected void generateArtifactsByEntity(Template template) throws MinuteProjectException {	
		for (Table table : getModel().getBusinessModel().getBusinessPackage().getTables()) {
			generateArtifactsByEntity (table, template);
//			table = getDecoratedTable(table);
//			boolean isToGenerate = true;
//    		if (template.getCheckTemplateToGenerate()!=null && template.getCheckTemplateToGenerate().equals("true")) {
//    			if (!template.isToGenerate(table)) {
//    				isToGenerate =false;
//    			}
//    		} 
//    		if (isToGenerate)
//			   writeTemplateResult(table, template);
		}
	}
	
	protected void generateArtifactsByEntity(Table table, Template template) throws MinuteProjectException {	
		table = getDecoratedTable(table);
//		boolean isToGenerate = true;
//		if (template.getCheckTemplateToGenerate()!=null && template.getCheckTemplateToGenerate().equals("true")) {
//			if (!template.isToGenerate(table)) {
//				isToGenerate =false;
//			}
//		} 
//		if (isToGenerate)
		if (isToGenerate(table, template))
		   writeTemplateResult(table, template);		
	}
	
	private boolean isToGenerate (GeneratorBean bean, Template template) {
		if (template.getCheckTemplateToGenerate()!=null && template.getCheckTemplateToGenerate().equals("true")) {
			if (!template.isToGenerate(bean)) {
				return false;
			}
		} 	
		return true;
	}

	protected void generateArtifactsByService(Template template) throws MinuteProjectException {	
		for (Scope scope : getModel().getBusinessModel().getService().getScopes()) {
			if (ServiceUtils.isToGenerate(template, scope))
				writeTemplateResult(scope, template);
		}		
	}

	private void generateArtifactsByApplication(Template template) throws MinuteProjectException {	
		writeTemplateResult(getModel().getConfiguration(), template);
	}
	
	protected void generateArtifactsByComponent(Template template) throws MinuteProjectException {	
		writeTemplateResult(getModel().getConfiguration(), template);
	}
	
	protected void generateArtifactsByFunction(Template template) throws MinuteProjectException {	
		for (Function function : getModel().getDataModel().getDatabase().getFunctions()) {
			writeTemplateResult(function, template);
		}
	}
	
	protected void generateArtifactsByFunction(Template template, Direction... direction) throws MinuteProjectException {	
		for (Function function : getModel().getDataModel().getDatabase().getFunctions()) {
			Direction functionDirection = function.getDirection();
			for (Direction dir : direction) { // dir has to be put in the correct order IN or OUT before NONE, INOUT
				if (dir.equals(functionDirection)) {
					writeTemplateResult(function.getEntity(dir), template);
					break;
				}
			}
		}
	}
		
	protected void writeTemplateResult(GeneratorBean bean,
			Template template) throws MinuteProjectException {
		String outputFilename = template.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		VelocityContext context = getVelocityContext(template);
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		if (bean instanceof Function) {
			context.put("table", bean);
		}			
		context.put("template", template);
		putCommonContextObject(context, template);
		try {
			produce(context, template, outputFilename);
		} catch (Exception ex) {
			logger.error("ERROR on template "+template.getName()+" - on bean "+bean.getName());
			throwException(ex, "ERROR : "+ex.getMessage());
//			logger.error("ERROR : "+ex.getMessage());
//			throw ex;
		}
	}

	protected void putCommonContextObject(VelocityContext context, Template template) {
		putStandardContextObject(context);
		putPluginContextObject(context, template);
		context.put("model", model);
	}
	
//	protected void putPluginContextObject (VelocityContext context, Template template) {
//		List <Plugin> plugins = template.getTemplateTarget().getTarget().getPlugins();
//		for (Plugin plugin : plugins) {
//			ClassLoader cl = ClassLoader.getSystemClassLoader();
//			try {
//				Class clazz = cl.loadClass(plugin.getClassName());
//				Object velocityObject = clazz.newInstance();
//				context.put(plugin.getName(), velocityObject);
//			} catch (ClassNotFoundException e) {
//				logger.info("cannot find plugin "+plugin.getName()+" via class "+plugin.getClassName());
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				logger.info("cannot instantiate plugin "+plugin.getName()+" via class "+plugin.getClassName());
//			} catch (IllegalAccessException e) {
//				logger.info("cannot access plugin "+plugin.getName()+" via class "+plugin.getClassName());
//			}
//		}
//	}
	
	protected void putStandardContextObject (VelocityContext context) {
		super.putStandardContextObject(context);
		context.put("convertUtils", getConvertUtils());
		context.put("commonUtils", getCommonUtils());
		context.put("columnUtils", getColumnUtils());
		context.put("viewUtils", getViewUtils());
		context.put("formatUtils", getFormatUtils());
		context.put("bslaLibraryUtils", getBslaLibraryUtils());
		context.put("databaseUtils", getDatabaseUtils());
		context.put("modelUtils", getModelUtils());
		context.put("URLUtils", getUrlUtils());
		context.put("TestUtils", getTestUtils());
		context.put("WebUtils", getWebUtils());
		context.put("sqlUtils", getSqlUtils());
		context.put("tableUtils", getTableUtils());
		context.put("testUtils", getTestUtils());	
		context.put("referenceUtils", referenceUtils);
	}
	
	public BslaLibraryUtils getBslaLibraryUtils() {
		if (bslaLibraryUtils==null)
			bslaLibraryUtils = new BslaLibraryUtils();
		return bslaLibraryUtils;
	}

	public ColumnUtils getColumnUtils() {
		if (columnUtils==null)
			columnUtils = new ColumnUtils();
		return columnUtils;
	}

	public CommonUtils getCommonUtils() {
		if (commonUtils==null)
			commonUtils = new CommonUtils();
		return commonUtils;
	}

	public ConvertUtils getConvertUtils() {
		if (convertUtils == null)
			convertUtils = new ConvertUtils();
		return convertUtils;
	}

	public DatabaseUtils getDatabaseUtils() {
		if (databaseUtils == null)
			databaseUtils = new DatabaseUtils();
		return databaseUtils;
	}

	public FormatUtils getFormatUtils() {
		if (formatUtils == null)
			formatUtils = new FormatUtils();
		return formatUtils;
	}

	public ModelUtils getModelUtils() {
		if (modelUtils == null)
			modelUtils = new ModelUtils();
		return modelUtils;
	}

	public SqlUtils getSqlUtils() {
		if (sqlUtils == null)
			sqlUtils = new SqlUtils();
		return sqlUtils;
	}

	public TableUtils getTableUtils() {
		if (tableUtils == null)
			tableUtils = new TableUtils();
		return tableUtils;
	}

	public TestUtils getTestUtils() {
		if (testUtils == null)
			testUtils = new TestUtils();
		return testUtils;
	}

	public URLUtils getUrlUtils() {
		if (urlUtils == null)
			urlUtils = new URLUtils();
		return urlUtils;
	}

	public ViewUtils getViewUtils() {
		if (viewUtils == null)
			viewUtils = new ViewUtils();
		return viewUtils;
	}

	public WebUtils getWebUtils() {
		if (webUtils == null)
			webUtils = new WebUtils();
		return webUtils;
	}

	/* 
	 * private getter of the context object 
	 */
	
	
	
}
