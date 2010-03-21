package net.sf.minuteProject.architecture.query.impl;

import net.sf.minuteProject.architecture.query.QueryWhatInit;

public class QuerySelectInit implements QueryWhatInit {

	public String getWhatInit(boolean isComma) {
       if (isComma)
           return " , ";
        return " select ";
	}

}
