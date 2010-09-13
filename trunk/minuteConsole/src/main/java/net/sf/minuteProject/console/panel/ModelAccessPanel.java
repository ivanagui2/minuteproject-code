package net.sf.minuteProject.console.panel;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.*;

import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

@SuppressWarnings("serial")
public class ModelAccessPanel extends JPanel implements FillBasicConfiguration{

    private Form f;
    public static final String url = "url";
    public static final String username = "username";
    public static final String password = "password";
    public static final String schema = "schema";
    public static final String database_type = "database type";
    
    
    public ModelAccessPanel() {
   	f = new Form("Model Access");
     	f.add(url,50);
    	f.add(username,15);
    	f.add(password,15);
    	f.add(schema,15);
    	f.add(database_type,new String[] {"oracle", "db2", "mysql", "hsqldb"});
    	f.synchronZones();
    	setLayout(new FlowLayout());  	
    	add(f);
	}

	public void fill(BasicIntegrationConfiguration bic) {
		bic.setUrl(f.getTextAt(url));
		bic.setUsername(f.getTextAt(username));
		bic.setPassword(f.getTextAt(password));
		bic.setSchema(f.getTextAt(schema));
		bic.setDatabase(f.getTextAt(database_type));
	}

}
