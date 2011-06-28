package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Table;

public class GlobalConvention extends Convention {

	public final static String ELIMINATE_NO_SELECTED_ENTITY_FOREIGN_KEY ="eliminate-no-selected-entity-foreign-key";

	@Override
	public void apply(BusinessModel model) {
		if (ELIMINATE_NO_SELECTED_ENTITY_FOREIGN_KEY.equals(type))
			for (Table table : getEntityNotInPackage(model)) {
				apply (table);
			}
	}

	private List<Table> getEntityNotInPackage(BusinessModel model) {
		List<Table> entities = new ArrayList<Table>();
		for (Table table : model.getModel().getDataModel().getDatabase().getTables()) {
			if (model.getBusinessPackage()!=null) {
				for (Table t : model.getBusinessPackage().getTables()) {
					if (t.getName().equals(table.getName()))
						entities.add (table);
				}
			}
		}
	
		return entities;
	}

	private void apply(Table table) {
		for (ForeignKey fk : table.getForeignKeys())
			fk=null;
	}

}
