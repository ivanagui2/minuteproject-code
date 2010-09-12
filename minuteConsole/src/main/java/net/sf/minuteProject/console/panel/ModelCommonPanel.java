package net.sf.minuteProject.console.panel;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import net.sf.minuteProject.console.component.form.Form;

@SuppressWarnings("serial")
public class ModelCommonPanel extends JPanel {

   private Form f;
   
   public ModelCommonPanel() {
  	f = new Form("Model common part");
    	f.add("root package",50);
   	f.add("model name",15);
   	f.add("primary key policy",new String[] {"sequence", "autoincrement"});
   	f.synchronZones();
   	setLayout(new FlowLayout());
   	setSize(400, 400);
   	add(f);
	}
}
