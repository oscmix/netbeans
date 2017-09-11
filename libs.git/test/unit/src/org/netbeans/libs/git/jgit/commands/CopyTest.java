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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */

package org.netbeans.libs.git.jgit.commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import org.netbeans.libs.git.GitClient;
import org.netbeans.libs.git.GitException;
import org.netbeans.libs.git.GitStatus;
import org.netbeans.libs.git.jgit.AbstractGitTestCase;
import org.netbeans.libs.git.progress.ProgressMonitor;

/**
 *
 * @author ondra
 */
public class CopyTest extends AbstractGitTestCase {
    private File workDir;

    public CopyTest (String testName) throws IOException {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        workDir = getWorkingDirectory();
    }

    public void testCopyUnderItself () throws Exception {
        File folder = new File(workDir, "folder");
        folder.mkdirs();
        File target = new File(folder, "subFolder");
        target.mkdirs();

        Monitor m = new Monitor();
        GitClient client = getClient(workDir);
        try {
            client.copyAfter(folder, target, m);
            fail();
        } catch (GitException ex) {
            assertEquals("Target folder [folder/subFolder] lies under the source [folder]", ex.getMessage());
        }
        try {
            client.copyAfter(target, folder, m);
            fail();
        } catch (GitException ex) {
            assertEquals("Source folder [folder/subFolder] lies under the target [folder]", ex.getMessage());
        }
    }

