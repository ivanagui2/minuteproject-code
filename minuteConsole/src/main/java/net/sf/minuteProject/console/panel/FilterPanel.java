package net.sf.minuteProject.console.panel;

import static net.sf.minuteProject.console.utils.UIUtils.createLabel;
import static net.sf.minuteProject.console.utils.UIUtils.createTextField;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.sf.minuteProject.configuration.bean.GenerationCondition;
import net.sf.minuteProject.console.face.FillBasicConfiguration;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

public class FilterPanel extends JPanel implements FillBasicConfiguration{

	private JTextField 
    	filterExcludeTf,
    	filterIncludeTf;
	private FilterItemListener filterItemListener;
	private ButtonGroup group;
	
	public static final String filterExclude = "filterExclude";
	public static final String filterInclude = "filterInclude";
	public FilterPanel() {
		filterItemListener = new FilterItemListener();
	}
	public void fill(BasicIntegrationConfiguration bic) {
		String command = group.getSelection().getActionCommand();
		if (filterExclude.equals(command)) {
			bic.setFilterFile(filterExcludeTf.getText());
			bic.setFilterFileType (GenerationCondition.FILTER_FILE_TYPE_EXCLUDE);
		}
		else {
			bic.setFilterFile(filterIncludeTf.getText());
			bic.setFilterFileType (GenerationCondition.FILTER_FILE_TYPE_INCLUDE);
		}
	}
	

	public void fillPanel (JPanel panel) {
		panel.add(createLabel("entities"),"skip,wrap");
		panel.add(createLabel(""));
	    JRadioButton filterIncludeButton = new JRadioButton("includes all entities except those starting with ");
	    filterIncludeButton.setActionCommand(filterInclude);
	    filterIncludeButton.setSelected(true);
	    filterIncludeButton.addItemListener(filterItemListener);
		panel.add(filterIncludeButton, "skip");
		filterIncludeTf = createTextField("", 10);
		panel.add(filterIncludeTf,"span, growx, wrap");
		
		panel.add(createLabel(""));
	    JRadioButton filterExcludeButton = new JRadioButton("excludes all entities except those starting with ");
	    filterExcludeButton.setActionCommand(filterExclude);
	    filterExcludeButton.addItemListener(filterItemListener);
		panel.add(filterExcludeButton, "skip");
		filterExcludeTf = createTextField("", 10);
		filterExcludeTf.setOpaque(true);
		filterExcludeTf.setEditable(false);
		panel.add(filterExcludeTf,"span, growx, wrap para");
		
		group = new ButtonGroup();
	    group.add(filterIncludeButton);
	    group.add(filterExcludeButton);		
	}
	
    private class FilterItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent itemEvent) {
        	JRadioButton aButton = (JRadioButton)itemEvent.getSource();
           int state = itemEvent.getStateChange();
           if (state == ItemEvent.SELECTED) {
        	  activate (aButton);
            }
        }

    }	
    
	private void activate(JRadioButton aButton) {
		if (aButton.getActionCommand()==filterExclude) {
			filterExcludeTf.setOpaque(false);
			filterExcludeTf.setEditable(true);			
			filterIncludeTf.setOpaque(true);
			filterIncludeTf.setEditable(false);		
//			filterIncludeTf.setText("");
		} else {
			filterExcludeTf.setOpaque(true);
			filterExcludeTf.setEditable(false);			
			filterIncludeTf.setOpaque(false);
			filterIncludeTf.setEditable(true);	
//			filterExcludeTf.setText("");
		}
	}

}
