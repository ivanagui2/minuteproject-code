package net.sf.minuteProject.console.panel;

import java.awt.FlowLayout;

import javax.swing.*;

import net.sf.minuteProject.console.component.form.Form;

@SuppressWarnings("serial")
public class ModelAccessPanel extends JPanel {

    private Form f;
    
    public ModelAccessPanel() {
   	f = new Form("Model Access");
     	f.add("url",50);
    	f.add("username",15);
    	f.add("password",15);
    	f.add("schema",15);
    	f.add("database type",new String[] {"oracle", "db2", "mysql", "hsqldb"});
    	f.synchronZones();
    	setLayout(new FlowLayout());
    	setSize(400, 400);
    	add(f);
	}

}
