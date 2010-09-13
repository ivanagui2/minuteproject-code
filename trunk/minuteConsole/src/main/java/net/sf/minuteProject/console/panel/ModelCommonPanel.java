package net.sf.minuteProject.console.panel;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

@SuppressWarnings("serial")
public class ModelCommonPanel extends JPanel implements FillBasicConfiguration {

	private Form f;
	public static final String root_package = "root package";
	public static final String model_name = "model name";
	public static final String primary_key_policy = "primary key policy";

	public ModelCommonPanel() {
		f = new Form("Model common part");
		f.add(root_package, 50);
		f.add(model_name, 15);
		f.add(primary_key_policy, new String[] { "sequence", "autoincrement" });
		f.synchronZones();
		setLayout(new FlowLayout());
		setAlignmentX(Component.LEFT_ALIGNMENT);
		add(f);
	}

	public void fill(BasicIntegrationConfiguration bic) {
		bic.setRootpackage(f.getTextAt(root_package));
		bic.setModelName(f.getTextAt(model_name));
		bic.setPrimaryKeyPolicy(f.getTextAt(primary_key_policy));
	}
}
