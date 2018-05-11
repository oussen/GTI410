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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.AnchoredTransModel;

import controller.TransformersIndex;

/**
 * <p>Title: ScalingTransformation</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.15 $
 */
public class RotationTransformation extends JPanel{
	/**
	 * 
	 */
	private JLabel angleLabel;
	/**
	 * 
	 */
	private JTextField angleTextField;
	/**
	 * 
	 */	 
	private JLabel relativeToLabel;
	/**
	 * 
	 */
	private JComboBox orientationComboBox;
	/**
	 * 
	 */
	private JButton applyButton;
	/**
	 * 
	 */
	private TransformersIndex ti; 
	/**
	 *
	 *
	 */
	public RotationTransformation(TransformersIndex ti) {
		this.ti = ti;
		//setLayout(new GridLayout(2,2));
		setLayout(new FlowLayout());
		
		angleLabel = new JLabel("Angle (degrees)");
		angleTextField = new JTextField(10);
		
		angleTextField.addFocusListener(
			new FocusListener(){
				public void focusGained(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					String text 	= tf.getText();		
					tf.select(0,text.length());				
				}
				
				public void focusLost(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					float d = Float.parseFloat(tf.getText());
					RotationTransformation.this.ti.getTheRotation().setAngleDegree(d);
				}	
			});
						
		relativeToLabel = new JLabel("Relative to");
		orientationComboBox = new JComboBox(AnchoredTransModel.ORIENTATIONS);
		orientationComboBox.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					JComboBox cb = (JComboBox)ae.getSource();
					int a = cb.getSelectedIndex();
					RotationTransformation.this.ti.getTheRotation().setOrientation(a);
				}	
			});
					
		applyButton = new JButton("Apply");
		applyButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					RotationTransformation.this.ti.getTheRotation().execute();
				}	
			});
		
		setDefaultRotationValue();
					
		add(angleLabel);
		add(angleTextField);
		
		add(relativeToLabel);
		add(orientationComboBox);
		
		add(applyButton);
	}
	
	/**
	 * 
	 *
	 */
	private void setDefaultRotationValue(){
		float a = RotationTransformation.this.ti.getTheRotation().getAngleDegree();
		angleTextField.setText(Float.toString(a));
	}
}
