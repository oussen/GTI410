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
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>Title: ColorSlider</p>
 * <p>Description: ... (JPanel)</p>
 * <p>Copyright: Copyright (c) 2003 Mohammed Elghaouat, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.6 $
 */
public class ColorSlider extends JPanel implements ColorSliderArrowObserver {
	private List observers = new LinkedList();
	private int imageWidth;
	private int arrowWidth = 10;
	private ColorSliderArrow colorSliderArrow;
	
	ColorSlider(String text, int v, Image img) {
		colorSliderArrow = new ColorSliderArrow(v, img, arrowWidth);
		colorSliderArrow.addObserver(this);
		
		setLayout(new FlowLayout(FlowLayout.LEADING));
		add(new JLabel(text));
		add(colorSliderArrow);
	}

	public void update(Image img) {
		repaint();
	}
	
	public void update(int v) {
		notifyObservers();
	}
	
	public void addObserver(SliderObserver o) {
		observers.add(o);
	}
	
	public void setValue(int v){
		// make sure that the actual arrow value is not the one
		// sent to this setValue methods
		if (colorSliderArrow.getValue() != v){
			colorSliderArrow.setValue(v);			
		}
	}
	
	public int getValue() {
		return colorSliderArrow.getValue();
	}
	
	private void notifyObservers() {
		Iterator i = observers.iterator();
		while (i.hasNext()) {
			SliderObserver o = (SliderObserver)i.next();
			o.update(this, getValue());
		}
	}
}

interface ColorSliderArrowObserver {
	void update(int value);
}

class ColorSliderArrow extends JPanel implements MouseListener, MouseMotionListener {
	private Image img;
	private int value;
	private int imageWidth;
	private int imageHeight;
	private int arrowWidth;
	private List observers = new LinkedList();
	
	ColorSliderArrow(int value, Image img, int arrowWidth) {
		this.img = img;
		this.value = value;
		imageWidth = img.getWidth(null);
		imageHeight = img.getHeight(null);
		this.arrowWidth = arrowWidth;
		Dimension d = new Dimension(imageWidth + arrowWidth, imageHeight + getArrowHeight());
		setSize(d);
		setPreferredSize(d);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param d
	 */
	public void setValue(int d) {
		value = d;
	}
	
	/**
	 * JPanel rendering method that displays all the shapes
	 */
	public void paint(Graphics g){
		int top    = 0;
		int bottom = getArrowHeight();
		int center = (int)(imageWidth * ((double)getValue()/255.0)) + arrowWidth/2;
		int left   = center - arrowWidth / 2;		
		int right  = center + arrowWidth / 2;		
		Polygon p  = new Polygon();
		
		p.addPoint(left, top);
		p.addPoint(right, top);
		p.addPoint(center, bottom);
		g.clearRect(0, 0, imageWidth + arrowWidth, imageHeight + getArrowHeight());
		g.setColor(Color.black);
		g.fillPolygon(p);
		g.drawImage(img, arrowWidth / 2, getArrowHeight(), null);
	}
	
	public void addObserver(ColorSliderArrowObserver o) { 
		observers.add(o);
	}

	private int getArrowHeight() {
		return arrowWidth / 2;
	}

	private void notifyObservers() {
		Iterator i = observers.iterator();
		while (i.hasNext()) {
			ColorSliderArrowObserver o = (ColorSliderArrowObserver)i.next();
			o.update(getValue());
		}
		repaint();
	}
	
	private void computeArrowMovement(MouseEvent me) {
		int cursorX = me.getX() - arrowWidth / 2;
		if (cursorX < 0) {
			cursorX = 0;
		}
		if (cursorX > imageWidth) {
			cursorX = imageWidth;
		}
		int newValue = (int)((double)cursorX / (double)imageWidth * 255.0); 
		if (newValue != value) {
			value = newValue;
			notifyObservers();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent arg0) {
		computeArrowMovement(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0) {
		computeArrowMovement(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent arg0) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0) {
	}
}

class ColorSliderImage extends JPanel {
	private Image img;
	private int arrowWidth;
	ColorSliderImage(Image img, int arrowWidth) {
		this.img = img;
		this.arrowWidth = arrowWidth;
		setSize(img.getWidth(null) + arrowWidth, img.getHeight(null));
		setPreferredSize(new Dimension(img.getWidth(null) + arrowWidth, img.getHeight(null)));
	}
	
	/**
	 * JPanel rendering method that displays the image of the interpolated colors.
	 */
	public void paint(Graphics g){
		g.drawImage(img, arrowWidth / 2, 0, null);
	}
}
