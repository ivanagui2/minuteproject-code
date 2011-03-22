package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.plugin.format.I18nUtils;

public class ReferenceNamingConvention extends Convention {

	public final String APPLY_REFERENCED_ALIAS_WHEN_NO_AMBIGUITY="apply-referenced-alias-when-no-ambiguity";
	
	private boolean isToPlurialize;
	
	@Override
	public void apply(BusinessModel model) {
		if (APPLY_REFERENCED_ALIAS_WHEN_NO_AMBIGUITY.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		}
	}

	private void apply(Table table) {
		List<Reference> fks = getApplicableReference(table);
		for (Reference reference : fks) {
			reference.setAlias(getFinalName(table));
		}
	}

	private String getFinalName(Table table) {
		return (isToPlurialize)?table.getAlias():I18nUtils.plurialize(table.getAlias());
	}

	private List<Reference> getApplicableReference(Table table) {
		List<Reference> list = new ArrayList<Reference>();
		for (Reference reference : table.getChildren()) {
			list.add(reference);
		}		
		return list;
	}

	public boolean isToPlurialize() {
		return isToPlurialize;
	}

	public void setToPlurialize(boolean isToPlurialize) {
		this.isToPlurialize = isToPlurialize;
	}

}
