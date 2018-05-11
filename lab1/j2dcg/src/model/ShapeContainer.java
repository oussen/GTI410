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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: ShapeContainer</p>
 * <p>Description: ... (Shape)</p>
 * <p>Copyright: Copyright (c) 2004 Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author Eric Paquette
 * @version $Revision: 1.4 $
 */
public abstract class ShapeContainer extends Shape implements ShapeObserver {

	/**
	 * @param centerX
	 * @param centerY
	 */
	public ShapeContainer(double centerX, double centerY) {
		super(centerX, centerY);
		shapes = new LinkedList();
	}

	/* (non-Javadoc)
	 * @see model.Shape#draw(java.awt.Graphics)
	 */
	public void drawHook(Graphics g) {
		Iterator i = shapes.iterator();
		while (i.hasNext()) {
			Shape s = (Shape)i.next();
			s.draw(g);
		}
	}

	public void addShape(Shape s) {
		s.addObserver(this);
		s.setContainer(this);
		shapes.add(s);
	}
	
	public List getShapes() {
		return shapes;
	}

	/* (non-Javadoc)
	 * @see model.ShapeObserver#update()
	 */
	public void update() {
		notifyObservers();
	}
	
	private List shapes;
}
