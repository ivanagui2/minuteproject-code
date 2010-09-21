package net.sf.minuteProject.console.utils;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.layout.PlatformDefaults;

public class UIUtils {

	static final Color LABEL_COLOR = new Color(0, 70, 213);
	public static void addSeparator(JPanel panel, String text)
	{
		JLabel l = createLabel(text);
		l.setForeground(LABEL_COLOR);

		panel.add(l, "gapbottom 1, span, split 2, aligny center");
		panel.add(configureActiveComponent(new JSeparator()), "gapleft rel, growx");
	}	
	public static JLabel createLabel(String text)
	{
		return createLabel(text, SwingConstants.LEADING);
	}	
	public static JLabel createLabel(String text, int align)
	{
		final JLabel b = new JLabel(text, align);
		configureActiveComponent(b);
		return b;
	}	
	public static JComponent configureActiveComponent(JComponent c)
	{
//		if (benchRuns == 0) {
//			c.addMouseMotionListener(toolTipListener);
//			c.addMouseListener(constraintListener);
//		}
		return c;
	}	
	public static JPanel createTabPanel(LayoutManager lm)
	{
		JPanel panel = new JPanel(lm);
		configureActiveComponent(panel);
		panel.setOpaque(false);
		return panel;
	}

	public static JTextField createTextField(int cols)
	{
		return createTextField("", cols);
	}

	public static JTextField createTextField(String text)
	{
		return createTextField(text, 0);
	}

	public static JTextField createTextField(String text, int cols)
	{
		final JTextField b = new JTextField(text, cols);

		configureActiveComponent(b);

		return b;
	}
	public static JComboBox createCombo(String[] items)
	{
		JComboBox combo = new JComboBox(items);

		if (PlatformDefaults.getCurrentPlatform() == PlatformDefaults.MAC_OSX)
			combo.setOpaque(false);

		return combo;
	}	
	
	public static JButton createButton(String text, ActionListener actionListener)
	{
		JButton b = new JButton(text);
		if (actionListener!=null)
			b.addActionListener(actionListener);
		return b;
	}	
}
