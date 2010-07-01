package net.sf.minuteProject.utils.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ParserUtils {

	public static List<String> getList(String s) {
		List<String> l = new ArrayList<String>();
		if (s!=null) {
			StringTokenizer st = new StringTokenizer(s,",");
			while (st.hasMoreElements()) {
				String element = (String) st.nextElement();
				l.add(element);
			}
		}
		return l;
	}
}
