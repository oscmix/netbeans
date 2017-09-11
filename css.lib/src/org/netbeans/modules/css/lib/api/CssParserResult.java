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
package org.netbeans.modules.css.lib.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.css.lib.AbstractParseTreeNode;
import org.netbeans.modules.css.lib.ErrorsProviderQuery;
import org.netbeans.modules.parsing.api.Snapshot;

/**
 *
 * @author marekfukala
 */
public class CssParserResult extends ParserResult {

    public static boolean IN_UNIT_TESTS = false;
    
    private AbstractParseTreeNode parseTree;
    private List<ProblemDescription> diagnostics;
    
    private Map properties;
    
    public CssParserResult(Snapshot snapshot, AbstractParseTreeNode parseTree, List<ProblemDescription> diagnostics) {
        super(snapshot);
        assert parseTree != null;
        this.parseTree = parseTree;
        this.diagnostics = diagnostics;
    }

    @Override
    protected void invalidate() {
        if(IN_UNIT_TESTS) {
            return ; //some simplification - do not invalidate the result in unit tests
        }
        parseTree = null;
        diagnostics = null;
        properties = null;
    }

    public Node getParseTree() {
        if(parseTree == null) {
            throw new IllegalStateException("Already invalidated parser result, "
                    + "you are likely trying to use it outside of the parsing task runnable!"); //NOI18N
        }
        return parseTree;
    }
    
    /**
     * Gets lexer / parser diagnostics w/o additional issues 
     * possibly added by {@link ExtendedDiagnosticsProvider}.
     * @return list of parsing issues
     */
    public List<ProblemDescription> getParserDiagnostics() {
        return diagnostics;
    }
    
    public List<? extends FilterableError> getDiagnostics(boolean includeFiltered) {
        List<? extends FilterableError> extendedDiagnostics = ErrorsProviderQuery.getExtendedDiagnostics(this);
        if(includeFiltered) {
            return extendedDiagnostics;
        } else {
            //remove filtered issues
            List<FilterableError> result = new ArrayList<>();
            for(FilterableError e : extendedDiagnostics) {
                if(!e.isFiltered()) {
                    result.add(e);
                }
            }
            return result;
        }
    }
    

    @Override
    public List<? extends FilterableError> getDiagnostics() {
        return getDiagnostics(false);
    }
    
    public <T> T getProperty(Class<T> type) {
        if(properties == null) {
            return null;
        } else {
            return (T)properties.get(type);
        }
    }
    
    public <T> void setProperty(Class<T> type, T value) {
        if(properties == null) {
            properties = new HashMap();
        }
        properties.put(type, value);
    }
    
}
