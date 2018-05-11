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
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controller.MementoIF;

/**
 * <p>Title: Shape</p>
 * <p>Description: Abstract class that Shape objects implement.</p>
 * <p>Copyright: Copyright (c) 2004 Mohammed Elghaouat, Eric Paquette, Colin B-B</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.13 $
 */
public abstract class Shape implements ShapeObservable {
	public static final int SELECTION_THICKNESS = 5;
	public static final int SELECTION_MARKERS_THICKNESS = 3; 

	private Point center = new Point();
	private AffineTransform t = new AffineTransform();
	
	/**
	 * Center initialized constructor.
	 * @param centerX Center's x coordinate
	 * @param centerY Center's y coordinate
	 */	
	public Shape(double centerX, double centerY) {
		setCenter(centerX, centerY);
	}

	/**
	 * Template method pattern. draw is the template method that appropriately adjusts the transformation of the 
	 * graphics object and calls the drawHook hook method.
	 * @param g
	 */	
	final public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform savedOriginalTransform = g2d.getTransform();
		g2d.transform(getAffineTransform());
		drawHook(g2d);
		g2d.setTransform(savedOriginalTransform);
	}
	
 	/**
 	 * Shape rendering method. 
 	 * @param g Graphics rendering context
 	 */
 	protected abstract void drawHook(Graphics g);
 
 	public void drawSelectionMarker(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform savedOriginalTransform = g2d.getTransform();

		addParentTransformation(g2d);
		g2d.transform(getAffineTransform());

		drawSelectionMarkerHook(g2d);

		g2d.setTransform(savedOriginalTransform);
 	}
 	
	/**
	 * @param g2d
	 */
	private void addParentTransformation(Graphics2D g2d) {
		if (getContainer() != null) {
			Shape container = getContainer();
			container.addParentTransformation(g2d);
			g2d.transform(container.getAffineTransform());
		}
	}

	/**
	 * Special Shape rendering method to draw the selection marker.
	 * Its default behaviour is to do nothing.
	 * @param g Graphics rendering context
	 */
	protected void drawSelectionMarkerHook(Graphics g){
		java.awt.Rectangle r = getRectangle();
		int value = 0;
		int dx = r.width/2;
		int dy = r.height/2;
		int x = r.x + dx;
		int y = r.y + dy;
		for (int i=-1; i <= 1; ++i){
			for (int j=-1; j <= 1; ++j){
				if (i == 0 && j == 0) continue;
				drawTwoColorPoint(g, new Point(x + dx*i, y + dy*j), SELECTION_MARKERS_THICKNESS, Color.white, Color.black);
			}
		}
	}
 
	/**
	 * Template pattern. This is the template method that will transform the point to the shape's reference space
	 * before calling the isThisPointIn hook method
	 * @param aPt specified point coordinates
	 * @return point contained inside flag value
	 */
 	final public boolean isThisPointIn(Point aPt) {
		// TODO EP We will be creating this object many times.
		Point transformedPt = new Point();
		try {
			inverseTransformPoint(aPt, transformedPt);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
			return false;
		}
 		return isThisPointInHook(transformedPt);
 	}

	public void inverseTransformPoint(Point ptSrc, Point ptDst) throws NoninvertibleTransformException {
		// TODO EP may be inverting the transformation many times.
		t.inverseTransform(ptSrc, ptDst);
	}
 	/**
 	 * This method is the hook method of isThisPointIn. 
 	 * @param aPt The point in the shape's reference space.
 	 * @return true if point inside the shape, false otherwise.
 	 */
 	protected abstract boolean isThisPointInHook(Point aPt);

	/**
	 * Sets the center coordinate of the rectangle.
	 * @param aCenter center coordinate of the rectangle 
	 */ 	
 	public void setCenter(Point aCenter) {
 		center.setLocation(aCenter);
		notifyObservers(); 		
 	}

	/**
	 * Sets the center coordinate of the rectangle.
	 * @param aCenter center coordinate of the rectangle 
	 */ 	
	public void setCenter(double centerX, double centerY) {
		center.setLocation(centerX, centerY);
		notifyObservers(); 		
	}
 	
	/**
	 * What is the center coordinate of the disc?
	 * @return center coordinate of the disc
	 */   	
 	public Point getCenter() {
 		return center;
	}

	public Point getTransformedCenter() {
		Point transformed = new Point();
		t.transform(getCenter(), transformed);
		return transformed;
	}
	
	/**
	 * notify all observers when a new event happens
   	 */
	public void notifyObservers(){
		Iterator iter;
	 	iter = observers.iterator();
	 	
	 	while(iter.hasNext()){
	   		((ShapeObserver)iter.next()).update();
	 	}
    }

	/**
	 * add a new observer
	 */
	public void addObserver(ShapeObserver aObserver){
	   observers.add(aObserver);
	}
	
	/**
	 * remove an observer from the list of the observers
	 */
	public void removeObserver(ShapeObserver aObserver){
		 observers.remove(aObserver);
	}
	
	public void setContainer(ShapeContainer sc) {
		parent = sc;
	}
	
	public ShapeContainer getContainer() {
		return parent;
	}
	
	public java.awt.Rectangle getRectangle() {
		return new java.awt.Rectangle(getCenter());
	}

	public java.awt.Rectangle getTransformedRectangle() {
		return new java.awt.Rectangle(getTransformedCenter());
	}

	/**
	 * @param center
	 * @param halfWidth
	 * @param color
	 * @param color2
	 * Points used to highlight the selected object.
	 */
	public static void drawTwoColorPoint(Graphics g, Point center, int halfWidth, Color color, Color color2) {
		// TODO EP cohesion of this method in this class is somehow low. Think of a better place to put it.
		int innerHalfWidth = halfWidth - 1;
		g.setColor(Color.black);
		g.fillRect(center.x-halfWidth, center.y-halfWidth, 2*halfWidth, 2*halfWidth);
		g.setColor(Color.white);
		g.fillRect(center.x-innerHalfWidth, center.y-innerHalfWidth, 2*innerHalfWidth, 2*innerHalfWidth);
	}

	public AffineTransform getAffineTransform() {
		return new AffineTransform(t);	
	}
	
	/**
	 * @param t
	 */
	public void setAffineTransform(AffineTransform newT) {
		t = newT;
		notifyObservers();
	}

	private ShapeContainer parent;
	private List observers = new ArrayList();
	
	// Memento Support.
	
	/**
	 * Creates a memento object to encapsulate this object's state.
	 * @return newly created memento to be stored
	 */
	public MementoIF createMemento() {
		return new ShapeMemento(center, getAffineTransform());
	}
	
	/**
	 * Restores this object's state from the given memento object.
	 * @param memento previously stored memento
	 */
	public void setMemento(MementoIF memento) {
		ShapeMemento m = (ShapeMemento)memento;
		setCenter(m.getCenter());
		setAffineTransform(m.getTransform());
	}

	/** Supports undo. */
	private class ShapeMemento implements MementoIF {
		private final Point memPoint;
		private final AffineTransform memTransform;
		/**
		 * @param p the center point
		 * @param at the affine transform
		 */
		ShapeMemento(Point p, AffineTransform at) {
			memPoint = p;
			memTransform = at;
		}
		Point getCenter() { return memPoint; }
		AffineTransform getTransform() { return memTransform; }
	}
}