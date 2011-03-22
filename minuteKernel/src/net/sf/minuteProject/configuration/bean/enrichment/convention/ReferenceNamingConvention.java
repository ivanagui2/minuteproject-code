package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.utils.enrichment.EnrichmentUtils;

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
//		List<Reference> fks = getApplicableReferenceNotMany2Many(table);
//		for (Reference reference : fks) {
//			reference.setAlias(getFinalName(table));
//		}
		applyNotMany2ManyReference(table);
		applyMany2ManyReference(table);
	}

	private void applyMany2ManyReference (Table table) {
		List<Reference> list = new ArrayList<Reference>();
		for (Reference reference : EnrichmentUtils.getLinkedTargetReferenceByMany2Many(table)) {
			list.add(reference);
		}	
		for (Reference reference : EnrichmentUtils.getLinkedTargetReferenceByMany2Many(table)) {
			if (!reference.getForeignTable().isManyToMany()) {
				if (isNoAmbiguityReference(reference, list))
					reference.setAlias(getNameForUnambiguiousCaseAndMany2Many(table, reference));
				else
					reference.setAlias(getNameForAmbiguiousCaseAndMany2Many(table, reference));
			} 
		}	
//		
//		for (Reference ref : EnrichmentUtils.getLinkedTargetReferenceByMany2Many(table)) {
//			ref.setAlias(ref.getForeignTable().getAlias());
////			return
//		}
	}

	private void applyNotMany2ManyReference(Table table) {
		List<Reference> list = new ArrayList<Reference>();
		for (Reference reference : table.getChildren()) {
			list.add(reference);
		}	
		for (Reference reference : table.getChildren()) {
			if (!reference.getForeignTable().isManyToMany()) {
				if (isNoAmbiguityReference(reference, list))
					reference.setAlias(getNameForUnambiguiousCaseAndNotMany2Many(table));
				else
					reference.setAlias(getNameForAmbiguiousCaseAndNotMany2Many(table, reference));
			} 
		}		
	}

	private String getNameForUnambiguiousCaseAndNotMany2Many(Table table) {
		return getFinalName(table.getAlias());
	}

	private String getNameForAmbiguiousCaseAndNotMany2Many(Table table, Reference reference) {
		String name = table.getAlias()+"_"+reference.getLocalColumn().getAlias();
		return getFinalName(name);
	}

	private String getNameForUnambiguiousCaseAndMany2Many(Table table, Reference reference) {
		String name = reference.getForeignTable().getAlias();
		return getFinalName(name);
	}

	private String getNameForAmbiguiousCaseAndMany2Many(Table table, Reference reference) {
		//{targetTableVariableName}Via${linkTableName}By${localColumnName}s
		String name = reference.getForeignTable().getAlias()+"_VIA_"+reference.getLocalTable().getAlias()+"_BY_"+reference.getLocalColumn().getAlias();
		return getFinalName(name);
	}
	
	private String getFinalName(String name) {
		return (!isToPlurialize)?name:I18nUtils.plurializeUppercase(name);
	}
	private boolean isNoAmbiguityReference (Reference reference, List<Reference> list) {
		int cpt = 0;
		for (Reference ref : list) {
			if (ref.getForeignTableName().equals(reference.getForeignTableName()))
				cpt++;
		}
		if (cpt>1)
			return false;
		return true;
	}

	public boolean isToPlurialize() {
		return isToPlurialize;
	}

	public void setIsToPlurialize(boolean isToPlurialize) {
		this.isToPlurialize = isToPlurialize;
	}

}
