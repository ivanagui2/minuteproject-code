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
import net.sf.minuteProject.console.panel.ModelAccessPanel;
import net.sf.minuteProject.console.panel.ModelCommonPanel;
import net.sf.minuteProject.console.panel.TargetPanel;
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
	private TechnologycatalogHolder technologycatalogHolder;
	private DatabasecatalogHolder databasecatalogHolder;
	private static String catalogDir;
	
	public ConsoleSample (String title, String catalogDir) {
		super(title);
		init(catalogDir);
	}
	
//	public ConsoleSample() {
//		init();
//	}
	
	private void initComponents() {
		
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

		tabbedPane = new JTabbedPane();
		
		modelAccessPanel = new ModelAccessPanel(this);
		modelCommonPanel = new ModelCommonPanel(this);
		targetPanel = new TargetPanel(this);

		MigLayout lm = new MigLayout("ins 20", "[para]0[][100lp, fill][80lp][125lp, fill]", "");
		JPanel panel = createTabPanel(lm);
		addSeparator(panel, "Model Access");
		modelAccessPanel.fillModelAccessPanel(panel);
		addSeparator(panel, "Common configuration");
		modelCommonPanel.fillModelCommonPanel (panel);
		addSeparator(panel, "Target technology");
		targetPanel.fillTargetPanel(panel);
		tabbedPane.addTab("Data model reverse-engineering", panel);	

		MigLayout lm2 = new MigLayout("ins 20", "[para]0[][100lp, fill][80lp][125lp, fill]", "");
		JPanel common = createTabPanel(lm2);
		addSeparator(common, "Restrictions");
		commonPanel = new CommonPanel(common);
		commonPanel.fillCommonPanel();
		
		tabbedPane.addTab("Information", common);	
		
		getContentPane().add(tabbedPane);
		
		pack();		
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
				new ConsoleSample("MinuteProject console 0.5 - beta -", catalogDir).setVisible(true);
			}
		});
	}

	public void fill(BasicIntegrationConfiguration bic) {
		modelAccessPanel.fill(bic);
		modelCommonPanel.fill(bic);
		targetPanel.fill(bic);
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

}