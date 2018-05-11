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
import model.ShearingTransformationModel;
import view.Application;

/**
 * <p>Title: Shearing</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.12 $
 */
public class Shearing extends AbstractTransformer {
	
	/**
	 * 
	 */
	private float shearAngleDegrees;
	/**
	 * 
	 */
	private int anchor;
	
	/**
	 * 
	 */
	private String shearType;
	
	/**
	 * Default constructor
	 */
	public Shearing() {
		this.setShearType(ShearingTransformationModel.SHEAR_X);
		this.setShearAngleDegree(90);
	}
	
	/**
	 * @return Curves's id as an AbstractTransformer
	 */
	public int getID() { 
		return ID_SHEAR; 
	}
			
	/**
	 * 
	 * @param v
	 */
	public void setShearAngleDegree(float angleDegrees){
		shearAngleDegrees = angleDegrees;
		System.out.println("Shearing : ShearAngleDegrees is now set to " + shearAngleDegrees);
	}
	
	/**
	 * 
	 * @param s
	 */
	public void setShearType(String s){
		shearType = s;
		System.out.println("Shearing : ShearType is now set to " + shearType);
	}
		
	/**
	 * 
	 * @param s
	 */
	public void setOrientation(int anchor){
		this.anchor = anchor;
		System.out.println("Shearing : Orientation is now relative to " +
				           AnchoredTransModel.ORIENTATIONS[anchor]);
	}
	
	/**
	 * @return
	 */
	public float getShearAngleDegrees() {
		return shearAngleDegrees;
	}

	/**
	 * @return
	 */
	public String getShearType() {
		return shearType;
	}
	
	/**
	 * Performs shearing.
	 */
	public void execute(){
		System.out.println("Shearing : Processing command ...");
		Document doc = Application.getInstance().getActiveDocument();
		List selectedObjects = doc.getSelectedObjects();
		Command c;
		
		if (shearType.equalsIgnoreCase(ShearingTransformationModel.SHEAR_X)) {
			c = new ShearXCommand(shearAngleDegrees,anchor,selectedObjects);
		} else {
			c = new ShearYCommand(shearAngleDegrees,anchor,selectedObjects);
		}
		Caretaker.getInstance().execute(c);
	}
}
