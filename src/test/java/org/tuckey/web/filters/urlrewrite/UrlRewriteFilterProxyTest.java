/**
 * Copyright (c) 2005-2007, Paul Tuckey
 * All rights reserved.
 * ====================================================================
 * Licensed under the BSD License. Text as follows.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   - Neither the name tuckey.org nor the names of its contributors
 *     may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package org.tuckey.web.filters.urlrewrite;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.tuckey.web.filters.urlrewrite.test.TestRunObj;
import org.tuckey.web.filters.urlrewrite.utils.Log;
import org.tuckey.web.testhelper.MockFilterChain;
import org.tuckey.web.testhelper.MockRequest;
import org.tuckey.web.testhelper.MockResponse;
import org.tuckey.web.testhelper.MockServletContext;

/**
 * @author Paul Tuckey
 * @version $Revision: 1 $ $Date: 2006-08-01 21:40:28 +1200 (Tue, 01 Aug 2006) $
 */
public class UrlRewriteFilterProxyTest extends TestCase {

    MockResponse response;
    MockRequest request;
    MockServletContext servletContext;
    MockFilterChain chain;

    public void setUp() {
        Log.setLevel("DEBUG");
        response = new MockResponse();
        request = new MockRequest("/");
        servletContext = new MockServletContext();
        chain = new MockFilterChain();
        TestRunObj.resetTestFlags();
    }

    public void testProxyInstances() throws IOException, ServletException, InvocationTargetException {
	final NormalRule rule1 = new NormalRule();
	rule1.setFrom("/A/(.*)/AAA/(.*)$");

	SetAttribute param1 = new SetAttribute();
	param1.setName("urlrewrite.service");
	param1.setType(SetAttribute.TYPE_PARAMETER);
	param1.setValue("$1");
	rule1.addSetAttribute(param1);

	SetAttribute param2 = new SetAttribute();
	param2.setName("urlrewrite.cache");
	param2.setType(SetAttribute.TYPE_PARAMETER);
	param2.setValue("$2");
	rule1.addSetAttribute(param2);

	//rule1.setTo("-");
	rule1.setToType(NormalRule.TO_TYPE_PROXY_LABEL);
	rule1.setToLast(Conf.TRUE);
	List<String> instances = new ArrayList<String>(5);
	instances.add("http://myserver1/B/$1/BBB/$2");
	instances.add("http://myserver2/B/$1/BBB/$2");
	instances.add("http://myserver3/B/$1/BBB/$2");
	instances.add("http://myserver4/B/$1/BBB/$2");
	instances.add("http://myserver5/B/$1/BBB/$2");

	rule1.setInstances(instances);
	rule1.initialise(null);

	final Conf conf = new Conf();
	conf.addRule(rule1);

	conf.initialise();
	
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver1/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver2/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver3/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver4/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver5/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver1/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver2/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver3/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver4/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver5/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver1/B/BB/BBB/CCCC/10/682/0");
	this.checkURL(conf, "/A/BB/AAA/CCCC/10/682/0", "http://myserver2/B/BB/BBB/CCCC/10/682/0");
    }
    
    /*
     * <rule>
	<from>^/cache/(.*)$</from>
   <!-- <set name="urlrewrite.cache">$1</set>
    <to type="proxy" last="true">http://s-insp-som2-st.net1.cec.eu.int/cache/INSP-EC-Overview-Map/Overview-Map/_alllayers/L00/R00000000/C00000000.png</to>
	 -->
	 <to type="proxy" last="true">http://s-insp-som2-st.net1.cec.eu.int/cache/$1</to>
</rule>
     */
    
    public void testProxyAGSFile() throws IOException, ServletException, InvocationTargetException {
	final NormalRule rule1 = new NormalRule();
	rule1.setFrom("^/cache/(.*)$");

	//rule1.setTo("-");
	rule1.setToType(NormalRule.TO_TYPE_PROXY_LABEL);
	rule1.setToLast(Conf.TRUE);
	List<String> instances = new ArrayList<String>(1);
	instances.add("http://s-insp-som2-st.net1.cec.eu.int/cache/$1");

	rule1.setInstances(instances);
	rule1.initialise(null);

	final Conf conf = new Conf();
	conf.addRule(rule1);

	conf.initialise();
	
	this.checkURL(conf, "/cache/INSP-EC-Overview-Map/Overview-Map/_alllayers/L00/R00000000/C00000000.png", "http://localhost:8092/cache/INSP-EC-Overview-Map/Overview-Map/_alllayers/L00/R00000000/C00000000.png");
    }
    
    private void checkURL(Conf conf,final String URLsrc,final String URLtarget) throws IOException, ServletException, InvocationTargetException
    {
	final UrlRewriter urlRewriter1 = new UrlRewriter(conf);
	final MockRequest request1 = new MockRequest(URLsrc);
	final RewrittenUrl rewrittenUrl1 = urlRewriter1.processRequest(request1, response);
	assertNotNull(rewrittenUrl1);
	assertEquals(URLtarget, rewrittenUrl1.getTarget());
    }
}
