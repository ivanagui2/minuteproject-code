package net.sf.minuteProject.console;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.swing.MigLayout;
import net.sf.minuteProject.console.panel.CommonPanel;
import net.sf.minuteProject.console.panel.ConventionPanel;
import net.sf.minuteProject.console.panel.EnrichmentFieldPanel;
import net.sf.minuteProject.console.panel.EnrichmentEntityPanel;
import net.sf.minuteProject.console.panel.FilterPanel;
import net.sf.minuteProject.console.panel.ModelAccessPanel;
import net.sf.minuteProject.console.panel.ModelCommonPanel;
import net.sf.minuteProject.console.panel.PackagePanel;
import net.sf.minuteProject.console.panel.TargetPanel;
import net.sf.minuteProject.console.panel.TechnologyConventionPanel;
import net.sf.minuteProject.console.panel.TechnologyLimitationPanel;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.catalog.databasecatalog.Databasecatalog;
import net.sf.minuteProject.loader.catalog.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.loader.catalog.databasecatalog.node.Database;
import net.sf.minuteProject.loader.catalog.technologycatalog.Technologycatalog;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.utils.catalog.CatalogUtils;
import static net.sf.minuteProject.console.utils.UIUtils.*;
import static net.sf.minuteProject.console.panel.ModelAccessPanel.*;
import static net.sf.minuteProject.console.panel.ModelCommonPanel.*;

public class ConsoleSample extends JFrame{

	private JTabbedPane tabbedPane;//, tabbedCommon;
	private ModelAccessPanel modelAccessPanel;
	private ModelCommonPanel modelCommonPanel;
	private TargetPanel targetPanel;
	private CommonPanel commonPanel; 
	private FilterPanel filterPanel;
	private ConventionPanel conventionPanel;
	private TechnologyLimitationPanel technologyLimitationPanel;
	private TechnologyConventionPanel technologyConventionPanel;
	private EnrichmentEntityPanel enrichmentEntityPanel;
	private EnrichmentFieldPanel enrichmentFieldPanel;
	private PackagePanel packagePanel;
	private TechnologycatalogHolder technologycatalogHolder;
	private DatabasecatalogHolder databasecatalogHolder;
	private JPanel technologyInfoTab;
	private static String catalogDir;
	
	public ConsoleSample (String title, String catalogDir) {
		super(title);
		init(catalogDir);
	}

