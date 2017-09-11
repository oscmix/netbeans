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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.bugtracking.tasks.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.netbeans.modules.team.commons.treelist.LeafNode;
import org.netbeans.modules.team.commons.treelist.ProgressLabel;
import org.netbeans.modules.team.commons.treelist.TreeLabel;

/**
 * Empty Node. E.g. No Projects Open, No Projects Bookmarked
 *
 * @author Jan Becicka
 */
public class EmptyNode extends LeafNode {

    private JPanel panel;
    private JLabel lbl;
    private ProgressLabel progress;
    private String title;
    private String p;
    private final Object LOCK = new Object();
    private int loadingCounter = 0;

    public EmptyNode(String name, String progress) {
        super(null);
        this.title = name;
        this.p = progress;
    }

    @Override
    protected JComponent getComponent(Color foreground, Color background, boolean isSelected, boolean hasFocus, int rowWidth) {
        if (null == panel) {
            panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(0, 0, 0, 0));
            panel.setOpaque(false);

            lbl = new TreeLabel(title);
            progress = createProgressLabel(p);

            panel.add(lbl, BorderLayout.WEST);
            panel.add(progress, BorderLayout.CENTER);
        }

        synchronized (LOCK) {
            progress.setVisible(loadingCounter > 0);
            lbl.setVisible(loadingCounter <= 0);
        }
        return panel;
    }

    void loadingStarted() {
        synchronized (LOCK) {
            loadingCounter++;
            fireContentChanged();
        }
    }

    void loadingFinished() {
        synchronized (LOCK) {
            loadingCounter--;
            if (loadingCounter < 0) {
                loadingCounter = 0;
            }
            if (loadingCounter == 0) {
                if (progress != null) {
                    progress.stop();
                }
            }
            fireContentChanged();
        }
    }
}
