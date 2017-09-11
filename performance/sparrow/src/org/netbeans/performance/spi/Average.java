/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2002, 2016 Oracle and/or its affiliates. All rights reserved.
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
/*
 * Average.java
 *
 * Created on October 16, 2002, 9:35 PM
 */

package org.netbeans.performance.spi;

/**An interface defining properties for standard statistical
 * analysis.
 *
 * @author  Tim Boudreau
 */
public interface Average {

    /** Returns the highest value in the set of values
     * used to create this element.
     * the set of values used to create this element.
     */
    public Float getMax();

    /** Returns the maximum percentage of the mean this
     * value varies by, calculated as<P>
     * <code>((Math.abs (mean - (Math.max (min, max)))/mean) * 100
     * </code>
     */
    public Float getMaxVariance();

    /** Returns the mean, or arithmetic average of
     * the set of values used to create this element.
     */
    public Float getMean();

    /** Returns the median value of the entry set used to create
     * this element
     */
    public Float getMedian();

    /** Returns the lowest value in the set of values
     * used to create this element.
     * the set of values used to create this element.
     */
    public Float getMin();
    
    /** Returns the values used to create this element.      */
    public float[] getSamples();
    
    /** Returns the standard deviation of the statistics used to
     * create this element.
     */
    public Double getStandardDeviation();
    
    /** Returns the standard deviation as a percentage of
     * the mean.
     */
    public Float getVariance();
    
}
