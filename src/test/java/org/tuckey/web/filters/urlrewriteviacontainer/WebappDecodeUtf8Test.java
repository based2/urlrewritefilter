package org.tuckey.web.filters.urlrewriteviacontainer;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import junit.framework.TestCase;

/**
 * TODO need to do a few tests
 * <p/>
 * with eocode-using not set (ie, browser encoding used, step down to utf8)
 * with eocode-using set to utf (force always with a specific decoding)
 * with eocode-using not set to null (never decode)
 * accept-encoding header?
 * <p/>
 * <p/>
 * don't decode anything - null
 * browser then utf then default - default browser,utf
 * browser then don't decode - default browser,null
 * always utf - utf
 * <p/>
 * <p/>
 * options: browser (may fail), enc (unlikely fail)
 */
public class WebappDecodeUtf8Test extends TestCase {

    private String baseUrl = "http://127.0.0.1:8080";
    protected HttpClient client = new HttpClient();
    private File systemPropBaseReportsDir = new File("container-test", "reports");
    private String containerId = "test";

    protected void setUp() throws Exception {
        String containerId = System.getProperty("test.container.id");
        if (containerId != null) {
            this.containerId = containerId;
        }
        String systemPropBaseUrl = System.getProperty("test.base.url");
        if (systemPropBaseUrl != null) {
            baseUrl = systemPropBaseUrl;
        }
        String systemPropBaseReports = System.getProperty("test.base.reports");
        if (systemPropBaseReports != null) {
            systemPropBaseReportsDir = new File(systemPropBaseReports);
        }
        System.err.println("systemPropBaseReportsDir: " + systemPropBaseReportsDir);

        GetMethod method = new GetMethod(getBaseUrl() + "/rewrite-status/?conf=/WEB-INF/" + getConf());
        client.executeMethod(method);
    }

    protected String getBaseUrl() {
        return baseUrl + "/" + getApp();
    }

    protected File getSystemPropBaseReports() {
        return systemPropBaseReportsDir;
    }

    protected void recordRewriteStatus() throws IOException {
        GetMethod method = new GetMethod(baseUrl + "/" + getApp() + "/rewrite-status");
        method.setFollowRedirects(false);
        client.executeMethod(method);
        File statusFile = new File(getSystemPropBaseReports(), containerId + "-" + getApp() + "-" + getConf() + "-rewrite-status.html");
        //noinspection ResultOfMethodCallIgnored
        statusFile.createNewFile();
        PrintWriter pw = new PrintWriter(statusFile);
        pw.print(method.getResponseBodyAsString());
        pw.close();
        System.out.println("status saved to " + statusFile.getAbsolutePath());
    }

   /* protected String getConf() {
        return "urlrewrite.xml";
    }*/

    public String getContainerId() {
        return containerId;
    }
    
    protected String getApp() {
        return "webapp";
    }

    protected String getConf() {
        return "urlrewrite-decode-utf8.xml";
    }

   public void testSetup() throws IOException {
        this.recordRewriteStatus();
    }

    /**
     *
     */
    public void testTestUtf() throws ServletException, IOException {
        String utfSampleString = "Fêtel'haïvolapük";
        GetMethod method = new GetMethod(getBaseUrl() + "/utf/" + URLEncoder.encode(utfSampleString, "UTF8") + "/");
        method.setRequestHeader("Accept-Encoding", "utf8");
        method.setFollowRedirects(false);
        client.executeMethod(method);
        assertEquals(getBaseUrl() + "/utf-redir/done/", method.getResponseHeader("Location").getValue());
    }




}
