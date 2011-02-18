package org.openxava.formatters;

import java.lang.reflect.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javax.servlet.http.*;

import org.openxava.util.*;


/**
 * Date/Time (combined) formatter with multilocale support. <p>
 *
 * Although it does some refinement in Spanish case, it support formatting
 * on locale basis.<br>
 *
 * @author Peter Smith
 * patched by Florian Adler for oracle timestamp issue
 */

public class DateTimeCombinedFormatter implements IFormatter {

	private static DateFormat spanishDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private static DateFormat [] spanishDateTimeFormats = {
		spanishDateTimeFormat,
		new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
		new SimpleDateFormat("dd/MM/yyyy HH:mm"),
		new SimpleDateFormat("ddMMyyyy HH:mm"),
		new SimpleDateFormat("ddMMyyyy HH:mm:ss"),
		new SimpleDateFormat("dd.MM.yyyy HH:mm"),		
		new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),		
		new SimpleDateFormat("dd/MM/yyyy"),		
		new SimpleDateFormat("ddMMyyyy"),		
		new SimpleDateFormat("dd.MM.yyyy")	};

//	public String format(HttpServletRequest request, Object date) {
//		if (date == null) return "";
//		if (Dates.getYear((java.util.Date)date) < 2) return "";
//		return getDateTimeFormat().format(date);
//	}
	
    public String format(HttpServletRequest request, Object date) {
        if (date == null) return "";
//        Date d = getDate(date);
//        if (d==null) return "";
        if (!(date instanceof java.util.Date))
            return getDate(date);
        if (Dates.getYear((java.util.Date)date) < 2) return "";
        return getDateTimeFormat().format(date);
//        if (Dates.getYear(d) < 2) return "";
//        return getDateTimeFormat().format(d);
    }

    private String getDate (Object date) {
        Method method;
        try {
            method = date.getClass().getMethod("timestampValue", new Class [0]); //timestampValue getTime
            Timestamp time = (Timestamp) method.invoke(date, new Object[0]);
//            time.getTime();
           
            Date d = Calendar.getInstance().getTime();
            d.setTime(time.getTime());   
            return getDateTimeFormat().format(d);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }	

	public Object parse(HttpServletRequest request, String string) throws ParseException {
		if (Is.emptyString(string)) return null;
		if (string.indexOf('-') >= 0) { // SimpleDateFormat does not work well with -
			string = Strings.change(string, "-", "/");
		}
		DateFormat [] dateFormats = getDateTimeFormats();
		for (int i=0; i<dateFormats.length; i++) {
			try {
				java.util.Date result = (java.util.Date) dateFormats[i].parseObject(string);
				return new java.sql.Timestamp( result.getTime() );
			}
			catch (ParseException ex) {
			}
		}
		throw new ParseException(XavaResources.getString("bad_date_format",string),-1);
	}

	private DateFormat getDateTimeFormat() {
		if ("es".equals(Locales.getCurrent().getLanguage()) ||
				"pl".equals(Locales.getCurrent().getLanguage())) return spanishDateTimeFormat;
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locales.getCurrent());
	}

	private DateFormat[] getDateTimeFormats() {
		if ("es".equals(Locales.getCurrent().getLanguage()) || 
				"pl".equals(Locales.getCurrent().getLanguage())) return spanishDateTimeFormats;
		return new DateFormat [] { getDateTimeFormat() };
	}

}
