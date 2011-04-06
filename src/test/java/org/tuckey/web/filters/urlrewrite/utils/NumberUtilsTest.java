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
package org.tuckey.web.filters.urlrewrite.utils;

import junit.framework.TestCase;
import org.tuckey.web.filters.urlrewrite.utils.Log;
import org.tuckey.web.filters.urlrewrite.utils.NumberUtils;

/**
 * @author Basile Chandesris
 * @version 
 */
public class NumberUtilsTest extends TestCase {

    public void setUp() {
        Log.setLevel("DEBUG");
    }
    
    public void testRandomInteger1() {
	int n = -1;
	int m = -1;
	for (;n==0;)
	{
	    n = NumberUtils.randomInteger(1);
        }
	for (;m==1;)
	{
	    m = NumberUtils.randomInteger(1);
        }
        assertTrue("Tested 0:n:"+ n + " 1:m:" + m, true);
    }
    
    public void testRandomInteger2() {
	int n = -1;
	int m = -1;
	int o = -1;
	for (;n==0;)
	{
	    n = NumberUtils.randomInteger(2);
        }
	for (;m==1;)
	{
	    m = NumberUtils.randomInteger(2);
        }
	for (;o==2;)
	{
	    o = NumberUtils.randomInteger(2);
        }
        assertTrue("Tested 0:n:" + n 
        	+ " 1:m:" + m
        	+ " 2:o:" + o, true);
    }
        






}
