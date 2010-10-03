package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.*;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.minuteProject.console.ConsoleSample;
import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.catalog.databasecatalog.node.Database;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.utils.code.RestrictedCodeUtils;

@SuppressWarnings("serial")
public class ModelCommonPanel extends JPanel implements FillBasicConfiguration {

//	private Form f;
	public static final String root_package = "root package";
	public static final String model_name = "model name";
	public static final String primary_key_policy = "primary key policy";
	private ConsoleSample consoleSample;
	
	private boolean isTargetDirTouched = false;
	private JTextField rootPackageTf, modelNameTf, targetDirTf;
	private JComboBox pkPolicyCb;
	
	public ModelCommonPanel(ConsoleSample consoleSample) {
		this.consoleSample = consoleSample;
	}

	public void fill(BasicIntegrationConfiguration bic) {
		bic.setRootpackage(rootPackageTf.getText());
		bic.setModelName(modelNameTf.getText());
		bic.setPrimaryKeyPolicy(pkPolicyCb.getSelectedItem().toString());
		bic.setOutputDir(targetDirTf.getText());
		
//		bic.setRootpackage(f.getTextAt(root_package));
//		bic.setModelName(f.getTextAt(model_name));
//		bic.setPrimaryKeyPolicy(f.getTextAt(primary_key_policy));		
	}
	
	public void fillModelCommonPanel (JPanel panel) {
		panel.add(createLabel(root_package),   "skip");
		rootPackageTf = createTextField("");
		panel.add(rootPackageTf,      "span, wrap");
		
		panel.add(createLabel(model_name),   "skip");
		modelNameTf = createTextField("",new ModelNameListener());
		panel.add(modelNameTf,      "span, growx");
		
		panel.add(createLabel(primary_key_policy),   "skip");
		pkPolicyCb = createCombo(new String[] {"sequence", "autoincrement"});
		panel.add(pkPolicyCb, "wrap");
		
		panel.add(createLabel("target dir"),   "skip");
	    targetDirTf = createTextField(getDefaultTargetDir(), new TargetDirListener());
		panel.add(targetDirTf,      "span, growx, wrap para");		
	}

	private class TargetDirListener implements FocusListener {

		String value, previousValue;
		public void focusLost(FocusEvent arg0) {
			value = targetDirTf.getText();
			if (previousValue!=null && value!=null) {
				if (!isTargetDirTouched)
					isTargetDirTouched=!(previousValue.equals(value));
			}
		}
		
		public void focusGained(FocusEvent arg0) {
			previousValue = targetDirTf.getText();
		}
	}
	
	private class ModelNameListener implements FocusListener {

		public void focusLost(FocusEvent arg0) {
			rebuildDefaultTargetDir();
		}
		
		public void focusGained(FocusEvent arg0) {
		}
	}
	
	public void rebuildDefaultTargetDir() {
		if (!isTargetDirTouched) {
			targetDirTf.setText(getDefaultTargetDir());
		}
	}

	private String getDefaultTargetDir() {
		StringBuffer sb = new StringBuffer ("../output");
		String formattedModelName = getFormattedModelName();
		if (formattedModelName!=null)
			sb.append("/"+formattedModelName);
		String formattedTargetName = getFormattedTargetName();
		if (formattedTargetName!=null)
			sb.append("/"+formattedTargetName);		
		return sb.toString();
	}

	private String getFormattedTargetName() {
		return getFormattedName(getTechnologyName());
	}

	private String getTechnologyName() {
		return consoleSample.getTargetPanel().getTargetTechnology();
	}

	private String getFormattedModelName() {
		return getFormattedName(modelNameTf.getText());
	}
	private String getFormattedName(String name) {
		if (name==null || name.trim().equals(""))
			return null;
		return getJavaFormattedName(name);
	}

	private String getJavaFormattedName(String text) {
		return RestrictedCodeUtils.convertToValidJava(text);
	}

	public void applyCurrentPrimaryKeyPolicy(Database database) {
		pkPolicyCb.setSelectedItem(database.getPrimaryKeyPolicy().getType());
		
	}	
	
	
	
}
