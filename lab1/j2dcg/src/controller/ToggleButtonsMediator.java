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

/**
 * <p>Title: ToggleButtonsMediator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.3 $
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JToggleButton;

public class ToggleButtonsMediator implements ActionListener 
{
	private Vector componentsGroup;
	
	public ToggleButtonsMediator()
	{
	}
	
	public ToggleButtonsMediator(Vector v) 
	{
		componentsGroup = v;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent evt)
	{
		for(int i = 0; i < componentsGroup.size(); i++)
		{
			if ( evt.getSource() != componentsGroup.get(i))
				((JToggleButton)componentsGroup.get(i)).setSelected(false);
		}
	}
}
