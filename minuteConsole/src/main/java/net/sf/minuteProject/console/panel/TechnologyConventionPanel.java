package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.createLabel;
import static net.sf.minuteProject.console.utils.UIUtils.createTextField;

import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import net.sf.minuteProject.console.ConsoleSample;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Convention;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Conventions;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;

public class TechnologyConventionPanel extends JPanel implements FillBasicConfiguration{

	public static final String limitations = "limitations";
	public static final String conventions = "conventions";
	private ConsoleSample consoleSample;
	
	public TechnologyConventionPanel (ConsoleSample consoleSample) {
		this.consoleSample = consoleSample;
	}
	
	public void fill(BasicIntegrationConfiguration bic) {
	}

	private List<Convention> getTechConventions () {
		Technology technology = consoleSample.getTargetPanel().getChoosenTechnology();
		return getTechConventions (technology);
	}

	public List<Convention> getTechConventions (Technology technology) {
		Conventions conventions = technology.getConventions();
		return conventions.getConventions();
	}
	
	public void fillPanel (JPanel panel) {
		for (Convention convention : getTechConventions()) {
			addConventionToPanel (convention, panel);
		}
	}
	
	private void addConventionToPanel(Convention convention, JPanel panel) {
		JCheckBox box = new JCheckBox(convention.getDescription());
		box.setSelected(true);
		box.setOpaque(true);
		panel.add(createLabel(""));
		panel.add(box, "skip");	
		panel.add(createTextField(""),"growx, span, wrap para");		
	}

	public void rebuildPanel(JPanel panel) {
		this.removeAll();
		fillPanel(panel);
//		this.updateUI();
		this.repaint();
		panel.repaint();
	}


}
