/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 * 
 * Contributor(s):
 * 
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.bugzilla.repository;

import org.netbeans.modules.bugzilla.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import junit.framework.Test;
import org.eclipse.core.runtime.CoreException;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.netbeans.modules.bugtracking.spi.*;
import org.netbeans.modules.bugtracking.util.BugtrackingUtil;
import static org.netbeans.modules.bugzilla.TestConstants.REPO_PASSWD;
import static org.netbeans.modules.bugzilla.TestConstants.REPO_URL;
import static org.netbeans.modules.bugzilla.TestConstants.REPO_USER;
import org.netbeans.modules.bugzilla.issue.BugzillaIssue;
import org.netbeans.modules.bugzilla.query.BugzillaQuery;
import org.netbeans.modules.mylyn.util.MylynSupport;
import org.openide.util.test.MockLookup;

/**
 *
 * @author tomas
 */
public class RepositoryTest extends NbTestCase implements TestConstants {

    private static String REPO_NAME; 
    private static String QUERY_NAME = "Hilarious";

    public RepositoryTest(String arg0) {
        super(arg0);
    }

    @Override
    protected Level logLevel() {
        return Level.ALL;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        // reset
        Method m = MylynSupport.class.getDeclaredMethod("reset", new Class[0]);
        m.setAccessible(true);
        m.invoke(MylynSupport.class);
                
        Field f = Bugzilla.class.getDeclaredField("instance");
        f.setAccessible(true);
        f.set(Bugzilla.class, null);
                
        REPO_NAME = "Beautiful-" + System.currentTimeMillis();
        System.setProperty("netbeans.user", getWorkDir().getAbsolutePath());
        MockLookup.setLayersAndInstances();
    }

    public static Test suite () {
        return NbModuleSuite.createConfiguration(RepositoryTest.class).gui(false).suite();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRepo() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, Throwable {
        RepositoryInfo info = new RepositoryInfo(REPO_NAME, BugzillaConnector.ID, REPO_URL, REPO_NAME, REPO_NAME, REPO_USER, null, REPO_PASSWD.toCharArray() , null);
        BugzillaRepository repo = new BugzillaRepository(info);
        repo.ensureCredentials();
        
        // test queries
        Collection<BugzillaQuery> queries = repo.getQueries();
        assertEquals(0, queries.size());

        BugzillaQuery q = repo.createQuery();
        queries = repo.getQueries();
        assertEquals(0, queries.size()); // returns only saved queries

        // save query
        long lastRefresh = System.currentTimeMillis();
        String parameters = "&product=zaibatsu";
        BugzillaQuery bq = new BugzillaQuery(QUERY_NAME, repo, parameters, true, false, true);
        repo.saveQuery(bq);
        queries = repo.getQueries();
        assertEquals(1, queries.size()); // returns only saved queries

        // remove query
        repo.removeQuery(bq);
        queries = repo.getQueries();
        assertEquals(0, queries.size());

        // XXX repo.createIssue();

        // get issue
        String id = TestUtil.createIssue(repo, "somari");
        BugzillaIssue i = repo.getIssue(id);
        assertNotNull(i);
        assertEquals(id, i.getID());
        assertEquals("somari", i.getSummary());
    }

    public void testSimpleSearch() throws MalformedURLException, CoreException {
        long ts = System.currentTimeMillis();
        String summary1 = "somary" + ts;
        String summary2 = "mary" + ts;
        RepositoryInfo info = new RepositoryInfo(REPO_NAME, BugzillaConnector.ID, REPO_URL, REPO_NAME, REPO_NAME, REPO_USER, null, REPO_PASSWD.toCharArray() , null);
        BugzillaRepository repo = new BugzillaRepository(info);
        repo.ensureCredentials();

        String id1 = TestUtil.createIssue(repo, summary1);
        String id2 = TestUtil.createIssue(repo, summary2);

        Collection<BugzillaIssue> issues = repo.simpleSearch(summary1);
        assertEquals(1, issues.size());
        assertEquals(summary1, issues.iterator().next().getSummary());

        issues = repo.simpleSearch(id1);
        // at least one as id might be also contained
        // in another issues summary
        assertTrue(issues.size() > 0);
        BugzillaIssue i = null;
        for(BugzillaIssue issue : issues) {
            if(issue.getID().equals(id1)) {
                i = issue;
                break;
            }
        }
        assertNotNull(i);

        issues = repo.simpleSearch(summary2);
        assertEquals(2, issues.size());
        List<String> summaries = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        for(BugzillaIssue issue : issues) {
            summaries.add(issue.getSummary());
            ids.add(issue.getID());
        }
        assertTrue(summaries.contains(summary1));
        assertTrue(summaries.contains(summary2));
        assertTrue(ids.contains(id1));
        assertTrue(ids.contains(id2));
    }
}
