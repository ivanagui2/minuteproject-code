package net.sf.minuteProject.console.component.form;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

public class Form extends JPanel {
	private String name;

	public Form(String n) {
		setBorder(BorderFactory.createTitledBorder(name = n));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public Form() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void add(String label, int textwidth) {
		add(new FormEntry(label, textwidth));
	}

	public void add(String label, String[] array) {
		add(new FormEntry(label, array));
	}
	
	public int synchronZones() {
		int max = 0;
		for (int i = 0; i < getComponentCount(); i++) {
			int l = ((FormEntry) this.getComponent(i)).getLabelText().length();
			max = max > l ? max : l;
		}
		for (int i = 0; i < getComponentCount(); i++)
			((FormEntry) this.getComponent(i))
					.setLabelPreferedSize(new Dimension(max * 7, 20));
		return max;
	}

	public void setTextCompenentAt(JTextComponent t, int i) {
		if (i < getComponentCount())
			((FormEntry) this.getComponent(i)).setTextComponent(t);
	}

	public void setLabelAt(JLabel l, int i) {
		if (i < getComponentCount())
			((FormEntry) this.getComponent(i)).setlabel(l);
	}

	public void setLabelTextAt(String s, int i) {
		if (i < getComponentCount())
			((FormEntry) this.getComponent(i)).setLabelText(s);
	}

	public String getLabelTextAt(int i) {
		return ((FormEntry) this.getComponent(i)).getLabelText();
	}

	public void setTextAt(String s, int i) {
		if (i < getComponentCount())
			((FormEntry) this.getComponent(i)).settext(s);
	}

	public String getTextAt(int i) {
		return ((FormEntry) this.getComponent(i)).getText();
	}

	public String getTextAt(String s) {
		for (int i = 0; i < getComponentCount(); i++)
			if (((FormEntry) this.getComponent(i)).getLabelText().equals(s))
				return getTextAt(i);
		return null;
	}

	public void setTextAt(String text, String labelName) {
		for (int i = 0; i < getComponentCount(); i++)
			if (((FormEntry) this.getComponent(i)).getLabelText().equals(
					labelName)) {
				((FormEntry) getComponent(i)).settext(text);
				return;
			}
	}

	public String getTextAt(JLabel l) {
		for (int i = 0; i < getComponentCount(); i++)
			if (((FormEntry) this.getComponent(i)).equals(l))
				return getTextAt(i);
		return null;
	}

	public void getTextAt(String text, JLabel l) {
		for (int i = 0; i < getComponentCount(); i++)
			if (((FormEntry) this.getComponent(i)).equals(l)) {
				((FormEntry) getComponent(i)).settext(text);
				return;
			}
	}

}
