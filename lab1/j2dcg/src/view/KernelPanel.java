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

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import model.KernelModel;
import model.ObservableIF;
import model.ObserverIF;
import controller.Coordinates;
import controller.FilteringTransformer;
import controller.TransformersIndex;

/**
 * 
 * <p>Title: KernelPanel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.7 $
 */
public class KernelPanel extends JPanel implements ObservableIF {
	/**
	 * 
	 */
	private KernelModel _model;
	/**
	 * 
	 */
	private int _size;
	/**
	 * 
	 */
	private JPanel _contentPanel;
	/**
	 * 
	 */
	private EditBox[][] _kernelCells;
	/**
	 * 
	 */
	private FilteringTransformer transformer;

	private List observers = new ArrayList();
	
	/**
	 * 
	 * @param model
	 */
	public KernelPanel(TransformersIndex ti){
		setLayout(new CardLayout());
		
		_size  = 3;
		
		_kernelCells  = new EditBox[_size][_size];
		
		_contentPanel = new JPanel();
		_contentPanel.setLayout(new GridLayout(_size, _size)); 
		generateGrid();
		
		//
		add(_contentPanel, "");
		
		//
		transformer = ti.getTheFilter();//new FilteringTransformer();
	}
	/**
	 * 
	 *
	 */
	private void generateGrid(){
		for(int i = 0; i < _size; i++){
			for (int j = 0; j < _size; j++){
				// Get the value from the float array of the model
				
				// Add the edit box in this panel
				_kernelCells[i][j] =  new EditBox(i + 1, j + 1, 0.0f);
				_contentPanel.add(_kernelCells[i][j]);
			}
		}	
	}

	public void setKernelValues(float[][] values) {
		for(int i = 0; i < _size; i++){
			for (int j = 0; j < _size; j++){
				_kernelCells[i][j].setValue(values[i][j]);
			}
		}
		updateTransformerKernel();
	}
	
	/**
	 * add a new observer
	 */
	public void addObserver(ObserverIF aObserver){
	   observers.add(aObserver);
	}
	
	/**
	 * remove an observer from the list of the observers
	 */
	public void removeObserver(ObserverIF aObserver){
		 observers.remove(aObserver);
	}
	
	/**
	 * notify all observers when a new event happens
	 */
	public void notifyObservers(){
		Iterator iter;
		iter = observers.iterator();
	 	
		while(iter.hasNext()){
			((ObserverIF)iter.next()).update();
		}
	}

	/**
	 * 
	 * <p>Title: EditBox</p>
	 * <p>Description: </p>
	 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
	 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
	 * @author unascribed
	 * @version
	 */
	class EditBox extends JTextField{
		/**
		 * 
		 */
		private Coordinates _coordinates;
		/**
		 * 
		 * @param row
		 * @param column
		 */
		public EditBox(int row, int column, float value){
			_coordinates = new Coordinates(row, column);
			setValue(value);
			addFocusListener(new KernelCellChange(_model));
			addActionListener(new KernelCellChange(_model));
		}
		/**
		 * 
		 * @return
		 */
		public float getValue(){
			return Float.parseFloat(getText());
		}
		/**
		 * 
		 *
		 */
		public void setValue(float v){
			setText(Float.toString(v));
		}
		/**
		 * 
		 * @return
		 */
		public Coordinates getCoordinates(){
			return _coordinates;
		}
		/**
		 * 
		 *
		 */
		public void updateTransformer () {
			transformer.updateKernel(_coordinates, getValue());
		}
		
		/**
		 * 
		 * <p>Title: KernelCellChange</p>
		 * <p>Description: </p>
		 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
		 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
		 * @author unascribed
		 * @version
		 */
		class KernelCellChange implements FocusListener, ActionListener{
			KernelModel _model;
	  		
			public KernelCellChange(KernelModel model){
				_model = model;
			}
			 			
			public void focusGained(FocusEvent fe) {
				JTextField tf = (JTextField)fe.getSource();
				String text 	= tf.getText();		
				tf.select(0,text.length());	
			}
			
			public void focusLost(FocusEvent fe) {
				updateTransformerKernel();
			}
			
			public void actionPerformed(ActionEvent ae) {
				updateTransformerKernel();
			}
		 }
	}
	
	private void updateTransformerKernel() {
		for (int i = 0; i < _size; ++i) {
			for (int j = 0; j < _size; ++j) {
				_kernelCells[i][j].updateTransformer();
			}
		}
		notifyObservers();
	}
}