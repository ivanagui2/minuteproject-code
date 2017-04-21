package net.sf.minuteProject.configuration.bean.enrichment.convention;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.utils.parser.ParserUtils;

class SddPresentationAccessConvention extends SddConvention{

	private String packageDisplayNameStripPrefixStartWith;
	
	public SddPresentationAccessConvention () {
	}
	@Override
	public void apply(StatementModel t) {
		t.getSddPackage().getQueryPackages()
			.stream()
			.forEach (u -> u.setDisplayName(getDisplayName(u)));
	}
	private String getDisplayName(Package u) {
		// TODO Auto-generated method stub
		String packName = u.getName();
		for (String s : ParserUtils.getListLowerCase(packageDisplayNameStripPrefixStartWith)) {
			if (packName.toLowerCase().startsWith(s.toLowerCase())) {
				return StringUtils.removeStartIgnoreCase(packName, s);
			}
		}
		return packName;
	}
	
}
