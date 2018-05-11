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
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import model.KernelModel;
import model.ObserverIF;
import controller.TransformersIndex;

/**
 * <p>Title: FilterKernelPanel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.8 $
 */
public class FilterKernelPanel extends JPanel implements ObserverIF {
	/**
	 * 
	 */
	private JPanel _setUpPanel;
	/**
	 * 
	 */
	private KernelPanel _kernelPanel;
	/**
	 * 
	 */
	private JLabel _handlingBorderLabel;
	/**
	 * 
	 */ 
	private JComboBox _handlingBorderComboBox;
	/**
	 * 
	 */
	private JLabel _rangeClampLabel;
	/**
	 * 
	 */
	private JComboBox _clampComboBox;
	/**
	 * 
	 */
	private JLabel _filterTypeLabel;
	/**
	 * 
	 */
	private JComboBox _filterTypeComboBox;
	/**
	 * 
	 */
	private TransformersIndex ti;
	
	public FilterKernelPanel(TransformersIndex ti){
		_setUpPanel  = new JPanel();
		 
		 this.ti = ti;
		 
		_setUpPanel.setLayout(new GridLayout(3, 2));

		_kernelPanel = new KernelPanel(ti);
		_kernelPanel.addObserver(this);
		
		_handlingBorderLabel	= new JLabel("Handling Border"); 
		_handlingBorderComboBox	= new JComboBox(KernelModel.HANDLING_BORDER_ARRAY);	
			
		_rangeClampLabel = new JLabel("Range");
		_clampComboBox   = new JComboBox(KernelModel.CLAMP_ARRAY);
		
		_filterTypeLabel    = new JLabel("Filter Type");
		_filterTypeComboBox = new JComboBox(KernelModel.FILTER_TYPE_ARRAY);
		
		// Initialize the Handling Border combo box to the default value of the handling border combo box
		_handlingBorderComboBox.setSelectedIndex(0);
		//_model.setHandlingBorderValue((String)_handlingBorderComboBox.getSelectedItem());
		
		// Initialize the Handling Border combo box to the default value of the handling border combo box
		_clampComboBox.setSelectedIndex(0);	
		//_model.setClampValue((String)_clampComboBox.getSelectedItem());
		
		_filterTypeComboBox.setSelectedIndex(0);
			
		_handlingBorderComboBox.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					FilterKernelPanel.this.ti.getTheFilter().setBorder((String)_handlingBorderComboBox.getSelectedItem());
				}	
			});
		
		_clampComboBox.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					FilterKernelPanel.this.ti.getTheFilter().setClamp((String)_clampComboBox.getSelectedItem());
				}	
			});
		
		_filterTypeComboBox.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					setFilter((String)_filterTypeComboBox.getSelectedItem());
				}	
			});
		
		_handlingBorderLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		_rangeClampLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		_filterTypeLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		_setUpPanel.add(_handlingBorderLabel);
		_setUpPanel.add(_handlingBorderComboBox);
		_setUpPanel.add(_rangeClampLabel);
		_setUpPanel.add(_clampComboBox);
		_setUpPanel.add(_filterTypeLabel);
		_setUpPanel.add(_filterTypeComboBox);
		
		//
		setLayout(new BorderLayout());
		add(_setUpPanel, BorderLayout.NORTH);
		add(_kernelPanel, BorderLayout.CENTER);
	}
	
	/* (non-Javadoc)
	 * @see model.ObserverIF#update()
	 */
	public void update() {
		// Switch to Custom.
		_filterTypeComboBox.setSelectedIndex(0);
	}
	 
	private void setFilter(String string) {
		int index = 0;
		for (int i = 0; i < KernelModel.FILTER_TYPE_ARRAY.length; ++i) {
			if (string.equals(KernelModel.FILTER_TYPE_ARRAY[i])) {
				index = i;
			}
		}
		switch (index) {
			case 1: // Mean filter
			{
				float meanKernel[][] = {{1, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 2: // Gaussian filter
			{
				float meanKernel[][] = {{2, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 3: // 4-Neighbour Laplacian
			{
				float meanKernel[][] = {{3, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 4: // 8-Neighbour Laplacian
			{
				float meanKernel[][] = {{4, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 5: // Prewitt Horiz
			{
				float meanKernel[][] = {{5, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 6: // Prewitt Vert
			{
				float meanKernel[][] = {{6, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 7: // Sobel Horiz 
			{
				float meanKernel[][] = {{7, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 8: // Sobel Vert
			{
				float meanKernel[][] = {{8, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 9: // Roberts 45 degrees
			{
				float meanKernel[][] = {{9, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 10: // Roberts -45 degrees
			{
				float meanKernel[][] = {{10, 2, 3},
										{4, 5, 6},
										{7, 8, 9}};
				_kernelPanel.setKernelValues(meanKernel);
			} 
			break;
			case 0: // Custom
			default:
			{
				// Do nothing
			}
			break;
		}
		// The following is needed because as we were updated, we automatically switched to Custom.
		_filterTypeComboBox.setSelectedIndex(index);
	}
}