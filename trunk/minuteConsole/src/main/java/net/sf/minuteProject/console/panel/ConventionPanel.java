package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.createLabel;
import static net.sf.minuteProject.console.utils.UIUtils.createTextField;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

public class ConventionPanel extends JPanel implements FillBasicConfiguration{

	private JCheckBox virtualPkForViewsConvention;
	private JTextField virtualPkForViewsConventionListTf;
	public void fill(BasicIntegrationConfiguration bic) {
		// TODO Auto-generated method stub
		
	}

	public void fillPanel (JPanel panel) {
		virtualPkForViewsConvention = new JCheckBox("add 'virtual' primary key specified in list");
		virtualPkForViewsConvention.setSelected(true);
		virtualPkForViewsConvention.setOpaque(true);
		panel.add(createLabel(""));
		panel.add(virtualPkForViewsConvention, "skip");

		virtualPkForViewsConventionListTf = createTextField("");
		panel.add(virtualPkForViewsConventionListTf,"growx, span, wrap para");
		
	}
}
