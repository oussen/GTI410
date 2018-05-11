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

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ColoredShapeIF;
import model.DocObserver;
import model.Pixel;
import model.Shape;
import controller.Caretaker;
import controller.ColorChangeCommand;

/**
 * <p>Title: ColorParameter</p>
 * <p>Description: Panel with a button controlling the selected object color.</p>
 * <p>Copyright: Copyright (c) 2004 Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author Eric Paquette
 * @version $Revision: 1.11 $
 */
public class ColorParameter extends JPanel implements DocObserver {
	private BufferedImage img;
	private int size = 40;
	private Pixel pixel = new Pixel();
	
	ColorParameter() {
		add(new JLabel("Color:"));
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		computeImage();
		ImageIcon ii = new ImageIcon(img);
		JButton colorButton = new JButton(ii);
		colorButton.addActionListener(
			new AbstractAction() {
				public void actionPerformed(ActionEvent ae) {
	        		System.out.println("Color change : Processing ...");
	        		Application app = Application.getInstance();
					Pixel p = ColorDialog.getColor(app, pixel, 150);
					if (p!=null) {
						ColorChangeCommand c = new ColorChangeCommand(p,
						        app.getActiveDocument().getSelectedObjects());
		        		Caretaker.getInstance().execute(c);
						app.getActiveDocument().notifySelectionChanged();
					}
				};
			});
		add(colorButton);
	}
	
	private void computeImage() {
		Pixel p = new Pixel();
		p.setRed(pixel.getRed());
		p.setGreen(pixel.getGreen());
		p.setBlue(pixel.getBlue());
		p.setAlpha(255);
		int rgb = p.getARGB();
		for (int i = 0; i<size; ++i) {
			for (int j = 0; j<size; ++j) {
				img.setRGB(i, j, rgb);
			}
		}
	}

	/* (non-Javadoc)
	 * @see Model.DocObserver#docChanged()
	 */
	public void docChanged() {
	}

	/* (non-Javadoc)
	 * @see Model.DocObserver#docSelectionChanged()
	 */
	public void docSelectionChanged() {
		List list = Application.getInstance().getActiveDocument().getSelectedObjects();
		// EP If the list contains many objects, it is not clear which color to show.
		// But the user should be able to modify the color and this color be affected to all the objects.
		if (list.iterator().hasNext()) {
			Object ColoredShape = null;
			Shape s = (Shape)list.iterator().next();
			if (s instanceof ColoredShapeIF) {
				ColoredShapeIF cs = (ColoredShapeIF)s;
				pixel.setColor(cs.getColor());
				computeImage();
				repaint();
			}
		}
	}
}
