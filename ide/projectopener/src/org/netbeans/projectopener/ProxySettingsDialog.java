/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */

package org.netbeans.projectopener;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.netbeans.projectopener.Utils.DialogDescriptor;
import org.netbeans.projectopener.Utils.ProxySettings;

/**
 *
 * @author  Milan Kubec
 */
public class ProxySettingsDialog extends javax.swing.JDialog {
    
    private ProxySettings proxySettings;
    private DialogDescriptor dialogDesc;
    
    /** Creates new form ProxySettingsDialog */
    public ProxySettingsDialog(ProxySettings ps, DialogDescriptor dd) {
        super((JFrame) null, true);
        proxySettings = ps;
        dialogDesc = dd;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // 
        }
        initComponents();
        getRootPane().setDefaultButton(contButton);
        hostTextField.setText(proxySettings.getProxyHost());
        portTextField.setText(proxySettings.getProxyPort());
        setLocation(getCenterPoint());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        hostLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        hostTextField = new javax.swing.JTextField();
        portTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        contButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/projectopener/Bundle"); // NOI18N
        setTitle(bundle.getString("BadProxyDialogTitle")); // NOI18N

        jPanel1.setLayout(new java.awt.GridBagLayout());

        titleLabel.setText(bundle.getString("BadProxyLabel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 12, 16);
        jPanel1.add(titleLabel, gridBagConstraints);

        hostLabel.setText(bundle.getString("hostLabel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(hostLabel, gridBagConstraints);

        portLabel.setText(bundle.getString("portLabel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(portLabel, gridBagConstraints);

        hostTextField.setMinimumSize(new java.awt.Dimension(200, 20));
        hostTextField.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        jPanel1.add(hostTextField, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 8);
        jPanel1.add(portTextField, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        contButton.setText(bundle.getString("contButtonLabel")); // NOI18N
        contButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 8, 0);
        jPanel2.add(contButton, gridBagConstraints);

        exitButton.setText(bundle.getString("exitButtonLabel")); // NOI18N
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 8, 8);
        jPanel2.add(exitButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
    dialogDesc.setValue(DialogDescriptor.EXIT);
    setVisible(false);
}//GEN-LAST:event_exitButtonActionPerformed

private void contButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contButtonActionPerformed
    dialogDesc.setValue(DialogDescriptor.CONTINUE);
    proxySettings.setProxyHost(stripHttp(hostTextField.getText()));
    proxySettings.setProxyPort(portTextField.getText());
    setVisible(false);
}//GEN-LAST:event_contButtonActionPerformed
    
    private String stripHttp(String s) {
        if (s.startsWith("http://")) {
            return s.substring("http://".length());
        }
        return s;
    }
    
    private Point getCenterPoint() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth() - getInsets().left - getInsets().right) / 2;
        int y = (screenSize.height - getHeight() - getInsets().top - getInsets().bottom) / 2;
        return new Point(x, y);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton contButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JTextField hostTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTextField;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
    
    
    
}
