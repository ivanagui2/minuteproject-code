package net.sf.minuteProject.architecture.cache;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class SimpleCache {
	
	private long delay = 1000*60;
	
	private static Hashtable table = new Hashtable();
	private static Hashtable timeTable = new Hashtable();
    
    public Object get(String key) {
    	Date date = (Date)timeTable.get(key);
    	if ((date != null) && ((new Date()).getTime() - date.getTime() > delay)) {
    		timeTable.remove(key);
    		if (table.contains(key))
    			table.remove(key);
    		return null;
    	}
        return table.get(key);
    }
    
    public void clean() {
    	Enumeration<String> keys = timeTable.keys();
    	while (keys.hasMoreElements()) {
    		String key = keys.nextElement();
    		removeIfNecessary(key);
    	}
    }
    
    private void removeIfNecessary(String key) {
    	Date date = (Date)timeTable.get(key);
    	if ((date != null) && ((new Date()).getTime() - date.getTime() > delay)) {
    		timeTable.remove(key);
    		if (table.contains(key))
    			table.remove(key);
    	}
    }
    
    public void put(String key, Object o) {
    	table.put(key, o);
    	timeTable.put(key, new Date());
    }
    
    public void remove(String key) {
        table.remove(key);
    }
    
    public void flush() {
    	table.clear();
    }
}
