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
import net.sf.minuteProject.console.panel.ModelAccessPanel;
import net.sf.minuteProject.console.panel.ModelCommonPanel;
import net.sf.minuteProject.console.panel.TargetPanel;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.catalog.databasecatalog.Databasecatalog;
import net.sf.minuteProject.loader.catalog.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.loader.catalog.technologycatalog.Technologycatalog;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.utils.catalog.CatalogUtils;
import static net.sf.minuteProject.console.utils.UIUtils.*;
import static net.sf.minuteProject.console.panel.ModelAccessPanel.*;
import static net.sf.minuteProject.console.panel.ModelCommonPanel.*;

public class ConsoleSample extends JFrame{

	private JTabbedPane tabbedPane;
	private ModelAccessPanel modelAccessPanel;
	private ModelCommonPanel modelCommonPanel;
	private TargetPanel targetPanel;
	private TechnologycatalogHolder technologycatalogHolder;
	private DatabasecatalogHolder databasecatalogHolder;
	
	public ConsoleSample (String title) {
		super(title);
		init();
	}
	
	public ConsoleSample() {
		init();
	}
	
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
		
		modelAccessPanel = new ModelAccessPanel(databasecatalogHolder);
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
		getContentPane().add(tabbedPane);
		pack();		
	}
	

	
	public void init() {
		initCatalogs();
		initComponents();
	}
	
	private void initCatalogs() {
		databasecatalogHolder = CatalogUtils.getPublishedDatabaseCatalogHolder();
		technologycatalogHolder = CatalogUtils.getPublishedTechnologyCatalogHolder();		
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ConsoleSample("MinuteProject console (beta)").setVisible(true);
			}
		});
	}

	public void fill(BasicIntegrationConfiguration bic) {
		modelAccessPanel.fill(bic);
		modelCommonPanel.fill(bic);
		targetPanel.fill(bic);
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

}
