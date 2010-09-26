package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.createCombo;
import static net.sf.minuteProject.console.utils.UIUtils.createLabel;
import static net.sf.minuteProject.console.utils.UIUtils.createTextField;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

@SuppressWarnings("serial")
public class TargetPanel extends JPanel implements FillBasicConfiguration{

	private static Logger logger = Logger.getLogger(TargetPanel.class);
	private Form f;
	private JButton button;
	public static String targetL = "choose target";
	private ConsolePanel consolePanel;
	private ConsoleSample consoleSample;
	private JComboBox targetCb;
	private List<Technology> technologies;
	
//	public TargetPanel(TechnologycatalogHolder technologycatalogHolder) {
//		technologies = technologycatalogHolder.getTechnologyCatalog().getTechnologiess();
//	}
	
	public TargetPanel(ConsoleSample consoleSample) {
		this.consoleSample = consoleSample;
		technologies = consoleSample.getTechnologycatalogHolder().getTechnologyCatalog().getTechnologiess();
		
	}	

	private JButton getGenerateButton() {
		button = new JButton("Generate");
		button.addActionListener(new ClickListener());
		return button;
	}

	private class ClickListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button) {
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
		}
	}

	public void fill(BasicIntegrationConfiguration bic) {
		bic.setTargetTechnology(getTargetTechnology());
	}

	private String getTargetTechnology() {
		String technologyName = targetCb.getSelectedItem().toString();
		return getTechnology(technologyName).getTemplateConfigFileName();
	}

	public void fillTargetPanel (JPanel panel) {
		panel.add(createLabel(targetL), "skip");
		targetCb = createCombo(getTechnologyNames());
		panel.add(targetCb);		
		panel.add(getGenerateButton());
	}

	private String[] getTechnologyNames() {
		List<String> list = new ArrayList<String>();
		for (Technology technology : technologies) {
			list.add(technology.getName());
		}
		return (String[])list.toArray(new String[list.size()]);
	}
	
	private Technology getTechnology(String name) {
		for (Technology technology : technologies) {
			if (technology.getName().equals(name))
				return technology;
		}		
		return null;
	}
}
