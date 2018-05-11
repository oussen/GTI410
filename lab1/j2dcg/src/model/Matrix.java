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
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title: Matrix</p>
 * <p>Description: ...</p>
 * <p>Copyright: Copyright (c) 2004 Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author Eric Paquette
 * @version $Revision: 1.4 $
 */
public class Matrix {

	public static List buildRowVector2(double v0, double v1) {
		List m = new ArrayList(1);
		List r0 = new ArrayList(2);
		m.add(r0);
		r0.add(new Double(v0));
		r0.add(new Double(v1));
		return m;
	}
	
	public static List buildRowVector4(double v0, double v1, double v2, int v3) {
		List m = new ArrayList(1);
		List r0 = new ArrayList(4);
		m.add(r0);
		r0.add(new Double(v0));
		r0.add(new Double(v1));
		r0.add(new Double(v2));
		r0.add(new Double(v3));
		return m;
	}
	
	public static List buildColumnVector2(Point v0, Point v1) {
		List m = new ArrayList(2);
		List r;
		r = new ArrayList(1);
		m.add(r);
		r.add(v0);
		
		r = new ArrayList(1);
		m.add(r);
		r.add(v1);

		return m;
	}

	public static List buildColumnVector4(Point v0, Point v1, Point v2, Point v3) {
		List m = new ArrayList(4);
		List r;
		r = new ArrayList(1);
		m.add(r);
		r.add(v0);
		
		r = new ArrayList(1);
		m.add(r);
		r.add(v1);

		r = new ArrayList(1);
		m.add(r);
		r.add(v2);

		r = new ArrayList(1);
		m.add(r);
		r.add(v3);

		return m;
	}

	public static List buildMatrix2(double v00, double v01,
									double v10, double v11 ) {
		List m = new ArrayList(2);
		List r;
		r = new ArrayList(2);
		m.add(r);
		r.add(new Double(v00));
		r.add(new Double(v01));
		
		r = new ArrayList(2);
		m.add(r);
		r.add(new Double(v10));
		r.add(new Double(v11));
		
		return m;
	}

	/**
	 * @return
	 */
	public static List buildMatrix4(double v00, double v01, double v02, double v03,
									double v10, double v11, double v12, double v13,
									double v20, double v21, double v22, double v23,
									double v30, double v31, double v32, double v33) {
		List m = new ArrayList(4);
		List r;
		r = new ArrayList(4);
		m.add(r);
		r.add(new Double(v00));
		r.add(new Double(v01));
		r.add(new Double(v02));
		r.add(new Double(v03));
		
		r = new ArrayList(4);
		m.add(r);
		r.add(new Double(v10));
		r.add(new Double(v11));
		r.add(new Double(v12));
		r.add(new Double(v13));
		
		r = new ArrayList(4);
		m.add(r);
		r.add(new Double(v20));
		r.add(new Double(v21));
		r.add(new Double(v22));
		r.add(new Double(v23));
		
		r = new ArrayList(4);
		m.add(r);
		r.add(new Double(v30));
		r.add(new Double(v31));
		r.add(new Double(v32));
		r.add(new Double(v33));
		
		return m;
	}

	/**
	 * @param tVector
	 * @param hermiteMatrix
	 * @param gVector
	 * @return
	 */
	public static Point eval(List t, List m, List g) {
		Point res = new Point();
		int M = ((List)t.get(0)).size();
		// We are computing the multiplication of 1xM * MxM * Mx1 of Point
		// First compute 1xM * MxM = 1xM
		List mul = new ArrayList(M);
		for (int i = 0; i < M; ++i){
			double val = 0;
			for (int j = 0; j < M; j++){
				val += ((Double)((List)t.get(0)).get(j)).doubleValue() * ((Double)((List)m.get(j)).get(i)).doubleValue(); 
			}
			mul.add(new Double(val));
		}
		
		// Then we compute 1xM * Mx1 of Point
		double x = 0, y = 0;
		for (int i = 0; i < M; ++i){
			x += ((Double)mul.get(i)).doubleValue() * ((Point)((List)g.get(i)).get(0)).x;
			y += ((Double)mul.get(i)).doubleValue() * ((Point)((List)g.get(i)).get(0)).y;
		}
		res.setLocation(x,y);
		return res;
	}
}
