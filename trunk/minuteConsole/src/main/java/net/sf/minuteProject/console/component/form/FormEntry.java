package net.sf.minuteProject.console.component.form;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class FormEntry extends JPanel {
	private JTextComponent text;
	private JComboBox combo;
	private JLabel label;

	public FormEntry(String l, int t) {
		((FlowLayout) getLayout()).setAlignment(0);
		add(label = new JLabel(l));
		add(text = new JTextField(t));
	}

	public FormEntry(String l, String[] array) {
		((FlowLayout) getLayout()).setAlignment(0);
		add(label = new JLabel(l));
		add(combo = new JComboBox(new DefaultComboBoxModel(array)));
	}

	public String getText() {
		return text.getText();
	}

	public void settext(String s) {
		text.setText(s);
	}

	public String getLabelText() {
		return label.getText();
	}

	public void setLabelText(String s) {
		label.setText(s);
	}

	public void setlabel(JLabel l) {
		remove(label);
		add(label = l, 0);
	}

	public void setTextComponent(JTextComponent t) {
		remove(text);
		add(text = t);
	}

	public void setLabelPreferedSize(Dimension d) {
		label.setPreferredSize(d);
	}

}