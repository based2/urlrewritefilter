package org.tuckey.web.filters.urlrewrite;


import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Various utilities about URLs and Files
 * 
 * @author Basile CHANDESRIS
 * 
 */
public class FilesURLs
{
    private final static Logger LOG = LoggerFactory.getLogger(FilesURLs.class);

    public static String getRessourcesPath(String RESSOURCES_PREFIX, Object object, String filename)
    {
	return RESSOURCES_PREFIX
	+ object.getClass().getPackage().getName().replace('.', File.pathSeparatorChar)
	+ File.separator + filename;
    }

    public static String getRessourcesPath(String RESSOURCES_PREFIX, @SuppressWarnings("rawtypes") Class clazz, String filename)
    {
	return RESSOURCES_PREFIX
	+ clazz.getPackage().getName().replace('.', File.pathSeparatorChar)
	+ File.separator + filename;
    }
    
    public static String getPath(@SuppressWarnings("rawtypes") Class clazz, String filename)
    {
	return getExecPath()
    	+ clazz.getPackage().getName().replace('.', File.pathSeparatorChar)
    	+ File.separator + filename;
    }
    
    public static FileInputStream getFileInputStream(@SuppressWarnings("rawtypes") Class clazz, String filename)
    {	
	String path = FilesURLs.getPath(clazz, filename);
	try
	{
	    return new FileInputStream(new File(path));
	} catch (FileNotFoundException e)
	{
	    LOG.error(path, e);
	    return null;
	}
    }

    public static FileInputStream getRessourcesFileInputStream(Object object,
	    String RESSOURCES_PREFIX, String filename)
    {
	String path = FilesURLs.getRessourcesPath(RESSOURCES_PREFIX, object, filename);
	try
	{
	    return new FileInputStream(new File(path));
	} catch (FileNotFoundException e)
	{
	    LOG.error(path, e);
	    return null;
	}
    }
    
    /**
     * Get execution path, use full for loading properties files.
     * 
     * @return execution path string
     */
    public static String getExecPath()
    {
	try
	{
	    if (LOG.isDebugEnabled())
		LOG.debug("Executing at =>"
			+ System.getProperty("user.dir"));//.replace("\\", "/"));
	    return System.getProperty("user.dir");
	} catch (Exception e)
	{
	    LOG.debug("", e);
	    return null;
	}
    }
    
    public static String getFilename(String filepath)
    {
	if (LOG.isDebugEnabled())
	{
	    LOG.debug("filepath:"+filepath);
	    LOG.debug("index:"+filepath.lastIndexOf(File.separator));
	}
	int index = filepath.lastIndexOf(File.separator);
	if (index == -1)
	{
	    index = filepath.lastIndexOf("/");
	}
	return filepath.substring(index);
    }
}
