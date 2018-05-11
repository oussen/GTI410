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
import java.util.List;

import model.ColoredShapeIF;
import model.Pixel;
import model.Shape;

/**
 * <p>Title: CreateDiscCommand</p>
 * <p>Description: Command for appling color changes to multiple objects.</p>
 * <p>Copyright: Copyright (c) 2004 Jean-François Barras, Éric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on: 2004-08-29</p>
 * @version $Revision: 1.2 $
 */
public class ColorChangeCommand extends Command {

    private final Pixel color;
    private List objects;
    
    public ColorChangeCommand(Pixel color, List objects) {
        this.color = color;
        this.objects = objects;
    }
    
    // @see controller.Command#execute()
    public void execute() {
		Iterator i = objects.iterator();
		while (i.hasNext()) {
			Shape s = (Shape)i.next();
			if (s instanceof ColoredShapeIF) {
				ColoredShapeIF cs = (ColoredShapeIF)s;
				cs.setColor(color);
			}
		}
    }

    // @see controller.Command#undo()
    public void undo() {
        mt.setBackMementos();
    }

	private MementoTracker mt = new MementoTracker();

}