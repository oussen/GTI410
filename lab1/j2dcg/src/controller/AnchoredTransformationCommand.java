/*
   This file is part of j2dcg.
   j2dcg is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.
   j2dcg is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   You should have received a copy of the GNU General Public License
   along with j2dcg; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package controller;

import java.awt.Point;
import java.util.List;

import model.Shape;

/**
 * <p>Title: AnchoredTransformationCommand</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Jean-François Barras, Éric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on: 2004-03-19</p>
 * @version $Revision: 1.3 $
 */
public abstract class AnchoredTransformationCommand extends Command {

	public final static int TOP_LEFT = 0;
	public final static int TOP_CENTER = 1;
	public final static int TOP_RIGHT = 2;
	public final static int MIDDLE_LEFT = 3;
	public final static int CENTER = 4;
	public final static int MIDDLE_RIGHT = 5;
	public final static int BOTTOM_LEFT = 6;
	public final static int BOTTOM_CENTER = 7;
	public final static int BOTTOM_RIGHT = 8;
	
	/**
	 * Constructor. Reserved for classes that inherit from this one.
	 * @param anchor one of the previously defined constants
	 */
	protected AnchoredTransformationCommand(int anchor) {
		this.anchor = anchor;
	}

	/**
	 * From the rectangle of the first object in the list, returns the anchor point 
	 * of this first object. 
	 * @param objects
	 * @return
	 */	
	protected Point getAnchorPoint(List objects) {
		Shape s = (Shape)objects.get(0);
		java.awt.Rectangle r = s.getRectangle();
		int x, y;
		if (anchor == TOP_LEFT || anchor == MIDDLE_LEFT || anchor == BOTTOM_LEFT) {
			x = (int)r.getMinX(); 
		} else if (anchor == TOP_CENTER || anchor == CENTER || anchor == BOTTOM_CENTER) {
			x = (int)r.getCenterX(); 
		} else {
			x = (int)r.getMaxX();
		}
		if (anchor == TOP_LEFT || anchor == TOP_CENTER || anchor == TOP_RIGHT) {
			y = (int)r.getMinY();
		} else if (anchor == MIDDLE_LEFT || anchor == CENTER || anchor == MIDDLE_RIGHT) {
			y = (int)r.getCenterY(); 
		} else {
			y = (int)r.getMaxY();
		}
		return new Point(x,y);
	}

	private final int anchor;
	
	protected int getAnchor() { return anchor; }

}
