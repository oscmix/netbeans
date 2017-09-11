<?xml version="1.0" encoding="UTF-8"?>
<!--
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright (c) 2011, 2016 Oracle and/or its affiliates. All rights reserved.

Oracle and Java are registered trademarks of Oracle and/or its affiliates.
Other names may be trademarks of their respective owners.

The contents of this file are subject to the terms of either the GNU
General Public License Version 2 only ("GPL") or the Common
Development and Distribution License("CDDL") (collectively, the
"License"). You may not use this file except in compliance with the
License. You can obtain a copy of the License at
http://www.netbeans.org/cddl-gplv2.html
or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
specific language governing permissions and limitations under the
License.  When distributing the software, include this License Header
Notice in each file and include the License file at
nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
particular file as subject to the "Classpath" exception as provided
by Oracle in the GPL Version 2 section of the License file that
accompanied this code. If applicable, add the following below the
License Header, with the fields enclosed by brackets [] replaced by
your own identifying information:
"Portions Copyrighted [year] [name of copyright owner]"

If you wish your version of this file to be governed by only the CDDL
or only the GPL Version 2, indicate your decision by adding
"[Contributor] elects to include this software in this distribution
under the [CDDL or GPL Version 2] license." If you do not indicate a
single choice of license, a recipient has the option to distribute
your version of this file under either the CDDL, the GPL Version 2 or
to extend the choice of license to its licensees as provided above.
However, if you add GPL Version 2 code and therefore, elected the GPL
Version 2 license, then the option applies only if the new code is
made subject to such option by the copyright holder.

Contributor(s):
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:xalan="http://xml.apache.org/xslt"
                exclude-result-prefixes="xalan">
    <xsl:output method="xml" indent="yes" encoding="UTF-8" xalan:indent-amount="4"/>
    <xsl:template match="/">
        <project name="platform" default="download" basedir="..">
            <condition property="download.required">
                <and>
                    <not>
                        <available file="${{harness.dir}}/suite.xml"/>
                    </not>
                    <isset property="bootstrap.url"/>
                    <isset property="autoupdate.catalog.url"/>
                </and>
            </condition>
            <target name="download" if="download.required">
                <mkdir dir="${{harness.dir}}"/>
                <pathconvert pathsep="|" property="download.clusters">
                    <mapper type="flatten"/>
                    <path path="${{cluster.path}}"/>
                </pathconvert>
                <property name="disabled.modules" value=""/>
                <pathconvert property="module.includes" pathsep="">
                    <mapper type="glob" from="${{basedir}}${{file.separator}}*" to="(?!^\Q*\E$)"/>
                    <path>
                        <filelist files="${{disabled.modules}}" dir="."/>
                    </path>
                </pathconvert>
                <echo message="Downloading clusters ${{download.clusters}}"/>
                <property name="tasks.jar" location="${{java.io.tmpdir}}/tasks.jar"/>
                <get src="${{bootstrap.url}}" dest="${{tasks.jar}}" usetimestamp="true" verbose="true"/>
                <taskdef name="autoupdate" classname="org.netbeans.nbbuild.AutoUpdate" classpath="${{tasks.jar}}"/>
                <autoupdate installdir="${{nbplatform.active.dir}}" updatecenter="${{autoupdate.catalog.url}}">
                    <modules includes="${{module.includes}}.*" clusters="${{download.clusters}}"/>
                    <modules includes="org[.]netbeans[.]modules[.]apisupport[.]harness" clusters="harness"/>
                </autoupdate>
            </target>
        </project>
    </xsl:template>
</xsl:stylesheet>
