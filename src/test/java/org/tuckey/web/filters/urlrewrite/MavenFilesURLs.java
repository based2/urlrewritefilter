package org.tuckey.web.filters.urlrewrite;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Various utilities about URLs and Files
 * 
 * @author Basile CHANDESRIS
 * 
 */
public class MavenFilesURLs extends FilesURLs
{
    private final static Logger LOG = LoggerFactory.getLogger(MavenFilesURLs.class);

    private final static String MAVEN_RESSOURCES = "src/resources/";
    private final static String MAVEN_TEST_RESSOURCES = "src/test/resources/";

    public static String getMavenTestRessourcesPath(Object object, String filename)
    {
	return getRessourcesPath(MAVEN_TEST_RESSOURCES, object, filename);
    }

    public static String getMavenTestRessourcesPath(@SuppressWarnings("rawtypes") Class clazz, String filename)
    {
	return getRessourcesPath(MAVEN_TEST_RESSOURCES, clazz, filename);
    }

    /**
     * Get MAVEN Test Resources
     * 
     * @param object
     * @param filename
     * @return
     */
    public static FileInputStream getMavenTestRessourcesFileInputStream(
	    Object object, String filename)
    {
	return FilesURLs.getRessourcesFileInputStream(object,
		MAVEN_TEST_RESSOURCES, filename);
    }

    /**
     * Get MAVEN Test Resources
     * 
     * @param object
     * @param filename
     * @return
     */
    public static FileInputStream getMavenRessourcesInputStream(Object object, String filename)
    {
	return FilesURLs.getRessourcesFileInputStream(object, MAVEN_RESSOURCES, filename);
    }
    
    /**
     * Read properties file.
     * @param filename
     * @return
     */
    public static Properties getMavenTestRessourcesProperties(Object object, String filename)
    {
        Properties properties = new Properties();
        try 
        {
            properties.load(getMavenTestRessourcesFileInputStream(object, filename));
        } catch (IOException e)
        {
            LOG.error("", e);
        }
        return properties;
    }
    
    /**
     * Read properties file.
     * @param filename
     * @return
     */
    public static Properties getMavenRessourcesProperties(Object object, String filename)
    {
        Properties properties = new Properties();
        try 
        {
            properties.load(getMavenRessourcesInputStream(object, filename));
        } catch (IOException e)
        {
            LOG.error("", e);
        }
        return properties;
    }
}
