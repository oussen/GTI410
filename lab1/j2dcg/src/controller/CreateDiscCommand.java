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

import model.Disc;
import model.Pixel;
import view.Application;

/**
 * <p>Title: CreateDiscCommand</p>
 * <p>Description: Command for disc creation.</p>
 * <p>Copyright: Copyright (c) 2004 Jean-François Barras, Éric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on: 2004-08-29</p>
 * @version $Revision: 1.2 $
 */
public class CreateDiscCommand
extends Command implements Cloneable {
    
    private final int x, y, radius;
    private final Pixel color;
    
    /**
     * @param x the horiz. coordinate of the disc's center
  	 * @param y the vert. coordinate of the disc's center
  	 * @param color a pixel with color information
  	 * @param radius the radius of the disc
     */
    public CreateDiscCommand(int x, int y, Pixel color, int radius) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.radius = radius;
    }
    
    public Command copy() {
        return new CreateDiscCommand(x, y, color, radius);
    }
    
    // @see controller.Command#execute()
    public void execute() {
		System.out.println("command: disc creation r=" + radius +
                " @ (" + x + "," + y + ")");
        
		Application.getInstance().getActiveDocument().addObject(
		        new Disc(x, y, color, radius));
    }
    
    // @see controller.Command#undo()
    public void undo() {
        mt.setBackMementos();
    }

	private MementoTracker mt = new MementoTracker();

}
