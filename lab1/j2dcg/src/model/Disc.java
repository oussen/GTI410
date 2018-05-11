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
import java.awt.Graphics;
import java.awt.Point;

import controller.MementoIF;

/**
 * <p>Title: Disc</p>
 * <p>Description: Subclass of a Shape, representing a Disc.</p>
 * <p>Copyright: Copyright (c) 2003 Mohammed Elghaouat, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.9 $
 */
public class Disc extends ColoredShape {
  	private int radius;
        
  	/**
  	 * Disc constructor, with color.
  	 * @param centerX the x coordinate of the disc (center)
  	 * @param centerY the y coordinate of the disc (center)
  	 * @param color a pixel containing color info
  	 * @param radius the radius of the disc
  	 */
  	public Disc(int centerX, int centerY, Pixel color, int radius) {
  		super(centerX, centerY, color);
  		setRadius(radius);
  	}
  	
  	/**
  	 * Black disc constructor.
  	 * @param centerX the x coordinate of the disc (center)
  	 * @param centerY the y coordinate of the disc (center)
  	 * @param radius the radius of the disc
  	 */
  	public Disc(int centerX, int centerY, int radius) {
  		this(centerX, centerY, new Pixel(0), radius);
  	}

	/**
	 * Disc rendering method.
	 * @param g Graphics rendering context
	 */
  	public void drawHook(Graphics g){
  	    g.setColor(getColor().toColor());
   		g.fillOval((int)(getCenter().getX() - radius),
   				   (int)(getCenter().getY() - radius),
              	   radius*2,radius*2);
  	}

	/**
	 * @param aPt specified point coordinates
	 * @return point contained inside flag value
	 */
  	public boolean isThisPointInHook(Point aPt){
    	return ((aPt.distance(getCenter()))<=radius);
  	}

	/**
	 * What is the radius of the disc?
	 * @return radius of the disc
	 */
  	public int getRadius(){
    	return radius;
  	}
  	
	public java.awt.Rectangle getRectangle() {
		return new java.awt.Rectangle((int)(getCenter().getX()-radius),
						   (int)(getCenter().getY()-radius),
						   radius*2,
						   radius*2);
	}

	public java.awt.Rectangle getTransformedRectangle() {
		java.awt.Point transformedCenter = getTransformedCenter(); 
		return new java.awt.Rectangle((int)(transformedCenter.getX()-radius),
						   (int)(transformedCenter.getY()-radius),
						   radius*2,
						   radius*2);
	}
	
	// Memento Support.
  	
	private void setRadius(int radius) {
  	    this.radius = radius;
  	    // coloredshape already notifies observers
  	}	
	
	// @see model.ColoredShape#createMemento()
    public MementoIF createMemento() {
		return new DiscMemento(super.createMemento(), radius);
    }
    
    // @see model.ColoredShape#setMemento(controller.MementoIF)
    public void setMemento(MementoIF memento) {
        DiscMemento m = (DiscMemento)memento;
		setRadius(m.getRadius());
		super.setMemento(m.getParent());
    }
    
    /** Supports undo. */
	private class DiscMemento implements MementoIF {
		private final MementoIF memParent;
	    private final int memRadius;
	    /**
	     * @param m the parent memento
	     * @param i the radius
	     */
	    DiscMemento(MementoIF m, int i) {
			memParent = m;
		    memRadius = i;
		}
		int getRadius() { return memRadius; }
		MementoIF getParent() { return memParent; }	    
	}
}