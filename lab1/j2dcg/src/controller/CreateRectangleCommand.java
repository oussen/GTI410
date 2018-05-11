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

import view.Application;
import model.Pixel;
import model.Rectangle;

/**
 * <p>Title: CreateRectangleCommand</p>
 * <p>Description: Command for rectangle creation.</p>
 * <p>Copyright: Copyright (c) 2004 Jean-François Barras, Éric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on: 2004-08-29</p>
 * @version $Revision: 1.1 $
 */
public class CreateRectangleCommand extends Command {

    private final double x, y;
    private final int w, h;
    private final Pixel color;
    
    public CreateRectangleCommand(double x, double y, Pixel color, int w, int h) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.w = w;
        this.h = h;
    }
    
    public Command copy() {
        return new CreateRectangleCommand(x, y, color, w, h);
    }
    
    // @see controller.Command#execute()
    public void execute() {
		System.out.println("command: rectangle creation (" + w + "x" + h +
                ") centered on (" + x + "," + y + ")");
		
  	    Application.getInstance().getActiveDocument().addObject(
    	        new Rectangle(x, y, color, w, h));
    }

    // @see controller.Command#undo()
    public void undo() {
        mt.setBackMementos();
    }
    
    private MementoTracker mt = new MementoTracker();

}
