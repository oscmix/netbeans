/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.web.browser.ui.picker;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

/**
 *
 * @author S. Aubrecht
 */
class SelectionListImpl extends JList<ListItem> {

    private int mouseOverRow = -1;

    SelectionListImpl() {
        setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        setBorder( BorderFactory.createEmptyBorder() );
        setOpaque( false );
        setBackground( new Color(0,0,0,0) );
        if( BrowserMenu.GTK ) {
            Color foreground = UIManager.getColor( "MenuItem.foreground"); //NOI18N
            if( null != foreground ) {
                setForeground( new Color(foreground.getRGB()) );
            }
        }
        setCellRenderer( new RendererImpl() );

        addFocusListener( new FocusAdapter() {

            @Override
            public void focusGained( FocusEvent e ) {
                if( getSelectedIndex() < 0 && isShowing() && getModel().getSize() > 0 ) {
                    setSelectedIndex( 0 );
                }
            }
        });

        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mouseEntered( MouseEvent e ) {
                mouseMoved( e );
            }

            @Override
            public void mouseExited( MouseEvent e ) {
                setMouseOver( -1 );
            }

            @Override
            public void mouseMoved( MouseEvent e ) {
                int row = locationToIndex( e.getPoint() );
                setMouseOver( row );
            }

            @Override
            public void mouseClicked( MouseEvent e ) {
                if( e.getButton() == MouseEvent.BUTTON1 ) {
                    int row = locationToIndex( e.getPoint() );
                    if( row >= 0 && row == getSelectedIndex() ) {
                        e.consume();
                        clearSelection();
                        setSelectedIndex( row );
                    }
                }
            }
        };

        addMouseMotionListener( adapter );

        addMouseListener( adapter );
    }

    int getMouseOverRow() {
        return mouseOverRow;
    }

    private void setMouseOver( int newRow ) {
        int oldRow = mouseOverRow;
        mouseOverRow = newRow;
        repaintRow( oldRow );
        repaintRow( mouseOverRow );
    }

    private void repaintRow( int row ) {
        if( row >= 0 && row < getModel().getSize() ) {
            Rectangle rect = getCellBounds( row, row );
            if( null != rect )
                repaint( rect );
        }
    }
}
