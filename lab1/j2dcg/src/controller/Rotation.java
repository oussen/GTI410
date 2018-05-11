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

import model.AnchoredTransModel;
import model.Document;
import view.Application;

/**
 * <p>Title: Rotation</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.10 $
 */
public class Rotation extends AbstractTransformer {
	/**
	 * 
	 */
	private float rotationAngle;
	
	/**
	 * 
	 */
	private int anchor;
	
	/**
	 * Default constructor
	 */
	public Rotation() {
		this.setAngleDegree(0);
	}
	
	/**
	 * @return Rotation's id as an AbstractTransformer
	 */
	public int getID() { 
		return ID_ROTATE; 
	}
	
	/**
	 * 
	 * @param v
	 */
	public void setAngleDegree(float v){
		rotationAngle = v;
		System.out.println("Rotation : Angle value is set to " + rotationAngle);
	}
	
	/**
	 * 
	 *
	 */
	public float getAngleDegree(){
		return rotationAngle;
	}
	
	/**
	 * 
	 * @param s
	 */
	public void setOrientation(int anchor){
		this.anchor = anchor;
		System.out.println("Rotation : Orientation is now relative to " +
				           AnchoredTransModel.ORIENTATIONS[anchor]);
	}
	
	/**
	 * Performs the rotation.
	 */
	public void execute(){
		System.out.println("Rotation : Processing command ...");
		Document doc = Application.getInstance().getActiveDocument();
		List selectedObjects = doc.getSelectedObjects();
		Command c = new RotateCommand(rotationAngle,anchor,selectedObjects);
		Caretaker.getInstance().execute(c);
	}
	
}