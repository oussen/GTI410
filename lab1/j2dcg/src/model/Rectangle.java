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
import java.awt.Graphics2D;
import java.awt.Point;

import controller.MementoIF;

/**
 * <p>Title: Rectangle</p>
 * <p>Description: Subclass of a Shape, representing a Rectangle.</p>
 * <p>Copyright: Copyright (c) 2004 Mohammed Elghaouat, Colin B-B, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.13 $
 */
public class Rectangle extends ColoredShape {
 	private int width;
 	private int height;

	/**
	 * Constructor where rectangles attributes can be specified.
	 * @param centerX the x coordinate of the rectangle's center
	 * @param centerY the y coordinate of the rectangle's center
	 * @param color color of the rectangle
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public Rectangle(double centerX, double centerY, Pixel color, int width, int height) {
		super(centerX, centerY, color);
		setWidth(width);
		setHeight(height);
	}
 	
 	/**
	 * Rectangle constructor. Default color is black.
	 * @param centerX the x coordinate of the rectangle's center
	 * @param centerY the y coordinate of the rectangle's center
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public Rectangle(double centerX, double centerY, int width, int height) {
	    this(centerX, centerY, new Pixel(0), width, height);
  	}
  	
 	/**
 	 * Rectangle rendering method.
 	 * @param g Graphics rendering context
 	 */
 	public void drawHook(Graphics g){
 		g.setColor(getColor().toColor());
 		Graphics2D g2d = (Graphics2D)g;
   		g2d.draw(getRectangle());
 	}

	/**
	 * Is the specified point within the rectangle selection thickness?
	 * @param aPt specified point coordinates
	 * @return point contained inside flag value
	 */
 	public boolean isThisPointInHook(Point aPt) {
   		int distX =(int) Math.abs(aPt.getX()-getCenter().getX());
   		int distY =(int) Math.abs(aPt.getY()-getCenter().getY());
   		boolean onX = (Math.abs(distY-height/2)<=SELECTION_THICKNESS)&&(distX<=width/2);
   		boolean onY = (Math.abs(distX-width/2)<=SELECTION_THICKNESS)&&(distY<=height/2);

   		if (onX||onY) {
     		return true;
   		} else {
     		return false;
   		}
 	}

	/**
	 * Is the specified point within the rectangle area?
	 * @param aPt specified point coordinates
	 * @return point contained inside flag value
	 */
 	public boolean isThisPointWithIn(Point aPt){
  		int distX =(int) Math.abs(aPt.getX()-getCenter().getX());
  		int distY =(int) Math.abs(aPt.getY()-getCenter().getY());

  		if ((distX<width/2)&&(distY<height/2)) {
    		return true;
  		} else {
    		return false;
  		}
 	}

	/**
	 * Set the rectangle corner coordinates
	 * @param aCorner1 first corner coordinate (top-left)
	 * @param aCorner2 second corner coordinate (bottom-right)
	 */
 	public void setCorners(Point aCorner1, Point aCorner2){
   		double maxX = Math.max(aCorner2.getX(),aCorner1.getX());
   		double maxY = Math.max(aCorner2.getY(),aCorner1.getY());

		setCenter(maxX - Math.abs(aCorner2.getX()-aCorner1.getX())/2,
				  maxY - Math.abs(aCorner2.getY()-aCorner1.getY())/2);

   		width  = (int)Math.abs(aCorner2.getX()-aCorner1.getX());
   		height = (int)Math.abs(aCorner2.getY()-aCorner1.getY());
 	}
 	
	public java.awt.Rectangle getRectangle() {
		return new java.awt.Rectangle((int)(getCenter().getX()-width/2),
						   (int)(getCenter().getY()-height/2),
						   width,
						   height);
	}

	public java.awt.Rectangle getTransformedRectangle() {
		Point transformedCenter = getTransformedCenter();
		return new java.awt.Rectangle((int)(transformedCenter.getX()-width/2),
						   (int)(transformedCenter.getY()-height/2),
						   width,
						   height);
	}
	
	// Memento Support.
	
	private void setWidth(int dx) {
	    this.width = dx;
	    // coloredshape already notifies observers
	}
	
	private void setHeight(int dy) {
	    this.height = dy;
	    // coloredshape already notifies observers
	}
	
	// @see model.ColoredShape#createMemento()
    public MementoIF createMemento() {
		return new RectangleMemento(super.createMemento(), width, height);
    }
    
    // @see model.ColoredShape#setMemento(controller.MementoIF)
    public void setMemento(MementoIF memento) {
        RectangleMemento m = (RectangleMemento)memento;
		setWidth(m.getWidth());
		setHeight(m.getHeight());
		super.setMemento(m.getParent());
    }
    
    /** Supports undo. */
	private class RectangleMemento implements MementoIF {
		private final MementoIF memParent;
	    private final int memWidth;
	    private final int memHeight;
	    /**
	     * @param m the parent memento
	     * @param dx the width
	     * @param dy the height
	     */
	    RectangleMemento(MementoIF m, int dx, int dy) {
			memParent = m;
		    memWidth = dx;
		    memHeight = dy;
		}
		int getWidth() { return memWidth; }
		int getHeight() { return memHeight; }
		MementoIF getParent() { return memParent; }	    
	}
}
