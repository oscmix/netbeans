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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
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

package org.netbeans.modules.html.palette.items;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.NbBundle;




/**
 *
 * @author  Libor Kotouc
 */
public class SELECTCustomizer extends javax.swing.JPanel {
    
    private Dialog dialog = null;
    private DialogDescriptor descriptor = null;
    private boolean dialogOK = false;

    SELECT select;
            
    /**
     * Creates new form SELECTCustomizer
     */
    public SELECTCustomizer(SELECT select) {
        this.select = select;

        initComponents();
        try {
            ((JSpinner.NumberEditor)jSpinner1.getEditor()).getTextField().getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class,"ACSN_SELECT_Options_Spinner"));
            ((JSpinner.NumberEditor)jSpinner1.getEditor()).getTextField().getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class,"ACSD_SELECT_Options_Spinner"));
            ((JSpinner.NumberEditor)jSpinner2.getEditor()).getTextField().getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class,"ACSN_SELECT_Visible_Options_Spinner"));
            ((JSpinner.NumberEditor)jSpinner2.getEditor()).getTextField().getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class,"ACSD_SELECT_Visible_Options_Spinner"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean showDialog() {
        
        dialogOK = false;
        
        String displayName = "";
        try {
            displayName = NbBundle.getBundle("org.netbeans.modules.html.palette.items.resources.Bundle").getString("NAME_html-SELECT"); // NOI18N
        }
        catch (Exception e) {}
        
        descriptor = new DialogDescriptor
                (this, NbBundle.getMessage(SELECTCustomizer.class, "LBL_Customizer_InsertPrefix") + " " + displayName, true,
                 DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.OK_OPTION,
                 new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                        if (descriptor.getValue().equals(DialogDescriptor.OK_OPTION)) {
                            evaluateInput();
                            dialogOK = true;
                        }
                        dialog.dispose();
		     }
		 } 
                );
        
        dialog = DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_Dialog"));
        dialog.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_Dialog"));
        dialog.setVisible(true);
        repaint();
        
        return dialogOK;
    }
    
    private void evaluateInput() {
        
        String name = jTextField1.getText();
        select.setName(name);

        int options = ((Integer)jSpinner1.getValue()).intValue();
        select.setOptions(options);
        
        int optionsVisible = ((Integer)jSpinner2.getValue()).intValue();
        select.setOptionsVisible(optionsVisible);
        
        select.setMultiple(jCheckBox1.isSelected());
        
        select.setDisabled(jCheckBox2.isSelected());
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();

        setLayout(new java.awt.GridBagLayout());

        jTextField1.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(jTextField1, gridBagConstraints);
        jTextField1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_Name_TextField")); // NOI18N
        jTextField1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_Name_TextField")); // NOI18N

        jLabel1.setLabelFor(jTextField1);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "LBL_SELECT_Name")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jLabel1, gridBagConstraints);
        jLabel1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_Name")); // NOI18N
        jLabel1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_Name")); // NOI18N

        jLabel3.setLabelFor(jSpinner2);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "LBL_SELECT_VisOptions")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        add(jLabel3, gridBagConstraints);
        jLabel3.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_VisOptions")); // NOI18N
        jLabel3.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_VisOptions")); // NOI18N

        jLabel2.setLabelFor(jSpinner1);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "LBL_SELECT_Options")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jLabel2, gridBagConstraints);
        jLabel2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_Options")); // NOI18N
        jLabel2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_Options")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "LBL_SELECT_Multi")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jLabel4, gridBagConstraints);
        jLabel4.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_Multi")); // NOI18N
        jLabel4.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_Multi")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "LBL_SELECT_allowed")); // NOI18N
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(jCheckBox1, gridBagConstraints);
        jCheckBox1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_allowed")); // NOI18N
        jCheckBox1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_allowed")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "LBL_SELECT_disabled")); // NOI18N
        jCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        add(jCheckBox2, gridBagConstraints);
        jCheckBox2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_disabled")); // NOI18N
        jCheckBox2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_disabled")); // NOI18N

        jSpinner1.setModel(new SpinnerNumberModel(select.getOptions(), 0, Integer.MAX_VALUE, 1));
        jSpinner1.setEditor(new JSpinner.NumberEditor(jSpinner1, "#"));
        jSpinner1.setValue(new Integer(select.getOptions()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(jSpinner1, gridBagConstraints);
        jSpinner1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_Options_Spinner")); // NOI18N
        jSpinner1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_Options_Spinner")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "LBL_SELECT_State")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 0);
        add(jLabel5, gridBagConstraints);
        jLabel5.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_State")); // NOI18N
        jLabel5.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_State")); // NOI18N

        jSpinner2.setModel(new SpinnerNumberModel(select.getOptionsVisible(), 1, Integer.MAX_VALUE, 1));
        jSpinner2.setEditor(new JSpinner.NumberEditor(jSpinner2, "#"));
        jSpinner2.setValue(new Integer(select.getOptionsVisible()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 12);
        add(jSpinner2, gridBagConstraints);
        jSpinner2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSN_SELECT_Visible_Options_Spinner")); // NOI18N
        jSpinner2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SELECTCustomizer.class, "ACSD_SELECT_Visible_Options_Spinner")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}
