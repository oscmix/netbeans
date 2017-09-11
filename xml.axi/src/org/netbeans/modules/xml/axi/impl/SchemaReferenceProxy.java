/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
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
 */
package org.netbeans.modules.xml.axi.impl;

import org.netbeans.modules.xml.axi.AXIComponent;
import org.netbeans.modules.xml.axi.AXIModel;
import org.netbeans.modules.xml.axi.SchemaReference;
import org.netbeans.modules.xml.axi.visitor.AXIVisitor;

/**
 *
 * @author sdedic
 */
public class SchemaReferenceProxy extends SchemaReference implements AXIComponentProxy {
    public SchemaReferenceProxy(AXIModel model, AXIComponent sharedComponent) {
        super(model, sharedComponent);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.PROXY;
    }
    
    private SchemaReference getShared() {
        return (SchemaReference)getShared();
    }

    @Override
    public boolean isInclude() {
        return getShared().isInclude();
    }

    @Override
    public boolean isImport() {
        return getShared().isImport();
    }

    @Override
    public void accept(AXIVisitor visitor) {
        getShared().accept(visitor);
    }

    @Override
    public String getTargetNamespace() {
        return getShared().getTargetNamespace();
    }

    @Override
    public String getSchemaLocation() {
        return getShared().getSchemaLocation();
    }

    @Override
    public void setTargetNamespace(String targetNamespace) {
        getShared().setTargetNamespace(targetNamespace);
    }

    @Override
    public void setSchemaLocation(String schemaLocation) {
        getShared().setSchemaLocation(schemaLocation);
    }
}
