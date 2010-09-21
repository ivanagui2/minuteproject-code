package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.createCombo;
import static net.sf.minuteProject.console.utils.UIUtils.createLabel;
import static net.sf.minuteProject.console.utils.UIUtils.createTextField;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.minuteProject.application.ModelViewGenerator;
import net.sf.minuteProject.console.ConsoleSample;
import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

@SuppressWarnings("serial")
public class TargetPanel extends JPanel implements FillBasicConfiguration{

	private Form f;
	private JButton button;
	public static String targetL = "choose target";
	private ConsolePanel consolePanel;
	private ConsoleSample consoleSample;
	private JComboBox targetCb;
	
	public TargetPanel(ConsolePanel consolePanel) {
		this.consolePanel = consolePanel;
		f = new Form("Target part");
		f.add(targetL, new String[] { "spring/hibernate", "roo", "openxava" });
		f.add(getGenerateButton());
		//f.synchronZones();
		setLayout(new FlowLayout());
		setAlignmentX(Component.LEFT_ALIGNMENT);
		add(f);
	}
	
	public TargetPanel(ConsoleSample consoleSample) {
		this.consoleSample = consoleSample;
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

			}
		}
	}

	public void fill(BasicIntegrationConfiguration bic) {
//		bic.setTarget(f.getTextAt(target));
		bic.setTarget(targetCb.getSelectedItem().toString());
	}

	public void fillTargetPanel (JPanel panel) {
		panel.add(createLabel(targetL), "skip");
		targetCb = createCombo(new String[] { "spring/hibernate", "roo", "openxava" });
		panel.add(targetCb);		
		panel.add(getGenerateButton());
	}
}
