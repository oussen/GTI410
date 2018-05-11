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
 * <p>Title: Scaling</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.7 $
 */
public class Scaling extends AbstractTransformer {
	private float xScale;
	private float yScale;
	private int anchor;
	
	/**
	 * Default constructor
	 */
	public Scaling() {
		setXScale(1);
		setYScale(1);
	}
	
	/**
	 * 
	 * @param v
	 */
	public void setXScale(float v){
		xScale = v;
		System.out.println("Scaling : X scale value is now set to " + xScale);
	}
	
	/**
	 * 
	 *
	 */
	public float getXScale(){
		return xScale;
	}
	
	/**
	 * 
	 * @param v
	 */
	public void setYScale(float v){
		yScale = v;
		System.out.println("Scaling : Y scale value is now set to " + yScale);
	}
	
	/**
	 * 
	 * @return
	 */
	public float getYScale(){
		return yScale;
	}
	
	/**
	 * 
	 * @param s
	 */
	public void setOrientation(int anchor){
		this.anchor = anchor;
		System.out.println("Scaling : Orientation is now relative to " +
				           AnchoredTransModel.ORIENTATIONS[anchor]);
	}
	
	/**
	 * @return Scaling's id as an AbstractTransformer
	 */
	public int getID() { return ID_SCALE; }

	/**
	 * Performs scaling.
	 */
	public void execute(){
		System.out.println("Scaling : Processing command ...");
		Document doc = Application.getInstance().getActiveDocument();
		List selectedObjects = doc.getSelectedObjects();
		Command c = new ScaleCommand(xScale,yScale,anchor,selectedObjects);
		Caretaker.getInstance().execute(c);
	}

}