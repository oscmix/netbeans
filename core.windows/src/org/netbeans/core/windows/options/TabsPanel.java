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

package org.netbeans.core.windows.options;

import java.util.prefs.Preferences;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

@OptionsPanelController.Keywords(keywords={"#KW_TabsOptions"}, location="Appearance", tabTitle="#Tabs_DisplayName")
public class TabsPanel extends javax.swing.JPanel {

    protected final TabsOptionsPanelController controller;
    
    private final Preferences prefs = NbPreferences.forModule(TabsPanel.class);
    
    private final boolean isAquaLaF = "Aqua".equals(UIManager.getLookAndFeel().getID()); //NOI18N

    private boolean defMultiRow;
    private int defTabPlacement;
    
    protected TabsPanel(final TabsOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        initTabsPanel( panelTabs );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelDocTabs = new javax.swing.JPanel();
        isCloseActivatesMostRecentDocument = new javax.swing.JCheckBox();
        isNewDocumentOpensNextToActiveTab = new javax.swing.JCheckBox();
        panelTabs = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        radioTop = new javax.swing.JRadioButton();
        radioBottom = new javax.swing.JRadioButton();
        radioLeft = new javax.swing.JRadioButton();
        radioRight = new javax.swing.JRadioButton();
        checkMultiRow = new javax.swing.JCheckBox();
        filler = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new java.awt.GridBagLayout());

        panelDocTabs.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(isCloseActivatesMostRecentDocument, org.openide.util.NbBundle.getMessage(TabsPanel.class, "LBL_CloseActivatesRecentDocument")); // NOI18N
        isCloseActivatesMostRecentDocument.setToolTipText(org.openide.util.NbBundle.getMessage(TabsPanel.class, "TIP_CloseActivatesMostRecentDocument")); // NOI18N
        isCloseActivatesMostRecentDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isCloseActivatesMostRecentDocumentActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
        panelDocTabs.add(isCloseActivatesMostRecentDocument, gridBagConstraints);
        isCloseActivatesMostRecentDocument.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(TabsPanel.class, "TabsPanel.isCloseActivatesMostRecentDocument.AccessibleContext.accessibleDescription")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(isNewDocumentOpensNextToActiveTab, NbBundle.getMessage(TabsPanel.class, "TabsPanel.isNewDocumentOpensNextToActiveTab.text")); // NOI18N
        isNewDocumentOpensNextToActiveTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isNewDocumentOpensNextToActiveTabActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDocTabs.add(isNewDocumentOpensNextToActiveTab, gridBagConstraints);

