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


import model.ImageX;
import view.Application;

/**
 * <p>Title: CreateDiscCommand</p>
 * <p>Description: Command for imagex creation.</p>
 * <p>Copyright: Copyright (c) 2004 Jean-François Barras, Éric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on: 2004-08-29</p>
 * @version $Revision: 1.3 $
 */
public class CreateImageCommand extends Command {

    private String filename;
    
    public CreateImageCommand(String filename) {
        this.filename = filename;
    }
    
    public Command copy() {
        return new CreateImageCommand(filename);
    }
    
    public void setFilename(String filename) {
    	this.filename = filename;
    }
    
    // @see controller.Command#execute()
    public void execute() {
        Application.getInstance().getActiveDocument().addObject(
	            new ImageX(filename));
    }

    // @see controller.Command#undo()
    public void undo() {
        mt.setBackMementos();
    }

	private MementoTracker mt = new MementoTracker();
}