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
package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import controller.MementoIF;

/**
 * <p>Title: ControlPoint</p>
 * <p>Description: ... (Shape)</p>
 * <p>Copyright: Copyright (c) 2003 Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author Eric Paquette
 * @version $Revision: 1.8 $
 */
public class ControlPoint extends Shape {

	/**
	 * @param centerX
	 * @param centerY
	 */
	public ControlPoint(double centerX, double centerY) {
		super(centerX, centerY);
	}

	/* (non-Javadoc)
	 * @see model.Shape#draw(java.awt.Graphics)
	 */
	public void drawHook(Graphics g) {
		Point center = getCenter();
		Shape.drawTwoColorPoint(g, center, halfWidth, Color.white, Color.black);
	}

	public void drawSelectionMarkerHook(Graphics g){
		Point center = getCenter();
		int width = 3;
		int offset = halfWidth + width / 2 + 1;
		int value;
		for (int i=-1; i <= 1; ++i){
			for (int j=-1; j <= 1; ++j){
				if (i == 0 && j == 0) continue;
				value = (i + 1) * 3 + j + 1;
				if (value % 2 ==1) 
					g.setColor(Color.white);
				else
					g.setColor(Color.black);
				g.fillRect(center.x+offset*i - width / 2, 
						   center.y+offset*j - width / 2, width, width);
			}
		}
	}
	/* (non-Javadoc)
	 * @see model.Shape#isThisPointIn(java.awt.Point)
	 */
	public boolean isThisPointInHook(java.awt.Point aPt) {
		Point center = getCenter();
		int dx = Math.abs(aPt.x - center.x);
		int dy = Math.abs(aPt.y - center.y);
		return dx < SELECTION_THICKNESS && dy <= SELECTION_THICKNESS;
	}
	
	private static int halfWidth = 2;
	
	// Memento Support
	// doesn't add anything that wasn't covered by 'shape'
	
	// @see model.ColoredShape#createMemento()
    public MementoIF createMemento() {
		return new PointMemento(super.createMemento());
    }
    
    // @see model.ColoredShape#setMemento(controller.MementoIF)
    public void setMemento(MementoIF memento) {
        PointMemento m = (PointMemento)memento;
		super.setMemento(m.getParent());
    }
    
    /** Supports undo. */
	private class PointMemento implements MementoIF {
		private final MementoIF memParent;
	    /**
	     * @param m the parent memento
	     */
	    PointMemento(MementoIF m) {
			memParent = m;
		}
		MementoIF getParent() { return memParent; }	    
	}
}