        panelTabs.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, NbBundle.getMessage(TabsPanel.class, "TabsPanel.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panelTabs.add(jLabel1, gridBagConstraints);

        buttonGroup1.add(radioTop);
        radioTop.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(radioTop, NbBundle.getMessage(TabsPanel.class, "TabsPanel.radioTop.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        panelTabs.add(radioTop, gridBagConstraints);

        buttonGroup1.add(radioBottom);
        org.openide.awt.Mnemonics.setLocalizedText(radioBottom, NbBundle.getMessage(TabsPanel.class, "TabsPanel.radioBottom.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        panelTabs.add(radioBottom, gridBagConstraints);

        buttonGroup1.add(radioLeft);
        org.openide.awt.Mnemonics.setLocalizedText(radioLeft, NbBundle.getMessage(TabsPanel.class, "TabsPanel.radioLeft.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        panelTabs.add(radioLeft, gridBagConstraints);

        buttonGroup1.add(radioRight);
        org.openide.awt.Mnemonics.setLocalizedText(radioRight, NbBundle.getMessage(TabsPanel.class, "TabsPanel.radioRight.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        panelTabs.add(radioRight, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(checkMultiRow, NbBundle.getMessage(TabsPanel.class, "TabsPanel.checkMultiRow.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        panelTabs.add(checkMultiRow, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDocTabs.add(panelTabs, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        add(panelDocTabs, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(filler, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void isNewDocumentOpensNextToActiveTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isNewDocumentOpensNextToActiveTabActionPerformed
        fireChanged();
    }//GEN-LAST:event_isNewDocumentOpensNextToActiveTabActionPerformed

    private void isCloseActivatesMostRecentDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isCloseActivatesMostRecentDocumentActionPerformed
        fireChanged();
    }//GEN-LAST:event_isCloseActivatesMostRecentDocumentActionPerformed

    private void fireChanged() {
        boolean isChanged = false;
        if(isCloseActivatesMostRecentDocument.isSelected() != prefs.getBoolean(WinSysPrefs.EDITOR_CLOSE_ACTIVATES_RECENT, true)
                || isNewDocumentOpensNextToActiveTab.isSelected() != prefs.getBoolean(WinSysPrefs.OPEN_DOCUMENTS_NEXT_TO_ACTIVE_TAB, false)) {
            isChanged = true;
        }
        controller.changed(isChanged, null);
    }
    
    protected void load() {
        isCloseActivatesMostRecentDocument.setSelected(prefs.getBoolean(WinSysPrefs.EDITOR_CLOSE_ACTIVATES_RECENT, true));
        isNewDocumentOpensNextToActiveTab.setSelected(prefs.getBoolean(WinSysPrefs.OPEN_DOCUMENTS_NEXT_TO_ACTIVE_TAB, false));

        defMultiRow = prefs.getBoolean( WinSysPrefs.DOCUMENT_TABS_MULTIROW, false );
        checkMultiRow.setSelected( defMultiRow );
        defTabPlacement = prefs.getInt( WinSysPrefs.DOCUMENT_TABS_PLACEMENT, JTabbedPane.TOP );
        switch( defTabPlacement ) {
            case JTabbedPane.BOTTOM:
                radioBottom.setSelected( true );
                break;
            case JTabbedPane.LEFT:
                radioLeft.setSelected( true );
                break;
            case JTabbedPane.RIGHT:
                radioRight.setSelected( true );
                break;
            default:
                radioTop.setSelected( true );
        }
        
        if( isAquaLaF ) {
            checkMultiRow.setSelected(false);
            checkMultiRow.setEnabled(false);
            radioLeft.setEnabled(false);
            radioRight.setEnabled(false);
            if( radioLeft.isSelected() || radioRight.isSelected() ) {
                radioTop.setSelected(true);
            }
        }
    }

    protected boolean store() {
        prefs.putBoolean(WinSysPrefs.EDITOR_CLOSE_ACTIVATES_RECENT, isCloseActivatesMostRecentDocument.isSelected());
        prefs.putBoolean(WinSysPrefs.OPEN_DOCUMENTS_NEXT_TO_ACTIVE_TAB, isNewDocumentOpensNextToActiveTab.isSelected());
        
        boolean needsWinsysRefresh = false;
        needsWinsysRefresh = checkMultiRow.isSelected() != defMultiRow;
        prefs.putBoolean(WinSysPrefs.DOCUMENT_TABS_MULTIROW, checkMultiRow.isSelected());

        int tabPlacement = JTabbedPane.TOP;
        if( radioBottom.isSelected() )
            tabPlacement = JTabbedPane.BOTTOM;
        else if( radioLeft.isSelected() )
            tabPlacement = JTabbedPane.LEFT;
        else if( radioRight.isSelected() )
            tabPlacement = JTabbedPane.RIGHT;
        prefs.putInt( WinSysPrefs.DOCUMENT_TABS_PLACEMENT, tabPlacement );
        needsWinsysRefresh |= tabPlacement != defTabPlacement;

        return needsWinsysRefresh;
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    protected void initTabsPanel( JPanel panel ) {

    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkMultiRow;
    private javax.swing.JLabel filler;
    private javax.swing.JCheckBox isCloseActivatesMostRecentDocument;
    private javax.swing.JCheckBox isNewDocumentOpensNextToActiveTab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelDocTabs;
    private javax.swing.JPanel panelTabs;
    private javax.swing.JRadioButton radioBottom;
    private javax.swing.JRadioButton radioLeft;
    private javax.swing.JRadioButton radioRight;
    private javax.swing.JRadioButton radioTop;
    // End of variables declaration//GEN-END:variables

}
