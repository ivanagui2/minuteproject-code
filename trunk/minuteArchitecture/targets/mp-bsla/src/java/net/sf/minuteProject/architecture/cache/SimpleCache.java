package net.sf.minuteProject.architecture.cache;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class SimpleCache implements Cache2 {
	
	private Long delay;
	private long delayDefault = 1000*60;
	
	private static Hashtable table = new Hashtable();
	private static Hashtable timeTable = new Hashtable();
    
    public Object get(String key) {
    	Date date = (Date)timeTable.get(key);
    	if ((date != null) && ((new Date()).getTime() - date.getTime() > getDelay())) {
    		timeTable.remove(key);
    		if (table.contains(key))
    			table.remove(key);
    		return null;
    	}
        return table.get(key);
    }
    
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#clean()
	 */
    public void clean() {
    	Enumeration<String> keys = timeTable.keys();
    	while (keys.hasMoreElements()) {
    		String key = keys.nextElement();
    		removeIfNecessary(key);
    	}
    }
    
    private void removeIfNecessary(String key) {
    	Date date = (Date)timeTable.get(key);
    	if ((date != null) && ((new Date()).getTime() - date.getTime() > getDelay())) {
    		timeTable.remove(key);
    		if (table.contains(key))
    			table.remove(key);
    	}
    }
    
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#put(java.lang.String, java.lang.Object)
	 */
    public void put(String key, Object o) {
    	table.put(key, o);
    	timeTable.put(key, new Date());
    }
    
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#remove(java.lang.String)
	 */
    public void remove(String key) {
        table.remove(key);
    }
    
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#flush()
	 */
    public void flush() {
    	table.clear();
    }

	public long getDelay() {
		if (delay==null)
			setDelay(delayDefault);
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}
    
	public void setRefreshDelay(Long delay) {
		setDelay(delay);
	}
}
