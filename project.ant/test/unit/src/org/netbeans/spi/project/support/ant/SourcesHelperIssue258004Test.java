/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2016 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2016 Sun Microsystems, Inc.
 */
package org.netbeans.spi.project.support.ant;

import java.util.HashMap;
import java.util.concurrent.Callable;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.junit.NbTestCase;
import org.netbeans.modules.project.ui.convertor.ProjectConvertorAcceptor;
import org.netbeans.spi.project.ui.ProjectConvertor;
import org.netbeans.spi.project.ui.support.ProjectConvertors;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.test.MockLookup;

/**
 *
 * @author Tomas Zezula
 */
public final class SourcesHelperIssue258004Test extends NbTestCase {
    private FileObject prj;
    private FileObject autoPrj;
    private FileObject autoPrjContent;
    private Sources sources;

    public SourcesHelperIssue258004Test(final String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        clearWorkDir();
        MockLookup.setInstances(
            new ProjectConvertorAcceptor(new HashMap<String, Object>(){     //ProjectConvertor for Automatic Project
                {
                    put("requiredPattern", AutomaticProject.PATTERN);   //NOI18N
                    put("delegate", new AutomaticProject());            //NOI18N
                }
            }),
            AntBasedTestUtil.testAntBasedProjectType()                      //Ordinary project type
        );
        final FileObject wd = FileUtil.toFileObject(FileUtil.normalizeFile(getWorkDir()));
        prj = FileUtil.createFolder(wd,"project");  //NOI18N
        autoPrj = FileUtil.createFolder(prj, "automatic");  //NOI18N
        FileUtil.createData(autoPrj, AutomaticProject.PATTERN);
        autoPrjContent = FileUtil.createFolder(autoPrj, "content");  //NOI18N
        assertNotNull(autoPrjContent);

        Project owner = FileOwnerQuery.getOwner(autoPrj);
        assertNotNull(owner);
        assertTrue(ProjectConvertors.isConvertorProject(owner));

        final AntProjectHelper helper = ProjectGenerator.createProject(prj, "test");    //NOI18N
        assertNotNull(helper);
        final EditableProperties p = helper.getProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH);
        helper.putProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH, p);
        owner = FileOwnerQuery.getOwner(prj);
        assertNotNull(owner);
        assertTrue(!ProjectConvertors.isConvertorProject(owner));
        ProjectManager.getDefault().saveProject(owner);
        final SourcesHelper sh = new SourcesHelper(owner,
                helper,
                helper.getStandardPropertyEvaluator());
        sources = sh.createSources();
    }

    public void testIncludedAutomaticProjectAutoPrjFolder() throws Exception {
        assertNotNull(sources);
        final SourceGroup[] sgs = sources.getSourceGroups(Sources.TYPE_GENERIC);
        assertNotNull(sgs);
        assertEquals(1, sgs.length);
        assertTrue(sgs[0].contains(autoPrj));
    }

    public void testIncludedAutomaticProjectAutoPrjContent() throws Exception {
        assertNotNull(sources);
        final SourceGroup[] sgs = sources.getSourceGroups(Sources.TYPE_GENERIC);
        assertNotNull(sgs);
        assertEquals(1, sgs.length);
        assertTrue(sgs[0].contains(autoPrjContent));
    }


    private static final class AutomaticProject implements ProjectConvertor {
        public static final String PATTERN = "auto-prj";    //NOI18N

        @Override
        @CheckForNull
        public Result isProject(@NonNull final FileObject projectDirectory) {
            final FileObject marker = projectDirectory.getFileObject(PATTERN);
            if (marker != null && marker.isData()) {
                return new Result(
                        Lookup.EMPTY,
                        new Callable<Project>() {
                            @Override
                            public Project call() throws Exception {
                                return new OpenedProject(projectDirectory);
                            }
                        },
                        "Automatic Project",    //NOI18N
                        null);
            }
            return null;
        }

        private static final class OpenedProject implements Project {
            private final FileObject projectDirectory;

            OpenedProject(@NonNull final FileObject projectDirectory) {
                this.projectDirectory = projectDirectory;
            }

            @Override
            public FileObject getProjectDirectory() {
                return projectDirectory;
            }

            @Override
            public Lookup getLookup() {
                return Lookup.EMPTY;
            }
        }
    }
}
