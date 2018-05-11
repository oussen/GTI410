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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.ObservableIF;
import model.ObserverIF;
import model.Pixel;

class ColorDialogResult extends Object implements ObservableIF {
	private Pixel pixel;
	private boolean accepted = false;
	
	ColorDialogResult(Pixel defaultColor) {
		pixel = defaultColor;
	}
	
	/**
	 * @return
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @return
	 */
	public Pixel getPixel() {
		return pixel;
	}

	/**
	 * @param b
	 */
	public void setAccepted(boolean b) {
		accepted = b;
	}

	/**
	 * @param pixel
	 */
	public void setPixel(Pixel pixel) {
		this.pixel = pixel;
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see model.ObservableIF#addObserver(model.ObserverIF)
	 */
	public void addObserver(ObserverIF observer) {
		observers.add(observer);
	}

	/* (non-Javadoc)
	 * @see model.ObservableIF#removeObserver(model.ObserverIF)
	 */
	public void removeObserver(ObserverIF observer) {
		observers.remove(observer);
	}

	/* (non-Javadoc)
	 * @see model.ObservableIF#notifyObservers()
	 */
	public void notifyObservers() {
		Iterator iter;
		iter = observers.iterator();
	 	
		while(iter.hasNext()){
			((ObserverIF)iter.next()).update();
		}
	}

	private List observers = new ArrayList();
}
