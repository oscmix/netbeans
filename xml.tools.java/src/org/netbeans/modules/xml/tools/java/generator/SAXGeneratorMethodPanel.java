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
package org.netbeans.modules.xml.tools.java.generator;

import org.netbeans.modules.xml.tools.generator.*;
import org.netbeans.modules.xml.tools.java.generator.SAXGeneratorAbstractPanel;
import org.netbeans.modules.xml.tools.java.generator.ElementBindings;
import java.awt.*;
import java.util.*;
import java.beans.*;

import javax.swing.*;
import javax.swing.table.*;

import org.openide.util.*;


/**
 * Customizes element => (method, method type) bindings in visual way.
 *
 * @author  Petr Kuzel
 * @version
 */
public final class SAXGeneratorMethodPanel extends SAXGeneratorAbstractPanel {

    /** Serial Version UID */
    private static final long serialVersionUID =-4925652670676144240L;    


    private TableModel tableModel;
    private MethodsTable table;   

    // constants
    
    static final int ELEMENT_COLUMN = 0;
    static final int TYPE_COLUMN = 1;
    static final int METHOD_COLUMN = 2;
    static final int COLUMNS = 3;
    
    private final String[] COLUMN_NAMES = new String[] {
        NbBundle.getMessage(SAXGeneratorMethodPanel.class, "SAXGeneratorMethodPanel.table.column1"),
        NbBundle.getMessage(SAXGeneratorMethodPanel.class, "SAXGeneratorMethodPanel.table.column2"),
        NbBundle.getMessage(SAXGeneratorMethodPanel.class, "SAXGeneratorMethodPanel.table.column3"),
    };

    

    private final ValidatingTextField.Validator METHOD_VALIDATOR = new ValidatingTextField.Validator() {
        public boolean isValid(String text) {
            boolean ret = Utilities.isJavaIdentifier("_" + text); // NOI18N
            setValid(ret);
            return ret;
        }        
        
        public String getReason() {
            return NbBundle.getMessage(SAXGeneratorMethodPanel.class, "MSG_method_err_1");
        }
    };
    
