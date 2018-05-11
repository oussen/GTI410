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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import controller.MementoIF;

/**
 * <p>Title: Curve</p>
 * <p>Description: ... (ShapeContainer)</p>
 * <p>Copyright: Copyright (c) 2004 Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author Eric Paquette
 * @version $Revision: 1.15 $
 */
public class Curve extends ShapeContainer implements ColoredShapeIF {

	/**
	 * @param centerX
	 * @param centerY
	 */
	public Curve(double centerX, double centerY) {
		// The center is meaningless for curves.
		super(centerX, centerY);
		boldStroke = new BasicStroke(3);
		resetCurrentControlPoints();
		recomputeLineSegments();
		color.setRed(64);
		color.setGreen(255);
		color.setBlue(64);
	}

	/**
	 * @param point
	 */
	public void addPoint(ControlPoint point) {
		addShape(point);
		recomputeLineSegments();
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see model.Shape#draw(java.awt.Graphics)
	 */
	public void drawHook(Graphics g) {
		drawCurve(g);
		super.drawHook(g);
	}

	/**
	 * @param g
	 */
	private void drawCurve(Graphics g) {
		g.setColor(getColor().toColor());
		Graphics2D g2d = (Graphics2D)g;
		Line2D.Double line = new Line2D.Double();
		Iterator segment = lineSegments.iterator();
		Point p0 = new Point(), p1;
		while (segment.hasNext()){
			List sections = ((List)segment.next());
			boolean bold = true;
			p1 = (Point)sections.get(0);
			drawLineStart(g, p1, (Point)sections.get(1));
			for (int i = 1; i < sections.size(); ++i){
				p0 = p1;
				p1 = (Point)sections.get(i);
				line.setLine(p0.x, p0.y, p1.x, p1.y);
				if (bold){
					Color initialColor = g.getColor();
					Stroke initialStroke = g2d.getStroke();
					g.setColor(Color.black);
					g2d.setStroke(boldStroke);
					g2d.draw(line);
					g2d.setStroke(initialStroke);
					g2d.setColor(initialColor);
				}
				g2d.draw(line);
				bold = !bold;
			}
			drawLineEnd(g, p0, p1);
		}
	}

	/**
	 * @param p0
	 * @param p1
	 */
	private void drawLineEnd(Graphics g, Point p0, Point p1) {
		double p01x = p1.x-p0.x;
		double p01y = p1.y-p0.y;
		double norm = Math.sqrt(p01x*p01x + p01y*p01y);
		double perpx = -p01y / norm * 6;
		double perpy = p01x / norm * 6;
		
		g.drawLine((int)(p1.x+perpx), (int)(p1.y+perpy), (int)(p1.x-perpx), (int)(p1.y-perpy));
	}

	/**
	 * @param p0
	 * @param point
	 */
	private void drawLineStart(Graphics g, Point p0, Point p1) {
		double p01x = p1.x-p0.x;
		double p01y = p1.y-p0.y;
		double norm = Math.sqrt(p01x*p01x + p01y*p01y);
		double perpx = -p01y / norm * 6;
		double perpy = p01x / norm * 6;
		
		g.drawLine((int)(p0.x+perpx), (int)(p0.y+perpy), (int)(p0.x-perpx), (int)(p0.y-perpy));
	}

	/* (non-Javadoc)
	 * @see model.Shape#isThisPointIn(java.awt.Point)
	 */
	public boolean isThisPointInHook(Point aPt) {
		Iterator segment = lineSegments.iterator();
		Point p0, p1;
		while (segment.hasNext()){
			List sections = ((List)segment.next());
			p0 = (Point)sections.get(0);
			for (int i = 1; i < sections.size(); ++i){
				p1 = (Point)sections.get(i);
				double d = computePointToLineDistance(p0, p1, aPt);
				if (d <= SELECTION_THICKNESS)
					return true;
				p0 = p1;
			}
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public int getNumberOfSections() {
		return numberOfSections;
	}

	/**
	 * @param i
	 */
	public void setNumberOfSections(int i) {
		numberOfSections = i;
		recomputeLineSegments();
		notifyObservers();
	}

    public void recomputeLineSegments() {
    	// This is an exceptional case where we need to transform the points while we are not displaying them.
    	// This is needed because on display we display the line sections that are computed from the points, so the 
    	// sections will only be affected if they are computed from the transformed control points.
    	transformedControlPoints.clear();
    	Iterator it = getShapes().iterator();
    	while (it.hasNext()) {
    		ControlPoint cp = (ControlPoint)it.next();
    		Point transformedPoint = new Point();
    		cp.getAffineTransform().transform(cp.getCenter(), transformedPoint);
			ControlPoint transformedControlPoint = new ControlPoint(transformedPoint.x, transformedPoint.y);
    		transformedControlPoints.add(transformedControlPoint);
    	}
    	
    	lineSegments.clear();
    	for (int i = 0; i < curveType.getNumberOfSegments(getShapes().size()); ++i){
    		lineSegments.add(computeLineSections(i));
    	}
    	
    	// Recompute bouding box from segments.
		int minX, minY, maxX, maxY;
    	if (!lineSegments.isEmpty() && !((List)lineSegments.get(0)).isEmpty()) {
			Point p = (Point)(((List)lineSegments.get(0)).get(0));
			minX = maxX = p.x;
			minY = maxY = p.y;
    	} else {
    		// Not enough segments / sections.
    		return;
    	}
    	Iterator segmentsIterator = lineSegments.iterator(); 
    	while (segmentsIterator.hasNext()) {
    		Iterator sectionsIterator = ((List)segmentsIterator.next()).iterator(); 
    		while (sectionsIterator.hasNext()) {
    			Point p = (Point)sectionsIterator.next();
    			if (p.x<minX) minX = p.x;
    			if (p.x>maxX) maxX = p.x;
				if (p.y<minY) minY = p.y;
				if (p.y>maxY) maxY = p.y;
    		}
    	}
    	rectangle = new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }
    
    public List computeLineSections(int segmentNumber){
    	List sections = new ArrayList(numberOfSections + 1);
    	double t = curveType.getMinT(segmentNumber);
    	double deltaT = (curveType.getMaxT(segmentNumber)-curveType.getMinT(segmentNumber)) /
    	                numberOfSections;
    	for (int i = 0; i < curveType.getNumberOfControlPointsPerSegment(); ++i){
    		currentControlPoints.set(i, curveType.getControlPoint(transformedControlPoints, segmentNumber, i));
    	}
    	for (int i = 0; i < numberOfSections; ++i){
    		sections.add(curveType.evalCurveAt(currentControlPoints, t));
    		t += deltaT;
    	}
    	sections.add(curveType.evalCurveAt(currentControlPoints, curveType.getMaxT(segmentNumber)));
    	return sections;
    }
    
    public void update() {
    	recomputeLineSegments();
    	super.update();
    }
    
	// @see model.ColoredShapeIF#getColor()
	public Pixel getColor() {
		return color;
	}
	
	// @see model.ColoredShapeIF#setColor(model.Pixel)
	public void setColor(Pixel pixel) {
		color = pixel;
		notifyObservers();
	}
	
	public static double computePointToLineDistance(Point p0, Point p1, Point p) {
		double v01x = p1.x-p0.x;
		double v01y = p1.y-p0.y;
		double v0px = p.x-p0.x;
		double v0py = p.y-p0.y;
		double nv01_sq = v01x*v01x + v01y * v01y;
		double dot = (v01x*v0px + v01y*v0py) / nv01_sq;
		if (dot < 0) {
			return Math.sqrt(v0px*v0px + v0py * v0py);
		} else if (dot > 1) {
			double v1px = p.x-p1.x;
			double v1py = p.y-p1.y;
			return Math.sqrt(v1px*v1px + v1py * v1py);
		} else {
			double vpx = v0px - v01x * dot;
			double vpy = v0py - v01y * dot;  
			return Math.sqrt(vpx*vpx + vpy * vpy);
		}
	}
	
	public void setCurveType(CurveType ct) {
		curveType = ct;
		resetCurrentControlPoints();
		recomputeLineSegments();
		notifyObservers();
	}

	public String getCurveType() {
		for (int i = 0; i < CurvesModel.CTRL_POINTS_AND_CURVES_VALUES.length; ++i) {
			if (curveType.getName().equals(CurvesModel.CTRL_POINTS_AND_CURVES_VALUES[i])) {
				return curveType.getName();
			}
		}
		return null;
	}
	
	private void resetCurrentControlPoints() {
		currentControlPoints = new ArrayList(curveType.getNumberOfControlPointsPerSegment());
		ControlPoint cp = new ControlPoint(0,0);
		for (int i = 0; i <= curveType.getNumberOfControlPointsPerSegment(); ++i){
			currentControlPoints.add(cp);
		}
	}
	
	// @see model.Shape#getRectangle()
	public Rectangle getRectangle() {
		return rectangle;
	}

	// right hand side comment refers to whether or not it is in the memento..
	public static final int DEFAULT_NUMBER_OF_SECTIONS = 10;
	private int numberOfSections = DEFAULT_NUMBER_OF_SECTIONS;             //yes
	private CurveType curveType =new PolylineCurveType(CurvesModel.LINEAR);//yes
	private ArrayList currentControlPoints;                                //yes
	private Pixel color =new Pixel();                                     //fix?
	private List lineSegments =new LinkedList();                        //derive
	private java.awt.Rectangle rectangle =new java.awt.Rectangle();     //derive
	private List transformedControlPoints =new LinkedList();            //derive
	private Stroke boldStroke;                                              //no
	
	// @see model.Shape#createMemento()
    public MementoIF createMemento() {
        return new CurveMemento(super.createMemento(),
                numberOfSections, curveType, currentControlPoints, color);
    }
    
    // @see model.Shape#setMemento(controller.MementoIF)
    public void setMemento(MementoIF memento) {
        CurveMemento m = (CurveMemento)memento;
        setNumberOfSections(m.getSections());
        setCurveType(m.getType());
        resetCurrentControlPoints();
        setColor(m.getColor());
        Iterator i = m.getPoints().iterator();
        while (i.hasNext()) {
            addPoint((ControlPoint)i.next());
        }
        recomputeLineSegments();
        super.setMemento(m.getParent());
    }
    
    /** Supports undo. */
    private static class CurveMemento implements MementoIF {
        private final MementoIF parent;
        private final int sections;
        private final CurveType type;
        private final ArrayList points;
        private final Pixel color;
        CurveMemento(MementoIF parent, int sections,
                CurveType type, ArrayList points, Pixel color) {
            
            this.parent = parent;
            this.sections = sections;
            this.type = type; // TODO should copy, maybe
            this.points = new ArrayList(points); // copied
          this.color = color;
        }
        MementoIF getParent() { return parent; }
        int getSections() { return sections; }
        CurveType getType() { return type; }
        ArrayList getPoints() { return points; }
        Pixel getColor() { return color; }
    }
	
}
