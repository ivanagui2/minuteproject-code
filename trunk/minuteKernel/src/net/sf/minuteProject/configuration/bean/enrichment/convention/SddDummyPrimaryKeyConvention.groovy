package net.sf.minuteProject.configuration.bean.enrichment.convention

import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.statement.Queries;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.TableUtils;

class SddDummyPrimaryKeyConvention extends SddConvention{

	@Override
	public void apply(StatementModel t) {
		Queries queries = t.getQueries();
		if (queries!=null) {
			for (Query query : queries.getQueries()) {
				Table input = query.getInputBean();
				TableUtils.getPrimaryFirstColumn(input);
				if (input==null)
					addDummyPrimaryKey(input);
			}
		}
	}

	private void addDummyPrimaryKeyIfNoPrimaryKey(Table input) {
		TableUtils.getPrimaryFirstColumn(input);
		if (input==null)
			addDummyPrimaryKey(input);
	}
	
	private void addDummyPrimaryKey(Table input) {
		input.addColumn (getDummyPrimaryKeyColumn());
	}
	
	private Column getDummyPrimaryKeyColumn(Table input) {
		org.apache.ddlutils.model.Column col = new org.apache.ddlutils.model.Column()
		col.setName("DUMMY_PK_ID")
		col.setType("INTEGER")
		col.setPrimaryKey(true);
		Column column = new ColumnDDLUtils(col, input);
		column.setRequired(true);
		column.setHidden(true);
		column.setTransient(true);
	}
}
