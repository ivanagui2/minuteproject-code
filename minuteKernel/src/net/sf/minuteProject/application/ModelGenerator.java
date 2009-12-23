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
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.service.Scope;
import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.view.Function;
import net.sf.minuteProject.configuration.bean.view.Service;
import net.sf.minuteProject.configuration.bean.view.View;
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
	private CommonUtils commonUtils;
	private ConvertUtils convertUtils;
	private ColumnUtils columnUtils;
	private ViewUtils viewUtils;
	private FormatUtils formatUtils;
	private BslaLibraryUtils bslaLibraryUtils;
	private DatabaseUtils databaseUtils;
	private ModelUtils modelUtils;
	private URLUtils urlUtils;
	private TestUtils testUtils;
	private WebUtils webUtils;
	private SqlUtils sqlUtils;
	private TableUtils tableUtils;

	
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

	@Override
	public String getPropertyConfigurationRulesFile() {
		return GENERATOR_MODEL_PROPERTY_RULES;
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

	protected void loadModel(Model model) {
		model.getDataModel().loadDatabase();
		model.getBusinessModel().complementDataModelWithTables();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject.configuration.bean.Template)
	 */
	public void generate(Template template) throws Exception {
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
	}

	public Model getModel() throws Exception {
//		if (model == null) {
//			ModelGenerator modelGenerator = new ModelGenerator(getModelConfig());
//			setModel((Model) modelGenerator.load());
//		}
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	protected void generateArtifactsByModel(Template template) throws Exception {
		writeTemplateResult(getModel(), template);
	}

	protected void generateArtifactsByPackage(Template template) throws Exception {
		List packages = getModel().getBusinessModel().getBusinessPackage()
				.getPackages();
		for (Iterator<Package> iter = packages.iterator(); iter.hasNext();) {
			writeTemplateResult((Package) iter.next(), template);
		}
	}

	protected void generateArtifactsByField(Template template) throws Exception {	
		for (Iterator iter =  getModel().getBusinessModel().getBusinessPackage().getTables().iterator(); iter.hasNext(); ) {
			Table table = getDecoratedTable((Table) iter.next());
			for (Column column : table.getColumns()) {
				boolean isToGenerate = true;
	    		if (template.getCheckTemplateToGenerate()!=null && template.getCheckTemplateToGenerate().equals("true")) {
	    			if (!template.isToGenerate(column)) {
	    				isToGenerate =false;
	    			}
	    		} 
	    		if (isToGenerate)
				   writeTemplateResult(column, template);
			}
		}
	}
	
	protected void generateArtifactsByEntity(Template template) throws Exception {	
		for (Iterator iter =  getModel().getBusinessModel().getBusinessPackage().getTables().iterator(); iter.hasNext(); ) {
			Table table = getDecoratedTable((Table) iter.next());
			//table.getParents();
			boolean isToGenerate = true;
    		if (template.getCheckTemplateToGenerate()!=null && template.getCheckTemplateToGenerate().equals("true")) {
    			if (!template.isToGenerate(table)) {
    				isToGenerate =false;
    			}
    		} 
    		if (isToGenerate)
			   writeTemplateResult(table, template);
		}
	}

	protected void generateArtifactsByService(Template template) throws Exception {	
		for (Scope scope : getModel().getBusinessModel().getService().getScopes()) {
			if (ServiceUtils.isToGenerate(template, scope))
				writeTemplateResult(scope, template);
		}		
	}

	private void generateArtifactsByApplication(Template template) throws Exception {	
		writeTemplateResult(getModel().getConfiguration(), template);
	}
	
	protected void generateArtifactsByComponent(Template template) throws Exception {	
		writeTemplateResult(getModel().getConfiguration(), template);
	}
	
	protected void writeTemplateResult(GeneratorBean bean,
			Template template) throws Exception {
		String outputFilename = template
				.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		VelocityContext context = getVelocityContext(template);
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		context.put("template", template);
		putCommonContextObject(context, template);
		try {
			produce(context, template, outputFilename);
		} catch (Exception ex) {
			logger.error("ERROR on template "+template.getName()+" - on bean "+bean.getName());
			logger.error("ERROR : "+ex.getMessage());
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
