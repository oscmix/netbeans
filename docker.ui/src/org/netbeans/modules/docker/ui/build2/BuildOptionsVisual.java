/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package org.netbeans.modules.docker.ui.build2;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.netbeans.modules.docker.ui.UiUtils;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle;

public final class BuildOptionsVisual extends JPanel {

    private final ChangeSupport changeSupport = new ChangeSupport(this);

    private String buildContext;

    public BuildOptionsVisual() {
        initComponents();

        dockerfileTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }
        });
    }

    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    public void setBuildContext(String buildContext) {
        this.buildContext = buildContext;
    }

    public String getDockerfile() {
        return UiUtils.getValue(dockerfileTextField);
    }

    public void setDockerfile(String dockerfile) {
        dockerfileTextField.setText(dockerfile);
    }

    public Map<String, String> getBuildArgs() {
        Map<String, String> result = new TreeMap<>();
        DefaultTableModel model = (DefaultTableModel) buildargsTable.getModel();
        if (buildargsTable.isEditing()) {
            buildargsTable.getCellEditor().stopCellEditing();
        }
        for (Object o : model.getDataVector()) {
            Vector v = (Vector) o;
            String name = (String) v.elementAt(0);
            String value = (String) v.elementAt(1);
            if (name != null && value != null) {
                result.put(name, value);
            }
        }
        return result;
    }

    public void setBuildArgs(Map<String, String> buildArgs) {
        DefaultTableModel model = (DefaultTableModel) buildargsTable.getModel();
        // Clear JTable first
        model.setRowCount(0);
        buildArgs.forEach((key, value) -> {
            model.addRow(new Object[]{key, value});
        });
    }

    public boolean isPull() {
        return pullCheckBox.isSelected();
    }

    public void setPull(boolean pull) {
        pullCheckBox.setSelected(pull);
    }

    public boolean isNoCache() {
        return noCacheCheckBox.isSelected();
    }

    public void setNoCache(boolean noCache) {
        noCacheCheckBox.setSelected(noCache);
    }

    @NbBundle.Messages("LBL_BuildOptions=Build Options")
    @Override
    public String getName() {
        return Bundle.LBL_BuildOptions();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dockerfileLabel = new javax.swing.JLabel();
        dockerfileTextField = new javax.swing.JTextField();
        dockerfileButton = new javax.swing.JButton();
        pullCheckBox = new javax.swing.JCheckBox();
        noCacheCheckBox = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        buildargsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        addVariableButton = new javax.swing.JButton();
        deleteVariableButton = new javax.swing.JButton();

        dockerfileLabel.setLabelFor(dockerfileTextField);
        org.openide.awt.Mnemonics.setLocalizedText(dockerfileLabel, org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.dockerfileLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(dockerfileButton, org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.dockerfileButton.text")); // NOI18N
        dockerfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dockerfileButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(pullCheckBox, org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.pullCheckBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(noCacheCheckBox, org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.noCacheCheckBox.text")); // NOI18N

        buildargsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        buildargsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        buildargsTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                buildargsTablePropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(buildargsTable);
        if (buildargsTable.getColumnModel().getColumnCount() > 0) {
            buildargsTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.buildargsTable.columnModel.title0")); // NOI18N
            buildargsTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.buildargsTable.columnModel.title1")); // NOI18N
        }

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addVariableButton, org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.addVariableButton.text")); // NOI18N
        addVariableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVariableButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteVariableButton, org.openide.util.NbBundle.getMessage(BuildOptionsVisual.class, "BuildOptionsVisual.deleteVariableButton.text")); // NOI18N
        deleteVariableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVariableButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dockerfileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dockerfileTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dockerfileButton))
            .addComponent(pullCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(noCacheCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteVariableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVariableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dockerfileLabel)
                    .addComponent(dockerfileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dockerfileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pullCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noCacheCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addVariableButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteVariableButton)
                        .addGap(0, 155, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dockerfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dockerfileButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String dockerText = UiUtils.getValue(dockerfileTextField);
        if (dockerText != null) {
            File file = new File(dockerText);
            if (!file.isAbsolute()) {
                file = new File(buildContext, dockerText);
            }
            chooser.setSelectedFile(file);
        }
        if (chooser.showOpenDialog(SwingUtilities.getWindowAncestor(this)) == JFileChooser.APPROVE_OPTION) {
            // FIXME get relative path if it exists
            dockerfileTextField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_dockerfileButtonActionPerformed

    private void addVariableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVariableButtonActionPerformed
        DefaultTableModel model = (DefaultTableModel) buildargsTable.getModel();
        model.addRow(new Object[]{});
    }//GEN-LAST:event_addVariableButtonActionPerformed

    private void deleteVariableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVariableButtonActionPerformed
        DefaultTableModel model = (DefaultTableModel) buildargsTable.getModel();
        int selectedRowId = buildargsTable.getSelectedRow();
        if (selectedRowId != -1) {
            model.removeRow(selectedRowId);
        }
    }//GEN-LAST:event_deleteVariableButtonActionPerformed

    private void buildargsTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_buildargsTablePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_buildargsTablePropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addVariableButton;
    private javax.swing.JTable buildargsTable;
    private javax.swing.JButton deleteVariableButton;
    private javax.swing.JButton dockerfileButton;
    private javax.swing.JLabel dockerfileLabel;
    private javax.swing.JTextField dockerfileTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox noCacheCheckBox;
    private javax.swing.JCheckBox pullCheckBox;
    // End of variables declaration//GEN-END:variables
}
