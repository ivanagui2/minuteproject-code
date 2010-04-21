package net.sf.minuteProject.plugin.data;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.enrichment.EnrichmentUtils;

public class ReferenceDataUtils {

	public boolean isCandidateForReferenceDataGeneration (Template template, GeneratorBean bean) {
		//template has tag : generate
//		if (table.isrefdata() && no need
//		if bean instanceof field
//		if field isUk )=> candidate for enum 
//		if field tag contains enum in db => take from db
//		if field tag contains checkconstraint => take from db
//		tag enum 
		if (bean instanceof Column) {
			Column column = (Column)bean;
			if (ColumnUtils.isUnique(column) && EnrichmentUtils.isToGenerateBasedOnTag(template, bean))
				return true;
		}
		return false;
	}
}
