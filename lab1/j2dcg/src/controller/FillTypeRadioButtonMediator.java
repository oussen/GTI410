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
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 * <p>Title: RadioButtonsMediator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.1 $
 */

public class FillTypeRadioButtonMediator implements ActionListener{
	/**
	 * 
	 */
	private JRadioButton rbBoundaryFill;
	/**
	 *
	 */
	private JRadioButton rbFloodFill;
	/**
	 * 
	 */
	private ButtonGroup fillRadioButtonGroup;
	/**
	 * 
	 */
	private ImageLineFiller filler;
	
	/**
	 * 
	 * @param rbBoundaryFill
	 * @param rbFloodFill
	 * @param filler
	 */
	public FillTypeRadioButtonMediator(JRadioButton rbBoundaryFill, JRadioButton rbFloodFill, ImageLineFiller filler) {
		this.rbBoundaryFill = rbBoundaryFill;
		this.rbFloodFill 	= rbFloodFill;
		this.filler 		= filler;
		
		fillRadioButtonGroup = new ButtonGroup();
		fillRadioButtonGroup.add(rbBoundaryFill);
		fillRadioButtonGroup.add(rbFloodFill);
		
		rbBoundaryFill.addActionListener(this);
		rbFloodFill.addActionListener(this);
	
		if (filler.isFloodFill()) {
			rbFloodFill.setSelected(true);
		} else {
			rbBoundaryFill.setSelected(true);
		}
	}
	
	/**
	 * 
	 */
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource() == rbBoundaryFill) {
			rbFloodFill.setSelected(false);
			filler.setFloodFill(false);
		} else if (ae.getSource() == rbFloodFill) {
			rbBoundaryFill.setSelected(false);
			filler.setFloodFill(true);
		}
	}
}
