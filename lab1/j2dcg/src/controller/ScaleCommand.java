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

import java.util.List;

/**
 * <p>Title: ScaleCommand</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Jean-François Barras, Éric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on: 2004-03-19</p>
 * @version $Revision: 1.2 $
 */
public class ScaleCommand extends AnchoredTransformationCommand {

	/**
	 * @param sx the multiplier to the horizontal size
	 * @param sy the multiplier to the vertical size
	 * @param anchor one of the predefined positions for the anchor point
	 */
	public ScaleCommand(double sx, double sy, int anchor, List aObjects) {
		super(anchor);
		this.sx = sx;
		this.sy = sy;
		objects = aObjects;
	}
	
	/* (non-Javadoc)
	 * @see controller.Command#execute()
	 */
	public void execute() {
		System.out.println("command: scaling x by " + sx +
                           " and y by " + sy + " ; anchored on " + getAnchor() );

		// voluntarily undefined
	}

	/* (non-Javadoc)
	 * @see controller.Command#undo()
	 */
	public void undo() {
		mt.setBackMementos();
	}

	private MementoTracker mt = new MementoTracker();
	private List objects;
	private double sx;
	private double sy;

}