    public void testCopyFile () throws Exception {
        File file = new File(workDir, "file");
        file.createNewFile();
        File target = new File(workDir, "fileCopy");

        add(file);
        copyFile(file, target);
        assertTrue(target.exists());
        assertFile(file, target);
        Monitor m = new Monitor();
        GitClient client = getClient(workDir);
        client.addNotificationListener(m);
        client.copyAfter(file, target, m);
        assertEquals(Collections.singleton(target), m.notifiedFiles);
        Map<File, GitStatus> statuses = client.getStatus(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, file, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, target, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
        // sadly, rename detection works only when the source is deleted
        assertFalse(statuses.get(target).isCopied());
    }

    public void testCopyFileToFolder () throws Exception {
        File file = new File(workDir, "file");
        write(file, "aaa");
        File target = new File(new File(workDir, "folder"), "file");

        add(file);
        commit(file);
        copyFile(file, target);
        assertTrue(target.exists());
        assertFile(file, target);
        Monitor m = new Monitor();
        GitClient client = getClient(workDir);
        client.addNotificationListener(m);
        client.copyAfter(file, target, m);
        assertEquals(Collections.singleton(target), m.notifiedFiles);
        Map<File, GitStatus> statuses = client.getStatus(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, file, true, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, false);
        assertStatus(statuses, workDir, target, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);

        File target2 = new File(target.getParentFile(), "copy");
        copyFile(file, target2);
        assertTrue(target2.exists());
        assertFile(file, target2);
        m = new Monitor();
        client = getClient(workDir);
        client.addNotificationListener(m);
        client.copyAfter(file, target2, m);
        assertEquals(Collections.singleton(target2), m.notifiedFiles);
        statuses = client.getStatus(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, target, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, target2, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, file, true, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, false);
    }

    public void testCopyFileToExisting () throws Exception {
        File file = new File(workDir, "file");
        write(file, "aaa");
        File target = new File(new File(workDir, "folder"), "file");

        add(file);
        commit(file);
        copyFile(file, target);
        assertTrue(target.exists());
        assertFile(file, target);
        Monitor m = new Monitor();
        GitClient client = getClient(workDir);
        client.addNotificationListener(m);
        client.copyAfter(file, target, m);
        assertEquals(Collections.singleton(target), m.notifiedFiles);
        Map<File, GitStatus> statuses = client.getStatus(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, file, true, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, false);
        assertStatus(statuses, workDir, target, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);

        write(target, "bbb");
        add(target);
        commit(target);

        File target2 = target;
        assertTrue(target2.exists());
        m = new Monitor();
        copyFile(file, target2);
        client = getClient(workDir);
        client.addNotificationListener(m);
        client.copyAfter(file, target2, m);
        assertTrue(m.notifiedWarnings.contains("Index already contains an entry for folder/file"));
        assertEquals(Collections.singleton(target2), m.notifiedFiles);
        statuses = client.getStatus(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, target, true, GitStatus.Status.STATUS_MODIFIED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_MODIFIED, false);
        assertStatus(statuses, workDir, target2, true, GitStatus.Status.STATUS_MODIFIED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_MODIFIED, false);
        assertStatus(statuses, workDir, file, true, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, false);
    }

    public void testCopyTree () throws Exception {
        File folder = new File(workDir, "folder");
        folder.mkdirs();
        File file1 = new File(folder, "file1");
        write(file1, "file1 content");
        File file2 = new File(folder, "file2");
        write(file2, "file2 content");
        File subFolder1 = new File(folder, "folder1");
        subFolder1.mkdirs();
        File file11 = new File(subFolder1, "file");
        write(file11, "file11 content");
        File subFolder2 = new File(folder, "folder2");
        subFolder2.mkdirs();
        File file21 = new File(subFolder2, "file");
        write(file21, "file21 content");

        File target = new File(workDir, "target");
        File copy1 = new File(target, file1.getName());
        File copy2 = new File(target, file2.getName());
        File copy11 = new File(new File(target, file11.getParentFile().getName()), file11.getName());
        File copy21 = new File(new File(target, file21.getParentFile().getName()), file21.getName());

        add(file1, file11, file21);
        commit(file1, file11);

        copyFile(folder, target);
        assertTrue(copy1.exists());
        assertTrue(copy2.exists());
        assertTrue(copy11.exists());
        assertTrue(copy21.exists());
        Monitor m = new Monitor();
        GitClient client = getClient(workDir);
        client.addNotificationListener(m);
        client.copyAfter(folder, target, m);
        assertEquals(new HashSet<File>(Arrays.asList(copy1, copy11, copy21)), m.notifiedFiles);
        Map<File, GitStatus> statuses = client.getStatus(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, file1, true, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, false);
        assertStatus(statuses, workDir, file2, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, file11, true, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_NORMAL, false);
        assertStatus(statuses, workDir, file21, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, copy1, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, copy2, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, copy11, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, copy21, true, GitStatus.Status.STATUS_ADDED, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_ADDED, false);
    }

    public void testCancel () throws Exception {
        final File folder = new File(workDir, "folder");
        folder.mkdirs();
        File file = new File(folder, "file");
        file.createNewFile();
        File file2 = new File(folder, "file2");
        file2.createNewFile();
        final Monitor m = new Monitor();
        final GitClient client = getClient(workDir);
        client.add(new File[] { }, NULL_PROGRESS_MONITOR);

        // simulate copy
        final File target = new File(folder.getParentFile(), "folder2");
        target.mkdirs();
        new File(target, file.getName()).createNewFile();
        new File(target, file2.getName()).createNewFile();

        final Exception[] exs = new Exception[1];
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.addNotificationListener(m);
                    client.copyAfter(folder, target, m);
                } catch (GitException ex) {
                    exs[0] = ex;
                }
            }
        });
        m.cont = false;
        t1.start();
        m.waitAtBarrier();
        m.cancel();
        m.cont = true;
        t1.join();
        assertTrue(m.isCanceled());
        assertEquals(1, m.count);
        assertEquals(null, exs[0]);
    }
    
    public void testCopyToIgnored () throws Exception {
        final File folder = new File(workDir, "folder");
        folder.mkdirs();
        File file = new File(folder, "file");
        file.createNewFile();
        File file2 = new File(folder, "file2");
        file2.createNewFile();
        
        File ignored = new File(workDir, "ignored");
        GitClient client = getClient(workDir);
        client.ignore(new File[] { ignored }, NULL_PROGRESS_MONITOR);
        client.add(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        
        
        File target = new File(ignored, "folder");
        target.mkdirs();
        file = new File(target, "file");
        file.createNewFile();
        file2 = new File(target, "file2");
        file2.createNewFile();
        
        
        Map<File, GitStatus> statuses = client.getStatus(new File[] { file, file2 }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, file, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_IGNORED, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, file2, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_IGNORED, GitStatus.Status.STATUS_ADDED, false);
        
        client.copyAfter(folder, target, NULL_PROGRESS_MONITOR);
        
        statuses = client.getStatus(new File[] { file, file2 }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, file, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_IGNORED, GitStatus.Status.STATUS_ADDED, false);
        assertStatus(statuses, workDir, file2, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_IGNORED, GitStatus.Status.STATUS_ADDED, false);
    }
    
    public void testCopyToIgnoredFile () throws Exception {
        File file = new File(workDir, "test.global.php");
        file.createNewFile();
        File ignore = new File(workDir, ".gitignore");
        write(ignore, "*.local.php");
        
        GitClient client = getClient(workDir);
        client.add(new File[] { workDir }, NULL_PROGRESS_MONITOR);
        client.commit(new File[] { workDir }, "message", null, null, NULL_PROGRESS_MONITOR);
        
        File copy = new File(workDir, "test.local.php");
        copy.createNewFile();
        
        Map<File, GitStatus> statuses = client.getStatus(new File[] { copy }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, copy, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_IGNORED, GitStatus.Status.STATUS_ADDED, false);
                
        client.copyAfter(file, copy, NULL_PROGRESS_MONITOR);
        
        statuses = client.getStatus(new File[] { copy }, NULL_PROGRESS_MONITOR);
        assertStatus(statuses, workDir, copy, false, GitStatus.Status.STATUS_NORMAL, GitStatus.Status.STATUS_IGNORED, GitStatus.Status.STATUS_ADDED, false);
    }
}
