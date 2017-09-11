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

package org.netbeans.modules.bugtracking.ui.selectors;

import javax.swing.GroupLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import static java.lang.Character.MAX_RADIX;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.netbeans.modules.bugtracking.RepositoryImpl;
import org.netbeans.modules.bugtracking.team.TeamRepositoryPanel;
import org.netbeans.modules.bugtracking.spi.RepositoryController;
import org.netbeans.modules.bugtracking.spi.RepositoryInfo;

/**
 *
 * @author Marian Petras
 */
public class RepositoryFormPanel extends JPanel {

    private Collection<String> cardNames = new ArrayList<String>(6);

    private RepositoryImpl selectedRepository = null;
    private RepositoryController selectedFormController = null;

    private boolean isValidData = false;

    private JComponent cardsPanel;
    private JLabel errorLabel;
    private JTextArea errorText;

    private final FormDataListener formDataListener = new FormDataListener();

    private final List<ChangeListener> listeners = new ArrayList<ChangeListener>(4);
    private final ChangeEvent changeEvent = new ChangeEvent(this);
    private JPanel emptyPanel;
    private static final Color ERROR_COLOR;
    static {
        Color c = UIManager.getColor("nb.errorForeground"); //NOI18N
        if (c == null) {
            c = new Color(153, 0, 0);
        }
        ERROR_COLOR = c;
    }
    private JScrollPane errorScrollPane;

    public RepositoryFormPanel() {
        initComponents();
    }

    public RepositoryFormPanel(RepositoryImpl repository, String initialErrorMessage) {
        this();

        displayForm(repository, initialErrorMessage);
    }

    private void initComponents() {
        cardsPanel = new JPanel(new CardLayout());

        errorLabel = new JLabel();
        errorLabel.setForeground(ERROR_COLOR);
        errorLabel.setIcon(new ImageIcon(ImageUtilities.loadImage(
                "org/netbeans/modules/bugtracking/ui/resources/error.gif")));   //NOI18N
        errorText = new JTextArea();
        errorText.setForeground(ERROR_COLOR);
        errorText.setBackground(errorLabel.getBackground());
        errorText.setEditable(false);
        
        errorScrollPane = new javax.swing.JScrollPane();
        errorScrollPane.setBorder(null);
        errorScrollPane.setViewportView(errorText);
        
        updateErrorMessage(" ");                                        //NOI18N
        
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        
        int height = errorText.getFont().getSize() * 3;
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(errorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(errorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errorScrollPane, height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(errorLabel))
                )
        );
        layout.setHonorsVisibility(false);  //keep space for errorLabel
    }

    public boolean displayForm(RepositoryImpl repository, String initialErrMsg) {
        if (repository == selectedRepository) {
            return false;
        }

        boolean firstTimeUse;

        boolean wasValid = isValidData;
        firstTimeUse = displayFormPanel(repository, initialErrMsg);
        if (isValidData != wasValid) {
            fireValidityChanged();
        }

        return firstTimeUse;
    }

    void displayErrorMessage(String message) {
        updateErrorMessage(message);
    }

    public RepositoryImpl getSelectedRepository() {
        return selectedRepository;
    }

    private void checkDataValidity() {
        assert selectedFormController != null;

        boolean valid = selectedFormController.isValid();

        updateErrorMessage(selectedFormController.getErrorMessage());
        setDataValid(valid);
    }

    private void setDataValid(boolean valid) {
        if (valid != isValidData) {
            isValidData = valid;
            fireValidityChanged();
        }
    }

    private void updateErrorMessage(final String errorMessage) {
        final String msg = errorMessage != null ? errorMessage.trim() : null;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (msg != null && msg.length() != 0) {
                    errorLabel.setVisible(true);
                    errorText.setText(msg);
                    errorScrollPane.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                    errorScrollPane.setVisible(false);
                    errorText.setText(" ");                                    //NOI18N
                }
            }
        };
        if(SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    class FormDataListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            checkDataValidity();
        }
    }

    private boolean displayFormPanel(RepositoryImpl repositoryImpl, String initialErrMsg) {
        if (repositoryImpl == selectedRepository) {
            return false;
        }

        stopListeningOnController();

        if(repositoryImpl != null) {
            String cardName = getCardName(repositoryImpl);
            RepositoryController controller = repositoryImpl.getController();

            boolean firstTimeUse = registerCard(cardName);
            if (firstTimeUse) {
                RepositoryInfo info = repositoryImpl.getInfo();
                Component cmp;
                if(info != null && repositoryImpl.isTeamRepository()) {
                    cmp = new TeamRepositoryPanel(info);
                } else {
                    cmp = controller.getComponent();
                }
                cardsPanel.add(cmp, cardName);
            }

            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, cardName);

            selectedFormController = controller;
            selectedRepository = repositoryImpl;

            startListeningOnController();
            selectedFormController.populate();

            if ((initialErrMsg != null) && (initialErrMsg.trim().length() != 0)) {
                updateErrorMessage(initialErrMsg);
                setDataValid(false);
            } else {
                checkDataValidity();
            }
            return firstTimeUse;
        } else {
            String cardName = getCardName(repositoryImpl);
            if(emptyPanel == null) {
                emptyPanel = new JPanel();
            }
            cardsPanel.add(emptyPanel, cardName);
            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, cardName);
            
            selectedFormController = null;
            selectedRepository = null;

            updateErrorMessage(NbBundle.getMessage(RepositoryFormPanel.class, "LBL_CouldNotCreateRepository"));
            setDataValid(false);
            
            return true;
        }
    }

    private void startListeningOnController() {
        selectedFormController.addChangeListener(formDataListener);
    }

    private void stopListeningOnController() {
        if (selectedFormController != null) {
            assert formDataListener != null;
            selectedFormController.removeChangeListener(formDataListener);
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();

        stopListeningOnController();
    }

    private static String getCardName(RepositoryImpl repository) {
        return Integer.toString(System.identityHashCode(repository), MAX_RADIX);
    }

    /**
     * Registers the given card name, if it has not been registered yet.
     * @param  cardName  card name to be registered
     * @return  {@code true} if the card name was newly registered,
     *          {@code false} if it had already been registered
     */
    private boolean registerCard(String cardName) {
        if (!cardNames.contains(cardName)) {
            cardNames.add(cardName);
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidData() {
        return isValidData;
    }

    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }

    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }

    private void fireValidityChanged() {
        if (!listeners.isEmpty()) {
            for (ChangeListener l : listeners) {
                l.stateChanged(changeEvent);
            }
        }
    }

}
