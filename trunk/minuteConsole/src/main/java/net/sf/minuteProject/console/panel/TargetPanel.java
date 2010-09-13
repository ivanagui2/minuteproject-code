package net.sf.minuteProject.console.panel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

@SuppressWarnings("serial")
public class TargetPanel extends JPanel implements FillBasicConfiguration{

	private Form f;
	private JButton button;
	public static String target = "choose target";
	private ConsolePanel consolePanel;
	
	public TargetPanel(ConsolePanel consolePanel) {
		this.consolePanel = consolePanel;
		f = new Form("Target part");
		f.add(target, new String[] { "spring/hibernate", "roo", "openxava" });
		f.add(getGenerateButton());
		//f.synchronZones();
		setLayout(new FlowLayout());
		setAlignmentX(Component.LEFT_ALIGNMENT);
		add(f);
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
				consolePanel.fill(bic);

				System.out.println(bic);

			}
		}
	}

	public void fill(BasicIntegrationConfiguration bic) {
		bic.setTarget(f.getTextAt(target));
	}

}
