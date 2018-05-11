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

import java.util.Observable;

import controller.Coordinates;

/**
 * 
 * <p>Title: KernelModel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version
 */
public class KernelModel extends Observable{
	/**
	 * 
	 */
	public final static String[] HANDLING_BORDER_ARRAY = {"0", "None", "Copy", "Mirror", "Circular"};
	/**
	 * 
	 */
	public final static String[] CLAMP_ARRAY = {"Clamp 0...255", "Abs and normalize to 255", "Abs and normalize 0 to 255", "Normalize 0 to 255"};
	/**
	 * 
	 */
	public final static String[] FILTER_TYPE_ARRAY = {"Custom", "Mean", "Gaussian", "4-Neighbour Laplacian", "8-Neighbour Laplacian", "Prewitt Horiz", "Prewitt Vert", "Sobel Horiz", "Sobel Vert", "Roberts 45 degrees", "Roberts -45 degrees"};
	/**
	 * 
	 */
	private Coordinates[] _coordinateArray;
	/**
	 * 
	 */
	private Coordinates _center;
	/**
	 * 
	 */
	private float[][] _kernelValues;
	/**
	 * 
	 */
	private int _kernelSize;
	/**
	 * 
	 */
	private String _handlingBorderValue;
	/**
	 * 
	 */
	private String _clampValue;
	
	/**
	 * 
	 * @param kernelSize
	 */
	public KernelModel(int kernelSize){
		_kernelSize 		= kernelSize;
		_kernelValues   	= new float[kernelSize][kernelSize];
		_coordinateArray	= new Coordinates[kernelSize*kernelSize]; 
		
		for (int i = 0, k = 0; i < kernelSize; i++){
			for (int j = 0; j < kernelSize; j++){
				//
				_kernelValues[i][j] = 0;
				
				// 
				_coordinateArray[k] = new Coordinates(i + 1, j + 1);
				k++;
			}
		}
		//
		setCenter(new Coordinates(_kernelSize / 2, _kernelSize / 2));
	}
	/**
	 * 
	 * @return
	 */
	public String getClampValue(){
		return _clampValue;
	}
	/**
	 * 
	 *
	 */
	public void setClampValue(String clampValue){
		_clampValue = clampValue;
	}
	/**
	 * 
	 * @return
	 */
	public String getHandlingBorderValue(){
		return _handlingBorderValue;
	}
	/**
	 * 
	 *
	 */
	public void setHandlingBorderValue(String handlingBorderValue){
		_handlingBorderValue = handlingBorderValue;
	}
	/**
	 * 
	 * @return
	 */
	/*
	public String[] getHandlingBorderValues(){
		return _handlingBorderArray;
	}
	*/
	/**
	 * 
	 * @return
	 */
	public Coordinates[] getCoordinateValues(){
		return _coordinateArray;
	}
	/**
	 * 
	 * @return
	 */
	/*	
	public String[] getClampValues(){
		return _clampArray;
	}
	*/
	/**
	 * 
	 * @param kernelSize
	 * @return
	 */
	public Coordinates getCenter(){
		return _center; 
	}
	/**
	 * 
	 * @param coordinates
	 */
	public void setCenter(Coordinates c) {
		_center = c;
	}
	/**
	 * 
	 * @return
	 */
	public int getSize(){
		return _kernelSize;
	}
	/**
	 *  
	 * @param row
	 * @param column
	 * @return
	 */	
	public float getKernelValue(Coordinates c){
		return _kernelValues[c.getRow() - 1][c.getColumn() - 1];
	}
	/**
	 * 
	 * @param c
	 * @param v
	 */
	public void setKernelValue(Coordinates c, float v){
		_kernelValues[c.getRow() - 1][c.getColumn() - 1] = v;
		setChanged();
		notifyObservers();	
	}
}
