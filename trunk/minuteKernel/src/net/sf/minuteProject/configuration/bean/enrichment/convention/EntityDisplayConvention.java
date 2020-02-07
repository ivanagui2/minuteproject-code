package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.enrichment.group.FieldGroup;
import net.sf.minuteProject.configuration.bean.enrichment.validation.ComparisonOperand;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.StringUtils;

@Log4j
@Getter
@Setter
public class EntityDisplayConvention extends EntityMultiFieldConvention {

	public void apply2FieldConvention(Table table, Column firstColumn, Column secondColumn) {

		FieldGroup fg = new FieldGroup();
		
		if (StringUtils.checkExpression(firstColumn.getName(), fieldPatternType, firstFieldPattern)) {
			fg.setFields(firstColumn.getName()+","+secondColumn.getName());
		} else {
			fg.setFields(secondColumn.getName()+","+firstColumn.getName());
		}
		if(fieldNoAlreadyInFieldGroup(table, fg)) {
			table.getFieldGroups().add(fg);
		}
	}

	private boolean fieldNoAlreadyInFieldGroup(Table table, FieldGroup fg) {
		return !table.getFieldGroups().stream()
			.flatMap(u -> u.getFieldList().stream())
			.filter(u -> fg.getFieldList().contains(u))
			.findFirst()
			.isPresent();
	}
	protected boolean isValid() {
		return firstFieldPattern!=null 
				&& secondFieldPattern!=null 
				&& fieldPatternType!=null 
				&& ComparisonOperand.FIELD_GROUP.getConventionValue().equals(type)
				;
	}

}
