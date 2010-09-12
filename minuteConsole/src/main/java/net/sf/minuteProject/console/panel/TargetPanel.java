package net.sf.minuteProject.console.panel;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import net.sf.minuteProject.console.component.form.Form;

@SuppressWarnings("serial")
public class TargetPanel extends JPanel {

   private Form f;
   
   public TargetPanel() {
   	f = new Form("Target part");
   	f.add("choose target",new String[] {"spring/hibernate", "roo", "openxava"});
   	f.synchronZones();
   	setLayout(new FlowLayout());
   	setSize(400, 400);
   	add(f);
	}
}
