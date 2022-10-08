/*
 * $Header: /home/data/cvs/rt/org.eclipse.ecf/tests/bundles/org.eclipse.ecf.tests.apache.httpclient.server/src/org/apache/commons/httpclient/auth/TestChallengeParser.java,v 1.1 2009/02/13 18:07:51 slewis Exp $
 * $Revision: 1.1 $
 * $Date: 2009/02/13 18:07:51 $
 * ====================================================================
 *
 *  Copyright 1999-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * [Additional notices, if required by prior licensing conditions]
 *
 */
package org.apache.commons.httpclient.auth;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Map;
import org.apache.commons.httpclient.auth.AuthChallengeParser;
import org.apache.commons.httpclient.auth.MalformedChallengeException;

/**
 * Unit tests for {@link AuthChallengeParser}.
 *
 * @author <a href="mailto:oleg@ural.ru">Oleg Kalnichevski</a>
 */
public class TestChallengeParser extends TestCase {

    // ------------------------------------------------------------ Constructor
    public  TestChallengeParser(String testName) {
        super(testName);
    }

    // ------------------------------------------------------------------- Main
    public static void main(String args[]) {
        String[] testCaseName = { TestChallengeParser.class.getName() };
        junit.textui.TestRunner.main(testCaseName);
    }

    // ------------------------------------------------------- TestCase Methods
    public static Test suite() {
        return new TestSuite(TestChallengeParser.class);
    }

    public void testParsingChallenge() {
        String challenge = "Basic realm=\"realm1\", test, test1 =  stuff, test2 =  \"stuff, stuff\", test3=\"crap";
        String scheme = null;
        Map elements = null;
        try {
            scheme = AuthChallengeParser.extractScheme(challenge);
            elements = AuthChallengeParser.extractParams(challenge);
        } catch (MalformedChallengeException e) {
            fail("Unexpected exception: " + e.toString());
        }
        assertEquals("basic", scheme);
        assertEquals("realm1", elements.get("realm"));
        assertEquals(null, elements.get("test"));
        assertEquals("stuff", elements.get("test1"));
        assertEquals("stuff, stuff", elements.get("test2"));
        assertEquals("\"crap", elements.get("test3"));
    }
}
