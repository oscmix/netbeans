/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.modules.java.hints.jdk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.prefs.Preferences;
import javax.swing.JCheckBox;

/**
 *
 * @author lahvac
 */
public class ConvertToDiamondBulkHintPanel extends javax.swing.JPanel {

    private final Map<String, JCheckBox> settings = new TreeMap<>();

    public ConvertToDiamondBulkHintPanel(final Preferences p) {
        initComponents();

        settings.put("initializer", initializer);
        settings.put("assignment", assignment);
        settings.put("return", returnStatement);
        settings.put("argument", argument);
        settings.put("other", other);

        ActionListener toggle = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                StringBuilder res = new StringBuilder();

                for (Entry<String, JCheckBox> e : settings.entrySet()) {
                    if (!e.getValue().isSelected()) continue;
                    if (res.length() > 0) res.append(",");
                    res.append(e.getKey());
                }

                ConvertToDiamondBulkHint.putConfiguration(p, res.toString());
            }
        };

        for (Entry<String, JCheckBox> e : settings.entrySet()) {
            e.getValue().setSelected(ConvertToDiamondBulkHint.isEnabled(p, e.getKey()));
            e.getValue().addActionListener(toggle);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        initializer = new javax.swing.JCheckBox();
        assignment = new javax.swing.JCheckBox();
        returnStatement = new javax.swing.JCheckBox();
        argument = new javax.swing.JCheckBox();
        other = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        initializer.setText(org.openide.util.NbBundle.getMessage(ConvertToDiamondBulkHintPanel.class, "ConvertToDiamondBulkHintPanel.initializer.text")); // NOI18N

        assignment.setText(org.openide.util.NbBundle.getMessage(ConvertToDiamondBulkHintPanel.class, "ConvertToDiamondBulkHintPanel.assignment.text")); // NOI18N

        returnStatement.setText(org.openide.util.NbBundle.getMessage(ConvertToDiamondBulkHintPanel.class, "ConvertToDiamondBulkHintPanel.returnStatement.text")); // NOI18N

        argument.setText(org.openide.util.NbBundle.getMessage(ConvertToDiamondBulkHintPanel.class, "ConvertToDiamondBulkHintPanel.argument.text")); // NOI18N

        other.setText(org.openide.util.NbBundle.getMessage(ConvertToDiamondBulkHintPanel.class, "ConvertToDiamondBulkHintPanel.other.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ConvertToDiamondBulkHintPanel.class, "ConvertToDiamondBulkHintPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(initializer)
                    .addComponent(assignment)
                    .addComponent(returnStatement)
                    .addComponent(argument)
                    .addComponent(other)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(initializer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(assignment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(returnStatement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(argument)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(other)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox argument;
    private javax.swing.JCheckBox assignment;
    private javax.swing.JCheckBox initializer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox other;
    private javax.swing.JCheckBox returnStatement;
    // End of variables declaration//GEN-END:variables
}
