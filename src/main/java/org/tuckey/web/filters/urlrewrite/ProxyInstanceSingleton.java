package org.tuckey.web.filters.urlrewrite;

import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.Log;
import org.tuckey.web.filters.urlrewrite.utils.NumberUtils;

public class ProxyInstanceSingleton
{
    private static final Log log = Log.getLog(ProxyInstanceSingleton.class);
    private static ProxyInstanceSingleton instance;
    private static Object objetSync_;
    
    private static List<String> instances_ = null;
    //private static int instanceCurrentCursor = 0;
    
    private ProxyInstanceSingleton() {}

    protected static ProxyInstanceSingleton getSingleton() {
	if (null == instance) { // First call
	    synchronized(objetSync_) {
		if (null == instance) {
		    instance = new ProxyInstanceSingleton();
		}
	    }
	}
	return instance;
    }
    
    protected static void setInstances(List<String> instances)
    {
	instances_ = instances;
    }
    
    protected static List<String> getInstances()
    {
        return instances_;
    }
    
    /*protected static synchronized String getInstance()
    {
	if (instanceCurrentCursor==instances_.size())
	{
	    instanceCurrentCursor=0;
	}
	String instance = instances_.get(instanceCurrentCursor);
	if (log.isTraceEnabled())
	{
	    log.trace("instance:" + instanceCurrentCursor + ":" + instance);
	}
	instanceCurrentCursor++;
        return instance;
    }*/
    
    protected static synchronized String getInstance()
    {
	int n = NumberUtils.randomInteger(1);
	String instance = instances_.get(n);
	if (log.isTraceEnabled())
	{
	    log.trace("instance:" + n + ":" + instance);
	}
        return instance;
    }
    
}

