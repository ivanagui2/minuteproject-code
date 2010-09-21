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
import static net.sf.minuteProject.console.utils.UIUtils.*;
import static net.sf.minuteProject.console.panel.ModelAccessPanel.*;
import static net.sf.minuteProject.console.panel.ModelCommonPanel.*;

public class ConsoleSample extends JFrame{

	private JTabbedPane tabbedPane;
	private ModelAccessPanel modelAccessPanel;
	private ModelCommonPanel modelCommonPanel;
	private TargetPanel targetPanel;
	
	private void initComponents() {
		tabbedPane = new JTabbedPane();
		
		modelAccessPanel = new ModelAccessPanel();
		modelCommonPanel = new ModelCommonPanel();
		targetPanel = new TargetPanel(this);

		MigLayout lm = new MigLayout("ins 20", "[para]0[][100lp, fill][60lp][95lp, fill]", "");
		JPanel panel = createTabPanel(lm);

		addSeparator(panel, "Model Access");

		modelAccessPanel.fillModelAccessPanel(panel);


		addSeparator(panel, "Common configuration");

		modelCommonPanel.fillModelCommonPanel (panel);

		addSeparator(panel, "Target technology");

		targetPanel.fillTargetPanel(panel);

//		if (debug)
//			panel.add(createLabel("Red is cell bounds. Blue is component bounds."), "newline,ax left,span,gaptop 40,");

		tabbedPane.addTab("MinuteProject console", panel);	
		getContentPane().add(tabbedPane);
		pack();		
	}
	
	public ConsoleSample() {
		initComponents();
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
	
	
	
	
	
	

}
