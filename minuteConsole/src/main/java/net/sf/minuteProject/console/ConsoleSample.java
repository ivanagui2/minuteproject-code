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
	
	private void initComponents() {
		tabbedPane = new JTabbedPane();
		
		modelAccessPanel = new ModelAccessPanel(databasecatalogHolder);
		modelCommonPanel = new ModelCommonPanel();
		targetPanel = new TargetPanel(this);

		MigLayout lm = new MigLayout("ins 20", "[para]0[][100lp, fill][80lp][125lp, fill]", "");
		JPanel panel = createTabPanel(lm);

		addSeparator(panel, "Model Access");

		modelAccessPanel.fillModelAccessPanel(panel);

		addSeparator(panel, "Common configuration");

		modelCommonPanel.fillModelCommonPanel (panel);

		addSeparator(panel, "Target technology");

		targetPanel.fillTargetPanel(panel);

		tabbedPane.addTab("MinuteProject console", panel);	
		getContentPane().add(tabbedPane);
		pack();		
	}
	
	public ConsoleSample() {
		initCatalogs();
		initComponents();
	}
	
	private void initCatalogs() {
		if (databasecatalogHolder==null) {
			Databasecatalog loader = new Databasecatalog("catalog/database-catalog.xml");
			try {
				databasecatalogHolder = loader.load();
			} catch (Exception e) {
				System.out.println("CANNOT LOAD DATABASE CATALOG");
			}
		}
		if (technologycatalogHolder==null) {
			Technologycatalog loader = new Technologycatalog("catalog/technology-catalog.xml");
			try {
				technologycatalogHolder = loader.load();
			} catch (Exception e) {
				System.out.println("CANNOT LOAD TECHNOLOGY CATALOG");
			}
		}		
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ConsoleSample().setVisible(true);
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
	
	
	
	
	
	

}
