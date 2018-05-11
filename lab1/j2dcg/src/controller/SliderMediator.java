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

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>Title: SliderMediator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.3 $
 */

public class SliderMediator implements ChangeListener 
{
	private JLabel hLabel;
	private JSlider hSlider;
	private JLabel sLabel;
	private JSlider sSlider;
	private JLabel vLabel;
	private JSlider vSlider;
	private ImageLineFiller filler;
	
	public SliderMediator(JSlider hSlider, JLabel hLabel,
						  JSlider sSlider, JLabel sLabel,
						  JSlider vSlider, JLabel vLabel,
						  ImageLineFiller filler) 
	{
		this.hSlider = hSlider;
		this.hLabel = hLabel;
		this.sSlider = sSlider;
		this.sLabel = sLabel;
		this.vSlider = vSlider;
		this.vLabel = vLabel;
		this.filler = filler;
		
		hSlider.setValue(filler.getHueThreshold());
		hLabel.setText(Integer.toString(hSlider.getValue()));
		sSlider.setValue(filler.getSaturationThreshold());
		sLabel.setText(Integer.toString(sSlider.getValue()));
		vSlider.setValue(filler.getValueThreshold());
		vLabel.setText(Integer.toString(vSlider.getValue()));
		
		hSlider.addChangeListener(this);
		sSlider.addChangeListener(this);
		vSlider.addChangeListener(this);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce) {
		JSlider slider = (JSlider)ce.getSource();
		if (slider == hSlider) {
			hLabel.setText(Integer.toString(slider.getValue()));
			filler.setHueThreshold(slider.getValue());
		} else if (slider == sSlider) {
			sLabel.setText(Integer.toString(slider.getValue()));
			filler.setSaturationThreshold(slider.getValue());
		} else if (slider == vSlider) {
			vLabel.setText(Integer.toString(slider.getValue()));
			filler.setValueThreshold(slider.getValue());
		} 
	}
}
