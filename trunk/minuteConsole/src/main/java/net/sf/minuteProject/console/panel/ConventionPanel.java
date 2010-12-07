package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.createLabel;
import static net.sf.minuteProject.console.utils.UIUtils.createTextField;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolTip;

import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

public class ConventionPanel extends JPanel implements FillBasicConfiguration{

	private JCheckBox virtualPkForViewsConvention;
	private JTextField virtualPkForViewsConventionListTf;
	
	public void fill(BasicIntegrationConfiguration bic) {
		if (virtualPkForViewsConvention.isSelected())
			bic.setVirtualPrimaryKey(virtualPkForViewsConvention.getText());	
	}

	public void fillPanel (JPanel panel) {
		virtualPkForViewsConvention = new JCheckBox("add 'virtual' primary key columns");
		virtualPkForViewsConvention.setToolTipText("When no primary key is provided for an entity, a default one is provided from the ones retrieved in the list");
		virtualPkForViewsConvention.setSelected(true);
		virtualPkForViewsConvention.setOpaque(true);
		panel.add(createLabel(""));
		panel.add(virtualPkForViewsConvention, "skip");

		virtualPkForViewsConventionListTf = createTextField("");
		panel.add(virtualPkForViewsConventionListTf,"growx, span, wrap para");
		
	}
}
