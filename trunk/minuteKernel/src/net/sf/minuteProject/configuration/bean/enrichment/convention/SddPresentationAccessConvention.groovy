package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.statement.Queries;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

class SddPresentationAccessConvention extends SddConvention{

	private String noMenuEntryForPackages;
	
	public SddPresentationAccessConvention () {
	}
	@Override
	public void apply(StatementModel t) {
		Queries queries = t.getQueries();
		if (queries!=null) {
			for (Query query : queries.getQueries()) {
				if (ParserUtils.isInListLowerCase (query.getPackageName(), noMenuEntryForPackages)) {
					query.getSecurityColor().setHasMenuAccess(false);
					//TODO to improve
					query.getPackage().getSecurityColor().setHasMenuAccess(false);
				}
			}
		}
	}
	public String getNoMenuEntryForPackages() {
		return noMenuEntryForPackages;
	}
	public void setNoMenuEntryForPackages(String noMenuEntryForPackages) {
		this.noMenuEntryForPackages = noMenuEntryForPackages;
	}

	
}
