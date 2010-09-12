package net.sf.minuteProject.console;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

import net.sf.minuteProject.console.panel.ModelAccessPanel;
import net.sf.minuteProject.console.panel.ModelCommonPanel;
import net.sf.minuteProject.console.panel.TargetPanel;

/**
 * 
 * @author florian
 */
public class Console extends JFrame {

	private ModelAccessPanel modelAccessPanel;
	private ModelCommonPanel modelCommonPanel;
	private TargetPanel targetPanel;

	/** Creates new form TestFrame */
	public Console() {
		initComponents();
	}

	private void initComponents() {
//		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(0,1));
		modelAccessPanel = new ModelAccessPanel();
		modelCommonPanel = new ModelCommonPanel();
		targetPanel = new TargetPanel();

		System.out.println("size modelAccessPanel "+modelAccessPanel.getSize().width);
		System.out.println("size modelCommonPanel "+modelCommonPanel.getSize().width);
		System.out.println("size targetPanel "+targetPanel.getSize().width);

		getContentPane().add(modelAccessPanel);
		getContentPane().add(modelCommonPanel);
		add(targetPanel);

		pack();
	}// </editor-fold>

	/**
	 * @param args
	 *           the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Console().setVisible(true);
			}
		});
	}

}
