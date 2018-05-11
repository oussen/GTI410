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
import java.awt.event.*;
import java.awt.Point;

/**
 * <p>Title: AbstractTransformer</p>
 * <p>Description: Superclass representing a transformation that can be applied to a shape</p>
 * <p>Copyright: Copyright (c) 2003 Mohammed Elghaouat, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.13 $
 */
abstract public class AbstractTransformer {
	
	final static protected int idSelector   = 1;
	final static protected int idRectangularRegionSelector = idSelector + 1;
	final static protected int ID_FLOODER   = idRectangularRegionSelector + 1;
	final static protected int ID_FILTER    = ID_FLOODER + 1;
	final static protected int ID_CURVES    = ID_FILTER + 1;	
	final static protected int ID_TRANSLATE = ID_CURVES + 1;
	final static protected int ID_ROTATE    = ID_TRANSLATE + 1;
	final static protected int ID_SCALE     = ID_ROTATE + 1;
	final static protected int ID_SHEAR	    = ID_SCALE + 1;
	final static protected int ID_DELETE	= ID_SHEAR + 1;
	
	final static protected int idTransformersIndex = ID_DELETE + 1;
	
	static protected Point selectionOrigine = new Point();

	/**
	 * Default constructor
	 */
	public AbstractTransformer() {
	}
    
    public void activate() {
    }
    
	/**
	 * Transformer ID getter
	 * */
	public abstract int getID();

	/**
	 * Has mouse been clicked ?
	 * @return mouse clicked status flag
	 */
	protected boolean mouseClicked(MouseEvent e){
		return false;
	}

	/**
	 * Has mouse been released ?
	 * @return mouse released status flag
	 */
	protected boolean mouseReleased(MouseEvent e){
		return false;
	}

	/**
	 * Has mouse been pressed ?
	 * @return mouse pressed status flag
	 */
	protected boolean mousePressed(MouseEvent e){
		return false;
	}

	/**
	 * Has mouse been dragged ?
	 * @return mouse dragged status flag
	 */
	protected boolean mouseDragged(MouseEvent e){
		return false;
	}

	/**
	 * Has mouse moved ?
	 * @return mouse moved status flag
	 */
	protected boolean mouseMove(MouseEvent e){
		return false;
	}
	
	/**
	 * Has a key typed on the keyboard
	 * @return
	 */
	protected boolean keyTyped(KeyEvent e){
		return false;
	}
}