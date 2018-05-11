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

import java.awt.event.KeyEvent;

/**
 * <p>Title: Translation</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version 
 */
public class Deletion extends AbstractTransformer {
	/**
	 * Default constructor
	 */
	public Deletion() {
	}
	
	/**
	 * @return Deletion's id as an AbstractTransformer
	 */
	public int getID() { return ID_DELETE; }
	
	/**
	 * Has a key typed on the keyboard
	 * @return
	 */
	protected boolean keyTyped(KeyEvent e){
		System.out.println("key has been typed !");
		return false;
	}
}
