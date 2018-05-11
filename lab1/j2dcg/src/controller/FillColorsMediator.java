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

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import view.Application;
import view.ColorDialog;

import model.Pixel;

/**
 * <p>Title: FillColorsMediator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Eric Paquette </p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * <p>Created on 2004-01-27</p>
 * @author unascribed
 * @version $Revision: 1.4 $
 */
public class FillColorsMediator {
	private Pixel fill = new Pixel();
	private Pixel border = new Pixel();
	private int size = 40;
	private BufferedImage imgFill;
	private BufferedImage imgBorder;
	private ImageLineFiller filler;
	private JButton fillColorButton;
	private JButton borderColorButton;
	
	public FillColorsMediator(ImageLineFiller filler, JButton fillColorButton, JButton borderColorButton) {
		this.filler = filler;
		
		fill = filler.getFillColor();
		border = filler.getBorderColor();
		
		this.fillColorButton = fillColorButton;
		imgFill = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		computeImage(fill, imgFill);
		fillColorButton.setIcon(new ImageIcon(imgFill));
		fillColorButton.addActionListener(
			new AbstractAction() {
				public void actionPerformed(ActionEvent ae) {
					Application app = Application.getInstance();
					Pixel p = ColorDialog.getColor(app, fill, 150);
					setFillColor(p);
				};
			});
		
		this.borderColorButton = borderColorButton;
		imgBorder = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		computeImage(border, imgBorder);
		borderColorButton.setIcon(new ImageIcon(imgBorder));
		borderColorButton.addActionListener(
			new AbstractAction() {
				public void actionPerformed(ActionEvent ae) {
					Application app = Application.getInstance();
					Pixel p = ColorDialog.getColor(app, border, 150);
						setBorderColor(p);
				};
			});
	}
	
	private void setFillColor(Pixel color) {
		if (color == null) return;
		fill = color;
		filler.setFillColor(fill);
		computeImage(fill, imgFill);
		fillColorButton.repaint();
	}
	
	private void setBorderColor(Pixel color) {
		if (color == null) return;
		border = color;
		filler.setBorderColor(border);
		computeImage(border, imgBorder);
		borderColorButton.repaint();
	}
	
	private void computeImage(Pixel color, BufferedImage img) {
		int rgb = color.getARGB();
		for (int i = 0; i<size; ++i) {
			for (int j = 0; j<size; ++j) {
				img.setRGB(i, j, rgb);
			}
		}
	}
}
