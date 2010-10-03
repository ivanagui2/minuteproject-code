package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.*;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import net.sf.minuteProject.application.AbstractGenerator;
import net.sf.minuteProject.application.ModelViewGenerator;
import net.sf.minuteProject.console.ConsoleSample;
import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;
import net.sf.minuteProject.utils.catalog.TechnologyCatalogUtils;

@SuppressWarnings("serial")
public class TargetPanel extends JPanel implements FillBasicConfiguration{

	private static Logger logger = Logger.getLogger(TargetPanel.class);
	private JButton generateButton, detailButton;
	public static String targetL = "choose target";
	public static String statusL = "status";
	public static String descriptionL = "description";
	public static String showDetailL = "Show details";
	public static String hideDetailL = "Hide details";
	private boolean showDetail;
	private ConsoleSample consoleSample;
	private JComboBox targetCb;
	private JLabel statusJL, statusDetailJL, descriptionJL;
	private JScrollPane descriptionJSP;
	private JPanel panel, detailPanel;
//	private List<Technology> technologies;
	
	public TargetPanel(ConsoleSample consoleSample) {
		this.consoleSample = consoleSample;
		statusJL = createLabel(statusL);
		descriptionJL = createLabel(descriptionL);
//		technologies = TechnologyCatalogUtils.getPublishedTechnologies();
	}	

	private JButton getGenerateButton() {
		generateButton = new JButton("Generate");
		generateButton.addActionListener(new ClickListener());
		return generateButton;
	}

	private JButton getDetailButton() { 
		detailButton = new JButton(showDetailL);
		detailButton.addActionListener(new ClickListener());
		return detailButton;
	}
	
	private class ClickListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == generateButton) {
				BasicIntegrationConfiguration bic = new BasicIntegrationConfiguration();
				consoleSample.fill(bic);

				System.out.println(bic);
				
				ModelViewGenerator mvg = new ModelViewGenerator(bic);
				try {
					mvg.generate();
				} catch (MinuteProjectException mpe) {
					logger.info("error generating : "+mpe.getError());
				}
			}
			else if (e.getSource() == detailButton) {
				updateDetails(panel);
//				if (detailButton.getText().equals(showDetailL)) {
//					detailButton.setText(hideDetailL);
//					showDetails(panel);
//				} else {
//					detailButton.setText(showDetailL);
//					hideDetails(panel);
//				}
			}
		}
	}

	private class TargetNameListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			consoleSample.getModelCommonPanel().rebuildDefaultTargetDir();
			updateDetails(panel);
		}
	}
	
	public void fill(BasicIntegrationConfiguration bic) {
		bic.setTargetTechnology(getTargetTechnology());
	}

	public String getTargetTechnology() {
		if (targetCb==null || targetCb.getSelectedItem()==null)
			return getTechnologyNames()[0];
		return targetCb.getSelectedItem().toString();
//		return getTechnology(technologyName).getTemplateConfigFileName();
	}

	public void fillTargetPanel (JPanel panel) {
		this.panel = panel;
		panel.add(createLabel(targetL), "skip");
		targetCb = createCombo(getTechnologyNames(), new TargetNameListener());
		panel.add(targetCb);	
//		panel.add(getDetailButton(),"center");
		panel.add(getGenerateButton(), "wrap");
		showDetails(panel);
	}
	
//	private void showDetails (JPanel panel) {
//		detailPanel = new JPanel();
//		Technology technology = getChoosenTechnology();
//		detailPanel.add(statusJL, "skip");
//		statusDetailJL = createLabel(technology.getStatus());
//		detailPanel.add(statusDetailJL, "wrap");
//		detailPanel.add(descriptionJL, "skip");
//		descriptionJSP = createTextAreaScroll(technology.getDescription(), 10, 50, true, false);
//		detailPanel.add(descriptionJSP, "span, growx");	
//		panel.add(detailPanel);
//	}
//	
	private void showDetails (JPanel panel) {
		Technology technology = getChoosenTechnology();
		panel.add(statusJL, "skip");
		statusDetailJL = createLabel(technology.getStatus());
		panel.add(statusDetailJL, "wrap");
		panel.add(descriptionJL, "skip");
		descriptionJSP = createTextAreaScroll(technology.getDescription(), 5, 40, true, false);
		panel.add(descriptionJSP, "span, growx");	
	}	
	
	private void updateDetails (JPanel panel) {
		Technology technology = getChoosenTechnology();
		statusDetailJL.setText(technology.getStatus());
		updateTextAreaScroll(descriptionJSP,technology.getDescription());
//		descriptionJSP.getViewport().removeAll();
//		descriptionJSP.getViewport().add(new JLabel(technology.getDescription()));
//		descriptionJSP.repaint();
	}
	
	private void hideDetails (JPanel panel) {
		panel.remove(detailPanel);
//		panel.remove(statusJL);
//		panel.remove(statusDetailJL);
//		panel.remove(descriptionJL);
//		panel.remove(descriptionJSP);	
//		dispo
//		repaint();
//		pack();
	}
	
	private Technology getChoosenTechnology() {
		return TechnologyCatalogUtils.getPublishedTechnology(targetCb.getSelectedItem().toString(), consoleSample.getCatalogDir());
	}	

	private String[] getTechnologyNames() {
		return TechnologyCatalogUtils.getPublishedTechnologyNames(consoleSample.getCatalogDir());
	}
	
}
