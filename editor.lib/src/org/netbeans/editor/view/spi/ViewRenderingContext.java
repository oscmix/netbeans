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

package org.netbeans.editor.view.spi;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.TabExpander;
import javax.swing.text.View;

/**
 * Context used for rendering of a view.
 * It provides methods useful for measuring
 * of the view contents.
 * <br>
 * It also provides information about additional highlights
 * that the view should respect when painting
 * a particular text.
 *
 * <p>
 * A particular view can obtain an instance
 * of <code>ViewRenderingContext</code> by using methods in
 * {@link RenderingContextView}.
 *
 * <p>
 * Only one thread at the time can safely use
 * the rendering context. It does not have
 * to be event dispatch thread.
 *
 * @author Miloslav Metelka
 * @version 1.00
 */

public abstract class ViewRenderingContext {
    
    /**
     * Find instance of the <code>ViewRenderingContext</code>
     * for the given view.
     */
    public static synchronized ViewRenderingContext obtainContext(View v) {
       // return org.netbeans.lib.editor.view.PaintContextResolver.get(v);
        return null;
    }

	public abstract float getSpan(View v, int p0, int p1, TabExpander e, float x);

	public abstract float getHeight(View v);

	public abstract float getAscent(View v);

	public abstract float getDescent(View v);

	/**
	 * Paint the glyphs representing the given range.
	 */
        public abstract void paint(View v, Graphics g, Shape a, int p0, int p1);

	/**
	 * Provides a mapping from the document model coordinate space
	 * to the coordinate space of the view mapped to it.
	 * This is shared by the broken views.
	 *
	 * @param pos the position to convert
	 * @param a the allocated region to render into
	 * @param rightToLeft true if the text is rendered right to left.
	 * @return the bounding box of the given position
	 * @exception BadLocationException  if the given position does not represent a
	 *   valid location in the associated document
	 * @see View#modelToView
	 */
	public abstract Shape modelToView(View v, 
					  int pos, Position.Bias bias,
					  Shape a) throws BadLocationException;

	/**
	 * Provides a mapping from the view coordinate space to the logical
	 * coordinate space of the model.
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param a the allocated region to render into
	 * @param rightToLeft true if the text is rendered right to left
	 * @return the location within the model that best represents the
	 *  given point of view
	 * @see View#viewToModel
	 */
        public abstract int viewToModel(View v, 
					float x, float y, Shape a, 
					Position.Bias[] biasReturn);

	/**
	 * Determines the model location that represents the
	 * maximum advance that fits within the given span.
	 * This could be used to break the given view.  The result 
	 * should be a location just shy of the given advance.  This
	 * differs from viewToModel which returns the closest
	 * position which might be proud of the maximum advance.
	 *
	 * @param v the view to find the model location to break at.
	 * @param p0 the location in the model where the
	 *  fragment should start it's representation >= 0.
	 * @param pos the graphic location along the axis that the
	 *  broken view would occupy >= 0.  This may be useful for
	 *  things like tab calculations.
	 * @param len specifies the distance into the view
	 *  where a potential break is desired >= 0.  
	 * @return the maximum model location possible for a break.
	 * @see View#breakView
	 */
        public abstract int getBoundedPosition(View v, int p0, float x, float len);

	/**
	 * Provides a way to determine the next visually represented model
	 * location that one might place a caret.  Some views may not be
	 * visible, they might not be in the same order found in the model, or
	 * they just might not allow access to some of the locations in the
	 * model.
	 *
	 * @param v the view to use
	 * @param pos the position to convert >= 0
	 * @param a the allocated region to render into
	 * @param direction the direction from the current position that can
	 *  be thought of as the arrow keys typically found on a keyboard.
	 *  This may be SwingConstants.WEST, SwingConstants.EAST, 
	 *  SwingConstants.NORTH, or SwingConstants.SOUTH.  
	 * @return the location within the model that best represents the next
	 *  location visual position.
	 * @exception BadLocationException
	 * @exception IllegalArgumentException for an invalid direction
	 */
        public int getNextVisualPositionFrom(View v, int pos, Position.Bias b, Shape a, 
					     int direction,
					     Position.Bias[] biasRet) 
	    throws BadLocationException {

	    int startOffset = v.getStartOffset();
	    int endOffset = v.getEndOffset();
	    Segment text;
	    
	    switch (direction) {
	    case View.NORTH:
	    case View.SOUTH:
                if (pos != -1) {
                    // Presumably pos is between startOffset and endOffset,
                    // since View is only one line, we won't contain
                    // the position to the nort/south, therefore return -1.
                    return -1;
                }
                Container container = v.getContainer();

                if (container instanceof JTextComponent) {
                    Caret c = ((JTextComponent)container).getCaret();
                    Point magicPoint;
                    magicPoint = (c != null) ? c.getMagicCaretPosition() :null;

                    if (magicPoint == null) {
                        biasRet[0] = Position.Bias.Forward;
                        return startOffset;
                    }
                    int value = v.viewToModel(magicPoint.x, 0f, a, biasRet);
                    return value;
                }
                break;
	    case View.EAST:
		if(startOffset == v.getDocument().getLength()) {
		    if(pos == -1) {
			biasRet[0] = Position.Bias.Forward;
			return startOffset;
		    }
		    // End case for bidi text where newline is at beginning
		    // of line.
		    return -1;
		}
		if(pos == -1) {
		    biasRet[0] = Position.Bias.Forward;
		    return startOffset;
		}
		if(pos == endOffset) {
		    return -1;
		}
		if(++pos == endOffset) {
                    // Assumed not used in bidi text, GlyphPainter2 will
                    // override as necessary, therefore return -1.
                    return -1;
		}
		else {
		    biasRet[0] = Position.Bias.Forward;
		}
		return pos;
	    case View.WEST:
		if(startOffset == v.getDocument().getLength()) {
		    if(pos == -1) {
			biasRet[0] = Position.Bias.Forward;
			return startOffset;
		    }
		    // End case for bidi text where newline is at beginning
		    // of line.
		    return -1;
		}
		if(pos == -1) {
                    // Assumed not used in bidi text, GlyphPainter2 will
                    // override as necessary, therefore return -1.
		    biasRet[0] = Position.Bias.Forward;
		    return endOffset - 1;
		}
		if(pos == startOffset) {
		    return -1;
		}
		biasRet[0] = Position.Bias.Forward;
		return (pos - 1);
	    default:
		throw new IllegalArgumentException("Bad direction: " + direction); // NOI18N
	    }
	    return pos;

	}

}
