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

import java.awt.Point;
import java.util.List;

/**
 * <p>Title: PolylineCurveType</p>
 * <p>Description: ... (CurveType)</p>
 * <p>Copyright: Copyright (c) 2004 Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author Eric Paquette
 * @version $Revision: 1.4 $
 */
public class PolylineCurveType extends CurveType {

	public PolylineCurveType(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see model.CurveType#getNumberOfSegments(int)
	 */
	public int getNumberOfSegments(int numberOfControlPoints) {
		return numberOfControlPoints - 1;
	}

	/* (non-Javadoc)
	 * @see model.CurveType#getNumberOfControlPointsPerSegment()
	 */
	public int getNumberOfControlPointsPerSegment() {
		return 2;
	}

	/* (non-Javadoc)
	 * @see model.CurveType#getControlPoint(int, int)
	 */
	public ControlPoint getControlPoint(List controlPoints, 
	                                    int segmentNumber, int controlPointNumber) {
	    int controlPointIndex = segmentNumber + controlPointNumber;
	    return (ControlPoint)controlPoints.get(controlPointIndex);
	}

	/* (non-Javadoc)
	 * @see model.CurveType#evalCurveAt(java.util.List, int, double)
	 */
	public Point evalCurveAt(List controlPoints, double t) {
		boolean evalMatrix = true;
		if (evalMatrix){
			List tVector = Matrix.buildRowVector2(t, 1);
			List gVector = Matrix.buildColumnVector2(((ControlPoint)controlPoints.get(0)).getCenter(), 
													 ((ControlPoint)controlPoints.get(1)).getCenter()); 
			Point p = Matrix.eval(tVector, linearMatrix, gVector);
		
			return p;
		} else{
			Point c0 = ((ControlPoint)(controlPoints.get(0))).getCenter();
			Point c1 = ((ControlPoint)(controlPoints.get(1))).getCenter();
		
			int x = (int)Math.round(c0.x+t*(c1.x-c0.x));
			int y = (int)Math.round(c0.y+t*(c1.y-c0.y));
			return new Point(x,y);
		}
	}
	
	private List linearMatrix = 
		Matrix.buildMatrix2( -1, 1,
							  1, 0);
}
