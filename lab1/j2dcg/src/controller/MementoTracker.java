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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.Shape;

/**
 * <p>Title: MementoTracker</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on: 2004-03-15</p>
 * @author Eric Paquette
 * @version $Revision: 1.3 $
 */
public class MementoTracker {

	/**
	 * @param shape
	 */
	public void addMememto(Shape shape) {
		shapes.add(shape);
		mementos.add(shape.createMemento());
	}

	/**
	 * 
	 */
	public void setBackMementos() {
		Iterator iShape = shapes.iterator();
		Iterator iMemento = mementos.iterator();
		while (iShape.hasNext()) {
			Shape shape = (Shape)iShape.next();
			MementoIF memento = (MementoIF)iMemento.next();
			shape.setMemento(memento); 
		}
	}
	
	private List shapes = new LinkedList();
	private List mementos = new LinkedList();
}