    /** Creates new form SAXGeneratorMethodPanel */
    public SAXGeneratorMethodPanel() {
//        try {
//            this.putClientProperty(WizardDescriptor.PROP_HELP_URL, new URL("nbresloc:/org/netbeans/modules/xml/tools/generator/SAXGeneratorMethodPanel.html"));  //NOI18N
//        } catch (MalformedURLException ex) {
//        }            
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        descTextArea = new javax.swing.JTextArea();
        tableScrollPane = new javax.swing.JScrollPane();

        setName(NbBundle.getMessage(SAXGeneratorMethodPanel.class, "SAXGeneratorMethodPanel.Form.name")); // NOI18N
        setPreferredSize(new java.awt.Dimension(480, 350));
        setLayout(new java.awt.GridBagLayout());

        descTextArea.setEditable(false);
        descTextArea.setFont(javax.swing.UIManager.getFont ("Label.font"));
        descTextArea.setForeground(new java.awt.Color(102, 102, 153));
        descTextArea.setLineWrap(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/tools/java/generator/Bundle"); // NOI18N
        descTextArea.setText(bundle.getString("DESC_saxw_methods")); // NOI18N
        descTextArea.setWrapStyleWord(true);
        descTextArea.setDisabledTextColor(javax.swing.UIManager.getColor ("Label.foreground"));
        descTextArea.setEnabled(false);
        descTextArea.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        add(descTextArea, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(tableScrollPane, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void initModels() {
        tableModel = new MethodsTableModel();
    }

    protected void initView() {
        initModels();
        initComponents ();

        table = new MethodsTable();
        table.setModel(tableModel);        
        tableScrollPane.setViewportView(table);  //install it
        
        int height = table.getRowHeight();
    
        // Determine highest cell in the row
        for (int c=0; c<table.getColumnCount(); c++) {
            TableCellRenderer r = table.getCellRenderer(0, c);
            Component comp = table.prepareRenderer(r, 0, c);
            int h = comp.getPreferredSize().height;
            height = Math.max(height, h);
        }
        table.setRowHeight(height);
        initAccessibility();
    }
    
    protected void updateView() {
        checkNames();
    }
    
    protected void updateModel() {
        //tablemodel writes directly to model, no need for update
    }
    
    private void checkNames() {
    }
    
    // ~~~~~~~~~~~~~~~~~ Table models ~~~~~~~~~~~~~~`
        
    /**
     * The table using dynamic cell editors for TYPE_COLUMN
     */
    private class MethodsTable extends JTable {
        
        /** Serial Version UID */
        private static final long serialVersionUID =-8352980237774025436L;
        
        public MethodsTable() {
            getTableHeader().setReorderingAllowed(false);
          //  setRowHeight(Util.getTextCellHeight(this));
        }
        
        /** 
         * We need a cell editor that is initialized again and again to
         * that it contains fresh values.
         */
        public TableCellEditor getCellEditor(int row, int column) {
            if (column == TYPE_COLUMN) {                
                ElementBindings.Entry entry = model.getElementBindings().getEntry(row);
                final String element = entry.getElement();
                final JComboBox editor = 
                    new JComboBox(entry.displayTypesFor(model.getElementDeclarations().getEntry(element)));
                
                return new DefaultCellEditor(editor);
            } else if (column == METHOD_COLUMN) {
                ValidatingTextField input = new ValidatingTextField();
                input.setValidator(METHOD_VALIDATOR);
                return new DefaultCellEditor(input);
            } else {
                return super.getCellEditor(row, column);
            }
        }
                
    }

    /**
     * TableModel that directly access <tt>model</tt> field
     */
    private class MethodsTableModel extends AbstractTableModel {

        /** Serial Version UID */
        private static final long serialVersionUID =7287934953974099492L;
        
        public String getColumnName(int col) {
            return COLUMN_NAMES[col];
        }

        public int getRowCount() {
            if (model == null) return 0;
            return model.getElementBindings().size();
        }

        public int getColumnCount() {
            return COLUMNS;
        }

        /**
         * Return String (ELEMENT) or String (TYPE) or String (METHOD).
         */
        public Object getValueAt(int row, int column) {
            ElementBindings.Entry entry = model.getElementBindings().getEntry(row);
            if (entry == null) {
                return null;
            }
            switch (column) {
                case ELEMENT_COLUMN: 
                    return entry.getElement();
                case TYPE_COLUMN: 
                    return entry.displayTypeFor(entry.getType());
                case METHOD_COLUMN: 
                    return entry.getMethod();
                default: 
                    return null;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            
            ElementBindings.Entry entry = model.getElementBindings().getEntry(row);
            switch (col) {
                case TYPE_COLUMN:
                    entry.setType(entry.typeFor((String) value));
                    return;
                case METHOD_COLUMN:
                    // the "_" emulates actual prefix added by generator
                    if (Utilities.isJavaIdentifier("_" + (String) value) == false) { // NOI18N
                        setValid(false);
                        return;
                    } else {
                        checkNames();
                    }
                    entry.setMethod((String) value);  //!!! check for duplicities
                    return;
            }
        }

        public boolean isCellEditable(int row, int col) {
            return col != ELEMENT_COLUMN;
        }
    }

    // ~~~~~~~~~~~~~~~~~~~ Conversion routines ~~~~~~~~~~~~~~`
    
    


        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descTextArea;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables

    /** Initialize accesibility
     */
    public void initAccessibility(){

        this.getAccessibleContext().setAccessibleDescription(
                NbBundle.getMessage(SAXGeneratorMethodPanel.class, "ACSD_SAXGeneratorMethodPanel"));
        table.getAccessibleContext().setAccessibleDescription(
                NbBundle.getMessage(SAXGeneratorMethodPanel.class, "ACSD_table"));
        table.getAccessibleContext().setAccessibleName(
                NbBundle.getMessage(SAXGeneratorMethodPanel.class, "ACSN_table"));
    }    
}
