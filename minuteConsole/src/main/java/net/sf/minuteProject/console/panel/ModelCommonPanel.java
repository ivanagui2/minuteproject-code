package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.*;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

@SuppressWarnings("serial")
public class ModelCommonPanel extends JPanel implements FillBasicConfiguration {

	private Form f;
	public static final String root_package = "root package";
	public static final String model_name = "model name";
	public static final String primary_key_policy = "primary key policy";

	private JTextField rootPackageTf, modelNameTf, targetDirTf;
	private JComboBox pkPolicyCb;
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
		bic.setRootpackage(rootPackageTf.getText());
		bic.setModelName(modelNameTf.getText());
		bic.setPrimaryKeyPolicy(pkPolicyCb.getSelectedItem().toString());
		bic.setTargetDir(targetDirTf.getText());
		
//		bic.setRootpackage(f.getTextAt(root_package));
//		bic.setModelName(f.getTextAt(model_name));
		bic.setPrimaryKeyPolicy(f.getTextAt(primary_key_policy));		
	}
	
	public void fillModelCommonPanel (JPanel panel) {
		panel.add(createLabel(root_package),   "skip");
		rootPackageTf = createTextField("");
		panel.add(rootPackageTf,      "span, growx");
		
		panel.add(createLabel(model_name),   "skip");
		modelNameTf = createTextField("");
		panel.add(modelNameTf,      "span, growx");
		
		panel.add(createLabel(primary_key_policy),   "skip");
		pkPolicyCb = createCombo(new String[] {"sequence", "autoincrement"});
		panel.add(pkPolicyCb, "wrap");
		
		panel.add(createLabel("target dir"),   "skip");
	    targetDirTf = createTextField("");
		panel.add(targetDirTf,      "span, growx, wrap para");		
	}	
	
	
	
}
