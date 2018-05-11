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
import java.util.*;
import java.awt.Point;
import java.util.Iterator;

/**
 * <p>Title: Document</p>
 * <p>Description: Class that represents an implementation of a observable document</p>
 * <p>Copyright: Copyright (c) 2003 Mohammed Elghaouat, Eric Paquette, Colin B-B</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.13 $
 */
public class Document implements DocObservable,ShapeObserver{
	private LinkedList allObject;
  	private List selectedObject;
  	private List docObservers;
  	private Rectangle selectionRectangle;

  	/**
  	 * Default constructor.
  	 */
 	public Document() {
    	allObject      = new LinkedList();
    	selectedObject = new ArrayList();
    	docObservers   = new ArrayList();
		makeSelectionRectangle();
 	}

	/**
	 * Is the specified point in any shape ?
	 * @param aPt point coordinates
	 * @return point contained inside a shape flag
	 */
 	public boolean isThisPointInAnyShape(Point aPt){
	    Shape shape;
    	Iterator iterator = allObject.iterator();//return false;

    	while (iterator.hasNext()) {
      		shape = (Shape)iterator.next();

      		if(shape.isThisPointIn(aPt)){
        		return true;
      		}
    	}

   		return false;
 	}

	/**
	 * Add an object to the document.
	 * @param aObj object to add
	 */
 	public void addObject(Shape aObj) {
    	allObject.add(aObj);
        ((ShapeObservable)aObj).addObserver(this);
     	notifyDocChanged();
 	}

 	/**
 	 * Add an observer to the document.
 	 * @param aDocObserver observer to add
 	 */
 	public void addObserver(DocObserver aDocObserver) {
    	docObservers.add(aDocObserver);
 	}

 	/**
 	 * Remove an observer from the document.
 	 * @param aDocObserver observer to remove
 	 */
 	public void removeObserver(DocObserver aDocObserver) {
    	docObservers.remove(aDocObserver);
 	}

	/**
	 * Notify observers that the document has changed.
	 */
 	public void notifyDocChanged() {
    	Iterator iter = docObservers.iterator();

    	while (iter.hasNext()) {
      		((DocObserver)iter.next()).docChanged();
    	}
  	}

	/**
	 * Notify observers that the document has changed.
	 */
	public void notifySelectionChanged() {
		Iterator iter = docObservers.iterator();

		while (iter.hasNext()) {
			((DocObserver)iter.next()).docSelectionChanged();
		}
	}

 	/**
 	 * Add the selected shape to the document.
 	 * @param aObj shape object to add
 	 */
 	public void addSelectedObject(Shape aObj) {
    	selectedObject.add(aObj);
    	// EP This will cause repeated calls when multi selecting...
		notifySelectionChanged();
 	}

 	/**
 	 * Clear the selected objects list.
 	 */
 	public void clearSelectedObjectList() {
   		selectedObject.clear();
   		notifySelectionChanged();
 	}

 	/**
 	 * Returns the list of selected objects.
 	 * @return list of selected objects
 	 */
 	public List getSelectedObjects() {
    	return selectedObject;
 	}

 	/**
 	 * Returns the list of objects associated with the document.
 	 * @return list of objects associated with the document
 	 */
 	public List getDocumentObjects() {
   		return allObject;
 	}

 	/**
 	 * Create the selection rectangle.
 	 */
 	public void makeSelectionRectangle() {
   		selectionRectangle = new Rectangle(0,0,0,0);
 	}

 	/**
 	 * Delete the selection rectangle.
 	 */
 	public void deleteSelectionRectangle() {
 		// TODO EP This seems odd, why are we creating a rectangle here?
  		selectionRectangle = new Rectangle(0,0,0,0);
 	}

 	/**
 	 * Get the current instance of the selection rectangle.
 	 * @return instance of the selection rectangle
 	 */
 	public Rectangle getSelectionRectangle() {
   		return selectionRectangle;
 	}

    /**
     * Update the document after a change in one of its Shapes.
     */
     public void update(){
        notifyDocChanged();
     }
}