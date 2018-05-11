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

package view;
import model.*;
import controller.*;

import javax.swing.*;
import java.awt.Graphics;
import java.util.Iterator;

/**
 * <p>Title: Window</p>
 * <p>Description: Class representing a window rendering context (JPanel)</p>
 * <p>Copyright: Copyright (c) 2003 Mohammed Elghaouat, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.11 $
 */
public class Window extends JPanel implements DocObserver{
  	/**
  	 * Default constructor
  	 */
  	public Window(Dispatcher dispatcher) {
    	Application.getInstance().getActiveDocument().addObserver(this);
    	addMouseListener(dispatcher);
    	addMouseMotionListener(dispatcher);
    	addKeyListener(dispatcher);
  	}
  	
  	/**
  	 * JPanel rendering method that displays all the shapes
  	 */
  	public void paint(Graphics g){
	    Shape obj;
	    Rectangle selectionRectangle = Application.getInstance().getActiveDocument().getSelectionRectangle();
	    Iterator iter = Application.getInstance().getActiveDocument().getDocumentObjects().iterator();
	    super.paint(g);

	    if (selectionRectangle != null) {
    		selectionRectangle.drawHook(g);
    	}
    
    	while (iter.hasNext()) {
      		obj = (Shape)iter.next();
      		obj.draw(g);
    	}
    	
    	iter = Application.getInstance().getActiveDocument().getSelectedObjects().iterator();
    	while (iter.hasNext()) {
    		obj = (Shape)iter.next();
    		obj.drawSelectionMarker(g);
    	}
  	}
  	
  	/**
  	 * Document has changed event management method.
  	 */
  	public void docChanged() {
   		repaint();
  	}
  
  	/**
  	 * Document selection has changed event management method.
  	 */
  	public void docSelectionChanged() {
  	}
}