	private void initComponents() {
		
		setLookAndFeel();

		tabbedPane = new JTabbedPane();
		
		modelAccessPanel = new ModelAccessPanel(this);
		modelCommonPanel = new ModelCommonPanel(this);
		targetPanel = new TargetPanel(this);
		filterPanel = new FilterPanel();
		conventionPanel = new ConventionPanel();
		technologyLimitationPanel = new TechnologyLimitationPanel(this);
		technologyConventionPanel = new TechnologyConventionPanel(this);
		enrichmentEntityPanel = new EnrichmentEntityPanel();
		enrichmentFieldPanel = new EnrichmentFieldPanel();
		packagePanel = new PackagePanel();

		tabbedPane.addTab("Data model reverse-engineering", getDataModelReverseEngineeringMainPanel());	
		tabbedPane.addTab("Customisation", getDataModelReverseEngineeringCustomisationPanel());	

		tabbedPane.addTab("Technology information", getTechConventionsTab());				

		tabbedPane.addTab("General information", getInformationPanel());	
		
		getContentPane().add(tabbedPane);
		
		pack();		
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private Component getEnrichmentTab() {
		JPanel panel = createTabPanel(getDefaultMigLayout());
		addSeparator(panel, "Entity");
		enrichmentEntityPanel.fillPanel(panel);		
		addSeparator(panel, "Field");
		enrichmentFieldPanel.fillPanel(panel);			
		return panel;
	}
	
	private Component getDataModelReverseEngineeringCustomisationPanel() {
		JPanel panel = createTabPanel(getDefaultMigLayout());
		addSeparator(panel, "Filter");
		filterPanel.fillPanel(panel);
		addSeparator(panel, "Package");
		packagePanel.fillPanel (panel);
		addSeparator(panel, "Convention");
		conventionPanel.fillPanel(panel);		
		return panel;
	}
	
	private Component getTechConventionsTab() {
		technologyInfoTab = createTabPanel(getDefaultMigLayout());
		addSeparator(technologyInfoTab, getTargetConventionTitle());
		technologyLimitationPanel.fillPanel(technologyInfoTab);	
		addSeparator(technologyInfoTab, "Conventions");
		technologyConventionPanel.fillPanel(technologyInfoTab);			
		return technologyInfoTab;
	}	

	private String getTargetConventionTitle() {
		return "Restrictions";
	}

	private Component getInformationPanel() {
		JPanel common = createTabPanel(getDefaultMigLayout());
		addSeparator(common, "Restrictions");
		commonPanel = new CommonPanel(common);
		commonPanel.fillCommonPanel();
		return common;
	}

	private Component getDataModelReverseEngineeringMainPanel() {
		JPanel panel = createTabPanel(getDefaultMigLayout());
		addSeparator(panel, "Model Access");
		modelAccessPanel.fillPanel(panel);
		addSeparator(panel, "Common configuration");
		modelCommonPanel.fillPanel (panel);
		addSeparator(panel, "Target technology");
		targetPanel.fillPanel(panel);
		return panel;
	}

	private MigLayout getDefaultMigLayout() {
		return new MigLayout("ins 20", "[para]0[][100lp, fill][80lp][125lp, fill]", "");
	}
	
	
	public void init(String catalogDir) {
		initCatalogs(catalogDir);
		initComponents();
	}
	
	private void initCatalogs(String catalogDir) {
		databasecatalogHolder = CatalogUtils.getPublishedDatabaseCatalogHolder(catalogDir);
		technologycatalogHolder = CatalogUtils.getPublishedTechnologyCatalogHolder(catalogDir);		
	}

	public static void main(String args[]) {
		if (args.length>0)
			catalogDir=args[0];
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ConsoleSample("MinuteProject console 0.5.3 - beta -", catalogDir).setVisible(true);
			}
		});
	}

	public void fill(BasicIntegrationConfiguration bic) {
		modelAccessPanel.fill(bic);
		modelCommonPanel.fill(bic);
		targetPanel.fill(bic);
		filterPanel.fill(bic);
		packagePanel.fill(bic);
		conventionPanel.fill(bic);
		technologyConventionPanel.fill(bic);
		bic.setCatalogDir(catalogDir);
	}

	public TechnologycatalogHolder getTechnologycatalogHolder() {
		return technologycatalogHolder;
	}

	public void setTechnologycatalogHolder(
			TechnologycatalogHolder technologycatalogHolder) {
		this.technologycatalogHolder = technologycatalogHolder;
	}

	public DatabasecatalogHolder getDatabasecatalogHolder() {
		return databasecatalogHolder;
	}

	public void setDatabasecatalogHolder(DatabasecatalogHolder databasecatalogHolder) {
		this.databasecatalogHolder = databasecatalogHolder;
	}

	public ModelAccessPanel getModelAccessPanel() {
		return modelAccessPanel;
	}

	public ModelCommonPanel getModelCommonPanel() {
		return modelCommonPanel;
	}

	public TargetPanel getTargetPanel() {
		return targetPanel;
	}

	public void applyCurrentPrimaryKeyPolicy(Database database) {
		modelCommonPanel.applyCurrentPrimaryKeyPolicy(database);
	}

	public static String getCatalogDir() {
		return catalogDir;
	}

	public static void setCatalogDir(String catalogDir) {
		ConsoleSample.catalogDir = catalogDir;
	}

	public TechnologyLimitationPanel getTechnologyLimitationPanel() {
		return technologyLimitationPanel;
	}

	public TechnologyConventionPanel getTechnologyConventionPanel() {
		return technologyConventionPanel;
	}

	public JPanel getTechnologyInfoTab() {
		return technologyInfoTab;
	}

	public void rebuildPanel(JPanel panel) {
		technologyInfoTab.removeAll();
		getTechConventionsTab ();
		technologyInfoTab.repaint();
	}
	
	
	
}