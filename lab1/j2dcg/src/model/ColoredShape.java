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

import controller.MementoIF;

/**
 * <p>Title: ColoredShape</p>
 * <p>Description: Abstract class of all colored shapes.</p>
 * <p>Copyright: Copyright (c) 2003 Eric Paquette, Colin B-B</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.7 $
 */

public abstract class ColoredShape extends Shape implements ShapeObservable, ColoredShapeIF  {
	private Pixel color = new Pixel();

	/**
	 * Center initialized constructor with pixel color.
	 * @param centerX Center's x coordinate
	 * @param centerY Center's y coordinate
	 * @param color color of the shape
	 */	
	public ColoredShape(double centerX, double centerY, Pixel color) {
		super(centerX, centerY);
		setColor(color);
	}	

	/**
	 * Center initialized constructor. Default color is black.
	 * @param centerX Center's x coordinate
	 * @param centerY Center's y coordinate
	 */	
	public ColoredShape(double centerX, double centerY) {
		this(centerX, centerY, 0);
	}
	
	/**
	 * Center initialized constructor with color (ARGB).
	 * @param centerX Center's x coordinate
	 * @param centerY Center's y coordinate
	 * @param colorARGB ARGB color of the shape
	 */		
	public ColoredShape(double centerX, double centerY, int colorARGB) {
		this(centerX, centerY, new Pixel(colorARGB));
	}	
	
	/**
	 * @return The color of the object
	 */
	public Pixel getColor() {
		return color;
	}

	/**
	 * @param pixel The new color for the object
	 */
	public void setColor(Pixel pixel) {
		color = pixel;
		notifyObservers();
	}
	
	// Memento Support.
	
	// @see model.Shape#createMemento()
	public MementoIF createMemento() {
		return new ColoredShapeMemento(super.createMemento(), color);
	}
	
	// @see model.Shape#setMemento()
	public void setMemento(MementoIF memento) {
		ColoredShapeMemento m = (ColoredShapeMemento)memento;
		super.setMemento(m.getParent());
		setColor(m.getColor());
	}

	/** Supports undo. */
	private class ColoredShapeMemento implements MementoIF {
		private final MementoIF memParent;
	    private final Pixel memPixel;
		/**
		 * @param m the parent memento
		 * @param p the pixel, for color information
		 */
	    ColoredShapeMemento(MementoIF m, Pixel p) {
			memParent = m;
		    memPixel = p;
		}
		Pixel getColor() { return memPixel; }
		MementoIF getParent() { return memParent; }
	}
	
}
