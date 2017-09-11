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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2009 Sun
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

package org.netbeans.modules.debugger.jpda.jdi;

// DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
// Generated by org.netbeans.modules.debugger.jpda.jdi.Generate class located in 'gensrc' folder,
// perform the desired modifications there and re-generate by "ant generate".

/**
 * Wrapper for InterfaceType JDI class.
 * Use methods of this class instead of direct calls on JDI objects.
 * These methods assure that exceptions thrown from JDI calls are handled appropriately.
 *
 * @author Martin Entlicher
 */
public final class InterfaceTypeWrapper {

    private InterfaceTypeWrapper() {}

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static java.util.List<com.sun.jdi.ClassType> implementors0(com.sun.jdi.InterfaceType a) {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.InterfaceType",
                    "implementors",
                    "JDI CALL: com.sun.jdi.InterfaceType({0}).implementors()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            java.util.List<com.sun.jdi.ClassType> ret;
            ret = a.implementors();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            return java.util.Collections.emptyList();
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            return java.util.Collections.emptyList();
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.InterfaceType",
                        "implementors",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static java.util.List<com.sun.jdi.ClassType> implementors(com.sun.jdi.InterfaceType a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.InterfaceType",
                    "implementors",
                    "JDI CALL: com.sun.jdi.InterfaceType({0}).implementors()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            java.util.List<com.sun.jdi.ClassType> ret;
            ret = a.implementors();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.InterfaceType",
                        "implementors",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    /** Wrapper for method invokeMethod from JDK 1.8.0_40. */
    public static com.sun.jdi.Value invokeMethod(com.sun.jdi.InterfaceType a, com.sun.jdi.ThreadReference b, com.sun.jdi.Method c, java.util.List<? extends com.sun.jdi.Value> d, int e) throws com.sun.jdi.InvalidTypeException, com.sun.jdi.ClassNotLoadedException, com.sun.jdi.IncompatibleThreadStateException, com.sun.jdi.InvocationException, org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.InterfaceType",
                    "invokeMethod",
                    "JDI CALL: com.sun.jdi.InterfaceType({0}).invokeMethod({1}, {2}, {3}, {4})",
                    new Object[] {a, b, c, d, e});
        }
        Object retValue = null;
        try {
            com.sun.jdi.Value ret;
            ret = (com.sun.jdi.Value) com.sun.jdi.InterfaceType.class.getMethod("invokeMethod", com.sun.jdi.ThreadReference.class, com.sun.jdi.Method.class, java.util.List.class, int.class).invoke(a, b, c, d, e);
            retValue = ret;
            return ret;
        } catch (NoSuchMethodException ex) {
            retValue = ex;
            throw new IllegalStateException(ex);
        } catch (SecurityException ex) {
            retValue = ex;
            throw new IllegalStateException(ex);
        } catch (IllegalAccessException ex) {
            retValue = ex;
            throw new IllegalStateException(ex);
        } catch (IllegalArgumentException ex) {
            retValue = ex;
            throw new IllegalStateException(ex);
        } catch (java.lang.reflect.InvocationTargetException ex) {
            Throwable t = ex.getTargetException();
            retValue = t;
            if (t instanceof com.sun.jdi.InvalidTypeException) {
                throw (com.sun.jdi.InvalidTypeException) t;
            }
            if (t instanceof com.sun.jdi.ClassNotLoadedException) {
                throw (com.sun.jdi.ClassNotLoadedException) t;
            }
            if (t instanceof com.sun.jdi.IncompatibleThreadStateException) {
                throw (com.sun.jdi.IncompatibleThreadStateException) t;
            }
            if (t instanceof com.sun.jdi.InvocationException) {
                throw (com.sun.jdi.InvocationException) t;
            }
            if (t instanceof com.sun.jdi.InternalException) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report((com.sun.jdi.InternalException) t);
                throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper((com.sun.jdi.InternalException) t);
            }
            if (t instanceof com.sun.jdi.VMDisconnectedException) {
                throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper((com.sun.jdi.VMDisconnectedException) t);
            }
            throw new IllegalStateException(t);
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.InterfaceType",
                        "invokeMethod",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static java.util.List<com.sun.jdi.InterfaceType> subinterfaces0(com.sun.jdi.InterfaceType a) {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.InterfaceType",
                    "subinterfaces",
                    "JDI CALL: com.sun.jdi.InterfaceType({0}).subinterfaces()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            java.util.List<com.sun.jdi.InterfaceType> ret;
            ret = a.subinterfaces();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            return java.util.Collections.emptyList();
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            return java.util.Collections.emptyList();
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.InterfaceType",
                        "subinterfaces",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static java.util.List<com.sun.jdi.InterfaceType> subinterfaces(com.sun.jdi.InterfaceType a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.InterfaceType",
                    "subinterfaces",
                    "JDI CALL: com.sun.jdi.InterfaceType({0}).subinterfaces()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            java.util.List<com.sun.jdi.InterfaceType> ret;
            ret = a.subinterfaces();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.InterfaceType",
                        "subinterfaces",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static java.util.List<com.sun.jdi.InterfaceType> superinterfaces0(com.sun.jdi.InterfaceType a) throws org.netbeans.modules.debugger.jpda.jdi.ClassNotPreparedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.InterfaceType",
                    "superinterfaces",
                    "JDI CALL: com.sun.jdi.InterfaceType({0}).superinterfaces()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            java.util.List<com.sun.jdi.InterfaceType> ret;
            ret = a.superinterfaces();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            return java.util.Collections.emptyList();
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            return java.util.Collections.emptyList();
        } catch (com.sun.jdi.ClassNotPreparedException ex) {
            retValue = ex;
            throw new org.netbeans.modules.debugger.jpda.jdi.ClassNotPreparedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.InterfaceType",
                        "superinterfaces",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static java.util.List<com.sun.jdi.InterfaceType> superinterfaces(com.sun.jdi.InterfaceType a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.ClassNotPreparedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.InterfaceType",
                    "superinterfaces",
                    "JDI CALL: com.sun.jdi.InterfaceType({0}).superinterfaces()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            java.util.List<com.sun.jdi.InterfaceType> ret;
            ret = a.superinterfaces();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (com.sun.jdi.ClassNotPreparedException ex) {
            retValue = ex;
            throw new org.netbeans.modules.debugger.jpda.jdi.ClassNotPreparedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.InterfaceType",
                        "superinterfaces",
                        retValue);
            }
        }
    }

}
