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
package org.netbeans.modules.java.j2seplatform.platformdefinition;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 *
 * @author  tom
 */
public class BrokenPlatformCustomizer extends javax.swing.JPanel {

    private J2SEPlatformImpl platform;

    /** Creates new form BrokenPlatformCustomizer */
    public BrokenPlatformCustomizer(J2SEPlatformImpl platform) {
        this.platform = platform;
        initComponents();
        postInitComponents ();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        platformHome = new javax.swing.JTextField();
        jTextPane1 = new javax.swing.JTextPane();

        setPreferredSize(new java.awt.Dimension(J2SEPlatformCustomizer.PREF_WIDTH, J2SEPlatformCustomizer.PREF_HEIGHT));
        setLayout(new java.awt.GridBagLayout());

        jLabel2.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seplatform/platformdefinition/Bundle").getString("MNE_PlatformHome").charAt(0));
        jLabel2.setLabelFor(platformHome);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seplatform/platformdefinition/Bundle"); // NOI18N
        jLabel2.setText(bundle.getString("CTL_PlatformHome")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 6);
        add(jLabel2, gridBagConstraints);

        platformHome.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 12);
        add(platformHome, gridBagConstraints);

        jTextPane1.setEditable(false);
        jTextPane1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 12);
        add(jTextPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
   
    
    private void postInitComponents () {
        this.jLabel2.setVisible(false);
        this.platformHome.setVisible(false);
        final Collection installFolders = platform.getInstallFolderURLs();
        if (platform.getInstallFolders().isEmpty() && installFolders.size() > 0) {
            this.jLabel2.setVisible(true);
            this.platformHome.setVisible(true);
            this.platformHome.setForeground(new Color (164,0,0));
            this.platformHome.setText (Utilities.toFile(URI.create(((URL)installFolders.iterator().next()).toExternalForm())).getAbsolutePath());
        }
        HTMLEditorKit htmlkit = new HTMLEditorKit();                
        StyleSheet css = htmlkit.getStyleSheet();
        if (css.getStyleSheets() == null) {
            StyleSheet css2 = new StyleSheet();
            Font f = jLabel2.getFont();
            css2.addRule(new StringBuffer("body { font-size: ").append(f.getSize()) // NOI18N
                .append("; font-family: ").append(f.getName()).append("; }").toString()); // NOI18N
            css2.addStyleSheet(css);
            htmlkit.setStyleSheet(css2);
        }
        jTextPane1.setEditorKit(htmlkit);        
        jTextPane1.setText(NbBundle.getMessage(BrokenPlatformCustomizer.class,"MSG_BrokenProject"));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField platformHome;
    // End of variables declaration//GEN-END:variables
    
}
