package net.sf.minuteProject.console.panel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.sf.minuteProject.application.ModelViewGenerator;
import net.sf.minuteProject.console.component.form.Form;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import static net.sf.minuteProject.console.utils.UIUtils.*;

@SuppressWarnings("serial")
public class ModelAccessPanel extends JPanel implements FillBasicConfiguration{

    private Form f;
    public static final String url = "url";
    public static final String username = "username";
    public static final String password = "password";
    public static final String schema = "schema";
    public static final String database_type = "database type";
    
    private JComboBox databaseCb;
    private JTextField urlTf, usernameTf, passwordTf, schemaTf, driverClassNameTf;
    
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
		bic.setUrl(urlTf.getText());
		bic.setUsername(usernameTf.getText());
		bic.setPassword(passwordTf.getText());
		bic.setSchema(schemaTf.getText());
		bic.setDatabase(databaseCb.getSelectedItem().toString());
		bic.setDriver(driverClassNameTf.getText());
		
		
//		bic.setUrl(f.getTextAt(url));
//		bic.setUsername(f.getTextAt(username));
//		bic.setPassword(f.getTextAt(password));
//		bic.setSchema(f.getTextAt(schema));
//		bic.setDatabase(f.getTextAt(database_type));
	}
	
	public void fillModelAccessPanel (JPanel panel) {
		panel.add(createLabel(url),   "skip");
		urlTf = createTextField("");
		panel.add(urlTf,      "span, growx");
		
		panel.add(createLabel(username),   "skip");
		usernameTf = createTextField("");
		panel.add(usernameTf,      "span, growx");
		
		panel.add(createLabel(password),   "skip");
		passwordTf = createTextField("");
		panel.add(passwordTf,      "span, growx");		
		
		panel.add(createLabel(schema),  "skip");
		schemaTf = createTextField(15);
		panel.add(schemaTf,      "wrap");
		
		panel.add(createLabel(database_type), "skip");
		databaseCb = createCombo(new String[] {"oracle", "db2", "mysql", "hsqldb"}, new DatabaseChangeListener());
		panel.add(databaseCb);
		panel.add(createLabel("driver"),  "center");
		driverClassNameTf = createTextField(25);
		panel.add(driverClassNameTf,   "wrap para");	

	}

	private class DatabaseChangeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == databaseCb) {
				String databaseType = databaseCb.getSelectedItem().toString();
				if (databaseType.equals("oracle")) {
					driverClassNameTf.setText("oracle.jdbc.OracleDriver");
				} else if (databaseType.equals("mysql")) {
					driverClassNameTf.setText("org.gjt.mm.mysql.Driver");
				} else if (databaseType.equals("hsqldb")) {
					driverClassNameTf.setText("org.hsqldb.jdbcDriver");
				}
				//org.hsqldb.jdbcDriver
					
			}
		}
	}
}
