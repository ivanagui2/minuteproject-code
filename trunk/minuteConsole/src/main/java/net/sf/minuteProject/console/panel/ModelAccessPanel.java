package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.createCombo;
import static net.sf.minuteProject.console.utils.UIUtils.createLabel;
import static net.sf.minuteProject.console.utils.UIUtils.createTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.catalog.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.loader.catalog.databasecatalog.node.Database;

@SuppressWarnings("serial")
public class ModelAccessPanel extends JPanel implements FillBasicConfiguration{

    private List<Database> databases;
    private List<String> databaseNames;
    public static final String url = "url";
    public static final String username = "username";
    public static final String password = "password";
    public static final String schema = "schema";
    public static final String database_type = "database type";
    
    private JLabel schemaL;
    private JComboBox databaseCb;
    private JTextField urlTf, usernameTf, passwordTf, schemaTf, driverClassNameTf;
    
    public ModelAccessPanel(DatabasecatalogHolder databasecatalogHolder) {
//   	 this.databasecatalogHolder = databasecatalogHolder;
   	   databases = databasecatalogHolder.getDatabaseCatalog().getDatabasess();
 	   databaseNames = new ArrayList<String>();
	   for (Database database : databases) {
		  databaseNames.add(database.getName());
	   }
	}

	public void fill(BasicIntegrationConfiguration bic) {
		bic.setUrl(urlTf.getText());
		bic.setUsername(usernameTf.getText());
		bic.setPassword(passwordTf.getText());
		bic.setSchema(schemaTf.getText());
		bic.setDatabase(databaseCb.getSelectedItem().toString());
		bic.setDriver(driverClassNameTf.getText());
	}
	
	public void fillModelAccessPanel (JPanel panel) {
		panel.add(createLabel(database_type), "skip");
		databaseCb = createCombo(getTechnologies(), new DatabaseChangeListener());
		panel.add(databaseCb);
		panel.add(createLabel("driver"),  "center");
		driverClassNameTf = createTextField(25);
		driverClassNameTf.setText(getDefaultDriverClassNameTf());
		panel.add(driverClassNameTf,   "span, growx");	
		
		panel.add(createLabel(url),   "skip");
		urlTf = createTextField("");
		panel.add(urlTf,      "span, growx");
		
		panel.add(createLabel(username),   "skip");
		usernameTf = createTextField("");
		usernameTf.addFocusListener(new UserNameListener());
		
		panel.add(usernameTf);
		
		panel.add(createLabel(password),   "center");
		passwordTf = createTextField("");
		panel.add(passwordTf,      "wrap");		
		
		schemaL = createLabel(schema);
		schemaL.setVisible(isSchemaVisible());
		panel.add(schemaL,  "skip");
		schemaTf = createTextField(15);
		schemaTf.setVisible(isSchemaVisible());
		panel.add(schemaTf,      "wrap para");
	}

	private boolean isSchemaVisible() {
		return getDefaultDatabase().getUseSchema();
	}

	private Database getDefaultDatabase() {
		return databases.get(0);
	}
	
	private Database getCurrentDatabase() {
		String databaseType = databaseCb.getSelectedItem().toString();
		return getDatabase(databaseType);
	}

	private String getDefaultDriverClassNameTf() {
		return databases.get(0).getDriverclassname();
	}

	private String[] getTechnologies() {
		return (String[])databaseNames.toArray(new String[databaseNames.size()]);
	}

	private class UserNameListener implements FocusListener {

		public void focusLost(FocusEvent arg0) {
			if (getCurrentDatabase().useSchema() && schemaTf.getText().equals("")) {
				schemaTf.setText(usernameTf.getText());
			}
		}
		
		public void focusGained(FocusEvent arg0) {
		}
	}
	
	private class DatabaseChangeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == databaseCb) {
				Database database = getCurrentDatabase();
				driverClassNameTf.setText(database.getDriverclassname());
				if (database.getUseSchema())
					applySchema();
				else 
					removeSchema();
					
			}
		}
	}
	
	private void applySchema() {
		schemaL.setVisible(true);
		schemaTf.setVisible(true);
	}
	
	private void removeSchema() {
		schemaL.setVisible(false);
		schemaTf.setVisible(false);
	}
	
	private String getDriver(String databaseType) {
		return getDatabase(databaseType).getDriverclassname();
	}

	private Database getDatabase(String databaseType) {
		for (Database database : databases) {
			if (database.getName().equals(databaseType))
				return database;
		}
		return null;
	}	
}
