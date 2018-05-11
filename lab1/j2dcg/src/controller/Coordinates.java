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

/**
 * 
 * <p>Title: Coordinates</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.2 $
 */
public class Coordinates {
	/**
	 * 
	 */
	private int _row;
	/**
	 * 
	 */
	private int _column;
	
	/**
	 * 
	 * @param row
	 * @param column
	 */
	public Coordinates(int row, int column){
		_row 	= row;
		_column = column;			
	}
	/**
	 * 
	 */
	public String toString(){
		return "(" + Integer.toString(getRow()) + "," + Integer.toString(getColumn()) + ")";
	}
	/**
	 * 
	 * @return
	 */
	public int getRow(){
		return _row;
	}
	/**
	 * 
	 * @return
	 */
	public int getColumn(){
		return _column;
	}
}
